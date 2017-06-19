package com.test.springer.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {

	Category findCategoryByCode(String code);
	List<Category> findCategoryBySuperCategory(String superCategoryCode);

}
