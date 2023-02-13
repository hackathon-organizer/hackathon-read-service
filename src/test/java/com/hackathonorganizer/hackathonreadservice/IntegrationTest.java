package com.hackathonorganizer.hackathonreadservice;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@TestInstance(PER_CLASS)
@ActiveProfiles("test")
@SpringBootTest(classes = HackathonReadServiceApplication.class)
public class IntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    private static final String IMAGE_VERSION = "postgres:14.4";
    private static final PostgreSQLContainer container;

    static {
        container = new PostgreSQLContainer(IMAGE_VERSION);
        container.withUsername("postgres")
                        .withPassword("qwerty").withDatabaseName("test_db");

        container.start();
        System.out.println(container.getLogs());
    }

    @DynamicPropertySource
    public static void overrideProperties(final DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.liquibase.contexts", () -> "!prod");
    }

    protected MockHttpServletRequestBuilder getJsonResponse(String url) throws Exception {
        return get(url)
                .contentType(MediaType.APPLICATION_JSON);
    }
}
