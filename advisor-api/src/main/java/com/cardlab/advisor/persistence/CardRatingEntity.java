package com.cardlab.advisor.persistence;

import com.cardlab.advisor.domain.CardTag;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "card_rating")
public class CardRatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false, unique = true, length = 64)
    private String eventId;

    @Column(name = "card_id", nullable = false, length = 64)
    private String cardId;

    @Column(name = "user_id", nullable = false, length = 120)
    private String userId;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "experience", nullable = false, length = 2000)
    private String experience;

    @Column(name = "submitted_at", nullable = false)
    private Instant submittedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "card_rating_tag", joinColumns = @JoinColumn(name = "rating_id"))
    @Column(name = "tag", nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private Set<CardTag> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Instant submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Set<CardTag> getTags() {
        return tags;
    }

    public void setTags(Set<CardTag> tags) {
        this.tags = tags;
    }
}
