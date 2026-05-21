package in.sp.main.services;

import java.util.List;

import in.sp.main.entities.Orders;

public interface OrderService {
	
	public long CountOrder(int id);
	List<Orders> getorder(int id);
	void PlaceOrder(Integer userid);
	public long getOrders(String status);
	public double getrev();
	public List<Orders> getOders();
	public List<Orders> ManageUser();
	public boolean updateStatus(int id, String s);
}
