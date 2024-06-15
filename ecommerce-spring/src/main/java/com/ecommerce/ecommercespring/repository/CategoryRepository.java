package com.ecommerce.ecommercespring.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommercespring.dto.CategoryDTO;
import com.ecommerce.ecommercespring.entity.Category;

import jakarta.transaction.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
	//public Category findByName (String name);
	Optional<Category> findByName(String name);
	
	@Modifying
    @Transactional
    @Query("update Category c set c.name = :name, c.description = :description, c.timestamp = :timestamp where c.id = :id")
    void updateCategory(@Param("id") Long id, @Param("name") String name, @Param("description") String description, @Param("timestamp") LocalDateTime timestamp);
}
