package com.boolment.jobapp.util;

import static java.util.Arrays.stream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.boolment.jobapp.entity.UserPrincipal;

@Component
public class JWTTokenProvider {

	@Value("${jwt.secret}")
	private String secret;

//	once we verify our user then we will generate toke 

	public String generateJwtToken(UserPrincipal userPrincipal) {
		String[] claims = getClaimsFromUser(userPrincipal);

		return JWT.create().withIssuer(SecurityConstant.GET_BPSERVICES)
				.withAudience(SecurityConstant.GET_JOB_ADMINISTRATION).withIssuedAt(new Date())
				.withSubject(userPrincipal.getUsername()).withArrayClaim(SecurityConstant.AUTHORITIES, claims)
				.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_DATE))
				.sign(Algorithm.HMAC512(secret.getBytes()));

	}

	// once user request for any resource then from JWT token main se we have to
	// check authorities

	public List<GrantedAuthority> getAuthrities(String token) {
		String[] claims = getClaimsFromToken(token);

		return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	public Authentication getAuthentication(String username, List<GrantedAuthority> authorities,
			HttpServletRequest request) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, null, authorities);
		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return usernamePasswordAuthenticationToken;
	}

	public boolean isTokenValid(String username, String token) {
		JWTVerifier verifier = getJWTVerifier();
		return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);

	}

	public String getSubject(String token) {
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getSubject();
	}

	private boolean isTokenExpired(JWTVerifier verifier, String token) {

		Date expiration = verifier.verify(token).getExpiresAt();

		return expiration.before(new Date());
	}

	private String[] getClaimsFromToken(String token) {

		JWTVerifier verifier = getJWTVerifier();

		return verifier.verify(token).getClaim(SecurityConstant.AUTHORITIES).asArray(String.class);
	}

	private JWTVerifier getJWTVerifier() {

		JWTVerifier verifier;

		try {

			Algorithm algorithm = Algorithm.HMAC512(secret);
			verifier = JWT.require(algorithm).withIssuer(SecurityConstant.GET_BPSERVICES).build();

		} catch (JWTVerificationException jwtVerificationException) {

			// here insted of sending default exception of jwt we have customized it and
			// passed our own message so that end user don't know about any information
			throw new JWTVerificationException(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);

		}

		return verifier;
	}

	private String[] getClaimsFromUser(UserPrincipal user) {

		List<String> authorities = new ArrayList<>();
		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {

			authorities.add(grantedAuthority.getAuthority());
		}

		return authorities.toArray(new String[0]);
	}

}
