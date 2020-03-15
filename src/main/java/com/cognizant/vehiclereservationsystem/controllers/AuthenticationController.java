package com.cognizant.vehiclereservationsystem.controllers;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.vehiclereservationsystem.models.User;
import com.cognizant.vehiclereservationsystem.repositories.UserRepository;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AuthenticationController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/authenticate")
	public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {
		System.out.println("\n\t\tIn Authenticate");
		String user = getUser(authHeader);
		System.out.println("Auth Header  : " + authHeader);
		System.out.println("User : " + user);
		String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
		Map<String, String> auth = new HashMap<String, String>();
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User currentUser = userRepository.findByEmail(email);

		if(currentUser.getIsApproved()) {
			auth.put("token", generateJwt(user));
		}
		else {
			auth.put("token", null);
		}
		auth.put("role", role);
		auth.put("username", (currentUser.getFirstName()));
		return auth;
	}

	private String getUser(String authHeader) {
		byte[] auth = Base64.getDecoder().decode(authHeader.split(" ")[1]);
		String authString = new String(auth);
		System.out.println("Auth String " + authString);
		return authString.split(":")[0];
	}

	private String generateJwt(String user) {
		JwtBuilder builder = Jwts.builder();
		builder.setSubject(user);
		builder.setIssuedAt(new Date());
		builder.setExpiration(new Date((new Date()).getTime() + 1200000));
		builder.signWith(SignatureAlgorithm.HS256, "secretKey");
		String token = builder.compact();
		return token;
	}
}
