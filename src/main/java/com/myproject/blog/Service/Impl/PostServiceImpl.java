package com.myproject.blog.Service.Impl;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.myproject.blog.Exception.ResourceNotFoundException;
import com.myproject.blog.Repository.CategoryRepo;
import com.myproject.blog.Repository.PostRepo;
import com.myproject.blog.Repository.UserRepo;
import com.myproject.blog.Service.PostService;
import com.myproject.blog.entity.Category;
import com.myproject.blog.entity.Post;
import com.myproject.blog.entity.User;
import com.myproject.blog.payloads.PostDto;
import com.myproject.blog.payloads.PostResponse;

@Service
public class PostServiceImpl implements PostService {

	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","user  id",userId));
		Category category=this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost=this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post= this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost=this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
		 this.postRepo.delete(post);
		
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
		PageRequest p= PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost= this.postRepo.findAll(p);
		
		List<Post> allPosts= pagePost.getContent();
		
		List<PostDto>postDtos=  allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		List<Post> posts=this.postRepo.findByCategory(cat);
		List<PostDto> postDtos= posts.stream().map((post)->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user=this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","user id",userId));
		List<Post> posts= this.postRepo.findByUser(user);
		List<PostDto> postDtos= posts.stream().map((post)->this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		 List<Post>posts= this.postRepo.findByTitleContaining(keyword);
		 List<PostDto> postDto= posts.stream().map((post)->this.modelMapper.map(post, PostDto.class))
					.collect(Collectors.toList());
		return postDto;
	}


}
