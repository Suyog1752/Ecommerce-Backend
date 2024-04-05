package com.codewithzosh.ecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.standard.DateTimeContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithzosh.ecommerce.Exception.UserException;
import com.codewithzosh.ecommerce.JwtConfig.JwtProvider;
import com.codewithzosh.ecommerce.Model.User;
import com.codewithzosh.ecommerce.Repository.UserRepository;
import com.codewithzosh.ecommerce.Request.LoginRequest;
import com.codewithzosh.ecommerce.Response.AuthResponse;
import com.codewithzosh.ecommerce.Service.Imple.CustomeUserServiceImplementation;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private CustomeUserServiceImplementation customeUserServiceImplementation;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {

		String email = user.getEmail();
		String password = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String mobile = user.getMobile();

		User isEmailExits = this.userRepository.findByEmail(email);
		if (isEmailExits != null) {
			throw new UserException("Email is already used with anthor account !!");
		}
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		createdUser.setMobile(mobile);

		User savedUser = this.userRepository.save(createdUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
				savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = this.jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse(token, "Signup Success");

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		Authentication authentication = authenticate(username, password);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = this.jwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse(token, "Signin Success");

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = this.customeUserServiceImplementation.loadUserByUsername(username);

		if (userDetails == null) {
			throw new BadCredentialsException("Invalid Username...");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {

			throw new BadCredentialsException("Invalid Password...");
		}

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
