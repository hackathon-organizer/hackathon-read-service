package com.hackathonorganizer.hackathonreadservice.hackathon.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriteriaAnswer {

    @Id
    private Long id;

    private Integer value;

    private Long teamId;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "criteria_id")
    private Criteria criteria;
}
