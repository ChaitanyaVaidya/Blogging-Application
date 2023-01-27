package com.myproject.blog.Service.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.blog.Exception.ResourceNotFoundException;
import com.myproject.blog.Repository.CommentRepo;
import com.myproject.blog.Repository.PostRepo;
import com.myproject.blog.Service.CommentService;
import com.myproject.blog.entity.Comment;
import com.myproject.blog.entity.Post;
import com.myproject.blog.payloads.CommentDto;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return  this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com=this.commentRepo.findById(commentId)
				.orElseThrow(()-> new ResourceNotFoundException("Comment", "CommentId", commentId));
		this.commentRepo.delete(com);
	}

}
