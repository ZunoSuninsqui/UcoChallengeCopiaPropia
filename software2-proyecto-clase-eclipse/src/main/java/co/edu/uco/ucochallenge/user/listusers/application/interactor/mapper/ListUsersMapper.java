package co.edu.uco.ucochallenge.user.listusers.application.interactor.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import co.edu.uco.ucochallenge.user.listusers.application.interactor.dto.ListUsersInputDTO;
import co.edu.uco.ucochallenge.user.listusers.application.interactor.dto.ListUsersOutputDTO;
import co.edu.uco.ucochallenge.user.listusers.application.interactor.dto.UserSummaryDTO;
import co.edu.uco.ucochallenge.user.listusers.application.usecase.domain.ListUsersPageDomain;
import co.edu.uco.ucochallenge.user.listusers.application.usecase.domain.ListUsersQueryDomain;
import co.edu.uco.ucochallenge.user.listusers.application.usecase.domain.UserSummaryDomain;

@Component
public class ListUsersMapper {

    public ListUsersQueryDomain toDomain(final ListUsersInputDTO dto) {
        if (dto == null) {
            return null;
        }
        return ListUsersQueryDomain.builder()
                .withPage(dto.page())
                .withSize(dto.size())
                .build();
    }

    public ListUsersOutputDTO toDTO(final ListUsersPageDomain domain) {
        if (domain == null) {
            return null;
        }

        final List<UserSummaryDTO> users = domain.getUsers().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return new ListUsersOutputDTO(users, domain.getPage(), domain.getSize(), domain.getTotalElements(),
                domain.getTotalPages(), domain.isHasNext(), domain.isHasPrevious());
    }

    private UserSummaryDTO toDTO(final UserSummaryDomain domain) {
        if (domain == null) {
            return null;
        }

        return new UserSummaryDTO(domain.getId(), domain.getIdTypeId(), domain.getIdTypeName(), domain.getIdNumber(),
                domain.getFirstName(), domain.getSecondName(), domain.getFirstSurname(), domain.getSecondSurname(),
                domain.getHomeCityId(), domain.getHomeCityName(), domain.getHomeStateId(), domain.getHomeStateName(),
                domain.getEmail(), domain.getMobileNumber(), domain.isEmailConfirmed(), domain.isMobileNumberConfirmed());
    }

}