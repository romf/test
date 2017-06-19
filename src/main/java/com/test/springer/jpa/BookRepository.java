package com.test.springer.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, String> {

	Book findBookByTitle(String title);

	@Query("SELECT b FROM Book b WHERE LOWER(b.categoryCodes) LIKE LOWER(:categoryCodeBefore) OR LOWER(b.categoryCodes) LIKE LOWER(:categoryCodeAfter) OR LOWER(b.categoryCodes) = LOWER(:categoryCodeEquals)")
	List<Book> findBookByCategoryCode(@Param("categoryCodeBefore") String categoryCodeBefore, @Param("categoryCodeAfter") String categoryCodeAfter, @Param("categoryCodeEquals") String categoryCodeEquals);

}
