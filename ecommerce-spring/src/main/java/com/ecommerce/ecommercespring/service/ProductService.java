package com.ecommerce.ecommercespring.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.ecommerce.ecommercespring.dto.ProductDTO;
import com.ecommerce.ecommercespring.dto.ProductRegistrationDTO;
import com.ecommerce.ecommercespring.entity.Product;
import com.ecommerce.ecommercespring.entity.SearchHistory;
import com.ecommerce.ecommercespring.response.ApiResponse;


public interface ProductService {
	
	public void saveProduct(ProductRegistrationDTO product, MultipartFile file) throws Exception;
	
	public ApiResponse updateProduct(ProductDTO product, MultipartFile file) throws Exception;
	
	public ApiResponse updateProductDate(ProductDTO product) throws Exception;
	
	public void deleteProduct(Long id) throws Exception;
	
	public Product findProductById(Long id);
	
	public ProductDTO getProductById(Long id) throws Exception;
	
	public List<ProductDTO> searchProductsByName(String name);
	
	public SearchHistory getMostSearchedProduct();
	
	public List<ProductDTO> searchProductsByPriceRange(Double priceStart, Double priceEnd);
	
	public List<ProductDTO> getAllProduct() ;
	
	public List<ProductDTO> filterProductHighlights() ;
	
	public List<ProductDTO> findProductsByCategoria(String categoryName);
	
	public int countAllProducts();
	
	
	
}
