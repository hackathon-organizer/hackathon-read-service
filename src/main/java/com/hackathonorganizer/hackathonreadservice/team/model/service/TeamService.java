package com.hackathonorganizer.hackathonreadservice.team.model.service;

import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamInvitationDto;
import com.hackathonorganizer.hackathonreadservice.team.model.repository.TeamInvitationRepository;
import com.hackathonorganizer.hackathonreadservice.team.model.repository.TeamRepository;
import com.hackathonorganizer.hackathonreadservice.utils.TeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamInvitationRepository teamInvitationRepository;

    public List<TeamInvitationDto> getUserInvitations(Long userId) {

        return teamInvitationRepository.getUserTeamInvitations(userId).stream()
                .map(TeamMapper::mapToTeamInvitationDto).collect(Collectors.toList());
    }


}
