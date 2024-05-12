package com.ecommerce.ecommercespring.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.ecommercespring.dto.CategoryDTO;
import com.ecommerce.ecommercespring.entity.Category;
import com.ecommerce.ecommercespring.repository.CategoryRepository;
import com.ecommerce.ecommercespring.service.CategoryService;

@Service
public class CategoryServiceImp implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	private CategoryDTO convertdto(Optional<Category> category) throws Exception {
		if (category.isPresent()) {
			Category cat = category.get();
	    return CategoryDTO.builder()
	            .id(cat.getId())
	            .name(cat.getName())
	            .description(cat.getDescription())
	            .timestamp(cat.getTimestamp())
	            .build();
		} else {
	        // Manejo de error si el usuario no está presente
	        throw new Exception("Product not found");
	    }
	}
	
	private CategoryDTO convertToDTO(Category cat) {

		return CategoryDTO.builder()
	            .id(cat.getId())
	            .name(cat.getName())
	            .description(cat.getDescription())
	            .timestamp(cat.getTimestamp())
	            .build();
	}
	
	@Override
	public Category saveCategory(Category category) {
		// TODO Auto-generated method stub
		return categoryRepository.save(category);
	}

	@Override
	public CategoryDTO getCategoryById(Long id) throws Exception {
		// TODO Auto-generated method stub
		 Optional<Category> product = categoryRepository.findById(id);
		return convertdto(product);
	}

	@Override
	public void deleteCategory(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<CategoryDTO> getAllCategory() throws Exception {
		// TODO Auto-generated method stub
		List<Category>  allCategory = categoryRepository.findAll();		 
		List<CategoryDTO> allCategoryDTO = new ArrayList<>();
		
		for (Category cat : allCategory) {
			allCategoryDTO.add(convertToDTO(cat));
		}
	
		return allCategoryDTO;
	}

}
