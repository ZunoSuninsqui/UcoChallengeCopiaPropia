package co.edu.uco.ucochallenge.user.listusers.application.interactor.dto;

import java.util.UUID;

public record UserSummaryDTO(UUID id, UUID idTypeId, String idTypeName, String idNumber, String firstName,
        String secondName, String firstSurname, String secondSurname, UUID homeCityId, String homeCityName,
        UUID homeStateId, String homeStateName, String email, String mobileNumber, boolean emailConfirmed,
        boolean mobileNumberConfirmed) {
}