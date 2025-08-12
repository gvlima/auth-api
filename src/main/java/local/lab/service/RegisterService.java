package local.lab.service;

import local.lab.domain.User;
import local.lab.dto.RegisterRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User register(RegisterRequestDTO registerRequest) {
        if (userService.findByEmail(registerRequest.email()).isPresent()) {
            throw new RuntimeException("User already exists with email: " + registerRequest.email());
        }

        User user = User.builder()
                .name(registerRequest.name())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .roles(List.of("USER"))
                .enabled(true)
                .build();

        return userService.save(user);
    }

}
