package com.hackathon.security.jwt;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class TokenAuthenticationService {

	private static final int EXPIRATION_HOURS = 24; // 10 days
	private static final String SECRET = "HACKATHONCONDUCTOR2017";
	private static final String TOKEN_PREFIX = "Bearer";
	private static final String HEADER = "Authorization";


	public void addAuthentication(HttpServletResponse response, String username) {
		// We generate a token now.
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(TimeUnit.HOURS.toMillis(EXPIRATION_HOURS) + new Date().getTime()))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		response.addHeader(HEADER, TOKEN_PREFIX + " "+ JWT);
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER);
		if(token != null) {
			// parse the token.
			token = extract(token);
			String username = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
			if(username != null) { // we managed to retrieve a user
				AuthenticatedUser authenticatedUser = new AuthenticatedUser(username);
				return authenticatedUser;
			}
		}
		return null;
	}

	public String extract(String header) {
		if (header.isEmpty()) {
			throw new AuthenticationServiceException("Authorization header cannot be blank!");
		}

		if (header.length() < TOKEN_PREFIX.length()) {
			throw new AuthenticationServiceException("Invalid authorization header size.");
		}

		return header.substring(TOKEN_PREFIX.length(), header.length());
	}
}
