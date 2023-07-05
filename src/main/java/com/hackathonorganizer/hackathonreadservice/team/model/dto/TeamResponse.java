package com.hackathonorganizer.hackathonreadservice.team.model.dto;

import com.hackathonorganizer.hackathonreadservice.team.model.Tag;

import java.util.List;

public record TeamResponse(

        Long id,
        String name,
        String description,
        Long ownerId,
        boolean isOpen,
        Long teamChatRoomId,
        List<Tag> tags
) {
}
