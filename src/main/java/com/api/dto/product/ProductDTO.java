package com.api.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDTO {

	private Long idProduct;
	
	@NotBlank(message = "Product name is required")
    @Size(min = 4, max = 50, message = "Product name must be between 4 and 50 characters")
	private String name;
	
	@NotBlank(message = "Product description is required")
	@Size(min =10, message = "Product description must be at least 10 characters")
    private String description;
	
	@NotNull(message = "Product quantity is required")
    @Min(value = 0, message = "Total quantity cannot be negative")
    private Integer totalQuantity;
	
	@NotNull(message = "Price cannot be null")
    @Min(value = 1, message = "Price must be a positive value")
	private Double price;
    
	@NotNull(message = "Category cannot be null")
    private Long idCategory;
    
	public Long getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public Long getIdCategory() {
		return idCategory;
	}
	public void setIdCategory(Long idCategory) {
		this.idCategory = idCategory;
	}
}

