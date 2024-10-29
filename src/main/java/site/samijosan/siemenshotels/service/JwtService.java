package site.samijosan.siemenshotels.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "G0oOT5wDevcPNTZk9dwXTcnAb7NgPbDztecysyUwtU3oPU06uNCWhYHlYHdZvndQC0pvrLentgTN9zEQcBP2dwFzDyKk0QYkwLH54KTMHfRj2t/nvZ6mhg3QY93m3P8LoLp0rs2g8yHKVzvu/vv1FhZ5SXipiQ7rSX7+QDaSIg+a8vdqN4igxQ5zP+aFmft65SUoHuwsBSyyZ7lVCtDA7pwLeftvqkoGnABOC/qVdS7dG32vyGq6N9ShI0RT9lhe9R30Ww89pVR0jB3xOmKermaOSIy2LB+Z7NHrB1r223U5FMQOuTcFmZR3b3qeGqKyN0DMK85Z0oieWRn3v7FN9mf6q1OxaJW65N0PoPew+z0=";
    private final byte[] SECRET_KEY_BYTES = Base64.getDecoder().decode(SECRET_KEY);
    private final SecretKey secretKey = new SecretKeySpec(SECRET_KEY_BYTES, "HmacSHA256");

    private static final int TOKEN_VALID_TIME_MS = 1000 * 60 * 60 * 24;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(
            UserDetails userDetails
    ) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (TOKEN_VALID_TIME_MS)))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private byte[] getSignInKey() {
        return SECRET_KEY_BYTES;
    }

}
