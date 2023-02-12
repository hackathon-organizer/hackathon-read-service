package com.hackathonorganizer.hackathonreadservice.hackathon.repository;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.Criteria;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.CriteriaAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CriteriaRepository extends JpaRepository<Criteria, Long> {
    List<Criteria> findCriteriaByHackathonId(Long hackathonId);
}