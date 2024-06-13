package com.ecommerce.ecommercespring.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.ecommercespring.dto.CategoryDTO;
import com.ecommerce.ecommercespring.dto.UserDTO;
import com.ecommerce.ecommercespring.entity.Category;
import com.ecommerce.ecommercespring.entity.User;
import com.ecommerce.ecommercespring.response.ApiResponse;

public interface UserService {
	
	public User saveUser(User userRequest);
	
	public ApiResponse updateUser(UserDTO userRequest);
	
	public UserDTO getUserById(Long id) throws Exception;
	
	public List<UserDTO> getAllUser() throws Exception;
	
	public void deleteUser(Long id);
	
	public boolean existsByName(String name);
	
	public boolean existsAdminRole();
	
	public boolean existsUsername(String name);

}
