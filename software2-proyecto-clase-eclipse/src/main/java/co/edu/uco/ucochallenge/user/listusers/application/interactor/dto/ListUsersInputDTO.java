package co.edu.uco.ucochallenge.user.listusers.application.interactor.dto;

public record ListUsersInputDTO(int page, int size) {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    public static ListUsersInputDTO normalize(final Integer page, final Integer size) {
        final int normalizedPage = page == null ? DEFAULT_PAGE : page;
        final int normalizedSize = size == null ? DEFAULT_SIZE : size;
        return new ListUsersInputDTO(normalizedPage, normalizedSize);
    }
}