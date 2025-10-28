package co.edu.uco.ucochallenge.primary.controller.advice;

import java.time.Instant;

public record ApiErrorResponse(String type, String layer, String userMessage, String technicalMessage,
        String messageKey, String supportContact, Instant timestamp) {
}