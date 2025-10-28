package co.edu.uco.ucochallenge.user.registeruser.application.interactor.mapper;

import org.springframework.stereotype.Component;

import co.edu.uco.ucochallenge.user.registeruser.application.interactor.dto.RegisterUserInputDTO;
import co.edu.uco.ucochallenge.user.registeruser.application.usecase.domain.RegisterUserDomain;

@Component
public class RegisterUserMapper {

        public RegisterUserDomain toDomain(final RegisterUserInputDTO dto) {
                return toDomainInternal(dto);
        }

        private RegisterUserDomain toDomainInternal(final RegisterUserInputDTO dto) {
                if (dto == null) {
                        return null;
                }

                return RegisterUserDomain.construir()
                                .conTipoIdentificacion(dto.idType())
                                .conNumeroIdentificacion(dto.idNumber())
                                .conPrimerNombre(dto.firstName())
                                .conSegundoNombre(dto.secondName())
                                .conPrimerApellido(dto.firstSurname())
                                .conSegundoApellido(dto.secondSurname())
                                .conCiudadResidencia(dto.homeCity())
                                .conCorreo(dto.email())
                                .conNumeroCelular(dto.mobileNumber())
                                .construir();
        }
}