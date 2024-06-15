package com.ecommerce.ecommercespring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.ecommercespring.dto.CategoryDTO;
import com.ecommerce.ecommercespring.dto.ProductDTO;
import com.ecommerce.ecommercespring.entity.Category;
import com.ecommerce.ecommercespring.response.ApiResponse;

public interface CategoryService {
	
	public Category saveCategory(Category category);
	
	public CategoryDTO getCategoryById(Long id) throws Exception;
	
	public List<CategoryDTO> getAllCategory() throws Exception;
	
	public void deleteCategory(Long id);
	
	public ApiResponse updateCategory(CategoryDTO product) throws Exception;

}
