package com.boolment.jobapp.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.boolment.jobapp.entity.User;
import com.boolment.jobapp.entity.UserPrincipal;
import com.boolment.jobapp.enumeration.Role;
import com.boolment.jobapp.exception.EmailExistException;
import com.boolment.jobapp.exception.EmailNotFoundException;
import com.boolment.jobapp.exception.UserNameExistException;
import com.boolment.jobapp.exception.UserNotFoundException;
import com.boolment.jobapp.repositoryinterface.UserRepository;
import com.boolment.jobapp.serviceinterface.UserServiceInterface;
import com.boolment.jobapp.util.ConstantVariables;
import com.boolment.jobapp.util.FileConstant;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserServiceInterface, UserDetailsService {

	private Logger Log = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Autowired
	private EmailService emailService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findUserByUserName(username);

		if (user == null) {
			Log.error("User not found by username" + username);
			throw new UsernameNotFoundException("We don't have such user" + username);
		} else {

			validateLoginAttempt(user);
			user.setLastLoginDateDisplay(user.getLastLoginDate());
			user.setLastLoginDate(new Date());
			userRepository.save(user);

			UserPrincipal userPrincipal = new UserPrincipal(user);
			Log.info("Returning found user by username" + username);
			return userPrincipal;

		}

	}

	private void validateLoginAttempt(User user) {
		if (user.isNotLocked()) {

			if (loginAttemptService.hasExceededMaxAttempts(user.getUserName())) {
				user.setNotLocked(false);
			} else {
				user.setNotLocked(true);
			}

		} else {
			loginAttemptService.evictUserFromLoginCache(user.getUserName());
		}

	}

	@Override
	public User register(String firstName, String lastName, String username, String email)
			throws UserNotFoundException, UserNameExistException, EmailExistException {
		validateNewUserNameAndEmail(StringUtils.EMPTY, username, email);

		User user = new User();
		user.setUserId(generateUserId());
		String password = generatedPassword();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setUserName(username);
		user.setPassword(encodedPassword(password));
		user.setJoinDate(new Date());
		user.setActive(true);
		user.setNotLocked(true);
		user.setRoles(Role.ROLE_USER.name());
		user.setAuthorities(Role.ROLE_USER.getAuthorities());
		user.setProfileImageUrl(getTemporaryProfileImageUrl(username));

		userRepository.save(user);
		Log.info("New User Password" + password);
		emailService.sendNewPasswordEmail(firstName, password, email);

		return user;
	}

	@Override
	public User addUser(String firstName, String lastName, String username, String email, String role,
			boolean isNonLocked, boolean isActive, MultipartFile profileImg)
			throws UserNotFoundException, UserNameExistException, EmailExistException, IOException {
		validateNewUserNameAndEmail(StringUtils.EMPTY, username, email);
		User user = new User();
		String password = generatedPassword();
		user.setUserId(generateUserId());
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(encodedPassword(password));
		user.setEmail(email);
		user.setJoinDate(new Date());
		user.setUserName(username);
		user.setNotLocked(isNonLocked);
		user.setActive(isActive);
		user.setRoles(getRoleEnumName(role).name());
		user.setAuthorities(getRoleEnumName(role).getAuthorities());
		user.setProfileImageUrl(getTemporaryProfileImageUrl(username));

		userRepository.save(user);

		saveProfileImage(user, profileImg);

		return user;
	}

	@Override
	public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername,
			String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImg) throws UserNotFoundException, UserNameExistException, EmailExistException, IOException {
		User currentUser = validateNewUserNameAndEmail(currentUsername, newUsername, newEmail);
		
		
		currentUser.setFirstName(newFirstName);
		currentUser.setLastName(newLastName);
		currentUser.setEmail(newEmail);
		currentUser.setUserName(newUsername);
		currentUser.setNotLocked(isNonLocked);
		currentUser.setActive(isActive);
		currentUser.setRoles(getRoleEnumName(role).name());
		currentUser.setAuthorities(Role.ROLE_USER.getAuthorities());
		

		userRepository.save(currentUser);

		saveProfileImage(currentUser, profileImg);

		return currentUser;
	}

	@Override
	public void deleteUser(long id) {
		userRepository.deleteById(id);

	}

	@Override
	public void resetPassword(String email) throws EmailNotFoundException {
		User user = userRepository.findUserByEmail(email);
		if(user == null)
		{
			throw new EmailNotFoundException(ConstantVariables.NO_USER_FOUND_BY_EMAIL);
		}
		String password = generatedPassword();
		user.setPassword(encodedPassword(password));
		userRepository.save(user);
		emailService.sendNewPasswordEmail(user.getFirstName(), password, user.getEmail());

	}

	private String getTemporaryProfileImageUrl(String username) {

		return ServletUriComponentsBuilder.fromCurrentContextPath().path(FileConstant.DEAFULT_IMG_PATH + username)
				.toUriString();
	}

	private String encodedPassword(String password) {

		return passwordEncoder.encode(password);
	}

	private String generatedPassword() {

		return RandomStringUtils.randomAlphanumeric(10);
	}

	private String generateUserId() {

		return RandomStringUtils.randomNumeric(10);
	}

	private User validateNewUserNameAndEmail(String currentUserName, String newUserName, String newEmail)
			throws UserNotFoundException, UserNameExistException, EmailExistException {
		User userByNewUserName = findUserByUserName(newUserName);
		User userByNewEmail = findUserByEmail(newEmail);

		if (StringUtils.isNoneBlank(currentUserName)) {
			User currentUser = findUserByUserName(currentUserName);
			if (currentUser == null) {
				throw new UserNotFoundException("No user found by username" + currentUserName);

			}

			if (userByNewUserName != null && !currentUser.getId().equals(userByNewUserName.getId())) {
				throw new UserNameExistException(ConstantVariables.USER_EXIST);
			}

			if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
				throw new EmailExistException(ConstantVariables.EMAIL_EXIST);
			}
			return currentUser;
		} else {

			if (userByNewUserName != null) {
				throw new UserNameExistException(ConstantVariables.USER_EXIST);

			}

			if (userByNewEmail != null) {
				throw new EmailExistException(ConstantVariables.EMAIL_EXIST);
			}
			return null;
		}

	}

	@Override
	public List<User> getUsers() {

		return userRepository.findAll();
	}

	@Override
	public User findUserByUserName(String username) {

		return userRepository.findUserByUserName(username);
	}

	@Override
	public User findUserByEmail(String email) {

		return userRepository.findUserByEmail(email);
	}

	@Override
	public User updateProfileImg(String username, MultipartFile profileImage) throws UserNotFoundException, UserNameExistException, EmailExistException, IOException {
	
		User user = validateNewUserNameAndEmail(username, null, null);
		
		saveProfileImage(user, profileImage);
		
		
		return user;
	}

	private void saveProfileImage(User user, MultipartFile profileImg) throws IOException {
		if(profileImg != null)
		{
			Path userFolder = Paths.get(FileConstant.USER_FOLDER+user.getUserName()).toAbsolutePath().normalize();
			if(!Files.exists(userFolder))
			{
				Files.createDirectories(userFolder);
				Log.info(FileConstant.DIRECTORY_CREATED);
			}
			Files.deleteIfExists(Paths.get(userFolder+user.getUserName()+FileConstant.DOT+FileConstant.JPG_EXTENSION));
			Files.copy(profileImg.getInputStream(), userFolder.resolve(user.getUserName()+FileConstant.DOT+FileConstant.JPG_EXTENSION), StandardCopyOption.REPLACE_EXISTING);
			user.setProfileImageUrl(setProfileImageUrl(user.getUserName()));
			userRepository.save(user);
			
		}

	}

	private String setProfileImageUrl(String userName) {
		
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(FileConstant.USER_IMAGE_PATH + userName+ FileConstant.FORWARD_SLASH +userName+FileConstant.DOT+FileConstant.JPG_EXTENSION)
				.toUriString();
	}

	private Role getRoleEnumName(String role) {

		return Role.valueOf(role.toUpperCase());
	}

}
