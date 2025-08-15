package local.lab.service;

import jakarta.transaction.Transactional;
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

    @Transactional
    public Login login(LoginRequestDTO loginRequest){
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(passwordEncoder.matches(loginRequest.password(), user.getPassword())){
            String accessToken = this.tokenService.generateAccessToken(user);
            String refreshToken = this.tokenService.generateRefreshToken(user);
            userRepository.updateRefreshToken(user.getId(), refreshToken);

            return Login.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        throw new RuntimeException("Wrong password");
    }

    public User changePassword(String id, String oldPassword, String newPassword, String confirmPassword) {
        if(newPassword.equals(confirmPassword)){
            User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            if(passwordEncoder.matches(oldPassword, user.getPassword())){
                user.setPassword(passwordEncoder.encode(newPassword));
                return userRepository.save(user);
            }
            throw new RuntimeException("Wrong password");
        }

        throw new RuntimeException("New password and confirmation do not match");
    }

}
