package com.fedrikp.feature.category;

import java.util.List;

import com.fedrikp.dto.category.CategoryRequestDTO;
import com.fedrikp.dto.category.CategoryResponseDTO;

public interface CategoryService {
	List<CategoryResponseDTO> findAll();
    CategoryResponseDTO findById(Long id);
    CategoryResponseDTO save(CategoryRequestDTO request);
    CategoryResponseDTO update(Long id, CategoryRequestDTO request);
    void deleteById(Long id);
}
