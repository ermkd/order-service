package com.boolment.jobapp.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.boolment.jobapp.entity.HttpResponse;
import com.boolment.jobapp.entity.User;
import com.boolment.jobapp.entity.UserPrincipal;
import com.boolment.jobapp.exception.EmailExistException;
import com.boolment.jobapp.exception.EmailNotFoundException;
import com.boolment.jobapp.exception.ExceptionHandling;
import com.boolment.jobapp.exception.UserNameExistException;
import com.boolment.jobapp.exception.UserNotFoundException;
import com.boolment.jobapp.serviceinterface.UserServiceInterface;
import com.boolment.jobapp.util.ConstantVariables;
import com.boolment.jobapp.util.FileConstant;
import com.boolment.jobapp.util.JWTTokenProvider;
import com.boolment.jobapp.util.SecurityConstant;

@RestController
@RequestMapping(path = { "/", "/user" })
public class UserController extends ExceptionHandling {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTTokenProvider jwtTokenProvider;

	@Autowired
	private UserServiceInterface userServiceInterface;

	@PostMapping(value = "/login")
	public ResponseEntity<User> login(@RequestBody User user) {
		authenticate(user.getUserName(), user.getPassword());

		User loginUser = userServiceInterface.findUserByUserName(user.getUserName());
		UserPrincipal userPrincipal = new UserPrincipal(loginUser);
		HttpHeaders jwtHeaders = getJwtHeaders(userPrincipal);

		return new ResponseEntity<>(loginUser, jwtHeaders, HttpStatus.OK);
	}

	@PostMapping(value = "/register")
	public ResponseEntity<User> register(@RequestBody User user)
			throws UserNotFoundException, UserNameExistException, EmailExistException {
		User newUser = userServiceInterface.register(user.getFirstName(), user.getLastName(), user.getUserName(),
				user.getEmail());

		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<User> addNewUser(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("userName") String userName,
			@RequestParam("email") String email, @RequestParam("role") String role,
			@RequestParam("isActive") String isActive, @RequestParam("isNotLocked") String isNotLocked,
			@RequestParam(value = "profileImage", required = false) MultipartFile profileImage)
			throws UserNotFoundException, UserNameExistException, EmailExistException, IOException {

		User user = userServiceInterface.addUser(firstName, lastName, userName, email, role,
				Boolean.parseBoolean(isNotLocked), Boolean.parseBoolean(isActive), profileImage);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<User> update(@RequestParam("currentUserName") String currentUserName,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("userName") String userName, @RequestParam("email") String email,
			@RequestParam("role") String role, @RequestParam("isActive") String isActive,
			@RequestParam("isNonLocked") String isNonLocked,
			@RequestParam(value = "profileImage", required = false) MultipartFile profileImage)
			throws UserNotFoundException, UserNameExistException, EmailExistException, IOException {

		User updatedUser = userServiceInterface.updateUser(currentUserName, firstName, lastName, userName, email, role,
				Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage);

		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	@GetMapping("/find/{userName}")
	public ResponseEntity<User> getUser(@PathParam("userName") String userName) {
		User user = userServiceInterface.findUserByUserName(userName);
		return new ResponseEntity<>(user, HttpStatus.OK);

	}

	@GetMapping("/list")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> users = userServiceInterface.getUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);

	}

	@GetMapping("/resetPassword/{email}")
	public ResponseEntity<HttpResponse> resetPassword(@PathParam("email") String email) throws EmailNotFoundException {
		userServiceInterface.resetPassword(email);
		return response(HttpStatus.OK, ConstantVariables.RESET_EMAIL_SEND + email);

	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAnyAuthority('user:delete')")
	public ResponseEntity<HttpResponse> deleteUser(@PathVariable("id") long id) {
		userServiceInterface.deleteUser(id);
		return response(HttpStatus.NO_CONTENT, ConstantVariables.DELETE_SUCCESS);

	}

	@PostMapping("/updateProfileImage")
	public ResponseEntity<User> updateProfileImage(@RequestParam("userName") String userName,
			@RequestParam("profileImage") MultipartFile profileImage)
			throws UserNotFoundException, UserNameExistException, EmailExistException, IOException {

		User user = userServiceInterface.updateProfileImg(userName, profileImage);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping(path = "/images/{userName}/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getProfileImage(@PathVariable("userName") String userName,
			@PathVariable("fileName") String fileName) throws IOException {
		return Files
				.readAllBytes(Paths.get(FileConstant.USER_FOLDER + userName + FileConstant.FORWARD_SLASH + fileName));
	}
	
	@GetMapping(path = "/images/{profile}/{username}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getTempProfileImage(@PathVariable("userName") String userName) throws IOException {
		
		URL url = new URL(FileConstant.TEMP_PROFILE_IMAGE_BASE_URL+userName);
		
		  ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        try (InputStream inputStream = url.openStream()) {
	            int bytesRead;
	            byte[] chunk = new byte[1024];
	            while((bytesRead = inputStream.read(chunk)) > 0) {
	                byteArrayOutputStream.write(chunk, 0, bytesRead);
	            }
	        }
	        return byteArrayOutputStream.toByteArray();
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {

		HttpResponse body = new HttpResponse(new Date(), httpStatus.value(), httpStatus,
				httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase());
		return new ResponseEntity<>(body, httpStatus);
	}

	private HttpHeaders getJwtHeaders(UserPrincipal userPrincipal) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(SecurityConstant.JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
		return headers;
	}

	private void authenticate(String userName, String password) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
	}
}
