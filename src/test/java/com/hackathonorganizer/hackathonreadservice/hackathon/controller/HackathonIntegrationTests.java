package com.hackathonorganizer.hackathonreadservice.hackathon.controller;

import com.hackathonorganizer.hackathonreadservice.BaseIntegrationTest;
import com.hackathonorganizer.hackathonreadservice.creator.Role;
import com.hackathonorganizer.hackathonreadservice.creator.TestDataUtils;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.Criteria;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.CriteriaAnswer;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.Hackathon;
import com.hackathonorganizer.hackathonreadservice.hackathon.repository.CriteriaRepository;
import com.hackathonorganizer.hackathonreadservice.hackathon.repository.HackathonRepository;
import com.hackathonorganizer.hackathonreadservice.team.model.Team;
import com.hackathonorganizer.hackathonreadservice.team.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class HackathonIntegrationTests extends BaseIntegrationTest {

    @Autowired
    private HackathonRepository hackathonRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private CriteriaRepository criteriaRepository;

    @Autowired
    private TestDataUtils testDataUtils;

    @BeforeEach
    void setUp() {
        criteriaRepository.deleteAll();
        teamRepository.deleteAll();
        hackathonRepository.deleteAll();
    }

    @Test
    void shouldReturnHackathonById() throws Exception {
        // given

        Hackathon savedHackathon = testDataUtils.createHackathon();

        // when

        ResultActions resultActions = mockMvc.perform(getHackathonJsonRequest("/" + savedHackathon.getId()));

        // then

        resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("$.name").value(savedHackathon.getName()))
                .andExpect(jsonPath("$.id").value(savedHackathon.getId()));
    }

    @Test
    void shouldNotReturnHackathonTeams() throws Exception {
        // given

        Hackathon savedHackathon = testDataUtils.createHackathon();
        Team team = testDataUtils.createTeam(savedHackathon);
        Team team2 = testDataUtils.createTeam(savedHackathon);

        // when

        ResultActions resultActions =
                mockMvc.perform(getHackathonJsonRequest("/" + savedHackathon.getId(), "teams"));

        // then

        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnAllHackathons() throws Exception {
        // given

        Hackathon savedHackathon = testDataUtils.createHackathon();
        Hackathon savedHackathon2 = testDataUtils.createHackathon();

        // when

        ResultActions resultActions = mockMvc.perform(getHackathonJsonRequest());

        // then

        resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].name").value(savedHackathon.getName()))
                .andExpect(jsonPath("$.content[1].name").value(savedHackathon2.getName()));
    }

    @Test
    void shouldReturnHackathonParticipantsIds() throws Exception {
        // given

        Hackathon savedHackathon = testDataUtils.createHackathon();
        savedHackathon.setHackathonParticipantsIds(Set.of(5L, 7L));
        testDataUtils.updateHackathonProperties(savedHackathon);

        // when

        ResultActions resultActions =
                mockMvc.perform(getHackathonJsonRequest("/" + savedHackathon.getId(), "participants"));

        // then

        resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnHackathonCriteria() throws Exception {
        // given

        Hackathon savedHackathon = testDataUtils.createHackathon();
        Criteria criteria = testDataUtils.createCriteria(savedHackathon);

        // when

        ResultActions resultActions =
                mockMvc.perform(getAuthorizedHackathonJsonRequest(Role.ORGANIZER,
                        "/" + savedHackathon.getId(), "criteria"));

        // then

        resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(criteria.getName()))
                .andExpect(jsonPath("$[0].id").value(criteria.getId()));
    }

    @Test
    void shouldReturnHackathonCriteriaAnswers() throws Exception {
        // given

        Hackathon savedHackathon = testDataUtils.createHackathon();
        Team team = testDataUtils.createTeam(savedHackathon);
        Criteria criteria = testDataUtils.createCriteria(savedHackathon);
        CriteriaAnswer criteriaAnswer = testDataUtils.createCriteriaAnswer(criteria, team.getId());

        // when

        ResultActions resultActions =
                mockMvc.perform(getAuthorizedHackathonJsonRequest(Role.ORGANIZER,
                        "/" + savedHackathon.getId(), "criteria", "answers?userId=1"));

        // then

        resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].value").value(criteriaAnswer.getValue()))
                .andExpect(jsonPath("$[0].teamId").value(criteriaAnswer.getTeamId()));
    }

    @Test
    void shouldReturnTeamsScoreboard() throws Exception {
        // given

        Hackathon savedHackathon = testDataUtils.createHackathon();
        Team team = testDataUtils.createTeam(savedHackathon);
        Criteria criteria = testDataUtils.createCriteria(savedHackathon);
        CriteriaAnswer criteriaAnswer = testDataUtils.createCriteriaAnswer(criteria, team.getId());
        CriteriaAnswer criteriaAnswer2 = testDataUtils.createCriteriaAnswer(criteria, team.getId());

        // when

        ResultActions resultActions =
                mockMvc.perform(getAuthorizedHackathonJsonRequest(Role.ORGANIZER,
                        "/" + savedHackathon.getId(), "leaderboard"));

        // then

        resultActions.andExpect(status().isOk())

                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].score").value(criteriaAnswer.getValue()));
    }

    @Test
    void shouldNotReturnTeamsScoreboard() throws Exception {
        // given

        Hackathon savedHackathon = testDataUtils.createHackathon();
        Team team = testDataUtils.createTeam(savedHackathon);
        Criteria criteria = testDataUtils.createCriteria(savedHackathon);
        CriteriaAnswer criteriaAnswer = testDataUtils.createCriteriaAnswer(criteria, team.getId());
        CriteriaAnswer criteriaAnswer2 = testDataUtils.createCriteriaAnswer(criteria, team.getId());

        // when

        ResultActions resultActions =
                mockMvc.perform(getAuthorizedHackathonJsonRequest(Role.USER,
                        "/" + savedHackathon.getId(), "leaderboard"));

        // then

        resultActions.andExpect(status().isForbidden());
    }
}
