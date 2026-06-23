package com.cardlab.collector.service;

import com.cardlab.collector.api.RatingSubmissionRequest;
import com.cardlab.collector.domain.RatingEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RatingPublisherService {

    private final KafkaTemplate<String, RatingEvent> kafkaTemplate;
    private final String ratingsTopic;

    public RatingPublisherService(
            KafkaTemplate<String, RatingEvent> kafkaTemplate,
            @Value("${app.kafka.topics.ratings}") String ratingsTopic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.ratingsTopic = ratingsTopic;
    }

    public UUID publish(RatingSubmissionRequest request) {
        RatingEvent event = new RatingEvent(
                UUID.randomUUID(),
                request.cardId(),
                request.userId(),
                request.score(),
                request.tags(),
                request.experience(),
                Instant.now()
        );

        // Intentionally flawed for Exercise 01: no card-based message key is used.
        kafkaTemplate.send(ratingsTopic, event);
        return event.eventId();
    }
}
