package com.cardlab.advisor.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "card")
public class CardEntity {

    @Id
    @Column(name = "card_id", nullable = false, length = 64)
    private String cardId;

    @Column(name = "display_name", nullable = false, length = 120)
    private String displayName;

    public CardEntity() {
    }

    public CardEntity(String cardId, String displayName) {
        this.cardId = cardId;
        this.displayName = displayName;
    }

    public String getCardId() {
        return cardId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
