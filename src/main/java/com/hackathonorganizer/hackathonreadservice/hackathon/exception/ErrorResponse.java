package com.hackathonorganizer.hackathonreadservice.hackathon.exception;

import java.util.List;

public record ErrorResponse(
        String message,
        List<String> details
) {
}
