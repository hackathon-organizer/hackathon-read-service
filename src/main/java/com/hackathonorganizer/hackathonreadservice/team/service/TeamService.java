package com.hackathonorganizer.hackathonreadservice.team.service;

import com.hackathonorganizer.hackathonreadservice.hackathon.exception.TeamException;
import com.hackathonorganizer.hackathonreadservice.team.model.Tag;
import com.hackathonorganizer.hackathonreadservice.team.model.Team;
import com.hackathonorganizer.hackathonreadservice.team.model.TeamSuggestion;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamDto;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamInvitationDto;
import com.hackathonorganizer.hackathonreadservice.team.model.repository.TagRepository;
import com.hackathonorganizer.hackathonreadservice.team.model.repository.TeamInvitationRepository;
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

        Team team = teamRepository.findById(teamId).orElseThrow(() ->
                new TeamException("Team with id: " + teamId + " not found",
                        HttpStatus.NOT_FOUND));

        return TeamMapper.mapToTeamDto(team);
    }

    public boolean isUserTeamOwner(Long teamId, Long userId) {

        Team team = teamRepository.findById(teamId).orElseThrow(() ->
                new TeamException("Team with id: " + teamId + " not found",
                        HttpStatus.NOT_FOUND));

        return team.getOwnerId().equals(userId);
    }

    public List<TeamDto> getUserMatchingTeams(List<String> userTags, Long hackathonId) {

        List<TeamSuggestion> foundedTeams = teamRepository.getUserMatchingTeams(
                userTags, hackathonId, PageRequest.of(0, 10));

        return foundedTeams.stream().map(teamSuggestion ->
                TeamMapper.mapToTeamDto(teamSuggestion.getTeam())).toList();
    }

    public Page<TeamDto> getTeamsByHackathonId(Long hackathonId, Pageable pageable) {

        Page<Team> teamsPage = teamRepository.findTeamsByHackathonId(hackathonId, pageable);

        List<TeamDto> teamsResponse = teamsPage.getContent()
                .stream().map(TeamMapper::mapToTeamDto).toList();

        return new PageImpl<>(teamsResponse, pageable, teamsPage.getTotalElements());
    }

    public Page<TeamDto> getTeamsByName(Long hackathonId, String name, Pageable pageable) {

        Page<Team> teamsPage = teamRepository
                .findTeamsByHackathonIdAndName(hackathonId, name, pageable);

        List<TeamDto> teamsResponse = teamsPage.getContent()
                .stream().map(TeamMapper::mapToTeamDto).toList();

        return new PageImpl<>(teamsResponse, pageable, teamsPage.getTotalElements());
    }

    public List<TeamDto> findTeamsLeaderboardByHackathonId(Long hackathonId) {

        return teamRepository.getLeaderboard(hackathonId).stream().map(TeamMapper::mapToTeamDto).toList();
    }
}