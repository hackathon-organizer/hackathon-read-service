package com.hackathonorganizer.hackathonreadservice.creator;


import com.hackathonorganizer.hackathonreadservice.hackathon.model.Criteria;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.CriteriaAnswer;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.Hackathon;
import com.hackathonorganizer.hackathonreadservice.hackathon.repository.CriteriaAnswerRepository;
import com.hackathonorganizer.hackathonreadservice.hackathon.repository.CriteriaRepository;
import com.hackathonorganizer.hackathonreadservice.hackathon.repository.HackathonRepository;
import com.hackathonorganizer.hackathonreadservice.team.model.InvitationStatus;
import com.hackathonorganizer.hackathonreadservice.team.model.Team;
import com.hackathonorganizer.hackathonreadservice.team.model.TeamInvitation;
import com.hackathonorganizer.hackathonreadservice.team.repository.TeamInvitationRepository;
import com.hackathonorganizer.hackathonreadservice.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TestDataUtils {

    private final HackathonRepository hackathonRepository;
    private final TeamRepository teamRepository;
    private final CriteriaRepository criteriaRepository;
    private final CriteriaAnswerRepository criteriaAnswerRepository;
    private final TeamInvitationRepository teamInvitationRepository;

    public static Hackathon buildHackathonMock() {
        return Hackathon.builder()
                .id(5L)
                .name("Hackathon")
                .description("Desc")
                .organizerInfo("Org Info")
                .isActive(true)
                .eventStartDate(OffsetDateTime.of(2123, 10, 10, 12, 0, 0, 0, ZoneOffset.UTC))
                .eventEndDate(OffsetDateTime.of(2123, 10, 10, 16, 0, 0, 0, ZoneOffset.UTC))
                .ownerId(1L)
                .hackathonParticipantsIds(Set.of(1L, 10L))
                .teams(List.of(buildTeamMock(), buildTeamMock()))
                .build();
    }

    public static Team buildTeamMock() {
        return Team.builder()
                .id(5L)
                .name("Team")
                .description("Desc")
                .isOpen(true)
                .chatRoomId(5L)
                .hackathon(Hackathon.builder().id(1L).build())
                .ownerId(1L)
                .tags(List.of())
                .build();
    }

    public static Criteria buildCriteriaMock() {
        return Criteria.builder()
                .id(1L)
                .name("crit")
                .criteriaAnswers(Set.of())
                .hackathon(buildHackathonMock())
                .build();
    }

    public static CriteriaAnswer buildCriteriaAnswerMock() {
        return CriteriaAnswer.builder()
                .id(1L)
                .userId(1L)
                .teamId(buildHackathonMock().getId())
                .value(50)
                .criteria(buildCriteriaMock())
                .build();
    }

    public static TeamInvitation buildTeamInvitationMock() {
        return TeamInvitation.builder()
                .id(1L)
                .fromUserName("from username")
                .invitationStatus(InvitationStatus.PENDING)
                .teamName("team name")
                .toUserId(1L)
                .team(Team.builder().build())
                .build();
    }

    public Hackathon createHackathon() {

        String name = "Hackathon";
        String description = "Hackathon desc";
        String organizerInfo = "Organizer info";

        OffsetDateTime eventStartDate = OffsetDateTime.of(2123, 10, 10, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime eventEndDate = OffsetDateTime.of(2123, 10, 12, 12, 0, 0, 0, ZoneOffset.UTC);

        return hackathonRepository.save(
                Hackathon.builder()
                        .name(name)
                        .description(description)
                        .organizerInfo(organizerInfo)
                        .eventStartDate(eventStartDate)
                        .eventEndDate(eventEndDate)
                        .hackathonParticipantsIds(Set.of())
                        .ownerId(1L)
                        .build());
    }

    public Hackathon updateHackathonProperties(Hackathon hackathon) {
        return hackathonRepository.save(hackathon);
    }

    public Team createTeam(Hackathon hackathon) {

        String name = "Team name";
        String description = "Team desc";

        return teamRepository.save(
                Team.builder()
                        .name(name)
                        .description(description)
                        .hackathon(hackathon)
                        .tags(List.of())
                        .invitations(Set.of())
                        .chatRoomId(1L)
                        .isOpen(true)
                        .invitations(Set.of())
                        .ownerId(1L)
                        .build()
        );
    }

    public Criteria createCriteria(Hackathon hackathon) {

        return criteriaRepository.save(
                Criteria.builder()
                        .id(14L)
                        .name("crit1")
                        .hackathon(hackathon)
                        .criteriaAnswers(Set.of())
                        .build()
        );
    }

    public CriteriaAnswer createCriteriaAnswer(Criteria criteria, Long teamId) {
        return criteriaAnswerRepository.save(
                CriteriaAnswer.builder()
                        .id(15L)
                        .teamId(teamId)
                        .userId(1L)
                        .value(70)
                        .criteria(criteria)
                        .build()
        );
    }

    public TeamInvitation createTeamInvitation(Team team) {
        return teamInvitationRepository.save(TeamInvitation.builder()
                        .id(5L)
                        .invitationStatus(InvitationStatus.PENDING)
                        .team(team)
                        .teamName(team.getName())
                        .toUserId(1L)
                        .fromUserName("username")
                .build());
    }
}
