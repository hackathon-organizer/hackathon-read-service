package com.hackathonorganizer.hackathonreadservice.hackathon.model.dto;

import java.util.List;

public record CriteriaResponse(

        Long id,
        String name,
        List<CriteriaAnswerResponse> answers
) {
}
