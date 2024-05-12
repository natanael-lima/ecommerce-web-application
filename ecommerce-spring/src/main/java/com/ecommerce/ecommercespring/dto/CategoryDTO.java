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
public class CategoryDTO {

     Long id;
     String name;
     String description;
     LocalDateTime timestamp;
 	
}
