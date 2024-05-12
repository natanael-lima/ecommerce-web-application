package com.ecommerce.ecommercespring.service;

import java.util.List;

import com.ecommerce.ecommercespring.dto.ProductDTO;
import com.ecommerce.ecommercespring.dto.ProductRegistrationDTO;
import com.ecommerce.ecommercespring.entity.Product;


public interface ProductService {
	
	public void saveProduct(ProductRegistrationDTO product) throws Exception;
	
	public Product findProductById(Long id);
	
	public void deleteProduct(Long id);
	
	public ProductDTO getProductById(Long id) throws Exception;
	
	//public ProductDTO updateUserProfileData(UserProfileRequest profile);
	
	public List<ProductDTO> getAllProduct() ;
}
