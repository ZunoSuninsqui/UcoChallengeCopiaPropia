package co.edu.uco.ucochallenge.crosscuting.messages;

public final class MessageKey {

    private MessageKey() {
    }

    public static final String GENERAL_UNEXPECTED_ERROR = "exception.general.unexpected";
    public static final String GENERAL_TECHNICAL_ERROR = "exception.general.technical";
    public static final String GENERAL_USER_ERROR = "exception.general.user";

    public static final class RegisterUser {
        public static final String SUCCESS = "register.user.success";

        public static final String ID_TYPE_REQUIRED = "register.user.validation.idType.required";
        public static final String ID_TYPE_NOT_FOUND = "register.user.validation.idType.notFound";

        public static final String ID_NUMBER_REQUIRED = "register.user.validation.idNumber.required";
        public static final String ID_NUMBER_INVALID_FORMAT = "register.user.validation.idNumber.invalidFormat";
        public static final String ID_NUMBER_LENGTH = "register.user.validation.idNumber.length";

        public static final String NAME_REQUIRED = "register.user.validation.name.required";
        public static final String NAME_INVALID_CHARACTERS = "register.user.validation.name.invalidCharacters";
        public static final String NAME_LENGTH = "register.user.validation.name.length";

        public static final String HOME_CITY_REQUIRED = "register.user.validation.homeCity.required";
        public static final String HOME_CITY_NOT_FOUND = "register.user.validation.homeCity.notFound";

        public static final String EMAIL_REQUIRED = "register.user.validation.email.required";
        public static final String EMAIL_LENGTH = "register.user.validation.email.length";
        public static final String EMAIL_INVALID_FORMAT = "register.user.validation.email.invalidFormat";

        public static final String MOBILE_REQUIRED = "register.user.validation.mobile.required";
        public static final String MOBILE_INVALID_FORMAT = "register.user.validation.mobile.invalidFormat";

        public static final String RULE_ID_DUPLICATED = "register.user.rule.id.duplicated";
        public static final String RULE_ID_TYPE_NUMBER_DUPLICATED_ADMIN =
                "register.user.rule.idTypeNumber.duplicated.admin";
        public static final String RULE_ID_TYPE_NUMBER_DUPLICATED_EXECUTOR =
                "register.user.rule.idTypeNumber.duplicated.executor";
        public static final String RULE_EMAIL_DUPLICATED_OWNER = "register.user.rule.email.duplicated.owner";
        public static final String RULE_EMAIL_DUPLICATED_EXECUTOR = "register.user.rule.email.duplicated.executor";
        public static final String RULE_MOBILE_DUPLICATED_OWNER = "register.user.rule.mobile.duplicated.owner";
        public static final String RULE_MOBILE_DUPLICATED_EXECUTOR = "register.user.rule.mobile.duplicated.executor";

        public static final String RULE_EMAIL_CONFIRMATION_STRATEGY = "register.user.rule.email.confirmation.strategy";
        public static final String RULE_EMAIL_CONFIRMATION_PENDING = "register.user.rule.email.confirmation.pending";
        public static final String RULE_MOBILE_CONFIRMATION_STRATEGY =
                "register.user.rule.mobile.confirmation.strategy";
        public static final String RULE_MOBILE_CONFIRMATION_PENDING =
                "register.user.rule.mobile.confirmation.pending";

        private RegisterUser() {
        }
    }

    public static final class ListUsers {
        public static final String PAGE_NEGATIVE = "list.users.validation.page.negative";
        public static final String SIZE_INVALID = "list.users.validation.size.invalid";

        private ListUsers() {
        }
    }
}
