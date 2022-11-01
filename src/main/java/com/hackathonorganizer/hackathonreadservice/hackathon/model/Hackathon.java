package com.hackathonorganizer.hackathonreadservice.hackathon.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hackathonorganizer.hackathonreadservice.team.model.Team;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import javax.validation.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name can not be empty!")
    private String name;

    @NotEmpty(message = "Description can not be empty!")
    private String description;

    @NotEmpty(message = "Organizer info can not be empty!")
    private String organizerInfo;

    @Builder.Default
    private boolean isActive = true;

    @NotNull(message = "Event start date can not be null!")
    @JsonFormat(pattern = "HH:mm:ss dd-MM-yyyy")
    private LocalDateTime eventStartDate;

    @NotNull(message = "Event end date can not be null!")
    @JsonFormat(pattern = "HH:mm:ss dd-MM-yyyy")
    private LocalDateTime eventEndDate;

    @OneToMany(mappedBy = "hackathon")
    private List<Team> teams;

    @ElementCollection
    @CollectionTable(name = "hackathon_participants",
            joinColumns = @JoinColumn(name = "hackathon_id"))
    @Column(name = "participant_id")
    @Builder.Default
    private Set<Long> hackathonParticipantsIds = new HashSet<>();
}
