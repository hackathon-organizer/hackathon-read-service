package com.hackathonorganizer.hackathonreadservice.hackathon.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hackathonorganizer.hackathonreadservice.team.model.Team;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private String organizerInfo;

    @Builder.Default
    private boolean isActive = true;

    @NotNull
    @JsonFormat(pattern = "HH:mm:ss dd-MM-yyyy")
    private LocalDateTime eventStartDate;

    @NotNull
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
