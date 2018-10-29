package uk.co.nhs.responses;

import lombok.Data;

@Data
public class AuthenticationTokenResponse {
    private String token;
    private int timeToLive;
}