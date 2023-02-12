package com.hackathonorganizer.hackathonreadservice.team.model;

import com.hackathonorganizer.hackathonreadservice.team.model.Team;

public record TeamSuggestion(

        Team team,
        Long numberOfEqualTags
) {
}
