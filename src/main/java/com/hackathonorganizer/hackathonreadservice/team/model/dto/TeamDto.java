package com.hackathonorganizer.hackathonreadservice.team.model.dto;

import com.hackathonorganizer.hackathonreadservice.team.model.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public record TeamDto (

        Long id,

        String name,

        String description,

        boolean isOpen,

        List<Tag> tags
){
}
