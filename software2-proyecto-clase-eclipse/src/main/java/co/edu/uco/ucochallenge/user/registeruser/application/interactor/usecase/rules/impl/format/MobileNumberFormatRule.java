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
public class MobileNumberFormatRule implements Rule<RegisterUserContext> {

    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[0-9]{10}$");

    @Override
    public String getName() {
        return RegisterUserRuleNames.MOBILE_NUMBER_FORMAT;
    }

    @Override
    public boolean evaluate(RegisterUserContext context) {
    	
        String mobileNumber = TextHelper.getDefaultWithTrim(context.getMobileNumber());

        if (TextHelper.isEmpty(mobileNumber)) {
            throw UcoChallengeException.createUserException(
                    ExceptionLayer.RULE,
                    MessageKey.RegisterUser.MOBILE_REQUIRED);
        }

        if (!MOBILE_PATTERN.matcher(mobileNumber).matches()) {
            throw UcoChallengeException.createUserException(
                    ExceptionLayer.RULE,
                    MessageKey.RegisterUser.MOBILE_INVALID_FORMAT);
        }

        return true;
    }
}