package com.cardlab.collector.api;

import java.time.Instant;
import java.util.UUID;

public record RatingAcceptedResponse(
        UUID eventId,
        Instant acceptedAt,
        String message
) {
}
