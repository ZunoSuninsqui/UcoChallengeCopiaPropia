package co.edu.uco.ucochallenge.user.listusers.application.usecase.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uco.ucochallenge.user.listusers.application.usecase.ListUsersUseCase;
import co.edu.uco.ucochallenge.user.listusers.application.usecase.domain.ListUsersPageDomain;
import co.edu.uco.ucochallenge.user.listusers.application.usecase.domain.ListUsersQueryDomain;
import co.edu.uco.ucochallenge.user.shared.application.port.out.UserPersistencePort;

@Service
@Transactional(readOnly = true)
public class ListUsersUseCaseImpl implements ListUsersUseCase {

    private final UserPersistencePort userPersistencePort;

    public ListUsersUseCaseImpl(final UserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

        @Override
    public ListUsersPageDomain execute(final ListUsersQueryDomain domain) {
        return userPersistencePort.list(domain);
    }
}
