package com.ecommerce.ecommercespring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommercespring.entity.Product;
import com.ecommerce.ecommercespring.entity.SearchHistory;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory,Long> {
	
	Optional<SearchHistory> findByQuery(String query);
    Optional<SearchHistory> findTopByOrderBySearchCountDesc();
    
}
