package com.ecommerce.ecommercespring.controller;



import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ecommerce.ecommercespring.dto.ProductDTO;
import com.ecommerce.ecommercespring.dto.ProductRegistrationDTO;
import com.ecommerce.ecommercespring.entity.Product;
import com.ecommerce.ecommercespring.enums.ActionType;
import com.ecommerce.ecommercespring.enums.TableType;
import com.ecommerce.ecommercespring.response.ApiResponse;
import com.ecommerce.ecommercespring.service.HistoryService;
import com.ecommerce.ecommercespring.service.ProductService;


@CrossOrigin(origins = "http://localhost:4200") // Reemplaza esto con el dominio de tu frontend
@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
    private ProductService productService;
	@Autowired
    private HistoryService historyService;

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
    
    // API para registrar un nuevo producto.
    @PostMapping(value="/registration-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerProducto(@RequestPart("file") MultipartFile file, @RequestPart ProductRegistrationDTO request) throws Exception
    {	
    	 if (file.isEmpty() || request == null) {
    	        return ResponseEntity.badRequest().body("Error: el archivo de imagen o la solicitud están vacíos");
    	    } else {
    	        try {
    	         
    	            productService.saveProduct(request, file);
    	            historyService.createHistory(TableType.PRODUCTO,ActionType.CREATE);
    	            return ResponseEntity.ok("El producto se registró correctamente");
    	        } catch (IOException ex) {
    	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen: " + ex.getMessage());
    	        }
    	    }
    }
    
    // API para eleminar un producto.
    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) throws Exception {
        try {
        	productService.deleteProduct(id);
        	historyService.createHistory(TableType.PRODUCTO,ActionType.DELETE);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build(); // Retorna un código 204 si el chat no está encontrado
        }
    }
    
    // API para actualizar un producto.
    @PutMapping(value="/update-product/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestPart("file") MultipartFile file, @RequestPart ProductDTO product) throws Exception {
    	 if (file.isEmpty() || product == null) {
    		 return ResponseEntity.badRequest().body(new ApiResponse("Error: el archivo de imagen o la solicitud están vacíos"));
 	    } else {
 	    	try {;
 	    		product.setId(id);
 	    		productService.updateProduct(product, file);  
 	        	historyService.createHistory(TableType.PRODUCTO,ActionType.UPDATE);
 	        	 return ResponseEntity.ok(new ApiResponse("Producto actualizado con éxito"));
 	        } catch (RuntimeException e) {
 	            return ResponseEntity.noContent().build(); // Retorna un código 204 si el chat no está encontrado
 	        }
 	    }
    }
    
    // API para obtener todos los productos.
    @GetMapping("/findAll")
    public ResponseEntity<List<ProductDTO>> findAll() throws Exception {
    	
    	try {
        	List<ProductDTO> categoryAll = productService.getAllProduct();
            return ResponseEntity.ok(categoryAll);
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build(); // Retorna un código 204 si el chat no está encontrado
        }
    }
    
    // API para obtener todos los productos por busqueda.
    @GetMapping("/search-products")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(@RequestParam String name) {
        List<ProductDTO> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }
    
    // Endpoint para buscar productos por rango de precios
    @GetMapping("/price-range")
    public ResponseEntity<List<ProductDTO>> searchProductsByPriceRange(@RequestParam Double priceStart, @RequestParam Double priceEnd) {
    	List<ProductDTO> products = productService.searchProductsByPriceRange(priceStart, priceEnd);
    	return ResponseEntity.ok(products);
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
        
        if (request==null) {
	        return ResponseEntity.badRequest().body(new String("Error ya existe ese producto o es vacio"));
	    } else {
	    	productService.saveProduct(request);
	    	return ResponseEntity.ok("El producto registro correctamente");
    	   }
    }*/
}	
