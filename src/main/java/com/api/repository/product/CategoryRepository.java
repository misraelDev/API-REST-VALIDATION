package com.api.repository.product;

import com.api.model.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * Checks if a category exists in the repository based on the provided name.
     * 
     * @param name the name of the category to check
     * @return true if the category exists, false otherwise
     */
    boolean existsCategoryByName(String name);
    /**
     * Finds a category in the repository based on the provided name and ID.
     * The category found must not have the same ID as the one provided.
     * 
     * @param name the name of the category to find
     * @param idCategory the ID of the category to exclude from the search
     * @return the category if found, empty otherwise
     */
    Optional<Category> findByNameAndIdCategoryNot(String name, Long idCategory);

}