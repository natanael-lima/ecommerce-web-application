package com.ecommerce.ecommercespring.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
     Long id;
     String username;
     String name;
     String lastname;
	 LocalDateTime timestamp;
	}
