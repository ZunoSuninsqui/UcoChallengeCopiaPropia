package co.edu.uco.ucochallenge.user.listusers.application.interactor.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uco.ucochallenge.user.listusers.application.interactor.ListUsersInteractor;
import co.edu.uco.ucochallenge.user.listusers.application.interactor.dto.ListUsersInputDTO;
import co.edu.uco.ucochallenge.user.listusers.application.interactor.dto.ListUsersOutputDTO;
import co.edu.uco.ucochallenge.user.listusers.application.interactor.mapper.ListUsersMapper;
import co.edu.uco.ucochallenge.user.listusers.application.usecase.ListUsersUseCase;

@Service
@Transactional(readOnly = true)
public class ListUsersInteractorImpl implements ListUsersInteractor {

    private final ListUsersUseCase useCase;
    private final ListUsersMapper mapper;

    public ListUsersInteractorImpl(final ListUsersUseCase useCase, final ListUsersMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @Override
    public ListUsersOutputDTO execute(final ListUsersInputDTO dto) {
        var domain = mapper.toDomain(dto);
        var result = useCase.execute(domain);
        return mapper.toDTO(result);
    }
}