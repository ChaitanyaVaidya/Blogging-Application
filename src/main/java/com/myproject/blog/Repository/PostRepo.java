package com.myproject.blog.Repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.blog.entity.Category;
import com.myproject.blog.entity.Post;
import com.myproject.blog.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
//	Page<Post> findAll(Pageable p);
	
	List<Post>findByTitleContaining(String title);

}
