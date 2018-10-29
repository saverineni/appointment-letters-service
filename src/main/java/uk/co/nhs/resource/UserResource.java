package uk.co.nhs.resource;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.nhs.dto.UserCreationRequest;
import uk.co.nhs.dto.UserUpdateRequest;
import uk.co.nhs.exception.UserNotFoundException;
import uk.co.nhs.model.Email;
import uk.co.nhs.model.User;
import uk.co.nhs.repository.UsersRepository;
import uk.co.nhs.services.EmailService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@Slf4j
public class UserResource {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") final Long id) {
        return usersRepository.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserCreationRequest userCreationRequest) {
        User user = convertToEntity(userCreationRequest);
        usersRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateUser(@PathVariable("id") final Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        return  usersRepository.findById(id)
                .map( existingUser -> {
                    existingUser.setEmail(userUpdateRequest.getEmail());
                    existingUser.setFirstName(userUpdateRequest.getFirstName());
                    existingUser.setLastName(userUpdateRequest.getLastName());
                    usersRepository.save(existingUser);
                    return new ResponseEntity<>(HttpStatus.OK);
        })
        .orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(HttpServletRequest request, @RequestParam("username") final String username) {

        usersRepository.findByUsername(username)
                .map(user -> {
                    user.setResetToken(UUID.randomUUID().toString());
                    usersRepository.save(user);
                    String appUrl = String.format("%s://%s:%s/reset?token=%s",
                            request.getScheme(), request.getServerName(),
                            request.getServerPort(), user.getResetToken());
                    Email email = Email.builder()
                            .to(user.getEmail())
                            .subject("Reset your NHS appointment letters password")
                            .body(createEmailBody(user.getUserFullName(), appUrl))
                            .build();

                    emailService.sendEmail(email);
                    return new ResponseEntity<>(HttpStatus.OK);
                });
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String createEmailBody(String name, String url) {

        return  "Hello "+ name +",\n\n" +
                    "We have received a request to reset your password.\n\n" +
                    "In order to do this please copy the following link into your web browser and then enter\n\n" +
                    url +"\n\n" +
                    "If you have not requested a password reset then please contact us immediately. \n\n" +
                    "Our phone number is 0161 875 1414.\n\n" +
                    "Thank you.\n\n" +
                    "NHS Appointment letters";
    }

    private User convertToEntity(UserCreationRequest userCreationRequest) {
        return  modelMapper.map(userCreationRequest, User.class);
    }
}
