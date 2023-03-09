package com.hackathonorganizer.hackathonreadservice.hackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hackathon", "criteriaAnswers"})
public class Criteria {

    @Id
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;

    @OneToMany(mappedBy = "criteria", cascade = CascadeType.ALL)
    private Set<CriteriaAnswer> criteriaAnswers;

}
