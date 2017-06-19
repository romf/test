package com.test.springer.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.springer.jpa.Newsletter;
import com.test.springer.jpa.NewsletterRepository;

@RestController
public class NewsletterRestController {
	@RequestMapping(value = "/newsletters", method = RequestMethod.GET)
	Collection<Newsletter> getNewsletters() {
		return newsletterRepository.findAll();
	}

	@Autowired
	NewsletterRepository newsletterRepository;
}
