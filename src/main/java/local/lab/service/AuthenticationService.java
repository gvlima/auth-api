package local.lab.service;

import local.lab.domain.Login;
import local.lab.domain.User;
import local.lab.dto.LoginRequestDTO;
import local.lab.dto.RegisterRequestDTO;
import local.lab.infrastructure.security.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    public Login login(LoginRequestDTO loginRequest){
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        UserDetails authenticatedUser = (UserDetails) auth.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(auth);

        return Login.builder()
                .token(tokenService.generateToken(authenticatedUser))
                .build();
    }

    public User register(RegisterRequestDTO registerRequest){
        if (userService.findByEmail(registerRequest.email()).isPresent()) {
            throw new RuntimeException("User already exists with email: " + registerRequest.email());
        }

        List<String> list = new ArrayList<>();

        list.add("admin");
        list.add("financial");

        User user = User.builder()
                .name(registerRequest.name())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .roles(list)
                .build();

        log.info(user.toString());
        return userService.save(user);
    }





}
