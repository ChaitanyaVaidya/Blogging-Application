package com.myproject.blog.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.blog.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	

	Optional<User> findByEmail(String email);

}
