package com.hackathonorganizer.hackathonreadservice.team.repository;

import com.hackathonorganizer.hackathonreadservice.team.model.Team;
import com.hackathonorganizer.hackathonreadservice.team.model.TeamSuggestion;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamScoreDto;
import org.springframework.data.domain.Page;
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

    Page<Team> findTeamsByHackathonId(Long hackathonId, Pageable pageable);

    @Query("SELECT t FROM Team t WHERE t.hackathon.id = :hackathonId AND t.name LIKE %:name%")
    Page<Team> findTeamsByHackathonIdAndName(Long hackathonId, String name, Pageable pageable);

    @Query("SELECT new com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamScoreDto(t.id, t.name, SUM(a.value) AS score)" +
            " FROM Team t LEFT JOIN CriteriaAnswer a " +
            "ON t.id = a.teamId " +
            "WHERE t.hackathon.id = :hackathonId " +
            "GROUP BY t.id ORDER BY score DESC")
    List<TeamScoreDto> getLeaderboard(Long hackathonId);
}