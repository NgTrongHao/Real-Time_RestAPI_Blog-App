package com.nguyentronghao.realtime_restapi_blogapp.service.implement;

import com.nguyentronghao.realtime_restapi_blogapp.model.entity.Category;
import com.nguyentronghao.realtime_restapi_blogapp.model.dto.category.CategoryDto;
import com.nguyentronghao.realtime_restapi_blogapp.model.error.ResourceNotFoundException;
import com.nguyentronghao.realtime_restapi_blogapp.repository.CategoryRepository;
import com.nguyentronghao.realtime_restapi_blogapp.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    
    
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }
    
    /**
     * Adds a new Category to the system.
     *
     * @param categoryDto The CategoryDto object containing information about the new Category to be added.
     * @return The CategoryDto object that has been added to the system after being saved into the database.
     */
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        return convertCategoryToCategoryDto(categoryRepository.save(convertCategoryDtoToCategory(categoryDto)));
    }
    
    /**
     * Retrieves a CategoryDto by its unique identifier.
     *
     * @param id The unique identifier of the Category to retrieve.
     * @return The CategoryDto object corresponding to the provided ID if found; otherwise, throws a ResourceNotFoundException.
     */
    @Override
    public CategoryDto getCategory(Long id) {
        return convertCategoryToCategoryDto(getCategoryFromRepositoryById(id));
    }
    
    /**
     * Retrieves all categories from the repository.
     *
     * @return A list of CategoryDto objects representing all categories in the system.
     */
    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(
                this::convertCategoryToCategoryDto
        ).toList();
    }
    
    /**
     * Updates a category with the provided ID using the information from the provided CategoryDto.
     *
     * @param categoryId The ID of the category to update.
     * @param categoryDto The CategoryDto object containing the updated information.
     * @return The updated CategoryDto object.
     * @throws ResourceNotFoundException if no category is found with the provided ID.
     */
    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = getCategoryFromRepositoryById(categoryId);
        return convertCategoryToCategoryDto(updateCategoryFields(category, categoryDto));
    }
    
    /**
     * Deletes a category with the provided ID.
     *
     * @param categoryId The ID of the category to delete.
     * @throws ResourceNotFoundException if no category is found with the provided ID.
     */
    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.delete(getCategoryFromRepositoryById(categoryId));
    }
    
    /**
     * Updates the fields of a Category object with the information from the provided CategoryDto and saves it to the repository.
     *
     * @param category The Category object to update.
     * @param categoryDto The CategoryDto object containing the updated information.
     * @return The updated Category object.
     */
    private Category updateCategoryFields(Category category, CategoryDto categoryDto) {
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return categoryRepository.save(category);
    }
    
    /**
     * Retrieves a Category by its unique identifier from the repository.
     *
     * @param id The unique identifier of the Category to retrieve.
     * @return The Category object corresponding to the provided ID if found; otherwise, throws a ResourceNotFoundException.
     * @throws ResourceNotFoundException if no Category is found with the provided ID.
     */
    private Category getCategoryFromRepositoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id.toString()));
    }
    
    /**
     * Converts a CategoryDto object to a Category object.
     *
     * @param categoryDto The CategoryDto object to be converted.
     * @return The Category object converted from the provided CategoryDto object.
     */
    private Category convertCategoryDtoToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
    
    
    /**
     * Converts a Category object to a CategoryDto object.
     *
     * @param category The Category object to be converted.
     * @return The CategoryDto object converted from the provided Category object.
     */
    private CategoryDto convertCategoryToCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }
}
