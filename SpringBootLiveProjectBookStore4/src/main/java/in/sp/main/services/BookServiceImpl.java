package in.sp.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.entities.Book;
import in.sp.main.repositories.BookRepository;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository bookrepository;

	@Override
	public long TotalBook() {
		return bookrepository.count();
	}

	@Override
	public List<Book> GetBooks() {
		return bookrepository.findAll();
	}

	@Override
	public List<Book> GetByType(String s) {
		return bookrepository.findByType(s);
	}

	@Override
	public List<Book> GetByName(String s) {
		return bookrepository.findByNameContainingIgnoreCase(s);
	}

	@Override
	public List<Book> GetByAuther(String s) {
		return bookrepository.findByAutherContainingIgnoreCase(s);
	}

	@Override
	public void GetBook(int id) {
		bookrepository.findById(id);
		
	}

	@Override
	public Boolean AddBook(Book b) {
		boolean b1=false;
		try {
			bookrepository.save(b);
			b1=true;
		}
		catch(Exception e){
			e.printStackTrace();
			b1=false;
			
		}
		
		return b1;
	}



}
