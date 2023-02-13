package com.hackathonorganizer.hackathonreadservice.hackathon.controller;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.Hackathon;

import com.hackathonorganizer.hackathonreadservice.IntegrationTest;
import com.hackathonorganizer.hackathonreadservice.hackathon.repository.HackathonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class HackathonControllerIntegrationTests extends IntegrationTest {

    @Autowired
    private HackathonRepository hackathonRepository;

    @BeforeEach
    void setUp() {
        hackathonRepository.deleteAll();
    }

    private Hackathon mockHackathon = Hackathon.builder()
            .name("Hackathon")
            .description("Desc")
            .organizerInfo("Org Info")
            .isActive(true)
            .eventStartDate(LocalDateTime.now())
            .eventEndDate(LocalDateTime.now().plusDays(2))
            .ownerId(1L)
            .teams(new ArrayList<>())
            .build();

    @Test
    @WithMockUser
    void shouldReturnHackathonById() throws Exception {
        // given

        Hackathon savedHackathon = hackathonRepository.save(mockHackathon);
        String url = "/api/v1/read/hackathons/" + savedHackathon.getId();

        // when

        ResultActions resultActions = mockMvc.perform(getJsonResponse(url));

        // then

        resultActions.andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$.id").value(savedHackathon.getId()));
    }

    @Test
    @WithMockUser
    void shouldReturnAllHackathons() throws Exception {
        // given

        Hackathon savedHackathon = hackathonRepository.save(mockHackathon);
        String url = "/api/v1/read/hackathons";

        // when

        ResultActions resultActions = mockMvc.perform(getJsonResponse(url));

        // then

        resultActions.andExpect(status().isOk());
    }


}
