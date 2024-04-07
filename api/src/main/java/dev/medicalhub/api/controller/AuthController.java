package dev.medicalhub.api.controller;

import dev.medicalhub.api.domain.RefreshJwtRequest;
import dev.medicalhub.api.model.JwtResponse;
import dev.medicalhub.api.model.RegistrationDoctor;
import dev.medicalhub.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import dev.medicalhub.api.model.JwtRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public JwtResponse login(@RequestBody JwtRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("registration")
    public JwtResponse registration(@RequestBody RegistrationDoctor registrationDoctor) {
        return authService.registration(registrationDoctor);
    }
    @RequestMapping(method= RequestMethod.HEAD)
    public  ResponseEntity<?> checkToken(){
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("token")
    public JwtResponse getNewAccessToken(@RequestBody @NotNull RefreshJwtRequest request) {
        return authService.getAccessToken(request.getRefreshToken());
    }

    @PostMapping("refresh")
    public JwtResponse getNewRefreshToken(@RequestBody @NotNull RefreshJwtRequest request) {
        return authService.refresh(request.getRefreshToken());
    }

}
