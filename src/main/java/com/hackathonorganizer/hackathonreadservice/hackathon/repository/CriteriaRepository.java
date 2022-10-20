package com.hackathonorganizer.hackathonreadservice.hackathon.repository;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CriteriaRepository extends JpaRepository<Criteria, Long> {

    @Query("SELECT c FROM Criteria c WHERE c.hackathon.id = :hackathonId")
    List<Criteria> findHackathonCriteriaById(Long hackathonId);
}