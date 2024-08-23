package com.api.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.service.product.CategoryService;
import com.api.service.product.ProductService;

import jakarta.validation.Valid;
import com.api.dto.product.ProductDTO;
import com.api.model.response.Response;

import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        if (!productService.existsProductById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Product does not exist"));
        }
        try {
            ProductDTO productDTO = productService.findProductById(id);
            return ResponseEntity.ok(productDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error retrieving product: " + e.getMessage()));
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String firstErrorMessage = bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .findFirst()
                .orElse("Unknown validation error");
            return new ResponseEntity<>(new Response(firstErrorMessage), HttpStatus.BAD_REQUEST);
        }
    
        if (!categoryService.existsCategoryById(productDTO.getIdCategory())) {
            return new ResponseEntity<>(new Response("The specified product category does not exist. Please verify the entered data."), HttpStatus.BAD_REQUEST);
        }
        if (productService.existsProductByName(productDTO.getName())) {
            return new ResponseEntity<>(new Response("Product name already exists. Please choose another name."), HttpStatus.CONFLICT);
        }
        try {
            productService.saveProduct(productDTO);
            return new ResponseEntity<>(new Response("Product created successfully"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response("Error registering product: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    
    
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String firstErrorMessage = bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .findFirst()
                .orElse("Unknown validation error");
            return new ResponseEntity<>(new Response(firstErrorMessage), HttpStatus.BAD_REQUEST);
        }
        if (!productService.existsProductById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Product does not exist"));
        }
            
        if (!categoryService.existsCategoryById(productDTO.getIdCategory())) {
            return new ResponseEntity<>(new Response("The specified product category does not exist. Please verify the entered data."), HttpStatus.BAD_REQUEST);
        }
        
        ProductDTO existingProduct = productService.findProductById(id);
        if (productDTO.getName() != null && !productDTO.getName().equals(existingProduct.getName()) 
                && productService.existsProductByNameExcludingId(productDTO.getName(), id)) {
            return new ResponseEntity<>(new Response("Product name already exists. Please choose another name."), HttpStatus.CONFLICT);
        }
        
        try {
            productService.updateProduct(id, productDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new Response("Product updated successfully"));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error updating product: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if (!productService.existsProductById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Product does not exist"));
        }
        try {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Response("Product deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error deleting product: " + e.getMessage()));
        }
    }
    
}
