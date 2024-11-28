package com.example.TaskManagement.security.jwt;


import com.example.TaskManagement.security.AppUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.tokenExpiration}")
    private Duration tokenExpiration;


    public String generateJwtToken(AppUserDetails userDetails){
        return  generateToken(userDetails.getEmail());
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenExpiration.toMillis()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getEmail(String token){
        return Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validate(String authToken){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e){
            log.error("Invalid signature: {}", e.getMessage());
        } catch (MalformedJwtException e){
            log.error("Invalid token : {}", e.getMessage());
        } catch (ExpiredJwtException e){
            log.error("Token is expire: {}", e.getMessage());
        } catch (UnsupportedJwtException e){
            log.error("Token is unsupported: {} ", e.getMessage());
        } catch (IllegalStateException e){
            log.error("Claim string is empty: {}", e.getMessage());
        }
        return false;
    }

}
