package com.cardlab.advisor.service;

import com.cardlab.advisor.domain.CardTag;
import com.cardlab.advisor.domain.CardId;
import com.cardlab.advisor.persistence.CardEntity;
import com.cardlab.advisor.persistence.CardRatingEntity;
import com.cardlab.advisor.persistence.CardRatingRepository;
import com.cardlab.advisor.persistence.CardRepository;
import com.cardlab.advisor.persistence.CardStatsEntity;
import com.cardlab.advisor.persistence.CardStatsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class BaselineDataSeeder implements CommandLineRunner {

    private final CardRepository cardRepository;
    private final CardRatingRepository cardRatingRepository;
    private final CardStatsRepository cardStatsRepository;

    public BaselineDataSeeder(
            CardRepository cardRepository,
            CardRatingRepository cardRatingRepository,
            CardStatsRepository cardStatsRepository
    ) {
        this.cardRepository = cardRepository;
        this.cardRatingRepository = cardRatingRepository;
        this.cardStatsRepository = cardStatsRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (cardRepository.count() > 0) {
            return;
        }

        Random random = new Random(42);
        List<String> users = new ArrayList<>();
        for (int index = 1; index <= 15; index++) {
            users.add(String.format("user-%02d", index));
        }

        List<String> comments = List.of(
                "Great texture and balanced sweetness.",
                "Very comforting, would order again.",
                "A little too rich for breakfast but still nice.",
                "Solid card, especially with coffee.",
                "Unexpectedly good tag combination.",
                "Crisp outside, soft middle, proper joy."
        );

        Instant cursor = Instant.now().minus(14, ChronoUnit.DAYS);
        for (CardId cardId : CardId.values()) {
            cardRepository.save(new CardEntity(cardId.name(), humanize(cardId.name())));

            int ratingCount = 1 + random.nextInt(10);
            for (int i = 0; i < ratingCount; i++) {
                CardRatingEntity rating = new CardRatingEntity();
                rating.setEventId(UUID.randomUUID().toString());
                rating.setCardId(cardId.name());
                rating.setUserId(users.get(random.nextInt(users.size())));
                rating.setScore(1 + random.nextInt(5));
                rating.setExperience(comments.get(random.nextInt(comments.size())));
                rating.setSubmittedAt(cursor.plus(2, ChronoUnit.HOURS));
                rating.setTags(randomTags(random));
                cardRatingRepository.save(rating);
                cursor = cursor.plus(2, ChronoUnit.HOURS);
            }
        }

        for (CardId cardId : CardId.values()) {
            recomputeStats(cardId.name());
        }
    }

    private Set<CardTag> randomTags(Random random) {
        CardTag[] allTags = CardTag.values();
        int pickCount = 1 + random.nextInt(3);
        Set<CardTag> picked = EnumSet.noneOf(CardTag.class);
        while (picked.size() < pickCount) {
            picked.add(allTags[random.nextInt(allTags.length)]);
        }
        return picked;
    }

    private void recomputeStats(String cardId) {
        List<CardRatingEntity> ratings = cardRatingRepository.findByCardId(cardId);
        if (ratings.isEmpty()) {
            return;
        }

        double average = ratings.stream().mapToInt(CardRatingEntity::getScore).average().orElse(0);
        CardRatingEntity lastReview = ratings.stream()
                .max(Comparator.comparing(CardRatingEntity::getSubmittedAt))
                .orElseThrow();

        CardStatsEntity stats = new CardStatsEntity(cardId);
        stats.setAverageScore(average);
        stats.setRatingCount(ratings.size());
        stats.setLastReviewSubmittedAt(lastReview.getSubmittedAt());
        stats.setLastReviewUserId(lastReview.getUserId());
        stats.setLastReviewScore(lastReview.getScore());
        stats.setLastReviewExperience(lastReview.getExperience());
        stats.setLastReviewTags(lastReview.getTags().stream().map(Enum::name).collect(Collectors.joining(",")));
        cardStatsRepository.save(stats);
    }

    private String humanize(String cardId) {
        return Arrays.stream(cardId.split("_"))
                .map(fragment -> Character.toUpperCase(fragment.charAt(0)) + fragment.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
