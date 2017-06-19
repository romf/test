package com.test.springer.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, String> {

	Subscriber findSubscriberByEmail(String email);

}
