package com.hackathonorganizer.hackathonreadservice.hackathon.model.dto;

import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamResponse;

import java.util.List;

public record HackathonTeamsResponse(

        List<TeamResponse> teams
) {
}
