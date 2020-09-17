package com.boolment.jobapp.serviceinterface;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.boolment.jobapp.entity.User;
import com.boolment.jobapp.exception.EmailExistException;
import com.boolment.jobapp.exception.EmailNotFoundException;
import com.boolment.jobapp.exception.UserNameExistException;
import com.boolment.jobapp.exception.UserNotFoundException;

public interface UserServiceInterface {

	
	User register(String firstName, String lastName, String username, String email ) throws UserNotFoundException, UserNameExistException, EmailExistException;
	
	List<User> getUsers();
	User findUserByUserName(String username);
	
	User findUserByEmail(String email);
	
	User addUser(String firstName, String lastName, String username, String email, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImg) throws UserNotFoundException, UserNameExistException, EmailExistException, IOException;
	
	
	User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImg) throws UserNotFoundException, UserNameExistException, EmailExistException, IOException;
	
	
	void deleteUser(long id);
	
	void resetPassword(String email) throws EmailNotFoundException;
	
	
	User updateProfileImg(String username, MultipartFile profileImage) throws UserNotFoundException, UserNameExistException, EmailExistException, IOException;
		
}
