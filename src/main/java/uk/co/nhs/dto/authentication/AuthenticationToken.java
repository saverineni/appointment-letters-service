package uk.co.nhs.dto.authentication;

import lombok.Data;

@Data
public class AuthenticationToken {
    private String token;
    private int timeToLive;
}