package com.boolment.jobapp.enumeration;

import com.boolment.jobapp.util.Authority;

public enum Role {

	ROLE_USER(Authority.USER_AUTHORITIES),
	ROLE_EMPLOYEE(Authority.EMPLOYEE_AUTHORITIES),
	ROLE_EMPLOYER(Authority.EMPLOYER_AUTHORITIES),
	ROLE_ADMIN(Authority.ADMIN_AUTHORITIES),
	ROLE_SUPER_ADMIN(Authority.SUPER_ADMIN_AUTHORITIES);
	
	
	private String[] authorities;
	
	Role(String... authorities)
	{
		this.authorities = authorities;
	}
	
	public String[] getAuthorities()
	{
		return authorities;
	}
}
