package org.benevity.server.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.benevity.server.repository.entity.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtil {

    private final String JWT_SECRET =
            "th1s1sjustr3all1feTh1s1sjustfantasyCaught1nalands1d3N03scap3fr0mr3al1ty0p3ny0ur3y3sL00kupt0th3sk13sands33";
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    private final String JWT_ISSUER = "Benevity";
    private final int ONE_DAY = 24 * 60 * 60 * 1000;

    public String generateAccessToken(User user) {
        return Jwts.builder()
                   .setSubject(format("%s,%s", user.getId(), user.getUsername()))
                   .setIssuer(JWT_ISSUER)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + ONE_DAY))
                   .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                   .compact();
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();

        return claims.getSubject().split(",")[0];
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();

        return claims.getSubject().split(",")[1];
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}" + ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}" + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}" + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}" + ex.getMessage());
        }
        return false;
    }
}
