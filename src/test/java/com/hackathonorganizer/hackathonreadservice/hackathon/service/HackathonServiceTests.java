package com.hackathonorganizer.hackathonreadservice.hackathon.service;

import com.hackathonorganizer.hackathonreadservice.creator.TestDataUtils;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.Criteria;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.CriteriaAnswer;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.Hackathon;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.CriteriaAnswerDto;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.CriteriaDto;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.HackathonResponse;
import com.hackathonorganizer.hackathonreadservice.hackathon.repository.CriteriaAnswerRepository;
import com.hackathonorganizer.hackathonreadservice.hackathon.repository.CriteriaRepository;
import com.hackathonorganizer.hackathonreadservice.hackathon.repository.HackathonRepository;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamDto;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamScoreDto;
import com.hackathonorganizer.hackathonreadservice.team.service.TeamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(MockitoExtension.class)
public class HackathonServiceTests {

    @InjectMocks
    private HackathonService hackathonService;

    @Mock
    private HackathonRepository hackathonRepository;

    @Mock
    private CriteriaRepository criteriaRepository;

    @Mock
    private CriteriaAnswerRepository criteriaAnswerRepository;

    @Mock
    private TeamService teamService;

    private Pageable pageable = Pageable.ofSize(9);

    private final Hackathon mockHackathon = TestDataUtils.buildHackathonMock();

    @Test
    void shouldReturnHackathonById() {
        // given

        when(hackathonRepository.findById(anyLong())).thenReturn(Optional.of(
                mockHackathon));

        // when

        HackathonResponse hackathonResponse = hackathonService.getHackathonResponse(mockHackathon.getId());

        //then

        verify(hackathonRepository).findById(anyLong());

        assertThat(hackathonResponse.id()).isEqualTo(mockHackathon.getId());
        assertThat(hackathonResponse.name()).isEqualTo(mockHackathon.getName());
        assertThat(hackathonResponse.description()).isEqualTo(mockHackathon.getDescription());
    }

    @Test
    void shouldReturnHackathonTeams() {
        // given

        when(hackathonRepository.findById(anyLong())).thenReturn(Optional.of(mockHackathon));

        // when

        List<TeamDto> result = hackathonService.getHackathonTeamsById(mockHackathon.getId());

        // then

        verify(hackathonRepository).findById(anyLong());

        assertThat(result.size()).isEqualTo(mockHackathon.getTeams().size());
        assertThat(result.get(0).name()).isEqualTo(mockHackathon.getTeams().get(0).getName());
    }

    @Test
    void shouldReturnAllHackathons() {
        // given

        when(hackathonRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(mockHackathon)));

        // when

        Page<HackathonResponse> result = hackathonService.getAllHackathons(pageable);

        //then

        verify(hackathonRepository).findAll(pageable);

        assertThat(result.getContent().size()).isEqualTo(1);
    }

    @Test
    void shouldReturnHackathonParticipantsIds() {
        // given

        when(hackathonRepository.findById(anyLong())).thenReturn(Optional.of(mockHackathon));

        // when

        Set<Long> result = hackathonService.getHackathonParticipantsIds(mockHackathon.getId());

        // then

        verify(hackathonRepository).findById(mockHackathon.getId());

        assertThat(result).containsExactlyInAnyOrderElementsOf(mockHackathon.getHackathonParticipantsIds());
    }

    @Test
    void shouldReturnHackathonCriteria() {
        // given

        Criteria criteria = TestDataUtils.buildCriteriaMock();

        when(criteriaRepository.findCriteriaByHackathonId(anyLong())).thenReturn(List.of(criteria));

        // when

        List<CriteriaDto> result = hackathonService.getHackathonRatingCriteria(mockHackathon.getId());

        // then

        verify(criteriaRepository).findCriteriaByHackathonId(mockHackathon.getId());

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).id()).isEqualTo(criteria.getId());
        assertThat(result.get(0).name()).isEqualTo(criteria.getName());
    }

    @Test
    void shouldReturnHackathonCriteriaAnswers() {
        // given

        CriteriaAnswer answer = TestDataUtils.buildCriteriaAnswerMock();
        CriteriaAnswer answer2 = TestDataUtils.buildCriteriaAnswerMock();

        when(criteriaAnswerRepository.findAllByUserIdAndHackathonId(anyLong(), anyLong()))
                .thenReturn(List.of(answer, answer2));

        // when

        List<CriteriaAnswerDto> result =
                hackathonService.getHackathonRatingCriteriaAnswers(1L, mockHackathon.getId());

        // then

        verify(criteriaAnswerRepository).findAllByUserIdAndHackathonId(1L, mockHackathon.getId());

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).value()).isEqualTo(answer.getValue());
        assertThat(result.get(0).teamId()).isEqualTo(answer.getTeamId());
        assertThat(result.get(1).value()).isEqualTo(answer2.getValue());
        assertThat(result.get(1).teamId()).isEqualTo(answer2.getTeamId());
    }

    @Test
    void shouldReturnTeamsScoreboard() {
        // given

        when(teamService.findTeamsLeaderboardByHackathonId(anyLong())).thenReturn(List.of(
                new TeamScoreDto(1L, "Team name", 100L)
        ));

        // when

        List<TeamScoreDto> result = hackathonService.getHackathonLeaderboard(mockHackathon.getId());

        // then

       verify(teamService).findTeamsLeaderboardByHackathonId(mockHackathon.getId());

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).score()).isEqualTo(100);
    }

    @Test
    void shouldNotReturnTeamsScoreboard() {
        // given

        when(teamService.findTeamsLeaderboardByHackathonId(anyLong())).thenReturn(List.of());

        // when

        List<TeamScoreDto> result = hackathonService.getHackathonLeaderboard(mockHackathon.getId());

        // then

        verify(teamService).findTeamsLeaderboardByHackathonId(mockHackathon.getId());

        assertThat(result.size()).isEqualTo(0);
    }
}
