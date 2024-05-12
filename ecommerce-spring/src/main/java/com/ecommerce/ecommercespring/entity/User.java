package com.ecommerce.ecommercespring.entity;

import java.time.LocalDateTime;

import com.ecommerce.ecommercespring.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name="users", uniqueConstraints= @UniqueConstraint(columnNames = "username"))
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "username", nullable=false)
    private String username;
	@Column(name = "password")
    private String password;
	@Column(name = "name")
	private String name;
	@Column(name = "lastname")
    private String lastname;
	@Column(name="timestamp")
 	@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp;
	@Enumerated(EnumType.STRING)
	private RoleType role; //ADMIN-USER
	
	public User() {
        this.timestamp = LocalDateTime.now();
    }
	
	 
	@Override
	public String toString() {
	return "User [id=" + id + ", username=" + username + "]";
	}
}
