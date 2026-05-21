package in.sp.main.services;

import java.util.List;

import in.sp.main.entities.Cart;

public interface CartService {
	public long Countcart(int id);
	List<Cart> GetCart(Long a);
	public void DeleteCart(int id);
	public void  UpdateCart(Long id, int quantity, double totalprice);
	public Integer getTotalPriceByUserId(Long userid);
	public void AddCart(int id, int userid);

}
