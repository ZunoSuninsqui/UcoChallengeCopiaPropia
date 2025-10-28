package co.edu.uco.ucochallenge.user.listusers.application.usecase.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.edu.uco.ucochallenge.crosscuting.helper.ObjectHelper;

public final class ListUsersPageDomain {

    private final List<UserSummaryDomain> users;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final boolean hasNext;
    private final boolean hasPrevious;

    private ListUsersPageDomain(final Builder builder) {
        this.users = Collections.unmodifiableList(new ArrayList<>(ObjectHelper.getDefault(builder.users, List.of())));
        this.page = builder.page;
        this.size = builder.size;
        this.totalElements = builder.totalElements;
        this.totalPages = builder.totalPages;
        this.hasNext = builder.hasNext;
        this.hasPrevious = builder.hasPrevious;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<UserSummaryDomain> getUsers() {
        return users;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public static final class Builder {
        private List<UserSummaryDomain> users;
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
        private boolean hasNext;
        private boolean hasPrevious;

        private Builder() {
        }

        public Builder withUsers(final List<UserSummaryDomain> users) {
            this.users = users;
            return this;
        }

        public Builder withPage(final int page) {
            this.page = page;
            return this;
        }

        public Builder withSize(final int size) {
            this.size = size;
            return this;
        }

        public Builder withTotalElements(final long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public Builder withTotalPages(final int totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public Builder withHasNext(final boolean hasNext) {
            this.hasNext = hasNext;
            return this;
        }

        public Builder withHasPrevious(final boolean hasPrevious) {
            this.hasPrevious = hasPrevious;
            return this;
        }

        public ListUsersPageDomain build() {
            return new ListUsersPageDomain(this);
        }
    }
}