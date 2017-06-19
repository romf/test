package com.test.springer.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

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
import com.test.springer.jpa.Newsletter;
import com.test.springer.jpa.NewsletterRepository;
import com.test.springer.jpa.Notification;
import com.test.springer.jpa.NotificationRepository;
import com.test.springer.jpa.Subscriber;
import com.test.springer.jpa.SubscriberRepository;

@RestController
public class SubscriberRestController {

	private static LinkedHashMap<String, LinkedList<String>> lm;

	@RequestMapping(value = "/subscribers", method = RequestMethod.GET)
	Collection<Subscriber> getSubscribers() {
		return subscriberRepository.findAll();
	}

	@RequestMapping(value = "/subscribers/{subscriberEmail}", method = RequestMethod.GET)
	Subscriber getSubscriber(@PathVariable String subscriberEmail) {
		return subscriberRepository.findSubscriberByEmail(subscriberEmail);
	}

	@RequestMapping(value = "/subscribers", method = RequestMethod.POST)
	public Subscriber createBook(@RequestParam(value = "email") String email,
			@RequestParam(value = "categoryCodes") String categoryCodes) {
		String[] listCategoryCodes = categoryCodes.split(",");
		LinkedList<String> categories = new LinkedList<String>();
		for (String code : listCategoryCodes) {
			Category cat = categoryRepository.findCategoryByCode(code);
			if (cat == null) {
				System.out.println("Category with code " + code + " not found. Ignoring this category.");
				continue;
			}
			if (categories.contains(cat)) {
				continue;
			}
			categories.add(cat.getCode());
		}

		// create subscriber
		Subscriber newSubscriber = new Subscriber(email, String.join(",", categories));
		subscriberRepository.save(newSubscriber);
		lm = new LinkedHashMap<String, LinkedList<String>>();

		// create a newsletter
		Newsletter newNewsletter = new Newsletter(newSubscriber.getEmail());
		newsletterRepository.save(newNewsletter);

		createNotifications(newNewsletter, categories);

		return newSubscriber;
	}

	public void createNotifications(final Newsletter newsletter, final LinkedList<String> categories) {
		for (String cat : categories) {
			getCategoryPaths(cat, cat);
		}

		for (Entry<String, LinkedList<String>> each : lm.entrySet()) {
			LinkedList<String> categoryPaths = each.getValue();
			List<List<String>> finalPaths = new ArrayList<List<String>>();
			for (String path : categoryPaths) {
				finalPaths.add(Arrays.asList(path.split(";")));
				notificationRepository.save(new Notification(newsletter, each.getKey(), finalPaths.toString()));
			}
		}
	}

	public void getCategoryPaths(String currentCategoryCode, String element) {
		List<Book> books = bookRepository.findBookByCategoryCode("%" + currentCategoryCode + ",%",
				"%," + currentCategoryCode + "%", currentCategoryCode);
		
		if (books.size() > 0) {
			for (Book b : books) {
				if (!lm.containsKey(b.getTitle())) {
					lm.put(b.getTitle(), new LinkedList<String>());
				}
				LinkedList<String> ll = lm.get(b.getTitle());
				ll.add(element);
			}
		}

		List<Category> categories = categoryRepository.findCategoryBySuperCategory(currentCategoryCode);
		for (Category cat : categories) {
			currentCategoryCode = cat.getCode();
			String newElement = element + ";" + currentCategoryCode;
			getCategoryPaths(currentCategoryCode, newElement);
		}
	}

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	SubscriberRepository subscriberRepository;

	@Autowired
	NewsletterRepository newsletterRepository;

	@Autowired
	NotificationRepository notificationRepository;

}
