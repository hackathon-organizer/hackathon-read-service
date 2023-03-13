package com.hackathonorganizer.hackathonreadservice.utils;

import com.hackathonorganizer.hackathonreadservice.hackathon.model.Criteria;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.CriteriaAnswer;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.CriteriaAnswerDto;
import com.hackathonorganizer.hackathonreadservice.hackathon.model.dto.CriteriaDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CriteriaMapper {

    public static CriteriaDto mapToDto(Criteria criteria) {

        return new CriteriaDto(
                criteria.getId(),
                criteria.getName(),
                criteria.getCriteriaAnswers().stream().map(CriteriaMapper::mapToCriteriaAnswerDto).toList()
        );
    }

    public static CriteriaAnswerDto mapToCriteriaAnswerDto(CriteriaAnswer criteriaAnswer) {

        return new CriteriaAnswerDto(
                criteriaAnswer.getId(),
                criteriaAnswer.getValue(),
                criteriaAnswer.getTeamId(),
                criteriaAnswer.getUserId(),
                criteriaAnswer.getCriteria().getId()
        );
    }
}
