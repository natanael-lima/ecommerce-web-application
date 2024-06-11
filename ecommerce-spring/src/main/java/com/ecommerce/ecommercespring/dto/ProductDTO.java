package com.ecommerce.ecommercespring.dto;

import java.time.LocalDateTime;

import com.ecommerce.ecommercespring.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
     Long id;
     String name;
     String description;
     Double price;
     int stock;
     String image;
     LocalDateTime timestamp;
     String categoria;
}
