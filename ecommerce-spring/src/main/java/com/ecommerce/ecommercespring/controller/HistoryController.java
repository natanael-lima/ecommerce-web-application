package com.ecommerce.ecommercespring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.ecommercespring.dto.HistoryDTO;
import com.ecommerce.ecommercespring.service.HistoryService;

@CrossOrigin(origins = "http://localhost:4200") // Reemplaza esto con el dominio de tu frontend
@RestController
@RequestMapping("/api/history")
public class HistoryController {

	@Autowired
    private HistoryService historyService;
	
	// API para obtener una categoria by ID.
    @GetMapping("/get-history/{id}")
    public ResponseEntity<HistoryDTO> findHistoryById(@PathVariable Long id) throws Exception {
        try {
        	HistoryDTO historyDTO = historyService.getHistoryById(id);
            return ResponseEntity.ok(historyDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build(); // Retorna un código 204 si el chat no está encontrado
        }
    }
	// API para obtener una lista de todos las categorias.
    @GetMapping("/find-all")
    public ResponseEntity<List<HistoryDTO>> findAll() throws Exception {
        try {
        	List<HistoryDTO> historyAll = historyService.getAllHistory();
            return ResponseEntity.ok(historyAll);
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build(); // Retorna un código 204 si el chat no está encontrado
        }
    }
}
