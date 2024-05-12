package com.ecommerce.ecommercespring.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommercespring.dto.CategoryDTO;
import com.ecommerce.ecommercespring.dto.ProductDTO;
import com.ecommerce.ecommercespring.entity.Category;
import com.ecommerce.ecommercespring.entity.Product;
import com.ecommerce.ecommercespring.service.CategoryService;


@CrossOrigin(origins = "http://localhost:4200") // Reemplaza esto con el dominio de tu frontend
@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
    private CategoryService categoryService;
	
	// API para obtener una categoria by ID.
    @GetMapping("/find/{id}")
    public ResponseEntity<CategoryDTO> findCategoryById(@PathVariable Long id) throws Exception {
        try {
        	CategoryDTO categoryDTO = categoryService.getCategoryById(id);
            return ResponseEntity.ok(categoryDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build(); // Retorna un código 204 si el chat no está encontrado
        }
    }
    
    @PostMapping("/registration-category")
    public ResponseEntity<String> registerProducto(@RequestBody Category request)
    {	if (request==null) {
	        return ResponseEntity.badRequest().body(new String("Error ya existe ese producto o es vacio"));
	    } else {
	    	categoryService.saveCategory(request);
	    	return ResponseEntity.ok("La categoria registro correctamente");
    	   }
    }
	
    @GetMapping("/findAll")
    public ResponseEntity<List<CategoryDTO>> findAll() throws Exception {
        try {
        	List<CategoryDTO> categoryAll = categoryService.getAllCategory();
            return ResponseEntity.ok(categoryAll);
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build(); // Retorna un código 204 si el chat no está encontrado
        }
    }
	
}
