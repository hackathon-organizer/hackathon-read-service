package com.hackathonorganizer.hackathonreadservice;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathonorganizer.hackathonreadservice.creator.Role;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Collections;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@TestInstance(PER_CLASS)
@ActiveProfiles("test")
@SpringBootTest(classes = HackathonReadServiceApplication.class)
@Slf4j
public class BaseIntegrationTest {

    private static final String POSTGRESQL_IMAGE_VERSION = "postgres:14.4";
    private static final String KC_IMAGE_VERSION = "quay.io/keycloak/keycloak:16.0.0";
    private static final PostgreSQLContainer sqlContainer;
    private static final KeycloakContainer keycloakContainer;

    static {
        sqlContainer = (PostgreSQLContainer) new PostgreSQLContainer(POSTGRESQL_IMAGE_VERSION).withReuse(true);
        sqlContainer.start();

        keycloakContainer = new KeycloakContainer(KC_IMAGE_VERSION).withRealmImportFile("/realm-export.json");
        keycloakContainer.start();
    }

    private final String BASE_URL_HACKATHON = "/api/v1/read/hackathons";
    private final String BASE_URL_TEAM = "/api/v1/read/teams";
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    protected RestTemplate restTemplate = new RestTemplate();

    @DynamicPropertySource
    public static void overrideProperties(final DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
        registry.add("spring.datasource.username", sqlContainer::getUsername);

        registry.add("keycloak.authUrl", keycloakContainer::getAuthServerUrl);
        registry.add("keycloak.username", keycloakContainer::getAdminUsername);
        registry.add("keycloak.password", keycloakContainer::getAdminPassword);

        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloakContainer.getAuthServerUrl() + "/realms/hackathon-organizer");
    }

    protected String getJaneDoeBearer(Role role) {

        try {
            MultiValueMap<String, String> formData = new HttpHeaders();
            formData.put("grant_type", Collections.singletonList("password"));
            formData.put("client_id", Collections.singletonList("hackathon-organizer-client"));
            formData.put("username", Collections.singletonList("janedoe_" + role.name()));
            formData.put("password", Collections.singletonList("qwerty"));

            String result = restTemplate.postForObject(
                    keycloakContainer.getAuthServerUrl() +
                            "/realms/hackathon-organizer/protocol/openid-connect/token", formData, String.class);

            JacksonJsonParser jsonParser = new JacksonJsonParser();

            return "Bearer " + jsonParser.parseMap(result)
                    .get("access_token")
                    .toString();
        } catch (Exception e) {
            log.error("Can't obtain an access token from Keycloak!", e);
        }

        return null;
    }

    protected MockHttpServletRequestBuilder getHackathonJsonRequest(String... urlVariables) throws Exception {
        return get(BASE_URL_HACKATHON + String.join("/", urlVariables))
                .contentType(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder getTeamJsonRequest(String... urlVariables) throws Exception {
        return get(BASE_URL_TEAM + String.join("/", urlVariables))
                .contentType(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder getAuthorizedHackathonJsonRequest(Role userRoleType,
                                                                              String... urlVariables) throws Exception {
        return get(BASE_URL_HACKATHON + String.join("/", urlVariables))
                .header("Authorization", getJaneDoeBearer(userRoleType))
                .contentType(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder getAuthorizedTeamJsonRequest(Role userRoleType,
                                                                         String... urlVariables) throws Exception {
        return get(BASE_URL_TEAM + String.join("/", urlVariables))
                .header("Authorization", getJaneDoeBearer(userRoleType))
                .contentType(MediaType.APPLICATION_JSON);
    }
}
