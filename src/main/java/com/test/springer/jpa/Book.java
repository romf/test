package com.test.springer.jpa;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book {

	@Id
	private String title;
	private String categoryCodes;

	public Book() {
	}

	public Book(String title, String categoryCodes) {
		this.title = title;
		this.categoryCodes = categoryCodes;
	}

	public String getTitle() {
		return title;
	}

	public Collection<String> getCategoryCodes() {
		return Arrays.asList(categoryCodes.split(","));
	}

	@Override
	public String toString() {
		return "Book [title =" + title + ", category codes = " + categoryCodes + "]";
	}

}
