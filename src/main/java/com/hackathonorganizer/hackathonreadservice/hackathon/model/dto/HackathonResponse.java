package com.hackathonorganizer.hackathonreadservice.hackathon.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

public record HackathonResponse (
        @NotNull
        Long id,

        @NotEmpty
        String name,

        @NotEmpty
        String description,

        boolean isActive,

        Integer hackathonParticipantsNumber,

        LocalDateTime eventStartDate,

        LocalDateTime eventEndDate
) {
}
