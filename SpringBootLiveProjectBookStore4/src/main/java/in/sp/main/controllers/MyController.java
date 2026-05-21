package in.sp.main.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.sp.main.entities.Cart;
import in.sp.main.entities.Address;
import in.sp.main.entities.Book;
import in.sp.main.entities.Orders;
import in.sp.main.entities.User;
import in.sp.main.repositories.OrderRepository;
import in.sp.main.services.AddressService;
import in.sp.main.services.BookService;
import in.sp.main.services.CartService;
import in.sp.main.services.OrderService;
import in.sp.main.services.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MyController {

	@Autowired
	private UserService userservice;
	
	@Autowired
	private BookService bookservice;
	
	@Autowired
	private CartService cartservice;
	
	@Autowired
	private OrderService orderservice;
	
	@Autowired
	private AddressService addressservice;

	@GetMapping("/")
	public String GetIndexPage() {
		return "index";
	}
	
	@GetMapping("/login")
	public String GetLogin() {
		return "Login";
	}
	
	@PostMapping("/login")
	public String CheckLogin(@ModelAttribute("user") User u, Book b, Model model, HttpSession session) {
		User s=userservice.LoginVerify(u.getRole(), u.getEmail(), u.getPassword());
		
		if(s!=null) {
			if(s.getRole().equals("USER")) {
				model.addAttribute("successMsg", "Login Successfull");
				model.addAttribute("name", s.getName());
				session.setAttribute("name", s.getName());
				
				long l=bookservice.TotalBook();
				model.addAttribute("totalBooks", l);
				session.setAttribute("totalBooks", l);
				session.setAttribute("userid", s.getId());
				
				long l1=cartservice.Countcart(s.getId());
				model.addAttribute("cartItems", l1);
				session.setAttribute("cartItems", l1);

				
				long l2=orderservice.CountOrder(s.getId());
				model.addAttribute("orders", l2);
				session.setAttribute("orders", l2);

				
				List<Orders> a=orderservice.getorder(s.getId());
				model.addAttribute("recentOrders", a);
				session.setAttribute("recentOrders", a);
				
				model.addAttribute("username", s.getName());
				return "Welcome";
			}
			else {
				model.addAttribute("successMsg", "Login Successfull");
				model.addAttribute("name", s.getName());
				long l=bookservice.TotalBook();
				model.addAttribute("totalBooks", l);
				long l1=userservice.CountUser("USER");
				model.addAttribute("totalUsers", l1);
				long l2=orderservice.getOrders("Ordered");
				model.addAttribute("totalOrders", l2);
				double d=orderservice.getrev();
				model.addAttribute("revenue", d);
				
				List<Orders> orders = orderservice.getOders();

				Map<Integer, Address> addressMap = new HashMap<>();

				for(Orders o : orders){

				    Address a = addressservice.getbyid(o.getUserid());

				    if(a != null){
				        addressMap.put(o.getUserid(), a);
				    }
				}

				model.addAttribute("orders", orders);
				model.addAttribute("addressMap", addressMap);

				return "admindeshboard";

				
			}
		}
		else {
			model.addAttribute("errorMsg", "Login Failed");
			return "Login";
			
		}
	}
	
	@GetMapping("/books")
	public String GetBooks(Model model ) {
		List<Book> a1=bookservice.GetBooks();
		model.addAttribute("books", a1);
		return "Books";
	}
	//@ModelAttribute ke sath Model model nhi Lagate hain woo entity class ke liye hota hai
	@GetMapping("/dashboard")
	public String GetDeshboard(Model model, HttpSession session) {
	    Integer userid = (Integer) session.getAttribute("userid");

	    if(userid == null){

	        return "redirect:/login";
	    }
	    
	    String s = (String) session.getAttribute("name");

	    Long s1 = (Long) session.getAttribute("totalBooks");

	    Long s2 = (Long) session.getAttribute("cartItems");

	    Long s3 = (Long) session.getAttribute("orders");
	    


		List<Orders> o=orderservice.getorder(userid);
		model.addAttribute("orders", o);

	    //List<Orders> s4 =
	           // (List<Orders>) session.getAttribute("recentOrders");


		model.addAttribute("name", s);
		model.addAttribute("totalBooks", s1);
		model.addAttribute("cartItems", s2);
		model.addAttribute("orders", s3);
		model.addAttribute("recentOrders", o);





		
		
		return "UserDeshBoard";
	}
	
	

	
	
	@GetMapping("/register")
	public String GetReg() {
		return "Register";
	}
	
	@PostMapping("/register")
	public String AddStd(@ModelAttribute("user") User u, Model model) {
		boolean status=userservice.AddStd(u);
		if(status) {
			model.addAttribute("successMsg", "Registeration Successfull");
			return "Login";
			
		}
		else {
			model.addAttribute("errorMsg", "Registeration Failed");
			return "Register";
		}
		
	}
	@GetMapping("/logout")
	public String Logout(HttpSession session ) {
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/cart")
	public String Cart(HttpSession session, Model model) {
		Integer uid = (Integer) session.getAttribute("userid");
		Long userid = uid.longValue();



		List<Cart> c=cartservice.GetCart(userid);
		Double totalPrice=0.0;
		for(Cart n : c) {
	        totalPrice += n.getTotalprice();	        
		}
		

		model.addAttribute("cartItems", c);
	    model.addAttribute("totalPrice", totalPrice);

		return "Cart";
	}
	@GetMapping("/remove/cart/{id}")
	public String DeleteCart(@PathVariable int id) {
		cartservice.DeleteCart(id);
		return "redirect:/cart";
	}
	
	@PostMapping("/update/cart")
	public String UpdateCart(@RequestParam List<Long> id, @RequestParam List<Integer> bookquantity, @RequestParam List<Double> totalprice, HttpSession session, Model model ) {

		Integer userid = (Integer) session.getAttribute("userid");

		if(userid == null){

		    return "redirect:/login";
		}
		for(int i=0; i<id.size(); i++) {
			cartservice.UpdateCart(id.get(i), bookquantity.get(i), totalprice.get(i));
			
			
		}
		
		Address a1=addressservice.getbyid(userid);
	    if(a1 == null) {
	        a1 = new Address();
	    }
		model.addAttribute("address", a1);
		

	    long i= cartservice.Countcart(userid);
		model.addAttribute("totalItems", i);
		long a=userid;
		double s3=cartservice.getTotalPriceByUserId(a);
		model.addAttribute("totalPrice", s3);
		return "Order";
		
		
	}
	@GetMapping("/addtocart/{id}")
	public String AddCart(@PathVariable int id, HttpSession session) {
		Integer userid = (Integer) session.getAttribute("userid");

		if(userid == null){

		    return "redirect:/login";
		}


		
		cartservice.AddCart(id, userid);
		
	   
	
	    

		
		
		return "redirect:/books";
		
	}
	@GetMapping("/orders")
	public String Getorders(Model model, HttpSession session) {
		Integer userid = (Integer) session.getAttribute("userid");

		if(userid == null){

		    return "redirect:/login";
		}

		List<Orders> o=orderservice.getorder(userid);
		model.addAttribute("orders", o);
		return "orderpage";
	}
	
	@GetMapping("/profile")
	public String GetProfile(Model model, HttpSession session) {

	    Integer userid = (Integer) session.getAttribute("userid");

	    if(userid == null) {
	        return "redirect:/login";
	    }

	    System.out.println(userid);

	    User u = userservice.GetUser(userid);

	    model.addAttribute("user", u);

	    return "userprofile";
	}
	
	
	@PostMapping("/updateprofile")
	public String Updateprofile(@ModelAttribute("user") User u, Model model) {

	    boolean status = userservice.UpdateUser(u);

	    if(status) {

	        model.addAttribute("successMsg", "Updated Successfully");

	    } else {

	        model.addAttribute("errorMsg", "Updation Failed");

	    }

	    return "userprofile";
	}
	
	@PostMapping("/order-now")
	public String orderdone(Model model, HttpSession session) {
	    Integer userid = (Integer) session.getAttribute("userid");

	    if(userid == null) {
	        return "redirect:/login";
	    }
	    Address a=addressservice.getbyid(userid);
	    if(a!=null) {
	    	orderservice.PlaceOrder(userid);
	    	return "OrderSuccess";
	    	
	    }
	    else {
	    	model.addAttribute("errorMsg", "Add Address First");
	        model.addAttribute("address", new Address());//ye ek khali object create karte jb humko koi null or empty field print karna ho
		    long i= cartservice.Countcart(userid);
			model.addAttribute("totalItems", i);
			long a1=userid;
			double s3=cartservice.getTotalPriceByUserId(a1);
			model.addAttribute("totalPrice", s3);
	    	return "Order";
	    	
	    }
		
		
	}
	
	@GetMapping("/address")
	public String getAddress(Model model, HttpSession session) {
	    Integer userid = (Integer) session.getAttribute("userid");
	    

	    if(userid == null) {
	        return "redirect:/login";
	    }
	    
		 Address a=addressservice.getbyid(userid);
		 if(a == null) {
		     a = new Address();
		 }
		 model.addAttribute("address", a);
	    
	    
	   

		return "Address";
		
	}
	
	@PostMapping("/save-address")
	public String updateAdd(@ModelAttribute("address") Address a, Model model, HttpSession session ) {
	    Integer userid = (Integer) session.getAttribute("userid");

	    if(userid == null) {
	        return "redirect:/login";
	    }
	    
		boolean s=addressservice.Addaddress(a, userid);
		if(s) {
			model.addAttribute("successMsg", "Saved Successfully");
			
		}
		else {
			model.addAttribute("errorMsg", "Failed to Save");
		}
		
		return "Address";
	}
	
	
	@GetMapping("/bookss")
	public String GetBooksAdmin(Model model ) {
		List<Book> a1=bookservice.GetBooks();
		model.addAttribute("books", a1);
		
		return "BrowseBookAdmin";
		
	}
	
	@GetMapping("/admin/addbook")
	public String AddBook(Model model) {
		model.addAttribute("book", new Book());
		
		return "AddBook";
	}
	@PostMapping("/admin/savebook")
	public String AddBooks(@ModelAttribute("book") Book b,
	                       Model model,
	                       @RequestParam("imagefile") MultipartFile file)
	{
	    try
	    {
	        b.setImage(file.getBytes());

	        boolean b1 = bookservice.AddBook(b);

	        if(b1)
	        {
	            model.addAttribute("successMsg",
	                               "Book Added Successfully");
	        }
	        else
	        {
	            model.addAttribute("errorMsg",
	                               "Book Failed To Add");
	        }
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();

	        model.addAttribute("errorMsg",
	                           "Image Upload Failed");
	    }

	    return "AddBook";
	}
	
	@GetMapping("/admin/users")
	public String GetName(Model model) {

		List<User> u=userservice.GetUsers("USER");
		model.addAttribute("users", u);
		return "UserList";		
	}
	
	@GetMapping("/admin/orders")
	public String ManageOrders(Model model) {
		List<Orders> orders = orderservice.ManageUser();

		Map<Integer, Address> addressMap = new HashMap<>();

		for(Orders o : orders){

		    Address a = addressservice.getbyid(o.getUserid());

		    if(a != null){
		        addressMap.put(o.getUserid(), a);
		    }
		}

		model.addAttribute("orders", orders);
		model.addAttribute("addressMap", addressMap);

		return "ManageOrders";
	}
	
	@PostMapping("/admin/updatestatus")
	public String UpdateStatus(@RequestParam("id") int id,  @RequestParam("status") String S, RedirectAttributes r) {
		
		boolean b=orderservice.updateStatus(id, S);
		if(b) {
			r.addFlashAttribute("successMsg", "Status Updated Successfully");
		}
		else {
			r.addFlashAttribute("errorMsg", "Status updation Failed");
		}
		return "redirect:/admin/orders";
		
	}
	


	

	
	
	
	
	
	
	
	
	


		
	
			
	

}
