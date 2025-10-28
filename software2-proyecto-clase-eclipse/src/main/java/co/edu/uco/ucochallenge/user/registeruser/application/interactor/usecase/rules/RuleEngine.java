package co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.rules;
import co.edu.uco.ucochallenge.crosscuting.exception.ExceptionLayer;
import co.edu.uco.ucochallenge.crosscuting.exception.UcoChallengeException;
import co.edu.uco.ucochallenge.crosscuting.messages.MessageKey;

import co.edu.uco.ucochallenge.application.interactor.usecase.rules.Rule;
import org.springframework.stereotype.Service;

@Service
public class RuleEngine {
    private final RuleRegistry ruleRegistry;

    public RuleEngine(RuleRegistry ruleRegistry) {
        this.ruleRegistry = ruleRegistry;
    }

    public boolean applyRule(String ruleName, RegisterUserContext context) {
        Rule<RegisterUserContext> rule = ruleRegistry.getRule(ruleName);
        if (rule == null) {
            throw UcoChallengeException.createTechnicalException(
                    ExceptionLayer.RULE,
                    MessageKey.GENERAL_TECHNICAL_ERROR);
        }
        return rule.evaluate(context);
    }
}
