package com.hackathonorganizer.hackathonreadservice.team.model.dto;

import com.hackathonorganizer.hackathonreadservice.team.model.InvitationStatus;

public record TeamInvitationResponse(

        Long id,
        String fromUserName,
        Long toUserId,
        InvitationStatus invitationStatus,
        String teamName,
        Long teamId
) {
}
