package com.myproject.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotBlank
	@Size(min=4,message="Minimum 4 characters required")
	private String categoryTitle;
	
	@NotBlank
	@Size(min=7,message = "Minimum 4 characters required")
	private String categoryDescription;

}
