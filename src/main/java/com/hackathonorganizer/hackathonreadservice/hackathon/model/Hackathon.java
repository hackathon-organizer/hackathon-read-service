package com.hackathonorganizer.hackathonreadservice.hackathon.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hackathonorganizer.hackathonreadservice.team.model.Team;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String organizerInfo;

    private Long ownerId;

    private String logoName;

    @Builder.Default
    private boolean isActive = true;

    @JsonFormat(pattern = "HH:mm:ss dd-MM-yyyy")
    private OffsetDateTime eventStartDate;

    @JsonFormat(pattern = "HH:mm:ss dd-MM-yyyy")
    private OffsetDateTime eventEndDate;

    @OneToMany(mappedBy = "hackathon")
    private List<Team> teams;

    @ElementCollection
    @CollectionTable(name = "hackathon_participants",
            joinColumns = @JoinColumn(name = "hackathon_id"))
    @Column(name = "participant_id")
    @Builder.Default
    private Set<Long> hackathonParticipantsIds = new HashSet<>();

    public void addUserToHackathonParticipants(Long userId) {

        if (!hackathonParticipantsIds.add(userId)) {
            log.info("Participant with id: {} is already added", userId);
        }
    }

    public void removeUserFromHackathonParticipants(Long userId) {

        if (!hackathonParticipantsIds.remove(userId)) {
            log.info("Participant with id: {} not found", userId);
        }
    }
}
