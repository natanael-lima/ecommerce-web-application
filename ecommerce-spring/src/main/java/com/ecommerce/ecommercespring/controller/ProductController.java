package com.ecommerce.ecommercespring.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.ecommercespring.dto.ProductDTO;
import com.ecommerce.ecommercespring.dto.ProductRegistrationDTO;
import com.ecommerce.ecommercespring.entity.Product;
import com.ecommerce.ecommercespring.service.ProductService;

@CrossOrigin(origins = "http://localhost:4200") // Reemplaza esto con el dominio de tu frontend
@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
    private ProductService productService;

	// API para obtener un producto by ID.
    @GetMapping("/find/{id}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable Long id) throws Exception {
        try {
        	ProductDTO prodDTO = productService.getProductById(id);
            return ResponseEntity.ok(prodDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build(); // Retorna un código 204 si el chat no está encontrado
        }
    }
    
    @PostMapping("/registration-product")
    public ResponseEntity<String> registerProducto(@RequestBody ProductRegistrationDTO request) throws Exception
    {	if (request==null) {
	        return ResponseEntity.badRequest().body(new String("Error ya existe ese producto o es vacio"));
	    } else {
	    	productService.saveProduct(request);
	    	return ResponseEntity.ok("El producto registro correctamente");
    	   }
    }
    // API que registra un like.
    /*@PostMapping("/register/{id}")
    public ResponseEntity<Map<String, String>> ActionLike(@PathVariable Long targetId) {
    
    	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername(); // Si el username se utiliza para buscar el UserProfile
        UserProfile actor = userProfileService.findByUsername(username); // Método para buscar el UserProfile por nombre de usuario
        UserProfile target = userProfileService.findUserById(targetId);
        matchService.processAction(actor, target,ActionType.LIKE);
        Map<String, String> response = new HashMap<>();
        response.put("message", "El like se registro correctamente");
        return ResponseEntity.ok(response);
    }*/
}	
