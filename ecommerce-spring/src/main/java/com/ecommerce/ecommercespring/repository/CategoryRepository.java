package com.ecommerce.ecommercespring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommercespring.dto.CategoryDTO;
import com.ecommerce.ecommercespring.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
	//public Category findByName (String name);
	Optional<Category> findByName(String name);
}
