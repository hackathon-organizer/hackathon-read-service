package com.hackathonorganizer.hackathonreadservice.hackathon.model.dto;

import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamDto;

import java.util.List;

public record HackathonTeamsResponse(
        List<TeamDto> teams
) {
}
