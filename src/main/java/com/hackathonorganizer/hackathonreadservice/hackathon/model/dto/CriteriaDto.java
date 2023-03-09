package com.hackathonorganizer.hackathonreadservice.hackathon.model.dto;

import java.util.List;

public record CriteriaDto(

        Long id,
        String name,
        List<CriteriaAnswerDto> answers
) {
}
