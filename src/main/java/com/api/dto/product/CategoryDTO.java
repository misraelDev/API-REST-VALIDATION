package com.api.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDTO {

	private Long idCategory;
	
	@NotBlank(message = "The category name is required")
	@Size(min = 4, max = 50, message = "Product name must be between 4 and 50 characters")
    private String name;

	@NotBlank(message = "The category description is required")
	@Size(min = 10, message = "Category description must be at least 10 characters")
	private String description;
	
    public Long getIdCategory() {
		return idCategory;
	}
	public void setIdCategory(Long idCategory) {
		this.idCategory = idCategory;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}


