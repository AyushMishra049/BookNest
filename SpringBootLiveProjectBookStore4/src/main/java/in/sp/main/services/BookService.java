package in.sp.main.services;

import java.util.List;

import in.sp.main.entities.Book;

public interface BookService {
	public long TotalBook();
	public List<Book> GetBooks();
	public List<Book> GetByType(String s);
	public List<Book> GetByName(String s);
	public List<Book> GetByAuther(String s);
	public void GetBook(int id);
	public Boolean AddBook(Book b);
	

}
