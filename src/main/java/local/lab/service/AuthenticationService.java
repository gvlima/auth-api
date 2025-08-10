package local.lab.service;

import local.lab.domain.Login;
import local.lab.domain.User;
import local.lab.dto.LoginRequestDTO;
import local.lab.dto.LoginResponseDTO;
import local.lab.dto.RegisterRequestDTO;
import local.lab.dto.RegisterResponseDTO;
import local.lab.infrastructure.security.TokenService;
import local.lab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;

    public Login login(LoginRequestDTO loginRequest){
        User user = this.userRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(loginRequest.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return Login.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .token(token)
                    .build();
        }

        throw new RuntimeException("User not found");
    }

    public Login register(RegisterRequestDTO registerRequest){
        Optional<User> user = this.userRepository.findByEmail(registerRequest.email());

        if(user.isEmpty()){
            User newUser = User.builder()
                    .name(registerRequest.name())
                    .email(registerRequest.email())
                    .password(passwordEncoder.encode(registerRequest.password()))
                    .build();

            userRepository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return Login.builder()
                    .id(newUser.getId())
                    .name(newUser.getName())
                    .token(token)
                    .build();
        }

        throw new RuntimeException("User already exist");
    }
}
