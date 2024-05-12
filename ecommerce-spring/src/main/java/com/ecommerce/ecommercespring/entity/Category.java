package com.ecommerce.ecommercespring.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "category")
@JsonIgnoreProperties({"productos"})
public class Category {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 	
 	@Column(name="name")
    private String name;
 	
 	@Column(name="description")
    private String description;
 	
 	@Column(name="timestamp")
 	@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp;
 	
 	@OneToMany(mappedBy = "categoria",cascade = CascadeType.ALL)
 	@JsonIgnoreProperties
    private List<Product> productos;
 	
 	public Category() {
        this.timestamp = LocalDateTime.now();
    }
 	
 	@Override
		public String toString() {
		    return "Category [id=" + id + ", name=" + name + ", description=" + description + "]";
		}
}
