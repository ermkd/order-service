package com.boolment.jobapp.repositoryinterface;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boolment.jobapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findUserByUserName(String username);
	
	
	User findUserByEmail(String email);

}
