package local.lab.service;

import local.lab.domain.Login;
import local.lab.dto.LoginRequestDTO;
import local.lab.infrastructure.security.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginService {

    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    public Login login(LoginRequestDTO loginRequest){
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        UserDetails authenticatedUser = (UserDetails) auth.getPrincipal();

        return Login.builder()
                .token(tokenService.generateToken(authenticatedUser))
                .build();
    }

}
