package com.hackathonorganizer.hackathonreadservice.exception;

import java.util.List;

public record ErrorResponse (
    String message,
    List<String> details
) {
}
