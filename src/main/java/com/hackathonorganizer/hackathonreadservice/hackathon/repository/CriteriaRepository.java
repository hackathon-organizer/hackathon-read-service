package com.hackathonorganizer.hackathonreadservice.hackathon.repository;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CriteriaRepository extends JpaRepository<Criteria, Long> {
    List<Criteria> findCriteriaByHackathonId(Long hackathonId);
}