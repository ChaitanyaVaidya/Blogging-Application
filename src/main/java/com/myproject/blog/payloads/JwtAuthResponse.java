package com.myproject.blog.payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {
	
	private String token;

	
	private UserDto user;

}
