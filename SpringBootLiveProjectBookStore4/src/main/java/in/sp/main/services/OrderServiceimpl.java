package in.sp.main.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.entities.Cart;
import in.sp.main.entities.Orders;
import in.sp.main.entities.User;
import in.sp.main.repositories.CartRepository;
import in.sp.main.repositories.OrderRepository;
import in.sp.main.repositories.UserRepository;

@Service
public class OrderServiceimpl implements OrderService{
	
	@Autowired
	private OrderRepository orderrepository;
	
	@Autowired
	private CartRepository cartrepository;
	
	@Autowired
	private UserRepository userrepository;

	@Override
	public long CountOrder(int id) {
		return orderrepository.countByUserid(id);
	}

	@Override
	public List<Orders> getorder(int id) {
		return orderrepository.findAllByUserid(id);
	}
	
	
	@Override
	public void PlaceOrder(Integer userid) {
		
	    User user = userrepository.findById(userid).orElse(null);


	    List<Cart> cartItems = cartrepository.findByUserid(userid);

	    for(Cart c : cartItems) {

	        Orders o = new Orders();

	        o.setUserid(c.getUserid());

	        o.setBookid(c.getBookid());

	        o.setBookname(c.getBookname());

	        o.setAuthorname(c.getBookauther());

	        o.setBookprice(c.getTotalprice());

	        o.setBookquantity(c.getBookquantity());

	        o.setEmail(user.getEmail());

	        o.setPhone(user.getPhone());
	        o.setUsername(user.getName());


	        o.setStatus("Ordered");

	        orderrepository.save(o);
	    }

	}

	@Override
	public long getOrders(String status) {
		return orderrepository.countBystatus(status);
	}

	@Override
	public double getrev() {
		Double d=orderrepository.getDeliveredRevenue();
		if(d==null) {
			return 0.0;
			
		}
		return d;
	}

	@Override
	public List<Orders> getOders() {
		return orderrepository.getOrders();
	}

	@Override
	public List<Orders> ManageUser() {
		return orderrepository.findAll();
	}

	@Override
	public boolean updateStatus(int id, String s) {
		Orders o=orderrepository.findById(id).orElse(null) ;
		boolean b=false;
		if(o!=null) {
			o.setStatus(s);
			orderrepository.save(o);
			b=true;
		}
		else {
			b=false;
		}
		return b;
	}
	
	






}
