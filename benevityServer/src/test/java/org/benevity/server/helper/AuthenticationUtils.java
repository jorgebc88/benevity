package org.benevity.server.helper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.lang.String.format;

public class AuthenticationUtils {

    private static final String JWT_SECRET = "th1s1sjustr3all1feTh1s1sjustfantasyCaught1nalands1d3N03scap3fr0mr3al1ty0p3ny0ur3y3sL00kupt0th3sk13sands33";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    private static final String JWT_ISSUER = "Benevity";
    private static final int ONE_DAY = 24 * 60 * 60 * 1000;

    public static String generateToken(String userId, String userName) {
        return Jwts.builder()
                   .setSubject(format("%s,%s", userId, userName))
                   .setIssuer(JWT_ISSUER)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + ONE_DAY))
                   .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                   .compact();
    }
}
