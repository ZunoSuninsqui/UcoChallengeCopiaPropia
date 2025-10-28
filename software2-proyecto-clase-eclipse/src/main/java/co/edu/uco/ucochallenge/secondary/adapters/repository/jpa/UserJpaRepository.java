package co.edu.uco.ucochallenge.secondary.adapters.repository.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uco.ucochallenge.secondary.adapters.repository.entity.UserEntity;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByIdTypeIdAndIdNumber(UUID idType, String idNumber);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByMobileNumber(String mobileNumber);
}
