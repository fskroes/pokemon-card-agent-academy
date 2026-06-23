package com.cardlab.advisor.api;

import com.cardlab.advisor.domain.CardTag;
import com.cardlab.advisor.service.SuggestionService;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suggestions")
@Validated
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping
    public SuggestionResponse suggest(
            @RequestParam("tags") @Size(max = 3) List<CardTag> tags
    ) {
        return suggestionService.suggest(tags);
    }
}
