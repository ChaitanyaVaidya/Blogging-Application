package com.myproject.blog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.blog.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
