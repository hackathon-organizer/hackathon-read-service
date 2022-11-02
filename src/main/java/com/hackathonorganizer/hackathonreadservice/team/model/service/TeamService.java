package com.hackathonorganizer.hackathonreadservice.team.model.service;

import com.hackathonorganizer.hackathonreadservice.hackathon.exception.TeamException;
import com.hackathonorganizer.hackathonreadservice.team.model.Tag;
import com.hackathonorganizer.hackathonreadservice.team.model.Team;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamDto;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamInvitationDto;
import com.hackathonorganizer.hackathonreadservice.team.model.repository.TagRepository;
import com.hackathonorganizer.hackathonreadservice.team.model.repository.TeamInvitationRepository;
import com.hackathonorganizer.hackathonreadservice.team.model.repository.TeamRepository;
import com.hackathonorganizer.hackathonreadservice.utils.TeamMapper;
import lombok.RequiredArgsConstructor;
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

        Team team =  teamRepository.findById(teamId).orElseThrow(
                () -> new TeamException("Team with id: " + teamId + " not found", HttpStatus.NOT_FOUND));

        return TeamMapper.mapToTeamDto(team);
    }

    public boolean isUserTeamOwner(Long teamId, Long userId) {

        Team team = teamRepository.findById(teamId).orElseThrow();

        return team.getOwnerId().equals(userId);
    }

    public List<TeamDto> getUserMatchingTeams(List<String> userTagsNames) {

        // TODO add teams limit

        return teamRepository.getUserMatchingTeams(userTagsNames).stream()
                .map(TeamMapper::mapToTeamDto).toList();
    }
}
