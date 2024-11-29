package com.github.matheuswwwp.dinenow.conf.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.matheuswwwp.dinenow.conf.mapper.Mapper;
import com.github.matheuswwwp.dinenow.controller.cart.CartController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWTVerifier;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key:default}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:36000000}")
    private final long validityInMilliseconds = 36000000;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    Algorithm algorithm = null;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC512(secretKey.getBytes());
    }

    public Token createAccessToken(String username, UUID id, List<String> roles) {
        Date now = new Date();
        var validity = new Date(now.getTime() + validityInMilliseconds);
        var accessToken = getRefreshToken(username, id.toString(), roles, now, validity);
        var refreshToken = getAccessToken(username, id.toString(), roles, now);
        return new Token(username, id.toString(), true, accessToken, refreshToken, validity, now);
    }

    private String getAccessToken(String username, String id, List<String> roles, Date now) {
        return JWT.create().
                withClaim("user_id", id).
                withClaim("roles", roles).
                withIssuedAt(now).
                withSubject(username).
                sign(algorithm).
                strip();
    }

    private String getRefreshToken(String username, String id, List<String> roles, Date now, Date validity) {
        String issueUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return JWT.create().
                withClaim("user_id", id).
                withClaim("roles", roles).
                withIssuedAt(now).
                withExpiresAt(validity).
                withSubject(username).
                withIssuer(issueUrl).
                sign(algorithm).
                strip();
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private DecodedJWT decodedToken(String token) {
        Algorithm alg = Algorithm.HMAC512(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(alg).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    public boolean validateToken(String token)  {
        DecodedJWT decodedJWT = decodedToken(token);
        try {
            if (decodedJWT.getExpiresAt().before(new Date())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaimFromToken(String token) {
      try {
          SecretKey secret = Keys.hmacShaKeyFor(secretKey.getBytes());
          return Mapper.parseObject(Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody(), Claims.class);
      } catch (Exception e) {
          logger.error("getClaimFromToken - error trying getClaimFromToken: {}", e.getMessage());
          return null;
      }
    }
}
