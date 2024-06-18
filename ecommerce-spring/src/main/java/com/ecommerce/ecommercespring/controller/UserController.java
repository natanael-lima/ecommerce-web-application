package com.ecommerce.ecommercespring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.ecommerce.ecommercespring.dto.UserDTO;
import com.ecommerce.ecommercespring.entity.User;
import com.ecommerce.ecommercespring.enums.ActionType;
import com.ecommerce.ecommercespring.enums.TableType;
import com.ecommerce.ecommercespring.repository.UserRepository;
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
    @PutMapping("/update-user")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserDTO userRequest)
    {	
    	try {
    		userService.updateUser(userRequest);
            historyService.createHistory(TableType.USUARIO, ActionType.UPDATE);
            return ResponseEntity.ok(new ApiResponse( "Usuario actualizado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new ApiResponse("Usuario no encontrado o editar"));
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
    //Long userId = getCurrentUserId();
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
    

}
