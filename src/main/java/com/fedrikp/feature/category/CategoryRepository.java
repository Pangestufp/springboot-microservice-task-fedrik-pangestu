package com.fedrikp.feature.category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fedrikp.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	boolean existsByCategoryNameIgnoreCase(String categoryName);
	Optional<Category> findByCategoryName(String categoryName);
}
