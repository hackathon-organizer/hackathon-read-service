package com.hackathonorganizer.hackathonreadservice.hackathon.service;

import com.hackathonorganizer.hackathonreadservice.hackathon.exception.HackathonException;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.Criteria;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.Hackathon;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.HackathonResponse;
import com.hackathonorganizer.hackathonreadservice.hackathon.repository.CriteriaRepository;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamDto;
import com.hackathonorganizer.hackathonreadservice.utils.HackathonMapper;
import com.hackathonorganizer.hackathonreadservice.utils.TeamMapper;
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
    private final CriteriaRepository criteriaRepository;

    public HackathonResponse getHackathonById(Long hackathonId) {

        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new HackathonException(String.format(
                        "Hackathon with id: %d not found", hackathonId),
                        HttpStatus.NOT_FOUND));

        return HackathonMapper.mapToHackathonResponse(hackathon);
    }

    public List<TeamDto> getHackathonTeamsById(Long hackathonId) {

        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new HackathonException(String.format(
                        "Hackathon with id: %d not found", hackathonId),
                        HttpStatus.NOT_FOUND));

        return hackathon.getTeams().stream().map(TeamMapper::mapToTeamDto).toList();
    }

    public List<HackathonResponse> getAllHackathons(Pageable pageable) {

        return hackathonRepository.findAll(pageable)
                .map(HackathonMapper::mapToHackathonResponse).stream().toList();
    }

    public List<Criteria> getHackathonCriteria(Long hackathonId) {

        return criteriaRepository.findHackathonCriteriaById(hackathonId);
    }
}
