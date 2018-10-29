package uk.co.nhs.resource;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.VndErrors;
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
import java.util.List;
import java.util.Optional;
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

    @GetMapping("/users")
    public List<User> getAll() {
        return usersRepository.findAll();
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

    @GetMapping("/forgot")
    public ResponseEntity<?> forgotPassword(HttpServletRequest request, @RequestParam("username") final String username) {
        Optional<User> userOptional = usersRepository.findByUsername(username);
        String userEmail = null;
        if (!userOptional.isPresent()) {
            log.error("We didn't find an account for the username: {}", username);
        } else {

            User user = userOptional.get();
            userEmail = user.getEmail();
            user.setResetToken(UUID.randomUUID().toString());
            usersRepository.save(user);
            String appUrl = String.format("%s://%s:%s/reset?token=%s",
                                            request.getScheme(), request.getServerName(), request.getServerPort(), user.getResetToken());
            Email email = Email.builder()
                    .to(userEmail)
                    .subject("Reset your NHS appointment letters password")
                    .body(createEmailBody(user.getUserFullName(), appUrl))
                    .build();

            emailService.sendEmail(email);

        }
        return new ResponseEntity<>(String.format("{\"message\":\"password reset email has been sent to the email address provided\"}"), HttpStatus.OK);

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
