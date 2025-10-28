package co.edu.uco.ucochallenge.crosscuting.exception;

import java.util.Optional;

import co.edu.uco.ucochallenge.crosscuting.helper.ObjectHelper;


public final class UcoChallengeException extends RuntimeException {

    private static final long serialVersionUID = 7374425466357071915L;

    private final ExceptionType type;
    private final ExceptionLayer layer;
    private final String userMessageKey;
    private final String technicalMessageKey;

    private UcoChallengeException(final ExceptionType type, final ExceptionLayer layer, final String userMessageKey,
            final String technicalMessageKey, final Throwable cause) {
    	
        super(Optional.ofNullable(technicalMessageKey).orElse(userMessageKey), cause, false, true);
        this.type = ObjectHelper.getDefault(type, ExceptionType.TECHNICAL);
        this.layer = ObjectHelper.getDefault(layer, ExceptionLayer.GENERAL);
        this.userMessageKey = userMessageKey;
        this.technicalMessageKey = technicalMessageKey;
    }

    public static UcoChallengeException createUserException(final ExceptionLayer layer, final String userMessageKey) {
        return new UcoChallengeException(ExceptionType.USER, layer, userMessageKey, null, null);
    }

    public static UcoChallengeException createUserException(final ExceptionLayer layer, final String userMessageKey,
            final String technicalMessageKey, final Throwable cause) {
        return new UcoChallengeException(ExceptionType.USER, layer, userMessageKey, technicalMessageKey, cause);
    }

    public static UcoChallengeException createTechnicalException(final ExceptionLayer layer,
            final String technicalMessageKey) {
        return new UcoChallengeException(ExceptionType.TECHNICAL, layer, null, technicalMessageKey, null);
    }

    public static UcoChallengeException createTechnicalException(final ExceptionLayer layer,
            final String technicalMessageKey, final Throwable cause) {
        return new UcoChallengeException(ExceptionType.TECHNICAL, layer, null, technicalMessageKey, cause);
    }

    public static UcoChallengeException wrap(final ExceptionType type, final ExceptionLayer layer,
            final String userMessageKey, final String technicalMessageKey, final Throwable cause) {
        return new UcoChallengeException(type, layer, userMessageKey, technicalMessageKey, cause);
    }

    public ExceptionType getType() {
        return type;
    }

    public ExceptionLayer getLayer() {
        return layer;
    }

    public String getUserMessageKey() {
        return userMessageKey;
    }

    public String getTechnicalMessageKey() {
        return technicalMessageKey;
    }

    public boolean isTechnical() {
        return ExceptionType.TECHNICAL.equals(type);
    }

    public boolean isUser() {
        return ExceptionType.USER.equals(type);
    }
}