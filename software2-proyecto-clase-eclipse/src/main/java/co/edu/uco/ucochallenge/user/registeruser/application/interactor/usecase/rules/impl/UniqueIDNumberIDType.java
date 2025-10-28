package co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.rules.impl;

import co.edu.uco.ucochallenge.application.interactor.usecase.rules.Rule;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.rules.RegisterUserContext;
import co.edu.uco.ucochallenge.crosscuting.exception.ExceptionLayer;
import co.edu.uco.ucochallenge.crosscuting.exception.UcoChallengeException;
import co.edu.uco.ucochallenge.crosscuting.helper.TextHelper;
import co.edu.uco.ucochallenge.crosscuting.helper.UUIDHelper;
import co.edu.uco.ucochallenge.crosscuting.messages.MessageKey;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.rules.RegisterUserRuleNames;
import co.edu.uco.ucochallenge.user.shared.application.port.out.UserPersistencePort;
import java.util.UUID;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component
public class UniqueIDNumberIDType implements Rule<RegisterUserContext> {

        private final UserPersistencePort userPersistencePort;

        public UniqueIDNumberIDType(UserPersistencePort userPersistencePort) {
                this.userPersistencePort = userPersistencePort;
        }

	@Override
	public String getName() {
		return RegisterUserRuleNames.UNIQUE_ID_TYPE_NUMBER;
	}

	@Override
	public boolean evaluate(RegisterUserContext context) {
		UUID idType = context.getIdType();
		String idNumber = TextHelper.getDefaultWithTrim(context.getIdNumber());

		if (idType == null || UUIDHelper.getDefault().equals(idType)) {
			return true;
		}

		if (TextHelper.isEmpty(idNumber)) {
			return true;
		}

		try {
                        if (userPersistencePort.existsByIdTypeAndIdNumber(idType, idNumber)) {
				throw UcoChallengeException.createUserException(ExceptionLayer.RULE,
						MessageKey.RegisterUser.RULE_ID_TYPE_NUMBER_DUPLICATED_ADMIN);
			}
		} catch (DataAccessException exception) {
			throw UcoChallengeException.createTechnicalException(ExceptionLayer.RULE,
					MessageKey.GENERAL_TECHNICAL_ERROR, exception);
		}

		return true;
	}
}
