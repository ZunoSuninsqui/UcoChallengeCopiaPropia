package co.edu.uco.ucochallenge.user.registeruser.application.interactor.usecase.rules;

import co.edu.uco.ucochallenge.user.registeruser.application.usecase.domain.RegisterUserDomain;
import java.util.UUID;

public class RegisterUserContext {

    private final UUID idType;
    private final String idNumber;
    private final String firstName;
    private final String secondName;
    private final String firstSurname;
    private final String secondSurname;
    private final UUID homeCity;
    private final String email;
    private final String mobileNumber;
    
    
    public RegisterUserContext(RegisterUserDomain domain) {
        this.idType = domain.getIdType();
        this.idNumber = domain.getIdNumber();
        this.firstName = domain.getFirstName();
        this.secondName = domain.getSecondName();
        this.firstSurname = domain.getFirstSurname();
        this.secondSurname = domain.getSecondSurname();
        this.homeCity = domain.getHomeCity();
        this.email = domain.getEmail();
        this.mobileNumber = domain.getMobileNumber();
    }
    
    public UUID getIdType() {
        return idType;
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

    public UUID getHomeCity() {
        return homeCity;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
}