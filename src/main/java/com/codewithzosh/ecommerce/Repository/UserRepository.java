package com.codewithzosh.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithzosh.ecommerce.Model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByEmail(String email);
}
