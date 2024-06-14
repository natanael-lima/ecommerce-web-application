package com.ecommerce.ecommercespring.service.imp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ecommerce.ecommercespring.dto.UserDTO;
import com.ecommerce.ecommercespring.entity.User;
import com.ecommerce.ecommercespring.enums.RoleType;
import com.ecommerce.ecommercespring.repository.UserRepository;
import com.ecommerce.ecommercespring.response.ApiResponse;
import com.ecommerce.ecommercespring.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private UserDTO convertdto(Optional<User> u) throws Exception {
		if (u.isPresent()) {
			User user = u.get();
			return UserDTO.builder()
					.id(user.getId())
		            .username(user.getUsername())
			        .lastname(user.getLastname())
			        .name(user.getName())
			        .role(user.getRole())
			        .timestamp(user.getTimestamp())
		            .build();
		} else {
	        // Manejo de error si el usuario no está presente
	        throw new Exception("Product not found");
	    }
	}
	
	
	
	private UserDTO convertToDTO(User u) {

		return UserDTO.builder()
				.id(u.getId())
	            .username(u.getUsername())
		        .lastname(u.getLastname())
		        .name(u.getName())
		        .role(u.getRole())
		        .timestamp(u.getTimestamp())
	            .build();
	}
	
	@Override
	public User saveUser(User userRequest) {
		// Construir el objeto User con datos
		User user = User.builder()
		        .username(userRequest.getUsername())
		        .password(passwordEncoder.encode(userRequest.getPassword()))
		        .lastname(userRequest.getLastname())
		        .name(userRequest.getName())
		        .role(RoleType.ADMIN)
		        .timestamp(LocalDateTime.now())
		        .build();

		// Guardar el usuario en la base de datos
		return userRepository.save(user);
	}
	
	@Override
	public UserDTO getUserById(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<User> usuario = userRepository.findById(id);
		return convertdto(usuario);
	}
	
	@Override
	public UserDTO getUser(Long id) {
	        
		User user= userRepository.findById(id).orElse(null);
	       
	        if (user!=null)
	        {
	            UserDTO userDTO = UserDTO.builder()
	            .id(user.getId())
	            .username(user.getUsername())
	            .lastname(user.getLastname())
	            .name(user.getName())
	            .role(user.getRole())
	            .timestamp(user.getTimestamp())
	            .build();
	            return userDTO;
	        }
			return null;      
	}

	@Override
	public List<UserDTO> getAllUser() throws Exception {
		// TODO Auto-generated method stub
				List<User>  allUsers = userRepository.findAll();		 
				List<UserDTO> allUsersDTO = new ArrayList<>();
				
				for (User u : allUsers) {
					allUsersDTO.add(convertToDTO(u));
				}
			
				return allUsersDTO;
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}



	@Override
	public ApiResponse updateUser(UserDTO userRequest) {
		User user = User.builder()
		        .id(userRequest.getId())
		        .lastname(userRequest.getLastname())
		        .name(userRequest.getName())
		        .timestamp(LocalDateTime.now())
		        .role(RoleType.USER)
		        .build();
		// Guardar el usuario en la base de datos
		userRepository.updateUser(user.getId(), user.getLastname(), user.getName());
		  return new ApiResponse("El usuario se actualizo satisfactoriamente");
	}



	@Override
	public boolean existsByName(String name) {
		// Convertir el nombre del rol a RoleType
        RoleType roleType = RoleType.valueOf(name.toUpperCase());

        // Verificar si existe un usuario con el rol dado
        User user = userRepository.findByRole(roleType);
        return user != null; // Devolver true si se encontró un usuario, false de lo contrario
	}



	@Override
	public boolean existsAdminRole() {
		// TODO Auto-generated method stub
		return userRepository.existsAdminRole();
	}



	@Override
	public boolean existsUsername(String name) {
		// TODO Auto-generated method stub
		return userRepository.existsByUsername(name);
	}

}
