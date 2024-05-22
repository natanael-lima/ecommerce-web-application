package com.ecommerce.ecommercespring.service.imp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.ecommercespring.dto.HistoryDTO;
import com.ecommerce.ecommercespring.entity.History;
import com.ecommerce.ecommercespring.enums.ActionType;
import com.ecommerce.ecommercespring.enums.TableType;
import com.ecommerce.ecommercespring.repository.HistoryRepository;
import com.ecommerce.ecommercespring.service.HistoryService;

@Service
public class HistoryServiceImp implements HistoryService{
	
	@Autowired
	private HistoryRepository historyRepository;
	
	private HistoryDTO convertToDTO(History history) {

		return HistoryDTO.builder()
	            .id(history.getId())
	            .action(history.getAction().toString())
	            .timestamp(history.getTimestamp())
	            .tableType(history.getTableType().toString())
	            .build();
	}
	
	private HistoryDTO convertdto(Optional<History> history) throws Exception {
		if (history.isPresent()) {
			History his = history.get();
	    return HistoryDTO.builder()
	    		.id(his.getId())
	            .action(his.getAction().toString())
	            .timestamp(his.getTimestamp())
	            .tableType(his.getTableType().toString())
	            .build();
		} else {
	        // Manejo de error si el usuario no está presente
	        throw new Exception("History not found");
	    }
	}
	
	@Override
	public History createHistory(TableType tableType, ActionType actionType) {
		// TODO Auto-generated method stub
		
		History history = History.builder()
                .action(actionType)
                .timestamp(LocalDateTime.now())
                .tableType(tableType) // Suponiendo que tienes un constructor en Product que toma el ID
                .build();
		
		return historyRepository.save(history);
	}

	@Override
	public HistoryDTO getHistoryById(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<History> history = historyRepository.findById(id);
		return convertdto(history);
	}

	@Override
	public List<HistoryDTO> getAllHistory() throws Exception {
		// TODO Auto-generated method stub
				List<History>  allHistory = historyRepository.findAll();		 
				List<HistoryDTO> allHistoryDTO = new ArrayList<>();
				
				for (History his : allHistory) {
					allHistoryDTO.add(convertToDTO(his));
				}
			
				return allHistoryDTO;
	}

}
