package com.api.service.product;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.dto.product.CategoryDTO;
import com.api.model.product.Category;
import com.api.repository.product.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Retrieves all categories from the repository and maps each category to a CategoryDTO object.
     *
     * @return a list of CategoryDTO objects representing all categories
     */
    public List<CategoryDTO> findAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertCategoryToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a CategoryDTO object by its ID from the category repository.
     *
     * @param  id  the ID of the category to retrieve
     * @return     the CategoryDTO object corresponding to the given ID, or null if not found
     */
    public CategoryDTO findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::convertCategoryToDTO)
                .orElse(null);
    }

    /**
     * Saves a CategoryDTO object to the category repository.
     *
     * @param  categoryDTO  the CategoryDTO object to save
     * @return              the saved CategoryDTO object
     */
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = convertCategoryToEntity(categoryDTO);
        return convertCategoryToDTO(categoryRepository.save(category));
    }


    /**
     * Updates a category in the repository based on the provided category ID and CategoryDTO.
     *
     * @param  id          the ID of the category to update
     * @param  categoryDTO  the updated category details
     * @return              the updated category as a CategoryDTO, or throws an exception if the category is not found
     */
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    
        boolean updated = false;
    
        if (categoryDTO.getName() != null && !categoryDTO.getName().equals(category.getName())) {
            if (categoryDTO.getName().trim().isEmpty() || categoryDTO.getName().length() < 4 || categoryDTO.getName().length() > 50) {
                throw new IllegalArgumentException("Product name must be between 4 and 50 characters");
            }
            category.setName(categoryDTO.getName());
            updated = true;
        }
    
        if (categoryDTO.getDescription() != null && !categoryDTO.getDescription().equals(category.getDescription())) {
            if (categoryDTO.getDescription().trim().isEmpty() || categoryDTO.getDescription().length() < 10) {
                throw new IllegalArgumentException("Category description must be at least 10 characters");
            }
            category.setDescription(categoryDTO.getDescription());
            updated = true;
        }
    
        if (updated) {
            return convertCategoryToDTO(categoryRepository.save(category));
        } else {
            return convertCategoryToDTO(category);
        }
    }

    /**
     * Deletes a category from the repository based on the provided category ID.
     *
     * @param  id  the ID of the category to delete
     */
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    /**
     * Checks if a category exists in the repository based on the provided ID.
     *
     * @param  id  the ID of the category to check
     * @return     true if the category exists, false otherwise
     */
    public boolean existsCategoryById(Long id) {
        return categoryRepository.existsById(id);
    }
    /**
     * Checks if a category exists in the repository based on the provided ID.
     *
     * @param  id  the ID of the category to check
     * @return     true if the category exists, false otherwise
     */
    public boolean existsCategoryByName(String name) {
        return categoryRepository.existsCategoryByName(name);
    }

    public boolean existsCategoryByNameExcludingId(String name, Long id) {
        return categoryRepository.findByNameAndIdCategoryNot(name, id).isPresent();
    }
    
    /**
     * Converts a Category object to a CategoryDTO object.
     *
     * @param  category  the Category object to convert
     * @return          the converted CategoryDTO object
     */
    private CategoryDTO convertCategoryToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setIdCategory(category.getIdCategory());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }

    /**
     * Converts a CategoryDTO object to a Category object.
     *
     * @param  categoryDTO   the CategoryDTO object to convert
     * @return               the converted Category object
     */
    private Category convertCategoryToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }
}
