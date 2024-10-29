package site.samijosan.siemenshotels.controller;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.samijosan.siemenshotels.request.AuthenticationRequest;
import site.samijosan.siemenshotels.request.RegisterRequest;
import site.samijosan.siemenshotels.response.AuthenticationResponse;
import site.samijosan.siemenshotels.service.AuthenticationService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody RegisterRequest request
    ) throws BadRequestException {
        String gmailRegex = "^[a-zA-Z0-9_.+-]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(gmailRegex);
        Matcher matcher = pattern.matcher(request.getEmail());

        if(!matcher.find()) {
            throw new BadRequestException();
        }
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}