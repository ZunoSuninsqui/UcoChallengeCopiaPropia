package co.edu.uco.ucochallenge.user.listusers.application.usecase.domain;

import co.edu.uco.ucochallenge.crosscuting.exception.ExceptionLayer;
import co.edu.uco.ucochallenge.crosscuting.exception.UcoChallengeException;
import co.edu.uco.ucochallenge.crosscuting.messages.MessageKey;

public final class ListUsersQueryDomain {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 50;

    private final int page;
    private final int size;

    private ListUsersQueryDomain(final int page, final int size) {
        this.page = validatePage(page);
        this.size = validateSize(size);
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    private int validatePage(final int value) {
        if (value < 0) {
            throw UcoChallengeException.createUserException(ExceptionLayer.DOMAIN,
                    MessageKey.ListUsers.PAGE_NEGATIVE);
        }
        return value;
    }

    private int validateSize(final int value) {
        if (value <= 0 || value > MAX_SIZE) {
            throw UcoChallengeException.createUserException(ExceptionLayer.DOMAIN,
                    MessageKey.ListUsers.SIZE_INVALID);
        }
        return value;
    }

    public static final class Builder {
        private Integer page;
        private Integer size;

        private Builder() {
        }

        public Builder withPage(final Integer page) {
            this.page = page;
            return this;
        }

        public Builder withSize(final Integer size) {
            this.size = size;
            return this;
        }

        public ListUsersQueryDomain build() {
            final int pageValue = page == null ? DEFAULT_PAGE : page;
            final int sizeValue = size == null ? DEFAULT_SIZE : size;
            return new ListUsersQueryDomain(pageValue, sizeValue);
        }
    }
}