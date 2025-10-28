package co.edu.uco.ucochallenge.primary.controller.advice;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import co.edu.uco.ucochallenge.crosscuting.exception.ExceptionLayer;
import co.edu.uco.ucochallenge.crosscuting.exception.UcoChallengeException;
import co.edu.uco.ucochallenge.crosscuting.helper.TextHelper;
import co.edu.uco.ucochallenge.crosscuting.messages.MessageKey;
import co.edu.uco.ucochallenge.crosscuting.parameters.ParameterKey;
import co.edu.uco.ucochallenge.secondary.ports.service.MessageServicePort;
import co.edu.uco.ucochallenge.secondary.ports.service.ParameterServicePort;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final MessageServicePort messageServicePort;
    private final ParameterServicePort parameterServicePort;

    public GlobalExceptionHandler(final MessageServicePort messageServicePort,
            final ParameterServicePort parameterServicePort) {
        this.messageServicePort = messageServicePort;
        this.parameterServicePort = parameterServicePort;
    }

    @ExceptionHandler(UcoChallengeException.class)
    public ResponseEntity<ApiErrorResponse> handleUcoChallengeException(final UcoChallengeException exception) {
        if (exception.isTechnical()) {
            LOGGER.error("Technical exception captured", exception);
        } else {
            LOGGER.debug("User exception captured: {}", exception.getUserMessageKey());
        }

        final HttpStatus status = exception.isUser() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        final String userMessage = resolveUserMessage(exception);
        final String technicalMessage = resolveTechnicalMessage(exception);
        final ApiErrorResponse payload = new ApiErrorResponse(exception.getType().name(),
                exception.getLayer().name(), userMessage, technicalMessage,
                Optional.ofNullable(exception.getUserMessageKey()).orElse(MessageKey.GENERAL_USER_ERROR),
                resolveSupportContact(exception), Instant.now());
        return new ResponseEntity<>(payload, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(final Exception exception) {
        LOGGER.error("Unexpected exception captured", exception);
        final UcoChallengeException wrapped = UcoChallengeException.createTechnicalException(ExceptionLayer.GENERAL,
                MessageKey.GENERAL_TECHNICAL_ERROR, exception);
        return handleUcoChallengeException(wrapped);
    }

    private String resolveUserMessage(final UcoChallengeException exception) {
        final String key = Optional.ofNullable(exception.getUserMessageKey()).orElse(MessageKey.GENERAL_USER_ERROR);
        return messageServicePort.getMessage(key);
    }

    private String resolveTechnicalMessage(final UcoChallengeException exception) {
        if (exception.isUser()) {
            return TextHelper.getDefault();
        }
        final String key = Optional.ofNullable(exception.getTechnicalMessageKey())
                .orElse(MessageKey.GENERAL_TECHNICAL_ERROR);
        return messageServicePort.getMessage(key);
    }

    private String resolveSupportContact(final UcoChallengeException exception) {
        if (exception.isTechnical()) {
            final String supportEmail = parameterServicePort.getParameter(ParameterKey.Notification.ADMIN_EMAIL);
            return TextHelper.isEmpty(supportEmail) ? TextHelper.getDefault() : supportEmail;
        }
        return TextHelper.getDefault();
    }
}