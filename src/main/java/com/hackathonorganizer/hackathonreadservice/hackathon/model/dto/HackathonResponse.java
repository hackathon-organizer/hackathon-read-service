package com.hackathonorganizer.hackathonreadservice.hackathon.model.dto;

import java.time.LocalDateTime;

public record HackathonResponse(

        Long id,
        String name,
        String description,
        boolean isActive,
        Integer hackathonParticipantsNumber,
        LocalDateTime eventStartDate,
        LocalDateTime eventEndDate
) {
}
