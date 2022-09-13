package com.hackathonorganizer.hackathonreadservice.team.model.controller;

import com.hackathonorganizer.hackathonreadservice.team.model.TeamInvitation;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamInvitationDto;
import com.hackathonorganizer.hackathonreadservice.team.model.repository.TeamInvitationRepository;
import com.hackathonorganizer.hackathonreadservice.team.model.repository.TeamRepository;
import com.hackathonorganizer.hackathonreadservice.team.model.service.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/read/teams")
@AllArgsConstructor
@Slf4j
public class TeamController {

    private final TeamService teamService;


    @GetMapping("/invites/{userId}")
    public List<TeamInvitationDto> fetchInvites(@PathVariable("userId") Long userId) {

        log.info("Fetching user invites");

        return teamService.getUserInvitations(userId);
    }

}
