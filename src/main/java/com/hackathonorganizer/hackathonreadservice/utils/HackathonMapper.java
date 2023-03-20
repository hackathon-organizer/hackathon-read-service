package com.hackathonorganizer.hackathonreadservice.utils;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.Hackathon;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.HackathonResponse;

public class HackathonMapper {

    public static HackathonResponse mapToHackathonDto(Hackathon hackathon) {

        return new HackathonResponse(
                hackathon.getId(),
                hackathon.getName(),
                hackathon.getDescription(),
                hackathon.isActive(),
                hackathon.getLogoName(),
                hackathon.getHackathonParticipantsIds().size(),
                hackathon.getEventStartDate(),
                hackathon.getEventEndDate()
        );
    }

}
