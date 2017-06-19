package com.test.springer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.test.springer.jpa.Book;
import com.test.springer.jpa.BookRepository;
import com.test.springer.jpa.Category;
import com.test.springer.jpa.CategoryRepository;
import com.test.springer.jpa.SubscriberRepository;

@SpringBootApplication
public class SpringerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringerApplication.class, args);
	}

}

@Component
class CategoryCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... arg0) throws Exception {
		categoryRepository.save(new Category("science", "science"));
		categoryRepository.save(new Category("engineering", "engineering", "science"));
		categoryRepository.save(new Category("software", "software", "engineering"));
		categoryRepository.save(new Category("functional_programming", "functional programming", "software"));
		categoryRepository.save(new Category("object_oriented_programming", "object oriented programming", "software"));

		bookRepository.save(new Book("Programming in Scala", "functional_programming,object_oriented_programming"));

		for (Category c : this.categoryRepository.findAll())
			System.out.println(c.toString());
	}

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	SubscriberRepository subscriberRepository;
}