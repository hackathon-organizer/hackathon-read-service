package com.hackathonorganizer.hackathonreadservice.team.controller;

import antlr.ASTNULLType;
import com.hackathonorganizer.hackathonreadservice.BaseIntegrationTest;
import com.hackathonorganizer.hackathonreadservice.creator.Role;
import com.hackathonorganizer.hackathonreadservice.creator.TestDataUtils;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.Hackathon;
import com.hackathonorganizer.hackathonreadservice.team.model.Team;
import com.hackathonorganizer.hackathonreadservice.team.model.TeamInvitation;
import com.hackathonorganizer.hackathonreadservice.team.repository.TagRepository;
import com.hackathonorganizer.hackathonreadservice.team.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TeamIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TestDataUtils testDataUtils;

    @Autowired
    private TagRepository tagsRepository;

    @BeforeEach
    void setUp() {
        teamRepository.deleteAll();
    }

    @Test
    void shouldReturnTeamsByHackathonId() throws Exception {
        // given

        Hackathon hackathon = testDataUtils.createHackathon();
        Team team = testDataUtils.createTeam(hackathon);

        // when

        ResultActions resultActions =
                mockMvc.perform(getTeamJsonRequest("?hackathonId=" + hackathon.getId()));

        // then

        resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].name").value(team.getName()))
                .andExpect(jsonPath("$.content[0].description").value(team.getDescription()))
                .andExpect(jsonPath("$.content[0].ownerId").value(team.getOwnerId()));
    }

    @Test
    void shouldReturnTeamsByName() throws Exception {
        // given

        Hackathon hackathon = testDataUtils.createHackathon();
        Team team = testDataUtils.createTeam(hackathon);

        // when

        ResultActions resultActions =
                mockMvc.perform(
                        getTeamJsonRequest(
                                "/search?hackathonId=" + hackathon.getId() + "&name=" + team.getName()));

        // then

        resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].name").value(team.getName()))
                .andExpect(jsonPath("$.content[0].description").value(team.getDescription()))
                .andExpect(jsonPath("$.content[0].ownerId").value(team.getOwnerId()));
    }

    @Test
    void shouldReturnInvitations() throws Exception {
        // given

        Hackathon hackathon = testDataUtils.createHackathon();
        Team team = testDataUtils.createTeam(hackathon);
        TeamInvitation teamInvitation = testDataUtils.createTeamInvitation(team);

        // when

        ResultActions resultActions =
                mockMvc.perform(getAuthorizedTeamJsonRequest(Role.USER, "/invitations/1"));

        // then

        resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].teamName").value(teamInvitation.getTeamName()))
                .andExpect(jsonPath("$[0].toUserId").value(teamInvitation.getToUserId()))
                .andExpect(jsonPath("$[0].invitationStatus")
                        .value(teamInvitation.getInvitationStatus().name()));
    }


    @Test
    void shouldReturnTags() throws Exception {
        // given

        // when

        ResultActions resultActions = mockMvc.perform(getTeamJsonRequest("/tags"));

        // then

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(tagsRepository.findAll().size()));
    }

    @Test
    void shouldReturnTeamById() throws Exception {
        // given

        Hackathon hackathon = testDataUtils.createHackathon();
        Team team = testDataUtils.createTeam(hackathon);

        // when

        ResultActions resultActions = mockMvc.perform(getTeamJsonRequest("/" + team.getId()));

        // then

        resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("$.name").value(team.getName()))
                .andExpect(jsonPath("$.description").value(team.getDescription()))
                .andExpect(jsonPath("$.ownerId").value(team.getOwnerId()));
    }
}
