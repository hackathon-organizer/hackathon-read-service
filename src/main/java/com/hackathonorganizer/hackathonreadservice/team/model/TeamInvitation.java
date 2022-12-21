package com.hackathonorganizer.hackathonreadservice.team.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity(name = "team_invitations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String fromUserName;

    @NotNull
    private Long toUserId;

    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;

    @NotEmpty
    private String teamName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Team team;
}
