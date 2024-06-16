package com.ecommerce.ecommercespring.exception;

public class CategoryNotFoundException extends RuntimeException  {
	
	public CategoryNotFoundException(String categoryName) {
        super("Category not found: " + categoryName);
    }

    public CategoryNotFoundException(String categoryName, Throwable cause) {
        super("Category not found: " + categoryName, cause);
    }
}
