package com.cardlab.advisor.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "card_stats")
public class CardStatsEntity {

    @Id
    @Column(name = "card_id", nullable = false, length = 64)
    private String cardId;

    @Column(name = "average_score", nullable = false)
    private double averageScore;

    @Column(name = "rating_count", nullable = false)
    private int ratingCount;

    @Column(name = "last_review_submitted_at")
    private Instant lastReviewSubmittedAt;

    @Column(name = "last_review_user_id", length = 120)
    private String lastReviewUserId;

    @Column(name = "last_review_score")
    private Integer lastReviewScore;

    @Column(name = "last_review_experience", length = 2000)
    private String lastReviewExperience;

    @Column(name = "last_review_tags", length = 600)
    private String lastReviewTags;

    public CardStatsEntity() {
    }

    public CardStatsEntity(String cardId) {
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public Instant getLastReviewSubmittedAt() {
        return lastReviewSubmittedAt;
    }

    public void setLastReviewSubmittedAt(Instant lastReviewSubmittedAt) {
        this.lastReviewSubmittedAt = lastReviewSubmittedAt;
    }

    public String getLastReviewUserId() {
        return lastReviewUserId;
    }

    public void setLastReviewUserId(String lastReviewUserId) {
        this.lastReviewUserId = lastReviewUserId;
    }

    public Integer getLastReviewScore() {
        return lastReviewScore;
    }

    public void setLastReviewScore(Integer lastReviewScore) {
        this.lastReviewScore = lastReviewScore;
    }

    public String getLastReviewExperience() {
        return lastReviewExperience;
    }

    public void setLastReviewExperience(String lastReviewExperience) {
        this.lastReviewExperience = lastReviewExperience;
    }

    public String getLastReviewTags() {
        return lastReviewTags;
    }

    public void setLastReviewTags(String lastReviewTags) {
        this.lastReviewTags = lastReviewTags;
    }
}
