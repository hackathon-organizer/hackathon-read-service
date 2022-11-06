package com.hackathonorganizer.hackathonreadservice.team.model.repository;

import com.hackathonorganizer.hackathonreadservice.team.model.Team;
import com.hackathonorganizer.hackathonreadservice.team.model.TeamSuggestion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

        @Query("SELECT DISTINCT " +
                "new com.hackathonorganizer.hackathonreadservice.team.model.TeamSuggestion(t, COUNT(*)) " +
                "FROM Team t LEFT JOIN t.tags tag " +
                "LEFT JOIN t.hackathon h " +
                "WHERE tag.name IN (:tags) " +
                "AND h.id = :hackathonId " +
                "AND t.isOpen = true " +
                "GROUP BY t")
    List<TeamSuggestion> getUserMatchingTeams(List<String> tags, Long hackathonId, Pageable pageable);
}