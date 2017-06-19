package com.test.springer.jpa;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Subscriber {

	@Id
	private String email;
	private String categoryCodes;

	// @ManyToMany(targetEntity = Category.class)
	// private Collection<Category> categories;
	//
	// @OneToMany(mappedBy = "subscriber")
	// private Collection<Newsletter> newsletters;

	public Subscriber() {
	}

	public Subscriber(String email, String categoryCodes) {
		this.email = email;
		this.categoryCodes = categoryCodes;
	}

	public String getEmail() {
		return email;
	}

	public Collection<String> getCategoryCodes() {
		return Arrays.asList(categoryCodes.split(","));
	}

	@Override
	public String toString() {
		return "Subscriber [email =" + email + ", category codes = " + categoryCodes + "]";
	}

}
