package com.hackathonorganizer.hackathonreadservice.hackathon.service;

import com.hackathonorganizer.hackathonreadservice.exception.HackathonException;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.Hackathon;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.CriteriaAnswerDto;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.CriteriaDto;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.HackathonResponse;
import com.hackathonorganizer.hackathonreadservice.hackathon.repository.CriteriaAnswerRepository;
import com.hackathonorganizer.hackathonreadservice.hackathon.repository.CriteriaRepository;
import com.hackathonorganizer.hackathonreadservice.hackathon.repository.HackathonRepository;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamDto;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamScoreDto;
import com.hackathonorganizer.hackathonreadservice.team.service.TeamService;
import com.hackathonorganizer.hackathonreadservice.utils.CriteriaMapper;
import com.hackathonorganizer.hackathonreadservice.utils.HackathonMapper;
import com.hackathonorganizer.hackathonreadservice.utils.TeamMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HackathonService {

    private final HackathonRepository hackathonRepository;
    private final TeamService teamService;
    private final CriteriaRepository criteriaRepository;
    private final CriteriaAnswerRepository criteriaAnswerRepository;

    public HackathonResponse getHackathonResponse(Long hackathonId) {

        return HackathonMapper.mapToHackathonDto(hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new HackathonException(String.format("Hackathon with id: %d not found", hackathonId),
                        HttpStatus.NOT_FOUND)));
    }

    public List<TeamDto> getHackathonTeamsById(Long hackathonId) {

        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new HackathonException(String.format("Hackathon with id: %d not found", hackathonId),
                        HttpStatus.NOT_FOUND));

        return hackathon.getTeams().stream().map(TeamMapper::mapToDto).toList();
    }

    public Page<HackathonResponse> getAllHackathons(Pageable pageable) {

        Page<Hackathon> hackathonsPage = hackathonRepository.findAll(pageable);

        List<HackathonResponse> hackathonResponses = hackathonsPage.getContent()
                .stream().map(HackathonMapper::mapToHackathonDto).toList();

        return new PageImpl<>(hackathonResponses, pageable, hackathonsPage.getTotalElements());
    }

    public List<CriteriaDto> getHackathonRatingCriteria(Long hackathonId) {

        return criteriaRepository.findCriteriaByHackathonId(hackathonId).stream().map(CriteriaMapper::mapToDto).toList();
    }

    public List<CriteriaAnswerDto> getHackathonRatingCriteriaAnswers(Long userId, Long hackathonId) {

        return criteriaAnswerRepository.findAllByUserIdAndHackathonId(userId, hackathonId)
                .stream().map(CriteriaMapper::mapToCriteriaAnswerDto).toList();
    }

    public Set<Long> getHackathonParticipantsIds(Long hackathonId) {

        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new HackathonException(String.format("Hackathon with id: %d not found", hackathonId),
                        HttpStatus.NOT_FOUND));

        return hackathon.getHackathonParticipantsIds();
    }

    public List<TeamScoreDto> getHackathonLeaderboard(Long hackathonId) {
        return teamService.findTeamsLeaderboardByHackathonId(hackathonId);
    }

    public byte[] getFileByName(String filename) {

        Path path = Paths.get("/var/files/" + filename);

        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new HackathonException("Can not read file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
