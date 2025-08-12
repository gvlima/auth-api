package local.lab.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import local.lab.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String tokenSecret;

    @Value("${api.security.token.issuer}")
    private String tokenIssuer;

    @Value("${api.security.token.lifetime}")
    private Integer tokenLifetime;

    public String generateToken(User user){

        return Jwts.builder()
                .subject(user.getId())
                .issuer(tokenIssuer)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(tokenLifetime, ChronoUnit.HOURS)))
                .claim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .signWith(key())
                .compact();
    }

    public Jws<Claims> parse(String jwt) {
        return Jwts.parser()
                .requireIssuer(tokenIssuer)
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(jwt);
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenSecret));
    }

}
