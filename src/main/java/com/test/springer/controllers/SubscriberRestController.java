package com.test.springer.controllers;

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

import com.test.springer.jpa.BookRepository;
import com.test.springer.jpa.Category;
import com.test.springer.jpa.CategoryRepository;
import com.test.springer.jpa.Newsletter;
import com.test.springer.jpa.NewsletterRepository;
import com.test.springer.jpa.Notification;
import com.test.springer.jpa.NotificationRepository;
import com.test.springer.jpa.Subscriber;
import com.test.springer.jpa.SubscriberRepository;
import com.test.springer.utils.Node;
import com.test.springer.utils.Tree;

@RestController
public class SubscriberRestController {

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

		// create a newsletter
		Newsletter newNewsletter = new Newsletter(newSubscriber.getEmail());
		newsletterRepository.save(newNewsletter);

		createNotifications(newNewsletter, categories);

		return newSubscriber;
	}

	// private static Node<String> root;

	public void loadTree(String category, Node<String> root) {
		List<Category> categoriesChild = categoryRepository.findCategoryBySuperCategory(category);
		for (Category cat : categoriesChild) {
			Node<String> child = new Node<String>(cat.getCode());
			root.addChild(child);
			loadTree(cat.getCode(), child);
		}
	}

	public void createNotifications(final Newsletter newsletter, final LinkedList<String> categories) {
		// load categories tree
		for (String cat : categories) {
			Node<String> root = new Node<String>(cat);
			loadTree(cat, root);
			Tree<String> tree = new Tree<String>(root);
			tree.lm = new LinkedHashMap<String, LinkedList<LinkedList<String>>>();
			tree.setBookRepository(bookRepository);
			tree.getPathsFromRootToAnyLeaf();
			System.out.println("LM NOW");
			System.out.println(tree.lm);
			for (Entry<String, LinkedList<LinkedList<String>>> each : tree.lm.entrySet()) {
				LinkedList<LinkedList<String>> categoryPaths = each.getValue();
				notificationRepository.save(new Notification(newsletter, each.getKey(), categoryPaths.toString()));
			}
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
