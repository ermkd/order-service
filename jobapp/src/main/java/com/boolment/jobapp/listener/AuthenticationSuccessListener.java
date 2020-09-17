package com.boolment.jobapp.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.boolment.jobapp.entity.UserPrincipal;
import com.boolment.jobapp.serviceimpl.LoginAttemptService;

@Component
public class AuthenticationSuccessListener {

	@Autowired
	private LoginAttemptService loginAttemptService;

	@EventListener
	public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
		Object principal = event.getAuthentication().getPrincipal();
		if (principal instanceof UserPrincipal) {
			UserPrincipal user = (UserPrincipal) event.getAuthentication().getPrincipal();
			loginAttemptService.evictUserFromLoginCache(user.getUsername());
		}

	}

}
