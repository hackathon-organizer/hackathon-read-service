package com.hackathonorganizer.hackathonreadservice.utils;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.Criteria;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.CriteriaAnswer;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.CriteriaAnswerResponse;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.CriteriaResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CriteriaMapper {

    public static CriteriaResponse mapToDto(Criteria criteria) {

        return new CriteriaResponse(
                criteria.getId(),
                criteria.getName(),
                criteria.getCriteriaAnswers().stream().map(CriteriaMapper::mapToCriteriaAnswerDto).toList()
        );
    }

    public static CriteriaAnswerResponse mapToCriteriaAnswerDto(CriteriaAnswer criteriaAnswer) {

        return new CriteriaAnswerResponse(
                criteriaAnswer.getId(),
                criteriaAnswer.getValue(),
                criteriaAnswer.getTeamId(),
                criteriaAnswer.getUserId(),
                criteriaAnswer.getCriteria().getId()
        );
    }
}
