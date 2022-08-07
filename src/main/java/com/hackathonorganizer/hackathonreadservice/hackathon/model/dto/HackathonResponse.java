package com.hackathonorganizer.hackathonreadservice.hackathon.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record HackathonResponse (
        @NotNull
        Long id,

        @NotEmpty
        String name,

        @NotEmpty
        String description
) {
}
