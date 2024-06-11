package com.ecommerce.ecommercespring.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ecommerce.ecommercespring.repository.UserRepository;
import com.ecommerce.ecommercespring.request.LoginRequest;
import com.ecommerce.ecommercespring.request.RegisterRequest;
import com.ecommerce.ecommercespring.response.AuthResponse;
import com.ecommerce.ecommercespring.service.AuthService;
import com.ecommerce.ecommercespring.service.JwtService;



public class AuthServiceImp implements AuthService{
	
	@Autowired
	private  UserRepository	userRepository;
	
	@Autowired
	private final JwtService jwtService;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuthenticationManager authenticationManager;

	/*@Override
	public AuthResponse registerUser(RegisterRequest request) {
			// Construir el objeto User con la imagen y otros datos
				User user = User.builder()
				        .username(request.getUsername())
				        .password(passwordEncoder.encode(request.getPassword()))
				        .lastname(request.getLastname())
				        .name(request.getName())
				        .role(RoleType.USER)
				        .build();

				// Guardar el usuario en la base de datos
				userRepository.save(user);

				// Crear y devolver la respuesta de autenticación
				return AuthResponse.builder()
				        .token(jwtService.getToken(user))
				        .build();
	}*/



	@Override
	public AuthResponse loginUser(LoginRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();
	}
}
