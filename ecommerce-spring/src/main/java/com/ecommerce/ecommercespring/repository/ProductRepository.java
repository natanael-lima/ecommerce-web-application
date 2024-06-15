package com.ecommerce.ecommercespring.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.ecommercespring.entity.Category;
import com.ecommerce.ecommercespring.entity.Product;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
	
	// Método para buscar productos por nombre (contiene)
	public List<Product> findByNameContaining (String buscado);
	
	@Modifying
    @Transactional
    @Query("update Product p set p.name=:name, p.description=:description, p.price=:price, p.stock=:stock, p.image=:image, p.timestamp=:timestamp, p.categoria=:categoria where p.id = :id")
    void updateProduct(@Param("id") Long id, @Param("name") String name, @Param("description") String description, 
                       @Param("price") Double price, @Param("stock") int stock, @Param("image") String image, 
                       @Param("timestamp") LocalDateTime timestamp, @Param("categoria") Category categoria);
	
	// Método para buscar productos por rango de precios
    public List<Product> findByPriceBetween(Double priceStart, Double priceEnd);
    
    // Método para buscar todos los productos destacados
    public List<Product> findByHighlight(Boolean filter);
    
    // Método para buscar todos los productos by categoria
    List<Product> findByCategoriaName(String categoryName);
    
    // Método para buscar todos los productos by color
    //List<Product> findByColor(String color);
}
