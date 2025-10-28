package co.edu.uco.ucochallenge.user.registeruser.application.interactor.impl;

import org.springframework.stereotype.Service;

import co.edu.uco.ucochallenge.application.Void;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.RegisterUserInteractor;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.dto.RegisterUserInputDTO;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.RegisterUserUseCase;
import co.edu.uco.ucochallenge.user.registeruser.application.usecase.domain.RegisterUserDomain;
import jakarta.transaction.Transactional;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.mapper.RegisterUserMapper;

@Service
@Transactional
public class RegisterUserInteractorImpl implements RegisterUserInteractor {

    private final RegisterUserUseCase useCase;
    private final RegisterUserMapper mapper;


    public RegisterUserInteractorImpl(final RegisterUserUseCase useCase, final RegisterUserMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @Override
    public Void execute(final RegisterUserInputDTO dto) {
            RegisterUserDomain registerUserDomain = mapper.toDomain(dto);
            return useCase.execute(registerUserDomain);
    }

}
