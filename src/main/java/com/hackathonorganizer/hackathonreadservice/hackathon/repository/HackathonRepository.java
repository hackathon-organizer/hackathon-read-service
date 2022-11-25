package com.hackathonorganizer.hackathonreadservice.hackathon.repository;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
}
