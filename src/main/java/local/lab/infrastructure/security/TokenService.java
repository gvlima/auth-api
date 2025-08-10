package local.lab.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import local.lab.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String tokenSecret;

    @Value("${api.security.token.issuer}")
    private String tokenIssuer;

    @Value("${api.security.token.lifetime}")
    private Integer tokenLifetime;

    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            return JWT.create()
                    .withIssuer(tokenIssuer)
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException ex){
            throw new RuntimeException("Error while authenticating");
        }
    }

    public String validateToken(String token){
        try {
            return JWT.require(Algorithm.HMAC256(tokenSecret))
                    .withIssuer(tokenIssuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex){
            return null;
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(tokenLifetime).toInstant(ZoneOffset.of("-03:00"));
    }
}
