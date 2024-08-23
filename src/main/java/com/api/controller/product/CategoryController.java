package com.api.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import com.api.dto.product.CategoryDTO;
import com.api.model.response.Response;
import com.api.service.product.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        if (!categoryService.existsCategoryById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Category does not exist"));
        }
        try {
            CategoryDTO categoryDTO = categoryService.findCategoryById(id);
            return ResponseEntity.ok(categoryDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error retrieving category: " + e.getMessage()));
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String firstErrorMessage = bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .findFirst()
                .orElse("Unknown validation error");
            return new ResponseEntity<>(new Response(firstErrorMessage), HttpStatus.BAD_REQUEST);
        }
        if (categoryService.existsCategoryByName(categoryDTO.getName())) {
            return new ResponseEntity<>(new Response("Category name already exists. Please choose another name."), HttpStatus.CONFLICT);
        }
        try {
            categoryService.saveCategory(categoryDTO);
            return new ResponseEntity<>(new Response("Category created successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response("Error registering category: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        if (!categoryService.existsCategoryById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Category does not exist"));
        }
        
        CategoryDTO existingCategory = categoryService.findCategoryById(id);
        
        if (categoryDTO.getName() != null && !categoryDTO.getName().equals(existingCategory.getName()) 
                && categoryService.existsCategoryByNameExcludingId(categoryDTO.getName(), id)) {
            return new ResponseEntity<>(new Response("Category name already exists. Please choose another name."), HttpStatus.CONFLICT);
        }
    
        try {
            categoryService.updateCategory(id, categoryDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new Response("Category updated successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error updating category: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        if (!categoryService.existsCategoryById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Category does not exist"));
        }
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Response("Category deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error deleting category: " + e.getMessage()));
        }
    }    
}

