package com.hackathonorganizer.hackathonreadservice.hackathon.repository;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.CriteriaAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CriteriaAnswerRepository extends JpaRepository<CriteriaAnswer, Long> {

    @Query("SELECT ca FROM CriteriaAnswer ca JOIN FETCH ca.criteria c " +
            "WHERE c.hackathon.id = :hackathonId AND " +
            "ca.userId = :userId")
    List<CriteriaAnswer> findAllByUserIdAndHackathonId(Long userId, Long hackathonId);
}
