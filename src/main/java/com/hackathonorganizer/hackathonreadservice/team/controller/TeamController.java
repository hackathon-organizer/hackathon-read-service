package com.hackathonorganizer.hackathonreadservice.team.controller;

import com.hackathonorganizer.hackathonreadservice.team.model.Tag;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamResponse;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamInvitationResponse;
import com.hackathonorganizer.hackathonreadservice.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/read/teams")
@RequiredArgsConstructor
@Slf4j
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public Page<TeamResponse> getTeamsByHackathonId(@RequestParam("hackathonId") Long hackathonId, Pageable pageable) {
        return teamService.getTeamsByHackathonId(hackathonId, pageable);
    }

    @GetMapping("/search")
    public Page<TeamResponse> getTeamsByName(@RequestParam("hackathonId") Long hackathonId, @RequestParam("name") String name,
                                             Pageable pageable) {
        return teamService.getTeamsByName(hackathonId, name, pageable);
    }

    @GetMapping("/invitations/{userId}")
    @RolesAllowed("USER")
    public List<TeamInvitationResponse> getInvitations(@PathVariable("userId") Long userId) {
        return teamService.getUserInvitations(userId);
    }

    @GetMapping("/tags")
    public List<Tag> getAvailableTags() {
        return teamService.getAvailableTags();
    }

    @GetMapping("/{id}")
    public TeamResponse getTeamById(@PathVariable("id") Long teamId) {
        return teamService.getTeamById(teamId);
    }

    @PostMapping("/suggestions")
    @RolesAllowed("USER")
    public List<TeamResponse> getMatchingTeams(@RequestBody List<String> userTagsNames,
                                               @RequestParam("hackathonId") Long hackathonId) {
        return teamService.getUserMatchingTeams(userTagsNames, hackathonId);
    }
}
