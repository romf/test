package com.test.springer.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.springer.jpa.Book;
import com.test.springer.jpa.BookRepository;
import com.test.springer.jpa.Category;
import com.test.springer.jpa.CategoryRepository;

@RestController
public class BookRestController {
	@RequestMapping(value = "/books", method = RequestMethod.GET)
	Collection<Book> getBooks() {
		return bookRepository.findAll();
	}

	@RequestMapping(value = "/books/{bookTitle}", method = RequestMethod.GET)
	Book getBook(@PathVariable String bookTitle) {
		return bookRepository.findBookByTitle(bookTitle);
	}

	@RequestMapping(value = "/books", method = RequestMethod.POST)
	public Book createBook(@RequestParam(value = "title") String title,
			@RequestParam(value = "categoryCodes") String categoryCodes) {
		String[] listCategoryCodes = categoryCodes.split(",");
		List<String> categories = new ArrayList<String>();
		for (String code : listCategoryCodes) {
			Category cat = categoryRepository.findCategoryByCode(code);
			if (cat == null) {
				System.out.println("Category with code " + code + " not found. Ignoring this category.");
				continue;
			}
			categories.add(cat.getCode());
		}
		Book newBook = new Book(title, String.join(",", categories));
		bookRepository.save(newBook);
		return newBook;
	}

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	BookRepository bookRepository;
}
