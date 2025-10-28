package co.edu.uco.ucochallenge.user.listusers.application.interactor.dto;

import java.util.List;

public record ListUsersOutputDTO(List<UserSummaryDTO> users, int page, int size, long totalElements, int totalPages,
        boolean hasNext, boolean hasPrevious) {
}