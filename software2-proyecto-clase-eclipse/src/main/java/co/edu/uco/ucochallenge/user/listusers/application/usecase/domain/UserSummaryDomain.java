package co.edu.uco.ucochallenge.user.listusers.application.usecase.domain;

import java.util.UUID;

import co.edu.uco.ucochallenge.crosscuting.helper.TextHelper;
import co.edu.uco.ucochallenge.crosscuting.helper.UUIDHelper;

public final class UserSummaryDomain {

    private final UUID id;
    private final UUID idTypeId;
    private final String idTypeName;
    private final String idNumber;
    private final String firstName;
    private final String secondName;
    private final String firstSurname;
    private final String secondSurname;
    private final UUID homeCityId;
    private final String homeCityName;
    private final UUID homeStateId;
    private final String homeStateName;
    private final String email;
    private final String mobileNumber;
    private final boolean emailConfirmed;
    private final boolean mobileNumberConfirmed;

    private UserSummaryDomain(final Builder builder) {
        this.id = UUIDHelper.getDefault(builder.id);
        this.idTypeId = UUIDHelper.getDefault(builder.idTypeId);
        this.idTypeName = TextHelper.getDefaultWithTrim(builder.idTypeName);
        this.idNumber = TextHelper.getDefaultWithTrim(builder.idNumber);
        this.firstName = TextHelper.getDefaultWithTrim(builder.firstName);
        this.secondName = TextHelper.getDefaultWithTrim(builder.secondName);
        this.firstSurname = TextHelper.getDefaultWithTrim(builder.firstSurname);
        this.secondSurname = TextHelper.getDefaultWithTrim(builder.secondSurname);
        this.homeCityId = UUIDHelper.getDefault(builder.homeCityId);
        this.homeCityName = TextHelper.getDefaultWithTrim(builder.homeCityName);
        this.homeStateId = UUIDHelper.getDefault(builder.homeStateId);
        this.homeStateName = TextHelper.getDefaultWithTrim(builder.homeStateName);
        this.email = TextHelper.getDefaultWithTrim(builder.email);
        this.mobileNumber = TextHelper.getDefaultWithTrim(builder.mobileNumber);
        this.emailConfirmed = builder.emailConfirmed;
        this.mobileNumberConfirmed = builder.mobileNumberConfirmed;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getId() {
        return id;
    }

    public UUID getIdTypeId() {
        return idTypeId;
    }

    public String getIdTypeName() {
        return idTypeName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public UUID getHomeCityId() {
        return homeCityId;
    }

    public String getHomeCityName() {
        return homeCityName;
    }

    public UUID getHomeStateId() {
        return homeStateId;
    }

    public String getHomeStateName() {
        return homeStateName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public boolean isMobileNumberConfirmed() {
        return mobileNumberConfirmed;
    }

    public static final class Builder {
        private UUID id;
        private UUID idTypeId;
        private String idTypeName;
        private String idNumber;
        private String firstName;
        private String secondName;
        private String firstSurname;
        private String secondSurname;
        private UUID homeCityId;
        private String homeCityName;
        private UUID homeStateId;
        private String homeStateName;
        private String email;
        private String mobileNumber;
        private boolean emailConfirmed;
        private boolean mobileNumberConfirmed;

        private Builder() {
        }

        public Builder withId(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder withIdType(final UUID idTypeId, final String idTypeName) {
            this.idTypeId = idTypeId;
            this.idTypeName = idTypeName;
            return this;
        }

        public Builder withIdNumber(final String idNumber) {
            this.idNumber = idNumber;
            return this;
        }

        public Builder withFirstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withSecondName(final String secondName) {
            this.secondName = secondName;
            return this;
        }

        public Builder withFirstSurname(final String firstSurname) {
            this.firstSurname = firstSurname;
            return this;
        }

        public Builder withSecondSurname(final String secondSurname) {
            this.secondSurname = secondSurname;
            return this;
        }

        public Builder withHomeCity(final UUID homeCityId, final String homeCityName, final UUID homeStateId,
                final String homeStateName) {
            this.homeCityId = homeCityId;
            this.homeCityName = homeCityName;
            this.homeStateId = homeStateId;
            this.homeStateName = homeStateName;
            return this;
        }

        public Builder withEmail(final String email) {
            this.email = email;
            return this;
        }

        public Builder withMobileNumber(final String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public Builder withEmailConfirmed(final boolean emailConfirmed) {
            this.emailConfirmed = emailConfirmed;
            return this;
        }

        public Builder withMobileNumberConfirmed(final boolean mobileNumberConfirmed) {
            this.mobileNumberConfirmed = mobileNumberConfirmed;
            return this;
        }

        public UserSummaryDomain build() {
            return new UserSummaryDomain(this);
        }
    }
}