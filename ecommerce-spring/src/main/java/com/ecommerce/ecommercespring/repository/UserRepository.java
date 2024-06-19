package com.ecommerce.ecommercespring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.psm.model.Role;
import com.ecommerce.ecommercespring.entity.Product;
import com.ecommerce.ecommercespring.entity.User;
import com.ecommerce.ecommercespring.enums.RoleType;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	Optional<User> findByUsername(String username);
	
	public boolean existsByUsername(String username);

	@Modifying()
	@Query("update User set lastname=:lastname, name=:name where id = :id")
    void updateUser(@Param(value = "id") Long id,   @Param(value = "lastname") String lastname, @Param(value = "name") String name);

	public User findByRole(RoleType roleName);
	
	@Query("SELECT COUNT(u) > 0 FROM User u WHERE u.role = 'ADMIN'")
	public boolean existsAdminRole();
	
	public List<User> findAllByUsernameNot(String username);
}
