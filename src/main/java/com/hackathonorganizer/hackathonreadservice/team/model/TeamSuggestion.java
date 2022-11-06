package com.hackathonorganizer.hackathonreadservice.team.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TeamSuggestion {
        private Team team;
        private Long numberOfEqualTags;
}
