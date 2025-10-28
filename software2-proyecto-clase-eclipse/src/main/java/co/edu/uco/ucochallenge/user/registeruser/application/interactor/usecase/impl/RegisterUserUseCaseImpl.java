package co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.impl;

import org.springframework.stereotype.Service;

import co.edu.uco.ucochallenge.application.Void;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.mapper.RegisterUserMapper;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.RegisterUserUseCase;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.rules.RegisterUserContext;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.rules.RegisterUserRuleNames;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.rules.RuleEngine;
import co.edu.uco.ucochallenge.user.registeruser.application.usecase.domain.RegisterUserDomain;
import co.edu.uco.ucochallenge.user.shared.application.port.out.UserPersistencePort;

@Service
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final UserPersistencePort userPersistencePort;
    private final RuleEngine ruleEngine;
    private final RegisterUserMapper mapper;

    public RegisterUserUseCaseImpl(final UserPersistencePort userPersistencePort, final RuleEngine ruleEngine,
            final RegisterUserMapper mapper) {
        this.userPersistencePort = userPersistencePort;
    this.ruleEngine = ruleEngine;
    this.mapper = mapper;
}

	@Override
	public Void execute(final RegisterUserDomain domain) {

		RegisterUserContext context = new RegisterUserContext(domain);

		applyFormatRules(context);
		applyUniquenessRules(context);

        userPersistencePort.save(domain);
        return Void.returnVoid();
}

	private void applyFormatRules(RegisterUserContext context) {
		ruleEngine.applyRule(RegisterUserRuleNames.ID_NUMBER_FORMAT, context);
		ruleEngine.applyRule(RegisterUserRuleNames.EMAIL_FORMAT, context);
		ruleEngine.applyRule(RegisterUserRuleNames.MOBILE_NUMBER_FORMAT, context);
	}

	private void applyUniquenessRules(RegisterUserContext context) {
		ruleEngine.applyRule(RegisterUserRuleNames.UNIQUE_ID_TYPE_NUMBER, context);
		ruleEngine.applyRule(RegisterUserRuleNames.UNIQUE_EMAIL, context);
		ruleEngine.applyRule(RegisterUserRuleNames.UNIQUE_MOBILE_NUMBER, context);
	}
}
