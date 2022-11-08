package com.hackathonorganizer.hackathonreadservice.team.model.repository;

import com.hackathonorganizer.hackathonreadservice.team.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

}