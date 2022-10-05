package com.hackathonorganizer.hackathonreadservice.utils;

import com.hackathonorganizer.hackathonreadservice.team.model.InvitationStatus;
import com.hackathonorganizer.hackathonreadservice.team.model.Team;
import com.hackathonorganizer.hackathonreadservice.team.model.TeamInvitation;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamDto;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamInvitationDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TeamMapper {

    public static TeamInvitationDto mapToTeamInvitationDto(TeamInvitation teamInvitation) {
        return new TeamInvitationDto(
                teamInvitation.getId(),
                teamInvitation.getFromUserName(),
                teamInvitation.getToUserId(),
                InvitationStatus.PENDING,
                teamInvitation.getTeamName(),
                teamInvitation.getTeam().getId());
    }

    public static TeamDto mapToTeamDto(Team team) {
        return new TeamDto(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.getIsOpen(),
                team.getTags()
        );
    }

}
