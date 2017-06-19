package com.test.springer.jpa;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Newsletter {

	@Id
	private String recipient;

	@OneToMany(mappedBy = "newsletter")
	private Set<Notification> notifications;

	public Newsletter() {
	}

	public Newsletter(String recipient) {
		this.recipient = recipient;
	}

	public String getRecipient() {
		return recipient;
	}

	public Set<Notification> getNotifications() {
		return notifications;
	}

	@Override
	public String toString() {
		return "Newsletter [recipient =" + recipient + ", notifications = " + notifications + "]";
	}

}
