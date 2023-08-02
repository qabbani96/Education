package com.education.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTHandler {

	@Value("{$secet.key}")
	private  String key;
	
	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
	private static final Logger LOGGER = Logger.getLogger(JWTHandler.class.getName());
	private long expiryDate =3600000;
	
	private long getCurrentTimeMillis() {
	return System.currentTimeMillis();
	}
	
	private Date generateCurrentDate() {
		return new Date(getCurrentTimeMillis());
		}

	public String getUserNameFromToken(String Token) {
		String username;
		try {
			final Claims claims = this.getClaimsFromToken(Token);
			username = claims.getSubject();
	}
		catch (Exception e) {	
			username = null;
			}
		return username;
		}

	public String generateToken(String username) {
	
		Date now = new Date();
		long dateExpri= now.getTime()+expiryDate;
		Date expiryDate = new Date(dateExpri);
		String jws = Jwts.builder()
				.setIssuer( key).setIssuedAt(new Date()).setExpiration(expiryDate)
				.setSubject(username)
				.setIssuedAt(generateCurrentDate())
				.signWith( SIGNATURE_ALGORITHM, key )
				.compact();
		return jws;
	}

	public Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(key)
					.parseClaimsJws(token)
					.getBody();
	}
		catch (Exception e) {
			claims = null;
			}
		return claims;
	}



	public  Map<String,Object> validateToken(String token) {
	Map<String,Object>response= new HashMap<String, Object>();
	try {
		Jwts.parser().setSigningKey("zainCommission").parseClaimsJws(token);
		response.put("status", true);
		response.put("message", "success");
		return response;
	} catch (SignatureException ex) {
		LOGGER.info("Invalid JWT signature");
		response.put("status", false);
		response.put("message", "Invalid JWT signature");
		return response;
		} catch (MalformedJwtException ex) {
			LOGGER.info("Invalid JWT token");
			response.put("status", false);
			response.put("message", "Invalid JWT token");
			return response;
			} catch (ExpiredJwtException ex) {
				LOGGER.info("Expired JWT token");
				response.put("status", false);
				response.put("message", "Expired JWT token");
				return response;
				} catch (UnsupportedJwtException ex) {
					LOGGER.info("Unsupported JWT token");
					response.put("status", false);
					response.put("message", "Unsupported JWT token");
					return response;
					} catch (IllegalArgumentException ex) {
						LOGGER.info("JWT claims string is empty.");
						response.put("status", false);
						response.put("message", "JWT claims string is empty");
						return response;
						}
	}
}
