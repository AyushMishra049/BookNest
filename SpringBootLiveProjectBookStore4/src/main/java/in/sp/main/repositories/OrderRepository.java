package in.sp.main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.sp.main.entities.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer>{
	public long countByUserid(int userid);
	public List<Orders> findAllByUserid(int userid);
	public long countBystatus(String status);
    @Query("SELECT SUM(o.bookprice) FROM Orders o WHERE o.status='Delivered'")
    Double getDeliveredRevenue();
    
    @Query("SELECT o FROM Orders o WHERE o.status='Ordered'")
    List<Orders> getOrders();
    


}
