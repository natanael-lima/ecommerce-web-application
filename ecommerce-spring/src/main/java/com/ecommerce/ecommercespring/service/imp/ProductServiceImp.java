package com.ecommerce.ecommercespring.service.imp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ecommerce.ecommercespring.dto.ProductDTO;
import com.ecommerce.ecommercespring.dto.ProductRegistrationDTO;
import com.ecommerce.ecommercespring.entity.Category;
import com.ecommerce.ecommercespring.entity.Product;
import com.ecommerce.ecommercespring.exception.CategoryNotFoundException;
import com.ecommerce.ecommercespring.repository.CategoryRepository;
import com.ecommerce.ecommercespring.repository.ProductRepository;
import com.ecommerce.ecommercespring.response.ApiResponse;
import com.ecommerce.ecommercespring.service.ProductService;

@Service
public class ProductServiceImp implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public void saveProduct(ProductRegistrationDTO request, MultipartFile file) throws Exception {
		
		 Category categoria = categoryRepository.findByName(request.getCategoryName())
		            .orElseThrow(() -> new Exception("La categoría con nombre " + request.getCategoryName() + " no existe"));
		 
		 try {
			 
			 	String fileName = UUID.randomUUID().toString();
			 	byte[] bytes = file.getBytes();
			 	String fileOriginalName= file.getOriginalFilename();
			 	
			 	long fileSize= file.getSize();
			 	
			 	long maxFileSize = 5 * 1024 * 1024;
			 	
			 	if(fileSize>maxFileSize ) {
			 		throw new Exception("File excede tamaño para subir ");	
			 	}
			 	if(!fileOriginalName.endsWith(".jpg") && !fileOriginalName.endsWith(".jpeg") &&!fileOriginalName.endsWith(".png") ) {
			 		throw new Exception("Solo se permite JPG,JPEG & PNG");
			 	}
			 	
			 	String fileExtension = file.getOriginalFilename().substring(fileOriginalName.lastIndexOf("."));
			 	
			 	String newFileName = fileName+fileExtension;
			 
			 	File folder = new File("src/main/resources/uploads");
			 	
			 	// Crear la carpeta de almacenamiento si no existe
			 	if(!folder.exists()) {
			 		folder.mkdirs();
			 	}
			 	
			 	 // Guardar la imagen en la carpeta de almacenamiento
			 	Path path = Paths.get("src/main/resources/uploads/"+newFileName);
			 	
			 	Files.write(path,bytes);
			 	
			 	// Construir la ruta de la imagen
		        String imageUrl = "/uploads/" + newFileName;

		        Product product = new Product();
		        product.setName(request.getName());
		        product.setDescription(request.getDescription());
		        product.setPrice(request.getPrice());
		        product.setStock(request.getStock());
		        product.setImage(imageUrl); // Guardar la ruta de la imagen en el objeto Product
		        product.setHighlight(request.getHighlight());
		        product.setCategoria(categoria);

		        productRepository.save(product);
	           
	        } catch (Exception e) {
	            throw new Exception(e.getMessage());
	        }
		 
	}

	@Override
	public Product findProductById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id).orElseThrow();
	}

	@Override
	public void deleteProduct(Long id) throws Exception {
		// TODO Auto-generated method stub
		Product producto = productRepository.findById(id)
	            .orElseThrow(() -> new Exception("Producto no encontrado"));
		
		// Guarda la ruta de la imagen antigua
	    String oldImageUrl = producto.getImage();

		productRepository.deleteById(id);
		
		deleteOldImage(oldImageUrl);
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
		List<Product>  allProduct = productRepository.findAll();		 
		List<ProductDTO> allProductDTO = new ArrayList<>();
				
		for (Product prod : allProduct) {
			allProductDTO.add(convertToDTO(prod));
		}
			
			return allProductDTO;
	}
	
	private ProductDTO convertToDTO(Product prod) {

	    return ProductDTO.builder()
	            .id(prod.getId())
	            .name(prod.getName())
	            .description(prod.getDescription())
	            .price(prod.getPrice())
	            .stock(prod.getStock())
	            .image(prod.getImage())
	            .highlight(prod.getHighlight())
	            .timestamp(prod.getTimestamp())
	            .categoria(prod.getCategoria().getName())
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
	            .highlight(prod.getHighlight())
	            .timestamp(prod.getTimestamp())
	            .categoria(prod.getCategoria().getName())
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
		//producto.setCategoria(prod.getCategoria());
	    
	    // Aquí debes establecer las relaciones, como el sender y el chat
	    //Category cat = userProfileService.findUserById(messageDTO.getSenderId());
	    //if(cat == null) {
	    //    throw new RuntimeException("Sender with ID " + messageDTO.getSenderId() + " not found");
	    //}
	    //producto.setCategoria(cat);


	    return producto;
	}

	@Override
	public List<ProductDTO> searchProductsByName(String name) {
		// TODO Auto-generated method stub
				List<Product>  allProduct = productRepository.findByNameContaining(name);		 
				List<ProductDTO> allProductDTO = new ArrayList<>();
						
				for (Product prod : allProduct) {
					allProductDTO.add(convertToDTO(prod));
				}
					
					return allProductDTO;
	}

	@Override
	public ApiResponse updateProduct(ProductDTO dto, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		Product producto = productRepository.findById(dto.getId())
	            .orElseThrow(() -> new Exception("Producto no encontrado"));
		 
		// Guarda la ruta de la imagen antigua
	    String oldImageUrl = producto.getImage();
		
	    // Actualización de campos del producto
	    producto.setName(dto.getName());
	    producto.setDescription(dto.getDescription());
	    producto.setPrice(dto.getPrice());
	    producto.setStock(dto.getStock());
	    producto.setHighlight(dto.getHighlight());
	    producto.setTimestamp(LocalDateTime.now());
	    
	    // Actualización de la categoría si se proporciona
	    if (dto.getCategoria() != null && dto.getCategoria() != null) {
	        Category categoria = categoryRepository.findByName(dto.getCategoria())
	                .orElseThrow(() -> new Exception("La categoría con nombre " + dto.getCategoria() + " no existe"));
	        producto.setCategoria(categoria);
	    }
	    
	    // Guardar la imagen nueva si se proporciona
	    if (file != null && !file.isEmpty()) {
	        String imageUrl = saveImage(file);
	        producto.setImage(imageUrl);
	        // Eliminar la imagen antigua
	        deleteOldImage(oldImageUrl);
	    }
	    
	    // Llamar al método de actualización del repositorio
	    productRepository.updateProduct(
	            producto.getId(),
	            producto.getName(),
	            producto.getDescription(),
	            producto.getPrice(),
	            producto.getStock(),
	            producto.getImage(),
	            producto.getHighlight(),
	            producto.getTimestamp(),
	            producto.getCategoria()
	    );
	    
	    return new ApiResponse("El Producto se actualizó satisfactoriamente");
	}
	
	@Override
	public ApiResponse updateProductDate(ProductDTO dto) throws Exception {
		// TODO Auto-generated method stub
		Product producto = productRepository.findById(dto.getId())
	            .orElseThrow(() -> new Exception("Producto no encontrado"));
		 
		
	    // Actualización de campos del producto
	    producto.setName(dto.getName());
	    producto.setDescription(dto.getDescription());
	    producto.setPrice(dto.getPrice());
	    producto.setStock(dto.getStock());
	    producto.setHighlight(dto.getHighlight());
	    producto.setTimestamp(LocalDateTime.now());
	    
	    // Actualización de la categoría si se proporciona
	    if (dto.getCategoria() != null && dto.getCategoria() != null) {
	        Category categoria = categoryRepository.findByName(dto.getCategoria())
	                .orElseThrow(() -> new Exception("La categoría con nombre " + dto.getCategoria() + " no existe"));
	        producto.setCategoria(categoria);
	    }
	    	    
	    // Llamar al método de actualización del repositorio
	    productRepository.updateProductJSON(
	            producto.getId(),
	            producto.getName(),
	            producto.getDescription(),
	            producto.getPrice(),
	            producto.getStock(),
	            producto.getHighlight(),
	            producto.getTimestamp(),
	            producto.getCategoria()
	    );
	    
	    return new ApiResponse("El Producto se actualizó satisfactoriamente");
	}
	
	

	// Método para eliminar la imagen antigua del sistema de archivos
	private void deleteOldImage(String imageUrl) {
	    if (imageUrl != null && !imageUrl.isEmpty()) {
	        try {
	            Path path = Paths.get("src/main/resources" + imageUrl);
	            Files.deleteIfExists(path);
	        } catch (IOException e) {
	            // Manejar el error de eliminación, por ejemplo, loguear el error
	            System.err.println("Error al eliminar la imagen antigua: " + e.getMessage());
	        }
	    }
	}
	
	private String saveImage(MultipartFile file) throws Exception {
        String fileName = UUID.randomUUID().toString();
        byte[] bytes = file.getBytes();
        String fileOriginalName = file.getOriginalFilename();

        long fileSize = file.getSize();
        long maxFileSize = 5 * 1024 * 1024;

        if (fileSize > maxFileSize) {
            throw new Exception("El archivo excede el tamaño permitido para subir");
        }

        if (!fileOriginalName.endsWith(".jpg") && !fileOriginalName.endsWith(".jpeg") && !fileOriginalName.endsWith(".png")) {
            throw new Exception("Solo se permiten archivos JPG, JPEG y PNG");
        }

        String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
        String newFileName = fileName + fileExtension;

        File folder = new File("src/main/resources/uploads");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        Path path = Paths.get("src/main/resources/uploads/" + newFileName);
        Files.write(path, bytes);

        return "/uploads/" + newFileName;
    }

	@Override
	public List<ProductDTO> searchProductsByPriceRange(Double priceStart, Double priceEnd) {
		// TODO Auto-generated method stub
		List<Product>  allProduct = productRepository.findByPriceBetween(priceStart, priceEnd);		 
		List<ProductDTO> allProductDTO = new ArrayList<>();
				
		for (Product prod : allProduct) {
			allProductDTO.add(convertToDTO(prod));
		}
			
			return allProductDTO;
	}

	@Override
	public List<ProductDTO> filterProductHighlights() {
		List<Product>  allProduct = productRepository.findByHighlight(true);		 
		List<ProductDTO> allProductDTO = new ArrayList<>();
				
		for (Product prod : allProduct) {
			allProductDTO.add(convertToDTO(prod));
		}
			
			return allProductDTO;
	}
	
	 public List<ProductDTO> findProductsByCategoria(String categoryName) {
		 
		 	// Primero, obtén la categoría por su nombre
		    Optional<Category> category = categoryRepository.findByName(categoryName);
		    
		    
		    // Si la categoría no existe, puedes manejarlo según tu lógica de negocio
		    if (category == null) {
		        // Por ejemplo, lanzar una excepción o devolver un mensaje de error
		        throw new CategoryNotFoundException("Category not found: " + categoryName);
		    }
		 
		 	List<Product>  allProduct = productRepository.findByCategoriaName(categoryName);		 
			List<ProductDTO> allProductDTO = new ArrayList<>();
					
			for (Product prod : allProduct) {
				allProductDTO.add(convertToDTO(prod));
			}
				
				return allProductDTO;
	    }
}
