package uk.co.nhs.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import uk.co.nhs.dto.authentication.AuthenticationToken;
import uk.co.nhs.dto.authentication.LoginRequest;
import uk.co.nhs.security.jwt.JwtTokenService;

import java.util.List;

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
    public AuthenticationToken authenticate(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        String token = tokenGenerator.createToken(authentication);
        AuthenticationToken authenticationResponse = new AuthenticationToken();
        authenticationResponse.setToken(token);
        authenticationResponse.setTimeToLive(timeToLive);
        return authenticationResponse;
    }

    @ExceptionHandler(InvalidPrivilegesException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public void invalidPrivileges(InvalidPrivilegesException e) {

    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public void badCredentials(BadCredentialsException e) {

    }
}
