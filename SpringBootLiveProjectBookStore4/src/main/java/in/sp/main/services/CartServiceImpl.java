package in.sp.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.entities.Book;
import in.sp.main.entities.Cart;
import in.sp.main.entities.User;
import in.sp.main.repositories.BookRepository;
import in.sp.main.repositories.CartRepository;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartRepository cartrepository;
	@Autowired
	private BookRepository bookrepository;

	@Override
	public long Countcart(int id) {
		return cartrepository.countByUserid(id);
	}

	@Override
	public List<Cart> GetCart(Long id) {
		return cartrepository.findAllByUserid(id);
	}

	@Override
	public void DeleteCart(int id) {
		cartrepository.deleteById(id);
		
	}

	@Override
	public void UpdateCart(Long id, int bookquantity, double totalprice) {
		int i=id.intValue();

	    Cart cart = cartrepository.findById(i).orElse(null);

	    if(cart != null){

	        cart.setBookquantity(bookquantity);

	        double total = cart.getBookprice() * bookquantity;

	        cart.setTotalprice(total);

	        cartrepository.save(cart);
	    }
	}


	@Override
	public Integer getTotalPriceByUserId(Long userid) {

	    Integer total =
	            cartrepository.sumTotalPriceByUserId(userid);

	    if (total == null) {
	        return 0;
	    }

	    return total;
	}


	@Override
	public void AddCart(int id, int userid) {

	    Book b = bookrepository.findById(id).orElse(null);

	    if (b != null) {

	        Cart c = new Cart();
	        User u=new User();

	        c.setBookid(id);
	        c.setBookname(b.getName());
	        c.setBookauther(b.getAuther());
	        c.setBookquantity(1);
	        c.setUserid(userid);
	        c.setBookprice(b.getPrice());
	        c.setTotalprice(b.getPrice());
	        c.setBooktype(b.getType());
	        c.setStatus("PENDING");
	        cartrepository.save(c);
	    }
	}









}
