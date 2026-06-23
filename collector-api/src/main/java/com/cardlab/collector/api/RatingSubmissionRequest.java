package com.cardlab.collector.api;

import com.cardlab.collector.domain.CardTag;
import com.cardlab.collector.domain.CardId;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RatingSubmissionRequest(
        @NotNull CardId cardId,
        @NotBlank String userId,
        @NotNull @Min(1) @Max(5) Integer score,
        @NotEmpty @Size(max = 3) List<@NotNull CardTag> tags,
        @NotBlank @Size(max = 2000) String experience
) {
}
