package co.edu.uco.ucochallenge.application.interactor.usecase.rules;

public interface Rule<C> {
    String getName();
    boolean evaluate(C context);
}
