package com.ecommerce.ecommercespring.request;

import com.ecommerce.ecommercespring.enums.RoleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	
    private String name;
    private String lastname;
    private String username;
    private String password;
    private RoleType role;
    
}

