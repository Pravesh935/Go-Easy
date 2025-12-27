package com.ride.goeasy.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

	@Value("${jwt.secret:MySuperSecretKeyForJwtTokenThatIsAtLeast32CharsLong}")
	private String secretKeyString;

	@Value("${jwt.expiration-ms:3600000}") // 1 hour
	private long expirationMs;

	private SecretKey secretKey;

	@PostConstruct
	public void init() {
		this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
	}

	public String generateToken(String username, String role) {
		long now = System.currentTimeMillis();
		return Jwts.builder().subject(username).claim("role", role).issuedAt(new Date(now))
				.expiration(new Date(now + expirationMs)).signWith(secretKey).compact();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public String extractRole(String token) {
		Claims c = extractAllClaims(token);
		Object r = c.get("role");
		return r == null ? null : r.toString();
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public boolean isTokenValid(String token, String username) {
		final String extractedUsername = extractUsername(token);
		return (extractedUsername != null && extractedUsername.equals(username) && !isTokenExpired(token));
	}
}