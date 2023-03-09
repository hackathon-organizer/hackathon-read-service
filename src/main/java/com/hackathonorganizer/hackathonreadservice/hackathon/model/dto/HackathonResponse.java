package com.hackathonorganizer.hackathonreadservice.hackathon.model.dto;

import java.time.OffsetDateTime;

public record HackathonResponse(

        Long id,
        String name,
        String description,
        boolean isActive,
        Integer hackathonParticipantsNumber,
        OffsetDateTime eventStartDate,
        OffsetDateTime eventEndDate
) {
}
