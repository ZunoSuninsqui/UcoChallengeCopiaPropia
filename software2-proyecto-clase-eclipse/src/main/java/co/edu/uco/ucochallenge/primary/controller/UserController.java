package co.edu.uco.ucochallenge.primary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
>>>>>>> 7acc2a237779a5f2233b9770e98d187dd723b1c9

import co.edu.uco.ucochallenge.crosscuting.messages.MessageKey;
import co.edu.uco.ucochallenge.secondary.ports.service.MessageServicePort;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.RegisterUserInteractor;
import co.edu.uco.ucochallenge.user.registeruser.application.interactor.dto.RegisterUserInputDTO;
import co.edu.uco.ucochallenge.user.listusers.application.interactor.ListUsersInteractor;
import co.edu.uco.ucochallenge.user.listusers.application.interactor.dto.ListUsersInputDTO;
import co.edu.uco.ucochallenge.user.listusers.application.interactor.dto.ListUsersOutputDTO;

@RestController
@RequestMapping("/uco-challenge/api/v1/users")
public class UserController {
	
    private final RegisterUserInteractor registerUserInteractor;
    private final ListUsersInteractor listUsersInteractor;
    private final MessageServicePort messageServicePort;

    public UserController(final RegisterUserInteractor registerUserInteractor,
            final ListUsersInteractor listUsersInteractor, final MessageServicePort messageServicePort) {
            this.registerUserInteractor = registerUserInteractor;
            this.listUsersInteractor = listUsersInteractor;
            this.messageServicePort = messageServicePort;
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserInputDTO dto) {
            final var message = messageServicePort.getMessage(MessageKey.RegisterUser.SUCCESS);
            var normalizedDTO = RegisterUserInputDTO.normalize(dto.idType(), dto.idNumber(), dto.firstName(), dto.secondName(), dto.firstSurname(), dto.secondSurname(), dto.homeCity(), dto.email(), dto.mobileNumber());

            registerUserInteractor.execute(normalizedDTO);
            return new ResponseEntity<String>(message, HttpStatus.CREATED);
    }
<<<<<<< HEAD

=======
    
    @GetMapping
    public ResponseEntity<ListUsersOutputDTO> listUsers(
                    @RequestParam(name = "page", defaultValue = "0") final Integer page,
                    @RequestParam(name = "size", defaultValue = "10") final Integer size) {
            final var input = ListUsersInputDTO.normalize(page, size);
            final var result = listUsersInteractor.execute(input);
            return ResponseEntity.ok(result);
    }
    
>>>>>>> 7acc2a237779a5f2233b9770e98d187dd723b1c9
}
