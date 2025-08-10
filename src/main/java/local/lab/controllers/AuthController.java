package local.lab.controllers;

import local.lab.domain.Login;
import local.lab.domain.LoginMapper;
import local.lab.dto.LoginRequestDTO;
import local.lab.dto.LoginResponseDTO;
import local.lab.dto.RegisterRequestDTO;
import local.lab.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    LoginMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest){
        Login login = authenticationService.login(loginRequest);
        return ResponseEntity.ok(mapper.toLoginResponseDTO(login));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody RegisterRequestDTO registerRequest){
        Login login = authenticationService.register(registerRequest);
        return ResponseEntity.ok(mapper.toLoginResponseDTO(login));
    }
}
