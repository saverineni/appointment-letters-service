package uk.co.nhs.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String email;
    private String firstName;
    private String lastName;
}
