package com.hackathonorganizer.hackathonreadservice.team.service;

import com.hackathonorganizer.hackathonreadservice.exception.TeamException;
import com.hackathonorganizer.hackathonreadservice.team.model.Tag;
import com.hackathonorganizer.hackathonreadservice.team.model.Team;
import com.hackathonorganizer.hackathonreadservice.team.model.TeamSuggestion;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamDto;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamInvitationDto;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamScoreDto;

import com.hackathonorganizer.hackathonreadservice.team.repository.TagRepository;
import com.hackathonorganizer.hackathonreadservice.team.repository.TeamInvitationRepository;
import com.hackathonorganizer.hackathonreadservice.team.repository.TeamRepository;
import com.hackathonorganizer.hackathonreadservice.utils.TeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamInvitationRepository teamInvitationRepository;
    private final TagRepository tagRepository;
    private final TeamRepository teamRepository;

    public List<TeamInvitationDto> getUserInvitations(Long userId) {

        return teamInvitationRepository.getUserTeamInvitations(userId).stream()
                .map(TeamMapper::mapToTeamInvitationDto).collect(Collectors.toList());
    }

    public List<Tag> getAvailableTags() {

        return tagRepository.findAll();
    }

    public TeamDto getTeamById(Long teamId) {

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamException("Team with id: " + teamId + " not found",
                        HttpStatus.NOT_FOUND));

        return TeamMapper.mapToDto(team);
    }

    public List<TeamDto> getUserMatchingTeams(List<String> userTags, Long hackathonId) {

        List<TeamSuggestion> foundedTeams = teamRepository.getUserMatchingTeams(userTags, hackathonId, PageRequest.of(0, 10));

        return foundedTeams.stream().map(teamSuggestion -> TeamMapper.mapToDto(teamSuggestion.team())).toList();
    }

    public Page<TeamDto> getTeamsByHackathonId(Long hackathonId, Pageable pageable) {

        Page<Team> teamsPage = teamRepository.findTeamsByHackathonId(hackathonId, pageable);

        List<TeamDto> teamsResponse = teamsPage.getContent().stream().map(TeamMapper::mapToDto).toList();

        return new PageImpl<>(teamsResponse, pageable, teamsPage.getTotalElements());
    }

    public Page<TeamDto> getTeamsByName(Long hackathonId, String name, Pageable pageable) {

        Page<Team> teamsPage = teamRepository.findTeamsByHackathonIdAndName(hackathonId, name, pageable);

        List<TeamDto> teamsResponse = teamsPage.getContent().stream().map(TeamMapper::mapToDto).toList();

        return new PageImpl<>(teamsResponse, pageable, teamsPage.getTotalElements());
    }

    public List<TeamScoreDto> findTeamsLeaderboardByHackathonId(Long hackathonId) {

        return teamRepository.getLeaderboard(hackathonId);
    }
}
