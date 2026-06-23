package com.cardlab.advisor.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRatingRepository extends JpaRepository<CardRatingEntity, Long> {

    boolean existsByEventId(String eventId);

    List<CardRatingEntity> findByCardId(String cardId);

    long countByCardId(String cardId);

    @Query("select avg(r.score) from CardRatingEntity r where r.cardId = :cardId")
    Double findAverageScoreByCardId(@Param("cardId") String cardId);
}
