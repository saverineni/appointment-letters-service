package uk.co.nhs.api.responses;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class UserResponse {
    private long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String dateOfBirth;

    public void setDateOfBirth(LocalDate dateOfBirth){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dateOfBirth=dateOfBirth.format(formatter);
    }
}
