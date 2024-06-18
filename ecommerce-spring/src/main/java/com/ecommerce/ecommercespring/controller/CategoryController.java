package com.ecommerce.ecommercespring.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommercespring.dto.CategoryDTO;
import com.ecommerce.ecommercespring.dto.ProductDTO;
import com.ecommerce.ecommercespring.entity.Category;
import com.ecommerce.ecommercespring.entity.Product;
import com.ecommerce.ecommercespring.enums.ActionType;
import com.ecommerce.ecommercespring.enums.TableType;
import com.ecommerce.ecommercespring.response.ApiResponse;
import com.ecommerce.ecommercespring.service.CategoryService;
import com.ecommerce.ecommercespring.service.HistoryService;


@CrossOrigin(origins = "http://localhost:4200") // Reemplaza esto con el dominio de tu frontend
@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
    private CategoryService categoryService;
	
	@Autowired
    private HistoryService historyService;
	
	// API para obtener una categoria by ID.
    @GetMapping("/get-category/{id}")
    public ResponseEntity<CategoryDTO> findCategoryById(@PathVariable Long id) throws Exception {
        try {
        	CategoryDTO categoryDTO = categoryService.getCategoryById(id);
            return ResponseEntity.ok(categoryDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build(); // Retorna un código 204 si el chat no está encontrado
        }
    }
    // API para registrar una categoria.
    @PostMapping("/registration-category")
    public ResponseEntity<ApiResponse> registerProducto(@RequestBody Category request)
    {	if (request == null || request.getName() == null || request.getName().isEmpty()) {
    	 return ResponseEntity.badRequest().body(new ApiResponse("Error: La categoría no puede estar vacía o sin nombre"));
	    } else {
	    	categoryService.saveCategory(request);
	    	historyService.createHistory(TableType.CATEGORIA,ActionType.CREATE);
	    	return ResponseEntity.ok(new ApiResponse("La categoria se registró correctamente"));
    	   }
    }
    
    // API para eleminar un categoria.
    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<Category> deleteProduct(@PathVariable Long id) throws Exception {
        try {
        	categoryService.deleteCategory(id);
        	historyService.createHistory(TableType.CATEGORIA,ActionType.DELETE);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build();
        }
    }
    
    // API para actualizar un categoria.
    @PutMapping(value="/update-category/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @RequestBody CategoryDTO dto) throws Exception {
    	 if (dto == null) {
    		 return ResponseEntity.badRequest().body(new ApiResponse("Error: no se puedo actualizar"));
 	    } else {
 	    	try {;
 	    		dto.setId(id);
 	    		categoryService.updateCategory(dto);
 	        	historyService.createHistory(TableType.CATEGORIA,ActionType.UPDATE);
 	        	 return ResponseEntity.ok(new ApiResponse("Producto actualizado con éxito"));
 	        } catch (RuntimeException e) {
 	            return ResponseEntity.noContent().build(); 
 	        }
 	    }
    }
    
    
    // API para obtener una lista de todos las categorias.
    @GetMapping("/get-all")
    public ResponseEntity<List<CategoryDTO>> findAll() throws Exception {
        try {
        	List<CategoryDTO> categoryAll = categoryService.getAllCategory();
            return ResponseEntity.ok(categoryAll);
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build(); // Retorna un código 204 si el chat no está encontrado
        }
    }
    
    
	
}
