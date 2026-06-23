package com.cardlab.advisor.service;

import com.cardlab.advisor.api.CardSuggestion;
import com.cardlab.advisor.api.SuggestionResponse;
import com.cardlab.advisor.domain.CardTag;
import com.cardlab.advisor.domain.CardId;
import com.cardlab.advisor.persistence.CardEntity;
import com.cardlab.advisor.persistence.CardRatingEntity;
import com.cardlab.advisor.persistence.CardRatingRepository;
import com.cardlab.advisor.persistence.CardRepository;
import com.cardlab.advisor.persistence.CardStatsEntity;
import com.cardlab.advisor.persistence.CardStatsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SuggestionService {

    private final CardRepository cardRepository;
    private final CardRatingRepository cardRatingRepository;
    private final CardStatsRepository cardStatsRepository;

    public SuggestionService(
            CardRepository cardRepository,
            CardRatingRepository cardRatingRepository,
            CardStatsRepository cardStatsRepository
    ) {
        this.cardRepository = cardRepository;
        this.cardRatingRepository = cardRatingRepository;
        this.cardStatsRepository = cardStatsRepository;
    }

    public SuggestionResponse suggest(List<CardTag> requestedTags) {
        if (requestedTags == null || requestedTags.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one tag must be supplied.");
        }
        if (requestedTags.size() > 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At most 3 tags are supported.");
        }

        Set<CardTag> requestSet = EnumSet.copyOf(requestedTags);
        Map<String, CardStatsEntity> statsByCard = cardStatsRepository.findAll().stream()
                .collect(Collectors.toMap(CardStatsEntity::getCardId, value -> value));

        List<CardSuggestion> suggestions = cardRepository.findAll().stream()
                .map(card -> buildSuggestion(card, requestSet, statsByCard.get(card.getCardId())))
                .filter(candidate -> !candidate.matchedTags().isEmpty())
                .sorted(
                        Comparator.comparingDouble(CardSuggestion::averageRating).reversed()
                                .thenComparing(Comparator.comparingInt((CardSuggestion candidate) -> candidate.matchedTags().size()).reversed())
                                .thenComparing(CardSuggestion::cardName)
                )
                .toList();

        return new SuggestionResponse(suggestions);
    }

    private CardSuggestion buildSuggestion(CardEntity card, Set<CardTag> requestSet, CardStatsEntity stats) {
        Set<CardTag> cardTags = cardRatingRepository.findByCardId(card.getCardId()).stream()
                .map(CardRatingEntity::getTags)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        List<CardTag> matched = requestSet.stream()
                .filter(cardTags::contains)
                .sorted(Enum::compareTo)
                .toList();

        return new CardSuggestion(
                CardId.valueOf(card.getCardId()),
                card.getDisplayName(),
                stats == null ? 0.0 : stats.getAverageScore(),
                stats == null ? 0 : stats.getRatingCount(),
                matched,
                stats == null ? null : stats.getLastReviewExperience()
        );
    }
}
