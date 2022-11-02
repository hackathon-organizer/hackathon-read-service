package com.hackathonorganizer.hackathonreadservice.team.model.repository;

import com.hackathonorganizer.hackathonreadservice.team.model.Tag;
import com.hackathonorganizer.hackathonreadservice.team.model.Team;
import com.hackathonorganizer.hackathonreadservice.team.model.TeamInvitation;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamInvitationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

        @Query("SELECT DISTINCT t, COUNT(*) FROM Team t LEFT JOIN t.tags " +
                "WHERE tag.name IN (:tags) " +
                "GROUP BY t.id")
    List<Team> getUserMatchingTeams(List<String> tags);
}