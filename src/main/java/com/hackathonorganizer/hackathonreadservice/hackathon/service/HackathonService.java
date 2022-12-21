package com.hackathonorganizer.hackathonreadservice.hackathon.service;

import com.hackathonorganizer.hackathonreadservice.hackathon.exception.HackathonException;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.Criteria;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.Hackathon;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.HackathonResponse;
import com.hackathonorganizer.hackathonreadservice.hackathon.repository.CriteriaRepository;
import com.hackathonorganizer.hackathonreadservice.hackathon.repository.HackathonRepository;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamDto;
import com.hackathonorganizer.hackathonreadservice.team.model.dto.TeamScoreDto;
import com.hackathonorganizer.hackathonreadservice.team.service.TeamService;
import com.hackathonorganizer.hackathonreadservice.utils.HackathonMapper;
import com.hackathonorganizer.hackathonreadservice.utils.TeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HackathonService {

    private final HackathonRepository hackathonRepository;
    private final TeamService teamService;
    private final CriteriaRepository criteriaRepository;

    public Hackathon getHackathonById(Long hackathonId) {

        return hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new HackathonException(String.format("Hackathon with id: %d not found", hackathonId),
                        HttpStatus.NOT_FOUND));
    }

    public List<TeamDto> getHackathonTeamsById(Long hackathonId) {

        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new HackathonException(String.format("Hackathon with id: %d not found", hackathonId),
                        HttpStatus.NOT_FOUND));

        return hackathon.getTeams().stream().map(TeamMapper::mapToTeamDto).toList();
    }

    public Page<HackathonResponse> getAllHackathons(Pageable pageable) {

        Page<Hackathon> hackathonsPage = hackathonRepository.findAll(pageable);

        List<HackathonResponse> hackathonResponses = hackathonsPage.getContent()
                .stream().map(HackathonMapper::mapToHackathonDto).toList();

        return new PageImpl<>(hackathonResponses, pageable, hackathonsPage.getTotalElements());
    }

    public List<Criteria> getHackathonRatingCriteria(Long hackathonId) {

        return criteriaRepository.findCriteriaByHackathonId(hackathonId);
    }

    public Set<Long> getHackathonParticipantsIds(Long hackathonId) {

        Hackathon hackathon = getHackathonById(hackathonId);

        return hackathon.getHackathonParticipantsIds();
    }

    public List<TeamScoreDto> getHackathonLeaderboard(Long hackathonId) {

        return teamService.findTeamsLeaderboardByHackathonId(hackathonId);
    }
}
