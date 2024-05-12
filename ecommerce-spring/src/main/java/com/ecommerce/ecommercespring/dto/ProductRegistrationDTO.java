package com.ecommerce.ecommercespring.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRegistrationDTO {
    private String name;
    private String description;
    private Double price;
    private int stock;
    private String image;
    private String categoryName; // Nombre de la categoría
    // Constructores, getters y setters
}