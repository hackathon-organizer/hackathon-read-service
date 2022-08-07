package com.hackathonorganizer.hackathonreadservice.hackathon.service;

import com.hackathonorganizer.hackathonreadservice.hackathon.exception.HackathonException;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.Hackathon;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.HackathonResponse;
import lombok.RequiredArgsConstructor;
import com.hackathonorganizer.hackathonreadservice.repository.HackathonRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HackathonService {

    private final HackathonRepository hackathonRepository;

    public HackathonResponse getHackathonById(Long hackathonId) {

        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new HackathonException(String.format(
                        "Hackathon with id: %d not found", hackathonId),
                        HttpStatus.NOT_FOUND));

        return mapToDto(hackathon);
    }

    public List<HackathonResponse> getAllHackathons(Pageable pageable) {

        return hackathonRepository.findAll(pageable).map(this::mapToDto).stream().toList();
    }

    private HackathonResponse mapToDto(Hackathon hackathon) {

        return new HackathonResponse(hackathon.getId(), hackathon.getName(),
                hackathon.getDescription());
    }
}
