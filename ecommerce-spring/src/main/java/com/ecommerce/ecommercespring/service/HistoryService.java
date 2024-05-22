package com.ecommerce.ecommercespring.service;

import java.util.List;

import com.ecommerce.ecommercespring.dto.CategoryDTO;
import com.ecommerce.ecommercespring.dto.HistoryDTO;
import com.ecommerce.ecommercespring.entity.History;
import com.ecommerce.ecommercespring.enums.ActionType;
import com.ecommerce.ecommercespring.enums.TableType;


public interface HistoryService {
	
	public History createHistory(TableType tableType, ActionType actionType);
	
	public HistoryDTO getHistoryById(Long id) throws Exception;
	
	public List<HistoryDTO> getAllHistory() throws Exception;
}
