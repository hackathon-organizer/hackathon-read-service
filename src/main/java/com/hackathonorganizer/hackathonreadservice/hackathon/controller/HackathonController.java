package com.hackathonorganizer.hackathonreadservice.hackathon.controller;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.CriteriaAnswerResponse;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.CriteriaResponse;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.HackathonResponse;
import com.hackathonorganizer.hackathonreadservice.hackathon.service.HackathonService;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamResponse;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamScoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/read/hackathons")
public class HackathonController {

    private final HackathonService hackathonService;

    @GetMapping("/{hackathonId}")
    public HackathonResponse getHackathonById(@PathVariable("hackathonId") Long id) {
        return hackathonService.getHackathonResponse(id);
    }

    @GetMapping("/{hackathonId}/teams")
    public List<TeamResponse> getHackathonTeamsById(@PathVariable("hackathonId") Long id) {
        return hackathonService.getHackathonTeamsById(id);
    }

    @GetMapping
    public Page<HackathonResponse> getAllHackathons(Pageable pageable) {
        return hackathonService.getAllHackathons(pageable);
    }

    @GetMapping("/{hackathonId}/criteria")
    @RolesAllowed({"ORGANIZER", "JURY"})
    public List<CriteriaResponse> getHackathonRateCriteria(@PathVariable("hackathonId") Long hackathonId) {
        return hackathonService.getHackathonRatingCriteria(hackathonId);
    }

    @GetMapping("/{hackathonId}/criteria/answers")
    @RolesAllowed({"ORGANIZER", "JURY"})
    public List<CriteriaAnswerResponse> getHackathonRateCriteriaWithAnswers(@PathVariable("hackathonId") Long hackathonId, @RequestParam("userId") Long userId) {
        return hackathonService.getHackathonRatingCriteriaAnswers(userId, hackathonId);
    }

    @GetMapping("/{hackathonId}/participants")
    public Set<Long> getHackathonParticipantsIds(@PathVariable("hackathonId") Long hackathonId) {
        return hackathonService.getHackathonParticipantsIds(hackathonId);
    }

    @GetMapping("/{hackathonId}/leaderboard")
    @RolesAllowed("ORGANIZER")
    public List<TeamScoreResponse> getHackathonLeaderboard(@PathVariable("hackathonId") Long hackathonId) {
        return hackathonService.getHackathonLeaderboard(hackathonId);
    }

    @GetMapping(value = "/files/{filename}")
    public byte[] getImage(@PathVariable("filename") String filename) {
       return hackathonService.getFileByName(filename);
    }
}
