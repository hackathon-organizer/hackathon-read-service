package com.hackathonorganizer.hackathonreadservice.hackathon.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriteriaAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Answer must have value")
    private Integer value;

    @NotNull(message = "Team id must not be null")
    private Long teamId;

    @NotNull(message = "User id must not be null")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "criteria_id")
    private Criteria criteria;
}
