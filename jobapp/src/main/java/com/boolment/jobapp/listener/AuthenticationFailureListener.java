package com.boolment.jobapp.listener;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.boolment.jobapp.serviceimpl.LoginAttemptService;

@Component
public class AuthenticationFailureListener {
	
	@Autowired
	private LoginAttemptService loginAttemptService;
	
	
	
	@EventListener
	public void authenticationFailure(AuthenticationFailureBadCredentialsEvent event) throws ExecutionException
	{
		
		Object principal = event.getAuthentication().getPrincipal();
		
		if(principal instanceof String)
		{
			String username = (String) event.getAuthentication().getPrincipal();
			
			loginAttemptService.addUserLoginAttemptCache(username);
			
		}
		
	}

}
