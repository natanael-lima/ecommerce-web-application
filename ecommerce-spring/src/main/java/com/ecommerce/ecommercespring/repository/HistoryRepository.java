package com.ecommerce.ecommercespring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommercespring.entity.History;

public interface HistoryRepository extends JpaRepository<History,Long>{

}
