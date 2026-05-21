package in.sp.main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sp.main.entities.Book;

public interface BookRepository extends JpaRepository<Book, Integer>{
	List<Book> findByType(String s);
	List<Book> findByNameContainingIgnoreCase(String s);
	List<Book> findByAutherContainingIgnoreCase(String s);
	

}
