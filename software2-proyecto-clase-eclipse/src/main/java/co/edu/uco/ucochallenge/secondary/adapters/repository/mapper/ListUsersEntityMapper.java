package co.edu.uco.ucochallenge.secondary.adapters.repository.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import co.edu.uco.ucochallenge.crosscuting.helper.TextHelper;
import co.edu.uco.ucochallenge.crosscuting.helper.UUIDHelper;
import co.edu.uco.ucochallenge.secondary.adapters.repository.entity.CityEntity;
import co.edu.uco.ucochallenge.secondary.adapters.repository.entity.IdTypeEntity;
import co.edu.uco.ucochallenge.secondary.adapters.repository.entity.StateEntity;
import co.edu.uco.ucochallenge.secondary.adapters.repository.entity.UserEntity;
import co.edu.uco.ucochallenge.user.listusers.application.usecase.domain.ListUsersPageDomain;
import co.edu.uco.ucochallenge.user.listusers.application.usecase.domain.UserSummaryDomain;

@Component
public class ListUsersEntityMapper {

    public ListUsersPageDomain toPageDomain(final Page<UserEntity> page) {
        if (page == null) {
            return null;
        }

        final List<UserSummaryDomain> users = page.stream()
                .map(this::toUserSummaryDomain)
                .collect(Collectors.toList());

        return ListUsersPageDomain.builder()
                .withUsers(users)
                .withPage(page.getNumber())
                .withSize(page.getSize())
                .withTotalElements(page.getTotalElements())
                .withTotalPages(page.getTotalPages())
                .withHasNext(page.hasNext())
                .withHasPrevious(page.hasPrevious())
                .build();
    }

    private UserSummaryDomain toUserSummaryDomain(final UserEntity entity) {
        if (entity == null) {
            return null;
        }

        final IdTypeEntity idType = entity.getIdType();
        final CityEntity city = entity.getHomeCity();
        final StateEntity state = city != null ? city.getState() : null;

        return UserSummaryDomain.builder()
                .withId(UUIDHelper.getDefault(entity.getId()))
                .withIdType(UUIDHelper.getDefault(idType != null ? idType.getId() : null),
                        TextHelper.getDefaultWithTrim(idType != null ? idType.getName() : null))
                .withIdNumber(TextHelper.getDefaultWithTrim(entity.getIdNumber()))
                .withFirstName(TextHelper.getDefaultWithTrim(entity.getFirstName()))
                .withSecondName(TextHelper.getDefaultWithTrim(entity.getSecondName()))
                .withFirstSurname(TextHelper.getDefaultWithTrim(entity.getFirstSurname()))
                .withSecondSurname(TextHelper.getDefaultWithTrim(entity.getSecondSurname()))
                .withHomeCity(UUIDHelper.getDefault(city != null ? city.getId() : null),
                        TextHelper.getDefaultWithTrim(city != null ? city.getName() : null),
                        UUIDHelper.getDefault(state != null ? state.getId() : null),
                        TextHelper.getDefaultWithTrim(state != null ? state.getName() : null))
                .withEmail(TextHelper.getDefaultWithTrim(entity.getEmail()))
                .withMobileNumber(TextHelper.getDefaultWithTrim(entity.getMobileNumber()))
                .withEmailConfirmed(entity.isEmailConfirmed())
                .withMobileNumberConfirmed(entity.isMobileNumberConfirmed())
                .build();
    }
}
