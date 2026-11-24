package com.cardlab.advisor.service;

import com.cardlab.advisor.domain.RatingEvent;
import com.cardlab.advisor.persistence.CardEntity;
import com.cardlab.advisor.persistence.CardRatingEntity;
import com.cardlab.advisor.persistence.CardRatingRepository;
import com.cardlab.advisor.persistence.CardRepository;
import com.cardlab.advisor.persistence.CardStatsEntity;
import com.cardlab.advisor.persistence.CardStatsRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RatingIngestionService {

    private final CardRepository cardRepository;
    private final CardRatingRepository cardRatingRepository;
    private final CardStatsRepository cardStatsRepository;

    public RatingIngestionService(
            CardRepository cardRepository,
            CardRatingRepository cardRatingRepository,
            CardStatsRepository cardStatsRepository
    ) {
        this.cardRepository = cardRepository;
        this.cardRatingRepository = cardRatingRepository;
        this.cardStatsRepository = cardStatsRepository;
    }

    @KafkaListener(topics = "${app.kafka.topics.ratings}")
    @Transactional
    public void onRatingEvent(RatingEvent event) {
        if (cardRatingRepository.existsByEventId(event.eventId().toString())) {
            return;
        }

        cardRepository.findById(event.cardId().name())
                .orElseGet(() -> cardRepository.save(new CardEntity(event.cardId().name(), humanize(event.cardId().name()))));

        CardRatingEntity rating = new CardRatingEntity();
        rating.setEventId(event.eventId().toString());
        rating.setCardId(event.cardId().name());
        rating.setUserId(event.userId());
        rating.setScore(event.score());
        rating.setExperience(event.experience());
        rating.setSubmittedAt(event.submittedAt());
        rating.setTags(Set.copyOf(event.tags()));
        cardRatingRepository.save(rating);

        refreshStatsFor(event.cardId().name(), event);
    }

    private void refreshStatsFor(String cardId, RatingEvent incomingEvent) {
        long ratingCount = cardRatingRepository.countByCardId(cardId);
        double averageScore = cardRatingRepository.findAverageScoreByCardId(cardId) == null
                ? 0
                : cardRatingRepository.findAverageScoreByCardId(cardId);

        CardStatsEntity stats = cardStatsRepository.findById(cardId).orElse(new CardStatsEntity(cardId));
        stats.setRatingCount((int) ratingCount);
        stats.setAverageScore(averageScore);

        stats.setLastReviewSubmittedAt(incomingEvent.submittedAt());
        stats.setLastReviewUserId(incomingEvent.userId());
        stats.setLastReviewScore(incomingEvent.score());
        stats.setLastReviewExperience(incomingEvent.experience());
        stats.setLastReviewTags(incomingEvent.tags().stream().map(Enum::name).collect(Collectors.joining(",")));

        cardStatsRepository.save(stats);
    }

    private String humanize(String cardId) {
        return java.util.Arrays.stream(cardId.split("_"))
                .map(fragment -> Character.toUpperCase(fragment.charAt(0)) + fragment.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
