package com.hackathonorganizer.hackathonreadservice.hackathon.model.dto;

public record CriteriaAnswerDto(

        Long id,
        Integer value,
        Long teamId,
        Long userId,
        Long criteriaId
) {
}
