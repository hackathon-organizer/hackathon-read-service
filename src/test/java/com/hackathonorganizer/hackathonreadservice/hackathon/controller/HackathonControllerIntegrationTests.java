package com.hackathonorganizer.hackathonreadservice.hackathon.service.controller;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.Hackathon;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.HackathonResponse;
import com.hackathonorganizer.hackathonreadservice.repository.HackathonRepository;

import com.hackathonorganizer.hackathonreadservice.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
            .teams(new ArrayList<>())
            .build();

    @Test
    void shouldReturnHackathonById() throws Exception {
        // given

        Hackathon savedHackathon = hackathonRepository.save(mockHackathon);
        String url = "/api/v1/hackathons/" + savedHackathon.getId();

        // when

        ResultActions resultActions = mockMvc.perform(getJsonResponse(url));

        // then

        resultActions.andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$.id").value(savedHackathon.getId()));
    }

    @Test
    void shouldReturnAllHackathons() throws Exception {
        // given

        Hackathon savedHackathon = hackathonRepository.save(mockHackathon);
        String url = "/api/v1/hackathons";

        // when

        ResultActions resultActions = mockMvc.perform(getJsonResponse(url));

        // then

        resultActions.andExpect(status().isOk());
    }


}
