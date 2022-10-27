package com.hackathonorganizer.hackathonreadservice.team.model.controller;

import com.hackathonorganizer.hackathonreadservice.team.model.Tag;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamDto;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamInvitationDto;
import com.hackathonorganizer.hackathonreadservice.team.model.service.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/tags")
    public List<Tag> getAvailableTags() {
        log.info("Returning available tags");

        return teamService.getAvailableTags();
    }

    @GetMapping("{id}")
    public TeamDto getTeamById(@PathVariable("id") Long teamId) {

        return teamService.getTeamById(teamId);
    }

    @GetMapping("{teamId}/owners")
    public boolean isUserTeamOwner(@PathVariable("teamId") Long teamId,
            @RequestParam("userId") Long userId) {

        return teamService.isUserTeamOwner(teamId, userId);
    }

}
