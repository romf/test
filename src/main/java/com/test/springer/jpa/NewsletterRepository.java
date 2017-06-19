package com.test.springer.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterRepository extends JpaRepository<Newsletter, String> {

	Newsletter findNewsletterByRecipient(String recipient);

}
