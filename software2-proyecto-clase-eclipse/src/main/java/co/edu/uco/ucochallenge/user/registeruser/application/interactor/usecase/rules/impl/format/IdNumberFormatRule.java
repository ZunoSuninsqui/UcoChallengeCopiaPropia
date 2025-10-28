package co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.rules.impl.format;

import co.edu.uco.ucochallenge.application.interactor.usecase.rules.Rule;
import co.edu.uco.ucochallenge.crosscuting.exception.ExceptionLayer;
import co.edu.uco.ucochallenge.crosscuting.exception.UcoChallengeException;
import co.edu.uco.ucochallenge.crosscuting.helper.TextHelper;
import co.edu.uco.ucochallenge.crosscuting.messages.MessageKey;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.rules.RegisterUserContext;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.rules.RegisterUserRuleNames;
import org.springframework.stereotype.Component;

@Component
public class IdNumberFormatRule implements Rule<RegisterUserContext> {

    @Override
    public String getName() {
        return RegisterUserRuleNames.ID_NUMBER_FORMAT;
    }

    @Override
    public boolean evaluate(RegisterUserContext context) {
        String idNumber = TextHelper.getDefaultWithTrim(context.getIdNumber());

        if (TextHelper.isEmpty(idNumber)) {
            throw UcoChallengeException.createUserException(
                    ExceptionLayer.RULE,
                    MessageKey.RegisterUser.ID_NUMBER_REQUIRED);
        }

        if (idNumber.length() < 5 || idNumber.length() > 20) {
            throw UcoChallengeException.createUserException(
                    ExceptionLayer.RULE,
                    MessageKey.RegisterUser.ID_NUMBER_LENGTH);
        }

        if (!idNumber.matches("^[0-9]+$")) {
            throw UcoChallengeException.createUserException(
                    ExceptionLayer.RULE,
                    MessageKey.RegisterUser.ID_NUMBER_INVALID_FORMAT);
        }

        return true;
    }
}