package com.codewithzosh.ecommerce.Request;

import lombok.Data;

@Data
public class LoginRequest {

	public LoginRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	private String email;
	private String password;

}
