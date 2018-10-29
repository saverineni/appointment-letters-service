package uk.co.nhs.dto;

import lombok.Data;

@Data
public class UserCreationRequest {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
