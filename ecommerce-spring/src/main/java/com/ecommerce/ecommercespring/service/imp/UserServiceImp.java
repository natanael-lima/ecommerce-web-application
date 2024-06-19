package com.ecommerce.ecommercespring.service.imp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ecommerce.ecommercespring.dto.UserDTO;
import com.ecommerce.ecommercespring.entity.User;
import com.ecommerce.ecommercespring.enums.RoleType;
import com.ecommerce.ecommercespring.exception.ResourceNotFoundException;
import com.ecommerce.ecommercespring.repository.UserRepository;
import com.ecommerce.ecommercespring.response.ApiResponse;
import com.ecommerce.ecommercespring.service.UserService;

import jakarta.transaction.Transactional;
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
	public List<UserDTO> getAllUserExceptMe(String name) throws Exception {
		// TODO Auto-generated method stub
				List<User>  allUsers = userRepository.findAllByUsernameNot(name);		 
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
		 User user = userRepository.findById(userRequest.getId())
                 .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		
		    // Actualizar solo los campos que pueden ser modificados
	        user.setLastname(userRequest.getLastname());
	        user.setName(userRequest.getName());
	        user.setTimestamp(LocalDateTime.now());
		   // Guardar el usuario en la base de datos


	       // No es necesario llamar a userRepository.updateUser(user.getId(), user.getLastname(), user.getName());
	        // Spring Data JPA se encargará de actualizar el usuario automáticamente al llamar a save()
	        userRepository.save(user);
		  return new ApiResponse("El usuario se actualizo satisfactoriamente");
	}
	
	@Transactional
	public ApiResponse updateUserBuildQuery(UserDTO userRequest) {
		 User user = userRepository.findById(userRequest.getId())
                 .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		
		 User user1 = User.builder()
			        .id(userRequest.getId())
			        .lastname(userRequest.getLastname())
			        .name(userRequest.getName())
			        .role(RoleType.USER)
			        .build();
			        
			        userRepository.updateUser(user1.getId(), user1.getLastname(), user1.getName());
		  return new ApiResponse("El usuario se actualizo satisfactoriamente");
	}

	

	public void changePassword(Long userId, String currentPassword, String newPassword) throws Exception {
        // Encuentra el usuario por su ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + userId));

        // Verifica si la contraseña actual coincide
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BadRequestException("La contraseña actual es incorrecta.");
        }

        // Encripta y establece la nueva contraseña
        user.setPassword(passwordEncoder.encode(newPassword));

        // Guarda el usuario actualizado en la base de datos
        userRepository.save(user);
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
	
	@Override
	public Optional<User>findByUsername(String username) {
		// TODO Auto-generated method stub
		Optional<User> result= userRepository.findByUsername(username);
		
		return result;
	}

}
