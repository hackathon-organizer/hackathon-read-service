package com.hackathonorganizer.hackathonreadservice.hackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hackathon", "criteriaAnswers"})
@ToString
public class Criteria {

    @Id
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;

    @OneToMany(mappedBy = "criteria")
    private Set<CriteriaAnswer> criteriaAnswers;

}
