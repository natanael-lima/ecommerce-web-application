package com.ecommerce.ecommercespring.entity;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "product")
@JsonIgnoreProperties({"categoria"})
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 	
 	@Column(name="name")
    private String name;
 	
 	@Column(name="description")
    private String description;
 	
 	@Column(name="price")
    private Double price;
 	
 	@Column(name="stock")
    private int stock;
 	
 	@Column(name="imagen")
    private String image;
 	
 	@Column(name="timestamp")
 	@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp;
 	
 	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
 	@JsonBackReference
    private Category categoria;
 	
 	//@OneToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "history_id")
 	//@JsonBackReference
    //private History history;
 	
 	public Product() {
        this.timestamp = LocalDateTime.now();
    }
 	
 		@Override
 		public String toString() {
 		    return "Product [id=" + id + ", name=" + name + ", categoria=" + categoria + "]";
 		}
 	
}
