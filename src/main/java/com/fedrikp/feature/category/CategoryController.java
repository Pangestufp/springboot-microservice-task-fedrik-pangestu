package com.fedrikp.feature.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fedrikp.dto.category.CategoryRequestDTO;
import com.fedrikp.dto.category.CategoryResponseDTO;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryResponseDTO>> getAll() {
		return ResponseEntity.ok(categoryService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryResponseDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok(categoryService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<CategoryResponseDTO> create(@RequestBody CategoryRequestDTO request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(request));
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @RequestBody CategoryRequestDTO request) {
		return ResponseEntity.ok(categoryService.update(id, request));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		categoryService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}