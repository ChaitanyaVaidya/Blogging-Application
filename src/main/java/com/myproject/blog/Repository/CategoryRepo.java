package com.myproject.blog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.blog.entity.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer>{

}
