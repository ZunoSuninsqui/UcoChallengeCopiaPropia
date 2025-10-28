package co.edu.uco.ucochallenge.secondary.adapters.repository;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uco.ucochallenge.secondary.adapters.repository.jpa.UserJpaRepository;
import co.edu.uco.ucochallenge.secondary.adapters.repository.mapper.ListUsersEntityMapper;
import co.edu.uco.ucochallenge.secondary.adapters.repository.mapper.RegisterUserEntityMapper;
import co.edu.uco.ucochallenge.user.listusers.application.usecase.domain.ListUsersPageDomain;
import co.edu.uco.ucochallenge.user.listusers.application.usecase.domain.ListUsersQueryDomain;
import co.edu.uco.ucochallenge.user.registeruser.application.usecase.domain.RegisterUserDomain;
import co.edu.uco.ucochallenge.user.shared.application.port.out.UserPersistencePort;

@Component
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserJpaRepository userJpaRepository;
    private final RegisterUserEntityMapper registerUserEntityMapper;
    private final ListUsersEntityMapper listUsersEntityMapper;

    public UserPersistenceAdapter(final UserJpaRepository userJpaRepository,
            final RegisterUserEntityMapper registerUserEntityMapper,
            final ListUsersEntityMapper listUsersEntityMapper) {
        this.userJpaRepository = userJpaRepository;
        this.registerUserEntityMapper = registerUserEntityMapper;
        this.listUsersEntityMapper = listUsersEntityMapper;
    }

    @Override
    @Transactional
    public void save(final RegisterUserDomain domain) {
        userJpaRepository.save(registerUserEntityMapper.toEntity(domain));
    }

    @Override
    public boolean existsByIdTypeAndIdNumber(final UUID idType, final String idNumber) {
        return userJpaRepository.existsByIdTypeIdAndIdNumber(idType, idNumber);
    }

    @Override
    public boolean existsByEmailIgnoreCase(final String email) {
        return userJpaRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public boolean existsByMobileNumber(final String mobileNumber) {
        return userJpaRepository.existsByMobileNumber(mobileNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public ListUsersPageDomain list(final ListUsersQueryDomain query) {
        final var pageable = PageRequest.of(query.getPage(), query.getSize());
        final var page = userJpaRepository.findAll(pageable);
        return listUsersEntityMapper.toPageDomain(page);
    }
}
