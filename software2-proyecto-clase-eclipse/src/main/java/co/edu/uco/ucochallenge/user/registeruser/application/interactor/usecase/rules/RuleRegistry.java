package co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.rules;

import co.edu.uco.ucochallenge.application.interactor.usecase.rules.Rule;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RuleRegistry {
	private final Map<String, Rule<RegisterUserContext>> rulesMap = new HashMap<>();

	public RuleRegistry(List<Rule<RegisterUserContext>> rules) {
		for (Rule<RegisterUserContext> rule : rules) {
			rulesMap.put(rule.getName(), rule);
		}
	}

    public Rule<RegisterUserContext> getRule(String ruleName) {
		return rulesMap.get(ruleName);
	}
}
