package com.cardlab.advisor.api;

import java.util.List;

public record SuggestionResponse(List<CardSuggestion> suggestions) {
}
