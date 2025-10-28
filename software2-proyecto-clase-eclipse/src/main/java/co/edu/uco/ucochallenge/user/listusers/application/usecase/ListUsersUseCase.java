package co.edu.uco.ucochallenge.user.listusers.application.usecase;

import co.edu.uco.ucochallenge.application.interactor.usecase.UseCase;
import co.edu.uco.ucochallenge.user.listusers.application.usecase.domain.ListUsersPageDomain;
import co.edu.uco.ucochallenge.user.listusers.application.usecase.domain.ListUsersQueryDomain;


public interface ListUsersUseCase extends UseCase<ListUsersQueryDomain, ListUsersPageDomain> {

}