package uk.co.nhs.dto.authentication;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}