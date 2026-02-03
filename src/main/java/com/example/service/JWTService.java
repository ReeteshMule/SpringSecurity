package com.example.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.crypto.Data;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	private String secureKey = "";
	
	public JWTService() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secureKey = Base64.getEncoder().encodeToString(sk.getEncoded());
		}catch(NoSuchAlgorithmException e) {
			e.getMessage();
		}
		
	}

	public String generateToken(String username) {
		
		Map<String, Object> claims = new HashMap<>();
		
		return Jwts.builder()
		        .setClaims(claims)
		        .setSubject(username)
		        .setIssuedAt(new Date(System.currentTimeMillis()))
		        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 minutes
		        .signWith(getKey())
		        .compact();
		

	}

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secureKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
    	//old version
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        // latest version
        // its dependent on the version that we are using we are using the oledest varsion of jjwtapi , jjwtimple that way it not works we want the version like 0.12.5
//        return Jwts.parser()   
//        		.verifyWith(getKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
    
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}