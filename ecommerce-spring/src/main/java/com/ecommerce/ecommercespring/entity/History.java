package com.ecommerce.ecommercespring.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.ecommercespring.enums.ActionType;
import com.ecommerce.ecommercespring.enums.TableType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "history")
public class History {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="action")
	@Enumerated(EnumType.STRING)
	private ActionType action;
	
	@Column(name="timestamp")
 	@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp;
	
	@Column(name="type")
	@Enumerated(EnumType.STRING)
	private TableType tableType;
	
	//@OneToOne(mappedBy = "history")
 	//@JsonIgnoreProperties
    //private Product producto;
	
	
	public History() {
        this.timestamp = LocalDateTime.now();
    }
 	
}
