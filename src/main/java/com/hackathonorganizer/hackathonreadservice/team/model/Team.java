package com.hackathonorganizer.hackathonreadservice.team.model;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.Hackathon;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private Long ownerId;

    private String description;

    @NotNull
    @Builder.Default
    @ColumnDefault("true")
    private Boolean isOpen = true;

    private Long chatRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotEmpty
    private Hackathon hackathon;

    @ElementCollection
    @CollectionTable(name = "team_members", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "team_member_id")
    private Set<Long> teamMembersIds;

    @ManyToMany
    @JoinTable(name = "team_tags", joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;
}
