package com.codewithzosh.ecommerce.Service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.codewithzosh.ecommerce.Exception.UserException;
import com.codewithzosh.ecommerce.Model.User;

public interface UserService  {

	public User findUserById(Long userId)throws UserException;
	
	public User findUserByJwt(String jwt)throws UserException;
}
