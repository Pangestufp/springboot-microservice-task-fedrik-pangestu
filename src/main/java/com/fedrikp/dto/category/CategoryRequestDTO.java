package com.fedrikp.dto.category;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDTO {
	
	@NotBlank(message = "Category Name is required")
	private String categoryName;
	
	public CategoryRequestDTO() {}
	
	public CategoryRequestDTO(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
}
