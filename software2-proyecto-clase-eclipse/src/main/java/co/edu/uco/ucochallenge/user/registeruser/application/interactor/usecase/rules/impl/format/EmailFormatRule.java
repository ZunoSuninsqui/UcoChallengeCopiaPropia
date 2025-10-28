package co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.rules.impl.format;

import co.edu.uco.ucochallenge.application.interactor.usecase.rules.Rule;
import co.edu.uco.ucochallenge.crosscuting.exception.ExceptionLayer;
import co.edu.uco.ucochallenge.crosscuting.exception.UcoChallengeException;
import co.edu.uco.ucochallenge.crosscuting.helper.TextHelper;
import co.edu.uco.ucochallenge.crosscuting.messages.MessageKey;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.rules.RegisterUserContext;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.rules.RegisterUserRuleNames;

@Component
public class EmailFormatRule implements Rule<RegisterUserContext> {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Override
    public String getName() {
        return RegisterUserRuleNames.EMAIL_FORMAT;
    }

    @Override
    public boolean evaluate(RegisterUserContext context) {
        String email = TextHelper.getDefaultWithTrim(context.getEmail());

        if (TextHelper.isEmpty(email)) {
            throw UcoChallengeException.createUserException(
                    ExceptionLayer.RULE,
                    MessageKey.RegisterUser.EMAIL_REQUIRED);
        }

        if (email.length() < 10 || email.length() > 100) {
            throw UcoChallengeException.createUserException(
                    ExceptionLayer.RULE,
                    MessageKey.RegisterUser.EMAIL_LENGTH);
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw UcoChallengeException.createUserException(
                    ExceptionLayer.RULE,
                    MessageKey.RegisterUser.EMAIL_INVALID_FORMAT);
        }

        return true;
    }
}