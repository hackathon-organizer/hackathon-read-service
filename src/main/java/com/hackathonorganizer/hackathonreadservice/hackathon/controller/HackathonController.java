package com.hackathonorganizer.hackathonreadservice.hackathon.controller;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.Criteria;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.HackathonResponse;
import com.hackathonorganizer.hackathonreadservice.hackathon.service.HackathonService;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamDto;
import com.hackathonorganizer.hackathonreadservice.utils.HackathonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/read/hackathons")
public class HackathonController {

    private final HackathonService hackathonService;

    @GetMapping("/{id}")
    public HackathonResponse getHackathonById(@PathVariable Long id) {

        return HackathonMapper.mapToHackathonDto(hackathonService.getHackathonById(id));
    }

    @GetMapping("/{id}/teams")
    public List<TeamDto> getHackathonTeamsById(@PathVariable Long id) {

        return hackathonService.getHackathonTeamsById(id);
    }

    @GetMapping
    public Page<HackathonResponse> getAllHackathons(Principal principal, Pageable pageable) {

        return hackathonService.getAllHackathons(pageable);
    }

    @GetMapping("/{hackathonId}/criteria")
    public List<Criteria> getHackathonRateCriteria(@PathVariable("hackathonId") Long hackathonId) {

        return hackathonService.getHackathonRatingCriteria(hackathonId);
    }

    @GetMapping("/{hackathonId}/participants")
    public Set<Long> getHackathonParticipantsIds(@PathVariable("hackathonId") Long hackathonId) {

        return hackathonService.getHackathonParticipantsIds(hackathonId);
    }

    @GetMapping("/{hackathonId}/leaderboard")
    public List<TeamDto> getHackathonLeaderboard(@PathVariable("hackathonId") Long hackathonId) {

        return hackathonService.getHackathonLeaderboard(hackathonId);
    }
}
