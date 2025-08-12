package local.lab.service;

import local.lab.domain.Login;
import local.lab.domain.User;
import local.lab.dto.LoginRequestDTO;
import local.lab.infrastructure.security.TokenService;
import local.lab.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Login login(LoginRequestDTO loginRequest){
        User user = this.userRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(passwordEncoder.matches(loginRequest.password(), user.getPassword())){
            return Login.builder()
                    .token(this.tokenService.generateToken(user))
                    .build();
        }

        throw new RuntimeException("Wrong password");
    }

}
