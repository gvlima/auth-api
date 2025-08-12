package local.lab.controllers;

import local.lab.domain.Login;
import local.lab.domain.LoginMapper;
import local.lab.domain.User;
import local.lab.dto.LoginRequestDTO;
import local.lab.dto.LoginResponseDTO;
import local.lab.dto.RegisterRequestDTO;
import local.lab.service.AuthenticationService;
import local.lab.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @Autowired
    LoginMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest){
        Login login = authenticationService.login(loginRequest);
        return ResponseEntity.ok(mapper.toLoginResponseDTO(login));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody RegisterRequestDTO registerRequest){
        User user = authenticationService.register(registerRequest);
        log.info("User registered: {}", user);
        Login login = authenticationService.login(new LoginRequestDTO(registerRequest.email(), registerRequest.password()));

        return ResponseEntity.ok(mapper.toLoginResponseDTO(login));
    }
}
