package uk.co.nhs.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.co.nhs.dto.LoginRequest;
import uk.co.nhs.responses.AuthenticationTokenResponse;
import uk.co.nhs.security.jwt.JwtTokenService;

@RestController
@RequestMapping("/authentication")
@Slf4j
public class AuthenticationResource {

    public class InvalidPrivilegesException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService tokenGenerator;
    private final int timeToLive;

    public AuthenticationResource(
            @Value("${jwt.expiration.seconds}") int jwtExpirationSeconds,
            AuthenticationManager authenticationManager,
            JwtTokenService tokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
        this.timeToLive = jwtExpirationSeconds;
    }

    @PostMapping("/generateToken")
    public AuthenticationTokenResponse authenticate(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        String token = tokenGenerator.createToken(authentication);
        AuthenticationTokenResponse authenticationResponse = new AuthenticationTokenResponse();
        authenticationResponse.setToken(token);
        authenticationResponse.setTimeToLive(timeToLive);
        return authenticationResponse;
    }

}
