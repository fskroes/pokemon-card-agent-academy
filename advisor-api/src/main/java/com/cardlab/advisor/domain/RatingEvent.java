package com.cardlab.advisor.domain;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record RatingEvent(
        UUID eventId,
        CardId cardId,
        String userId,
        int score,
        List<CardTag> tags,
        String experience,
        Instant submittedAt
) {
}
