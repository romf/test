package com.test.springer.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Category {

	@Id
	private String code;
	private String title;
	private String superCategory;

	public Category() {
	}

	public Category(String code, String title) {
		this.code = code;
		this.title = title;
		this.superCategory = null;
	}

	public Category(String code, String title, String superCategoryCode) {
		this.code = code;
		this.title = title;
		this.superCategory = superCategoryCode;
	}

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getSuperCategoryCode() {
		return superCategory;
	}

	@Override
	public String toString() {
		return "Category [code = " + code + ", title =" + title + ", super category code = " + superCategory + "]";
	}

}
