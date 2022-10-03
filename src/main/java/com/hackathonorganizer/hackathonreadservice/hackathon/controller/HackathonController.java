package com.hackathonorganizer.hackathonreadservice.hackathon.controller;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.HackathonResponse;
import com.hackathonorganizer.hackathonreadservice.hackathon.service.HackathonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/read/hackathons")
public class HackathonController {

    private final HackathonService hackathonService;

    @GetMapping("/{id}")
    public HackathonResponse getHackathonById(@PathVariable Long id) {

        return hackathonService.getHackathonById(id);
    }

    @GetMapping
    public List<HackathonResponse> getAllHackathons(Pageable pageable) {

        return hackathonService.getAllHackathons(pageable);
    }

}
