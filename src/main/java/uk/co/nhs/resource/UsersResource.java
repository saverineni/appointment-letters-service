package uk.co.nhs.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
public class UsersResource {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private EmailService emailService;

    @GetMapping("/users")
    public List<User> getAll() {
        return usersRepository.findAll();
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return usersRepository.save(user);
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable("id") final Long id, @RequestBody User user) {

        Optional<User>  existingUser = usersRepository.findById(id);
        if(existingUser.isPresent()) {
            user.setId(existingUser.get().getId());
            usersRepository.save(user);
            return user;
        }
        log.error("User with id {} doesn't exist",id);
        return null;
    }

    @GetMapping("/forgot")
    public User forgotPassword(HttpServletRequest request, @RequestParam("username") final String username) {
        Optional<User> userOptional = usersRepository.findByUsername(username);

        if (!userOptional.isPresent()) {
            log.error("We didn't find an account for the username: {}", username);
        } else {

            User user = userOptional.get();
            user.setResetToken(UUID.randomUUID().toString());
            usersRepository.save(user);
            String appUrl = String.format("%s://%s:%s/reset?token=%s",
                                            request.getScheme(), request.getServerName(), request.getServerPort(), user.getResetToken());
            Email email = Email.builder()
                    .to(user.getEmail())
                    .subject("Password Reset Request")
                    .body(createEmailBody(user.getUserFullName(), appUrl))
                    .build();

            emailService.sendEmail(email);
        }

        return null;
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
}
