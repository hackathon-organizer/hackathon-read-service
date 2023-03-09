package com.hackathonorganizer.hackathonreadservice.team.service;

import com.hackathonorganizer.hackathonreadservice.creator.TestDataUtils;
import com.hackathonorganizer.hackathonreadservice.exception.TeamException;
import com.hackathonorganizer.hackathonreadservice.team.model.Tag;
import com.hackathonorganizer.hackathonreadservice.team.model.Team;
import com.hackathonorganizer.hackathonreadservice.team.model.TeamInvitation;
import com.hackathonorganizer.hackathonreadservice.team.model.TeamSuggestion;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamDto;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamInvitationDto;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamScoreDto;
import com.hackathonorganizer.hackathonreadservice.team.repository.TagRepository;
import com.hackathonorganizer.hackathonreadservice.team.repository.TeamInvitationRepository;
import com.hackathonorganizer.hackathonreadservice.team.repository.TeamRepository;

import liquibase.pro.packaged.L;
import org.junit.jupiter.api.BeforeEach;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @InjectMocks
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamInvitationRepository teamInvitationRepository;

    @Mock
    private TagRepository tagRepository;

    private Pageable pageable = Pageable.ofSize(9);

    private Team mockTeam = TestDataUtils.buildTeamMock();

    @BeforeEach
    void setUp() {
        teamInvitationRepository.deleteAll();
        teamRepository.deleteAll();
    }

    @Test
    void shouldReturnUserInvitations() {
        // given

        TeamInvitation invitation = TestDataUtils.buildTeamInvitationMock();
        TeamInvitation invitation2 = TestDataUtils.buildTeamInvitationMock();

        List<TeamInvitation> teamInvitations = List.of(invitation, invitation2);

        when(teamInvitationRepository.getUserTeamInvitations(anyLong())).thenReturn(teamInvitations);

        // when

        List<TeamInvitationDto> result = teamService.getUserInvitations(1L);

        // then
        verify(teamInvitationRepository).getUserTeamInvitations(1L);

        assertThat(result.size()).isEqualTo(teamInvitations.size());
        assertThat(result.get(0).toUserId()).isEqualTo(invitation.getToUserId());
        assertThat(result.get(0).fromUserName()).isEqualTo(invitation.getFromUserName());
    }

    @Test
    void shouldReturnTags() {
        // given

        List<Tag> tags = List.of(new Tag(1L, "Tag1"), new Tag(2L, "Tag2"));

        when(tagRepository.findAll()).thenReturn(tags);

        // when

        List<Tag> result = teamService.getAvailableTags();

        // then
        verify(tagRepository).findAll();

        assertThat(result.size()).isEqualTo(tags.size());
        assertThat(result.get(0).getName()).isEqualTo(tags.get(0).getName());
        assertThat(result.get(1).getName()).isEqualTo(tags.get(1).getName());
    }

    @Test
    void shouldReturnTeamById() {
        // given

        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(mockTeam));

        // when

        TeamDto result = teamService.getTeamById(mockTeam.getId());

        // then

        verify(teamRepository).findById(mockTeam.getId());

        assertThat(result.name()).isEqualTo(mockTeam.getName());
        assertThat(result.ownerId()).isEqualTo(mockTeam.getOwnerId());
        assertThat(result.description()).isEqualTo(mockTeam.getDescription());
    }

    @Test
    void shouldNotReturnTeamById() {
        // given

        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when

        Throwable throwable = catchThrowable(() -> teamService.getTeamById(mockTeam.getId()));

        // then

        verify(teamRepository).findById(mockTeam.getId());

        assertThat(throwable).isExactlyInstanceOf(TeamException.class);
    }

    @Test
    void shouldReturnTeamSuggestions() {
        // given

        List<String> userTags = List.of("Java", "CSS");
        List<TeamSuggestion> teamSuggestions = List.of(new TeamSuggestion(mockTeam, 1L));

        when(teamRepository.getUserMatchingTeams(anyList(), anyLong(), any(Pageable.class))).thenReturn(teamSuggestions);
        pageable = Pageable.ofSize(10);

        // when

        List<TeamDto> result = teamService.getUserMatchingTeams(userTags, mockTeam.getHackathon().getId());

        // then

        verify(teamRepository).getUserMatchingTeams(userTags, mockTeam.getHackathon().getId(), pageable);

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).id()).isEqualTo(mockTeam.getId());
        assertThat(result.get(0).name()).isEqualTo(mockTeam.getName());
    }

    @Test
    void shouldReturnTeamsByHackathonId() {
        // given

        when(teamRepository.findTeamsByHackathonId(anyLong(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(mockTeam)));

        // when

        Page<TeamDto> result = teamService.getTeamsByHackathonId(mockTeam.getHackathon().getId(), pageable);

        // then

        verify(teamRepository).findTeamsByHackathonId(mockTeam.getHackathon().getId(), pageable);

        assertThat(result.getContent().size()).isEqualTo(1);
        assertThat(result.getContent().get(0).name()).isEqualTo(mockTeam.getName());
        assertThat(result.getContent().get(0).id()).isEqualTo(mockTeam.getId());
    }

    @Test
    void shouldReturnTeamsByName() {
        // given

        when(teamRepository.findTeamsByHackathonIdAndName(anyLong(), anyString(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(mockTeam)));

        // when

        Page<TeamDto> result = teamService.getTeamsByName(mockTeam.getHackathon().getId(), mockTeam.getName(), pageable);

        // then
        verify(teamRepository)
                .findTeamsByHackathonIdAndName(mockTeam.getHackathon().getId(), mockTeam.getName(), pageable);

        assertThat(result.getContent().size()).isEqualTo(1);
        assertThat(result.getContent().get(0).name()).isEqualTo(mockTeam.getName());
        assertThat(result.getContent().get(0).id()).isEqualTo(mockTeam.getId());
    }
}
