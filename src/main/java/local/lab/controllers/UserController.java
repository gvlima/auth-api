package local.lab.controllers;

import local.lab.domain.Login;
import local.lab.domain.UserMapper;
import local.lab.domain.User;
import local.lab.dto.*;
import local.lab.service.LoginService;
import local.lab.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest){
        Login login = loginService.login(loginRequest);
        log.info("User logged in: {}", login);

        return ResponseEntity.ok(mapper.toLoginResponseDTO(login));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO registerRequest){
        User user = registerService.register(registerRequest);
        log.info("User registered: {}", user);

        return ResponseEntity.ok(mapper.toRegisterResponseDTO(user));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequestDTO changePasswordRequest, Principal connectedUser) {
        User user = loginService.changePassword(connectedUser.getName(),
                changePasswordRequest.oldPassword(),
                changePasswordRequest.newPassword(),
                changePasswordRequest.confirmPassword()
        );

        log.info("User changed password: {}", user);
        return ResponseEntity.ok().build();
    }
}
