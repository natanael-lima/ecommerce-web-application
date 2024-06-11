package com.ecommerce.ecommercespring.service;

import com.ecommerce.ecommercespring.request.LoginRequest;
import com.ecommerce.ecommercespring.request.RegisterRequest;
import com.ecommerce.ecommercespring.response.AuthResponse;

public interface AuthService {

	public AuthResponse loginUser (LoginRequest request);
	
	public AuthResponse registerUser(RegisterRequest request);
}
