package com.hackathonorganizer.hackathonreadservice.utils;

import com.hackathonorganizer.hackathonreadservice.team.model.Team;
import com.hackathonorganizer.hackathonreadservice.team.model.TeamInvitation;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamResponse;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamInvitationResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TeamMapper {

    public static TeamResponse mapToDto(Team team) {

        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.getOwnerId(),
                team.getIsOpen(),
                team.getChatRoomId(),
                team.getTags()
        );
    }

    public static TeamInvitationResponse mapToTeamInvitationDto(TeamInvitation teamInvitation) {

        return new TeamInvitationResponse(
                teamInvitation.getId(),
                teamInvitation.getFromUserName(),
                teamInvitation.getToUserId(),
                teamInvitation.getInvitationStatus(),
                teamInvitation.getTeamName(),
                teamInvitation.getTeam().getId());
    }
}
