package com.ecommerce.ecommercespring.service.imp;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.ecommercespring.dto.ProductDTO;
import com.ecommerce.ecommercespring.dto.ProductRegistrationDTO;
import com.ecommerce.ecommercespring.entity.Category;
import com.ecommerce.ecommercespring.entity.Product;
import com.ecommerce.ecommercespring.repository.CategoryRepository;
import com.ecommerce.ecommercespring.repository.ProductRepository;
import com.ecommerce.ecommercespring.service.ProductService;
@Service
public class ProductServiceImp implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public void saveProduct(ProductRegistrationDTO request) throws Exception {
		
		 Category categoria = categoryRepository.findByName(request.getCategoryName())
		            .orElseThrow(() -> new Exception("La categoría con nombre " + request.getCategoryName() + " no existe"));
		
		//Category categoria = categoryRepository.findByName(request.getCategoryName());
		System.out.println("name:"+request.getCategoryName());
		System.out.println("id:"+categoria.getId());
	    Product product = new Product();
	    product.setName(request.getName());
	    product.setDescription(request.getDescription());
	    product.setPrice(request.getPrice());
	    product.setStock(request.getStock());
	    product.setImage(request.getImage());
	    product.setCategoria(categoria);

	     productRepository.save(product);
	}

	@Override
	public Product findProductById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id).orElseThrow();
	}

	@Override
	public void deleteProduct(Long id) {
		// TODO Auto-generated method stub
		productRepository.deleteById(id);
	}

	@Override
	public ProductDTO getProductById(Long id) throws Exception{
		// TODO Auto-generated method stub
	    Optional<Product> product = productRepository.findById(id);
		return convertdto(product);
	}

	@Override
	public List<ProductDTO> getAllProduct() {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	private ProductDTO convertToDTO(Product prod) {

	    return ProductDTO.builder()
	            .id(prod.getId())
	            .name(prod.getName())
	            .description(prod.getDescription())
	            .price(prod.getPrice())
	            .stock(prod.getStock())
	            .image(prod.getImage())
	            .timestamp(prod.getTimestamp())
	            .categoria(prod.getCategoria())
	            .build();
	}
	
	private ProductDTO convertdto(Optional<Product> product) throws Exception {
		if (product.isPresent()) {
		Product prod = product.get();
	    return ProductDTO.builder()
	            .id(prod.getId())
	            .name(prod.getName())
	            .description(prod.getDescription())
	            .price(prod.getPrice())
	            .stock(prod.getStock())
	            .image(prod.getImage())
	            .timestamp(prod.getTimestamp())
	            .categoria(prod.getCategoria())
	            .build();
		} else {
	        // Manejo de error si el usuario no está presente
	        throw new Exception("Product not found");
	    }
	}
	
	private Product convertToEntity(ProductDTO prod) {
		Product producto = new Product();
	    
	    // Aquí puedes asignar los valores del DTO al objeto Message
		producto.setId(prod.getId());
		producto.setName(prod.getName());
		producto.setDescription(prod.getDescription());
		producto.setPrice(prod.getPrice());
		producto.setStock(prod.getStock());
		producto.setImage(prod.getImage());
		producto.setTimestamp(prod.getTimestamp());
		producto.setCategoria(prod.getCategoria());
	    
	    // Aquí debes establecer las relaciones, como el sender y el chat
	    //Category cat = userProfileService.findUserById(messageDTO.getSenderId());
	    //if(cat == null) {
	    //    throw new RuntimeException("Sender with ID " + messageDTO.getSenderId() + " not found");
	    //}
	    //producto.setCategoria(cat);


	    return producto;
	}
}
