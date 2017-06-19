package com.test.springer.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@IdClass(NotificationId.class)
public class Notification {

	@Id
	private String book;
	@JsonIgnore
	@Id
	private String recipient;

	private String categoryPaths;

	@JsonIgnore
	@ManyToOne
	private Newsletter newsletter;

	public Notification() {
	}

	public Notification(Newsletter newsletter, String book, String categoryPaths) {
		this.newsletter = newsletter;
		this.recipient = newsletter.getRecipient();
		this.book = book;
		this.categoryPaths = categoryPaths;
	}

	public String getBook() {
		return book;
	}

	public String getCategoryPaths() {
		return categoryPaths;
	}

	public Newsletter getNewsletter() {
		return newsletter;
	}

	@Override
	public String toString() {
		return "Notification [book =" + book + ", categoryPaths = " + categoryPaths + ", newletter = " + newsletter
				+ "]";
	}

}
