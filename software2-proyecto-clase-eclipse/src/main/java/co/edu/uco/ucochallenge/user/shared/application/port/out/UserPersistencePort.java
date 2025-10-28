package co.edu.uco.ucochallenge.user.shared.application.port.out;

import java.util.UUID;

import co.edu.uco.ucochallenge.user.listusers.application.usecase.domain.ListUsersPageDomain;
import co.edu.uco.ucochallenge.user.listusers.application.usecase.domain.ListUsersQueryDomain;
import co.edu.uco.ucochallenge.user.registeruser.application.usecase.domain.RegisterUserDomain;

/**
 * Defines the output contract that the application layer uses to interact with
 * the persistence mechanism for user aggregates.
 */
public interface UserPersistencePort {

    void save(RegisterUserDomain domain);

    boolean existsByIdTypeAndIdNumber(UUID idType, String idNumber);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByMobileNumber(String mobileNumber);

    ListUsersPageDomain list(ListUsersQueryDomain query);
}
