package co.edu.uco.ucochallenge.crosscuting.parameters;

public final class ParameterKey {

    private ParameterKey() {
    }

    public static final class Notification {
        public static final String ADMIN_EMAIL = "notification.admin.email";
        public static final String DUPLICATED_EMAIL_TEMPLATE = "notification.duplicated.email.template";
        public static final String DUPLICATED_MOBILE_TEMPLATE = "notification.duplicated.mobile.template";
        public static final String EMAIL_CONFIRMATION_STRATEGY = "notification.confirmation.email.strategy";
        public static final String MOBILE_CONFIRMATION_STRATEGY = "notification.confirmation.mobile.strategy";
        public static final String EMAIL_MAX_RETRIES = "notification.email.maxRetries";

        private Notification() {
        }
    }
}