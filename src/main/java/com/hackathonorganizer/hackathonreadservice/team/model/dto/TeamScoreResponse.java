package com.hackathonorganizer.hackathonreadservice.team.model.dto;

public record TeamScoreResponse(

        Long id,
        String name,
        Long score
) {
}