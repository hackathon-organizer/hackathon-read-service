package com.hackathonorganizer.hackathonreadservice.hackathon.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class CriteriaAnswer {

    @Id
    private Long id;

    private Integer value;

    @NotNull
    private Long teamId;

    @NotNull
    private Long userId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "criteria_id")
    private Criteria criteria;
}
