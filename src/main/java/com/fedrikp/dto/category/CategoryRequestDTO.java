package com.fedrikp.dto.category;

public class CategoryRequestDTO {
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
