package com.api.service.product;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.model.product.Category;
import com.api.dto.product.ProductDTO;
import com.api.model.product.Product;
import com.api.repository.product.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves all products from the product repository and maps each product to a ProductDTO object.
     *
     * @return  a list of ProductDTO objects representing all products
    */
    public List<ProductDTO> findAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertProductToDTO)
                .collect(Collectors.toList());
    }

    
    /**
     * Retrieves a ProductDTO object by its ID from the product repository.
     *
     * @param  id  the ID of the product to retrieve
     * @return     the ProductDTO object corresponding to the given ID, or null if not found
     */
    public ProductDTO findProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertProductToDTO)
                .orElse(null);
    }

    /**
     * Saves a product to the repository and returns the saved product as a ProductDTO.
     *
     * @param  productDTO  the product to save
     * @return             the saved product as a ProductDTO
     */
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = convertProductToEntity(productDTO);
        return convertProductToDTO(productRepository.save(product));
    }

    /**
     * Updates an existing product in the repository based on the provided product ID and ProductDTO.
     *
     * @param  id          the ID of the product to update
     * @param  productDTO  the updated product details
     * @return             the updated product as a ProductDTO, or throws an exception if the product is not found
     */
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    
        boolean updated = false;
    
        if (productDTO.getName() != null && !productDTO.getName().equals(product.getName())) {
            product.setName(productDTO.getName());
            updated = true;
        }
        if (productDTO.getDescription() != null && !productDTO.getDescription().equals(product.getDescription())) {
            product.setDescription(productDTO.getDescription());
            updated = true;
        }
        if (productDTO.getTotalQuantity() != null && !productDTO.getTotalQuantity().equals(product.getTotalQuantity())) {
            product.setTotalQuantity(productDTO.getTotalQuantity());
            updated = true;
        }
        if (productDTO.getPrice() != null && !productDTO.getPrice().equals(product.getPrice())) {
            product.setPrice(productDTO.getPrice());
            updated = true;
        }
        if (productDTO.getIdCategory() != null && (product.getCategory() == null
                || !productDTO.getIdCategory().equals(product.getCategory().getIdCategory()))) {
            Category category = new Category();
            category.setIdCategory(productDTO.getIdCategory());
            product.setCategory(category);
            updated = true;
        }
    
        if (updated) {
            return convertProductToDTO(productRepository.save(product));
        } else {
            return convertProductToDTO(product);
        }
    }

    /**
     * Deletes a product from the repository based on the provided product ID.
     *
     * @param  id  the ID of the product to delete
     */
    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    /**
     * Checks if a product exists in the repository based on the provided ID.
     *
     * @param  id  the ID of the product to check
     * @return     true if the product exists, false otherwise
     */
    public boolean existsProductById(Long id) {
        return productRepository.existsById(id);
    }

        /**
     * Checks if a product exists in the repository based on the provided ID.
     *
     * @param  id  the ID of the product to check
     * @return     true if the product exists, false otherwise
     */
    public boolean existsProductByName(String name) {
        return productRepository.existsProductByName(name);
    }

    /**
     * Checks if a product exists in the repository based on the provided name, excluding the specified ID.
     *
     * @param  name  the name of the product to check
     * @param  id    the ID to exclude from the search
     * @return      true if a product with the given name exists and its ID is not the excluded ID, false otherwise
     */
    public boolean existsProductByNameExcludingId(String name, Long id) {
        return productRepository.findByNameAndIdProductNot(name, id).isPresent();
    }

    /**
     * Converts a Product entity to a ProductDTO.
     *
     * @param  product  the Product entity to convert
     * @return          the converted ProductDTO
     */
    private ProductDTO convertProductToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setIdProduct(product.getIdProduct());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setTotalQuantity(product.getTotalQuantity());
        productDTO.setPrice(product.getPrice());
        productDTO.setIdCategory(product.getCategory().getIdCategory());
        return productDTO;
    }

    /**
     * Converts a ProductDTO object to a Product entity.
     *
     * @param  productDTO  the ProductDTO object to convert
     * @return          the converted Product entity
     */
    private Product convertProductToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setIdProduct(productDTO.getIdProduct());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setTotalQuantity(productDTO.getTotalQuantity());
        product.setPrice(productDTO.getPrice());
        Category category = new Category();
        category.setIdCategory(productDTO.getIdCategory());
        product.setCategory(category);
        return product;
    }
}
