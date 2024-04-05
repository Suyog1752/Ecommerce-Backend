package com.codewithzosh.ecommerce.Service.Imple;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithzosh.ecommerce.Model.User;
import com.codewithzosh.ecommerce.Repository.UserRepository;

@Service
public class CustomeUserServiceImplementation implements UserDetailsService {
@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("user not found with this email :"+username);
		}
		
		List <GrantedAuthority> authorities=new ArrayList<>();
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
				
		
	}

}
