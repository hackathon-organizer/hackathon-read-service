package com.hackathonorganizer.hackathonreadservice.team.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

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

    private String fromUserName;

    private Long toUserId;

    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;

    private String teamName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Team team;
}
