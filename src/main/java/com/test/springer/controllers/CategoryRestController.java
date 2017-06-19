package com.test.springer.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.springer.jpa.Category;
import com.test.springer.jpa.CategoryRepository;

@RestController
public class CategoryRestController {
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	Collection<Category> getCategories() {
		return categoryRepository.findAll();
	}

	@RequestMapping(value = "/categories/{categoryCode}", method = RequestMethod.GET)
	Category getCategory(@PathVariable String categoryCode) {
		return categoryRepository.findCategoryByCode(categoryCode);
	}

	@RequestMapping(value = "/categories", method = RequestMethod.POST)
	public Category createCategory(@RequestParam(value = "code") String code,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "superCategoryCode", defaultValue = "null") String superCategoryCode) {
		Category cat = null;
		if (!"null".equals(cat)) {
			cat = categoryRepository.findCategoryByCode(superCategoryCode);
		}
		if (!"null".equals(superCategoryCode) && cat == null) {
			System.out.println("Category with code " + code + "not found.");
			return null;
		}
		Category newCat = new Category(code, title, superCategoryCode);
		categoryRepository.save(newCat);
		return newCat;
	}

	@Autowired
	CategoryRepository categoryRepository;
}
