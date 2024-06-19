package com.ecommerce.ecommercespring.controller;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

import com.ecommerce.ecommercespring.dto.ProductDTO;
import com.ecommerce.ecommercespring.dto.UserDTO;
import com.ecommerce.ecommercespring.entity.User;
import com.ecommerce.ecommercespring.enums.ActionType;
import com.ecommerce.ecommercespring.enums.TableType;
import com.ecommerce.ecommercespring.exception.CategoryNotFoundException;
import com.ecommerce.ecommercespring.exception.ResourceNotFoundException;
import com.ecommerce.ecommercespring.repository.UserRepository;
import com.ecommerce.ecommercespring.request.ChangePasswordRequest;
import com.ecommerce.ecommercespring.response.ApiResponse;
import com.ecommerce.ecommercespring.service.HistoryService;
import com.ecommerce.ecommercespring.service.UserService;

@CrossOrigin(origins = "http://localhost:4200") // Reemplaza esto con el dominio de tu frontend
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
    private UserService userService;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private HistoryService historyService;
	
	
	// API para actualizar los datos del usuario.
    @PutMapping("/update-user/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody UserDTO userRequest)
    {	
    	 try {
             userRequest.setId(id);
             ApiResponse response = userService.updateUser(userRequest);
             return ResponseEntity.ok(response);
         } catch (RuntimeException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Usuario no encontrado o no se pudo actualizar"));
         }
    	
    }
    
    // API para actualizar dedatos descartado
    @PutMapping("/update-user-query")
    public ResponseEntity<ApiResponse> updateUserQuery(@RequestBody UserDTO userRequest)
    {	
    	 try {
             ApiResponse response = userService.updateUser(userRequest);
             return ResponseEntity.ok(response);
         } catch (RuntimeException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Usuario no encontrado o no se pudo actualizar"));
         }
    	
    }
    
    
    // API para eliminar los datos del usuario.
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id)
    {   
    	 try {
             userService.deleteUser(id);
             historyService.createHistory(TableType.USUARIO, ActionType.DELETE);
             return ResponseEntity.ok(new ApiResponse( "Usuario eliminado correctamente"));
         } catch (RuntimeException e) {
             return ResponseEntity.status(404).body(new ApiResponse("Usuario no encontrado o no se pudo eliminar"));
         }
    }
	
	
	// API para verficar el username.
    @GetMapping("/check-username")
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
	public ResponseEntity<UserDTO> getCurrentUser() {
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        // Obtener los detalles del usuario actualmente autenticado
	    	String username = authentication.getName();
	    	//User logueado date
	        User user = userService.findByUsername(username).orElse(null);
	      
	        if (user == null) {
	        	 return ResponseEntity.notFound().build(); 
	        }
	        	System.out.println("id de user: "+user.getId());
       	 		UserDTO userDTO = userService.getUser(user.getId());
	         if (userDTO==null)
	         {
	            return ResponseEntity.notFound().build();
	         }
	         return ResponseEntity.ok(userDTO);
	       
	       
	}
    
    
    // Metodo para obtener el id del user logueado solo cuando sea necesario( no usando en current).
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.findByUsername(username).orElse(null);
            if (user != null) {
            	 System.out.println("id de user: "+user.getId());
                return user.getId();
               
            }
        }
        return null;
    }
    
    // API para cambiar password.
    @PutMapping("/{id}/change-password")
    public ResponseEntity<ApiResponse> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest changePasswordRequest) throws Exception {
    	
    	// Obtener el contexto de autenticación
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verificar si el usuario está autenticado
        if (authentication != null && authentication.isAuthenticated()) {
            		// Obtener los detalles del usuario autenticado
		        	String username = authentication.getName();
		            User userId = userService.findByUsername(username).orElse(null);
		            
		            System.out.println("id de user1: "+userId.getId());
		            System.out.println("id de user2: "+id);
		            
			        // Comparar el ID del usuario autenticado con el ID que se desea cambiar
			        if (!userId.getId().equals(id)) {
			            throw new AccessDeniedException("No tienes permiso para cambiar la contraseña de otro usuario.");
			        }
			        try {
		        		userService.changePassword(id, changePasswordRequest.getCurrentPassword(), changePasswordRequest.getNewPassword());
		                historyService.createHistory(TableType.USUARIO, ActionType.UPDATE);
		                return ResponseEntity.ok(new ApiResponse( "La contraseña se cambió satisfactoriamente."));
		            } catch (RuntimeException e) {
		                return ResponseEntity.status(404).body(new ApiResponse("La contraseña no se pudo cambiar."));
		            }    
		 
        } else {
            // Si no está autenticado
        	return ResponseEntity.notFound().build();
        }
    }
    
    
    // API para obtener el usuario by ID por paramaetro.
 	@GetMapping("/get-user/{id}")
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
    @GetMapping("/get-all")
    public ResponseEntity<List<UserDTO>> getAllUsers() throws Exception
    {      
    		List<UserDTO> users = userService.getAllUser(); // Llama al servicio para obtener todos los usuarios
    
		    if (users == null || users.isEmpty()) {
		        return ResponseEntity.notFound().build(); // Retorna 404 si no hay usuarios encontrados
		    }
		    
		    return ResponseEntity.ok(users); // Retorna 200 OK con la lista de usuarios
    }
    
    
    // API para obtener todos los usuarios menos el autenticado.
    @GetMapping("/get-all-except-me")
    public ResponseEntity<List<UserDTO>> findAllExceptMe() throws Exception {
    	
    	// Obtener el usuario autenticado
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = userDetails.getUsername();
    	
    	
    	List<UserDTO> users = userService.getAllUserExceptMe(currentUsername); // Llama al servicio para obtener todos los usuarios
        
	    if (users == null || users.isEmpty()) {
	        return ResponseEntity.notFound().build(); // Retorna 404 si no hay usuarios encontrados
	    }
	    
	    return ResponseEntity.ok(users); // Retorna 200 OK con la lista de usuarios
    }
    
    // API para contar users.
    @GetMapping("/count-user")
    public  ResponseEntity<Integer> getCountUsers() {
        try {
        	int total  = userService.countAllUsers();
            
            if (total==0) {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
            }
            return ResponseEntity.ok(total);
        } catch (CategoryNotFoundException e) {
        	return ResponseEntity.noContent().build(); 
        }
        
    }

}
