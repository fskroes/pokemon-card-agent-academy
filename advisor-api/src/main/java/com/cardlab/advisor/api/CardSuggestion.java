package com.cardlab.advisor.api;

import com.cardlab.advisor.domain.CardTag;
import com.cardlab.advisor.domain.CardId;

import java.util.List;

public record CardSuggestion(
        CardId cardId,
        String cardName,
        double averageRating,
        int ratingCount,
        List<CardTag> matchedTags,
        String lastReviewDescription
) {
}
