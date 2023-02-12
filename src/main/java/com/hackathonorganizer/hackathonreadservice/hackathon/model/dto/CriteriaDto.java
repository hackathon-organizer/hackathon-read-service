package com.hackathonorganizer.hackathonreadservice.hackathon.model.dto;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.CriteriaAnswer;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public record CriteriaDto(

        Long id,
        String name,
        List<CriteriaAnswerDto> answers
) {
}
