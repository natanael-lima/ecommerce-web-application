package com.ecommerce.ecommercespring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommercespring.dto.UserDTO;
import com.ecommerce.ecommercespring.entity.User;
import com.ecommerce.ecommercespring.repository.UserRepository;
import com.ecommerce.ecommercespring.response.ApiResponse;
import com.ecommerce.ecommercespring.service.UserService;

@CrossOrigin(origins = "http://localhost:4200") // Reemplaza esto con el dominio de tu frontend
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
    private UserService userService;
	
	@Autowired
    private UserRepository userRepository;
	
	
	// API para actualizar los datos del usuario.
    @PutMapping("/updateUser")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserDTO userRequest)
    {
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }
    
    // API para eliminar los datos del usuario.
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id)
    {    
    	userService.deleteUser(id);
    	
        return ResponseEntity.ok(new ApiResponse ("Usuario eliminado correctamente"));
    }
	
	
	// API para verficar el username.
    @GetMapping("/checkUsername")
    public ResponseEntity<Map<String, Boolean>> checkUsername(@RequestParam String username) {
    	 System.out.println("Buscando usuario: '" + username + "'");
        boolean exists = userRepository.findByUsername(username).isPresent();
        System.out.println("Usuario encontrado: " + exists);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
    
	// API para obtener el usuario logueado actual.
    @GetMapping("/current")
	public UserDetails getCurrentUser() {
	        // Obtener los detalles del usuario actualmente autenticado
	        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
    
    // API para obtener el usuario by ID por paramaetro.
 	@GetMapping(value = "{id}")
     public ResponseEntity<UserDTO> getUser(@PathVariable Long id)
     {
         UserDTO userDTO = userService.getUser(id);
         if (userDTO==null)
         {
            return ResponseEntity.notFound().build();
         }
         return ResponseEntity.ok(userDTO);
     }
    
    
    
    // API para mostrar todos los usuarios.
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() throws Exception
    {      
    		List<UserDTO> users = userService.getAllUser(); // Llama al servicio para obtener todos los usuarios
    
		    if (users == null || users.isEmpty()) {
		        return ResponseEntity.notFound().build(); // Retorna 404 si no hay usuarios encontrados
		    }
		    
		    return ResponseEntity.ok(users); // Retorna 200 OK con la lista de usuarios
    }
    

}
