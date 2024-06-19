package com.ecommerce.ecommercespring.dto;

import com.ecommerce.ecommercespring.entity.SearchHistory;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchHistoryDTO {
	
	    Long id;
		String query;
		int searchCount;

}
