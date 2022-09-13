package com.hackathonorganizer.hackathonreadservice.team.model.repository;

import com.hackathonorganizer.hackathonreadservice.team.model.TeamInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamInvitationRepository extends JpaRepository<TeamInvitation, Long> {

    @Query("SELECT i FROM team_invitations i " +
            "WHERE i.toUserId = :userId " +
            "AND i.invitationStatus = 'PENDING'")
    List<TeamInvitation> getUserTeamInvitations(Long userId);

}
