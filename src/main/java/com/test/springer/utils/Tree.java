package src.main.java.com.test.springer.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.test.springer.jpa.Book;
import com.test.springer.jpa.BookRepository;

public class Tree<T> {
	
	public static LinkedHashMap<String, LinkedList<LinkedList<String>>> lm;
	private BookRepository bookRepository;

	private Node<T> root;

	public Tree(Node<T> root) {
		this.root = root;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}

	public ArrayList<ArrayList<Node<T>>> getPathsFromRootToAnyLeaf() {
		ArrayList<ArrayList<Node<T>>> paths = new ArrayList<ArrayList<Node<T>>>();
		ArrayList<Node<T>> currentPath = new ArrayList<Node<T>>();
		getPath(root, currentPath, paths);

		return paths;
	}

	private void getPath(Node<T> node, ArrayList<Node<T>> currentPath, ArrayList<ArrayList<Node<T>>> paths) {
		if (currentPath == null)
			return;

		currentPath.add(node);

		List<Book> books = bookRepository.findBookByCategoryCode("%" + node.toString() + ",%",
				"%," + node.toString() + "%", node.toString());

		if (books.size() > 0) {
			paths.add(clone(currentPath));
			
			for (Book b : books) {
				if (!lm.containsKey(b.getTitle())) {
					lm.put(b.getTitle(), new LinkedList<LinkedList<String>>());
				}
				LinkedList<LinkedList<String>> ll = lm.get(b.getTitle());
				LinkedList<String> newll = new LinkedList<String>();
				for (Node<T> stackEle : currentPath) {
					newll.add(stackEle.toString());
				}
				ll.add(newll);
			}
		}

		for (Node<T> child : node.getChildren())
			getPath(child, currentPath, paths);

		int index = currentPath.indexOf(node);
		for (int i = index; i < currentPath.size(); i++)
			currentPath.remove(index);
	}

	private ArrayList<Node<T>> clone(ArrayList<Node<T>> list) {
		ArrayList<Node<T>> newList = new ArrayList<Node<T>>();
		for (Node<T> node : list)
			newList.add(new Node<T>(node));

		return newList;
	}

	public void setBookRepository(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public BookRepository getBookRepository() {
		return this.bookRepository;
	}

}