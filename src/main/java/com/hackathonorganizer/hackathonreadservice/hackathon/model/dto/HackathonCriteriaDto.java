package com.hackathonorganizer.hackathonreadservice.hackathon.model.dto;

import java.util.List;

public record HackathonCriteriaDto(

        List<CriteriaDto> criteria,
        List<CriteriaAnswerDto> answers
) {
}
