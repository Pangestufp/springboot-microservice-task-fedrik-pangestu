package com.fedrikp.feature.category.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fedrikp.dto.category.CategoryRequestDTO;
import com.fedrikp.dto.category.CategoryResponseDTO;
import com.fedrikp.entity.Category;
import com.fedrikp.exception.DuplicateResourceException;
import com.fedrikp.exception.ResourceNotFoundException;
import com.fedrikp.feature.category.CategoryRepository;
import com.fedrikp.feature.category.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	//function untuk convert entity ke dto
	private CategoryResponseDTO toResponse(Category category) {
        return new CategoryResponseDTO(
        	category.getId(),
        	category.getCategoryName()
        );
    }

	//function untuk menarik semua category
	@Override
	public List<CategoryResponseDTO> findAll() {
		List<Category> categories = categoryRepository.findAll();
		
		return categories.stream().map(category -> toResponse(category)).toList();
	}

	//function untuk mengambil category sesuai id, jika tidak ada maka return ResourceNotFoundException
	@Override
	public CategoryResponseDTO findById(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
		        		"Category not found with id: " + id
		        ));

		return toResponse(category);
	}

	//function untuk membuat category
	@Override
	public CategoryResponseDTO save(CategoryRequestDTO request) {
		//function untuk cek apakah categoryname unique, jika tidak maka return DuplicateResourceException
		if (categoryRepository.existsByCategoryNameIgnoreCase(request.getCategoryName())) {
            throw new DuplicateResourceException("Category with name: " + request.getCategoryName() + " already exists");
        }
		
        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        
        return toResponse(categoryRepository.save(category));
	}

	@Override
	public CategoryResponseDTO update(Long id, CategoryRequestDTO request) {
		//function untuk cek apakah id category valid, jika tidak maka return ResourceNotFoundException
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
		        		"Category not found with id: " + id
		        ));
		
		//function untuk cek apakah categoryname unique, jika tidak maka return DuplicateResourceException
		categoryRepository.findByCategoryName(request.getCategoryName())
		.ifPresent(existingCategory -> {
			if (!existingCategory.getId().equals(id)) {
				throw new DuplicateResourceException("Category with name: " + request.getCategoryName() + " already used by another category");
				}
		});
    
		category.setCategoryName(request.getCategoryName());
		
		return toResponse(categoryRepository.save(category));
	}

	//hapus book berdasarkan id
	@Override
	public void deleteById(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(
	            		"Category not found with id: " + id
	            ));

	    categoryRepository.delete(category);
	}

	
}
