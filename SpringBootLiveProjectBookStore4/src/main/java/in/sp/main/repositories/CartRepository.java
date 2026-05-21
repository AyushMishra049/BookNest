package in.sp.main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.sp.main.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{

	long countByUserid(int id);


	List<Cart> findAllByUserid(Long id);


	
    @Query("SELECT SUM(c.totalprice) " +
            "FROM Cart c " +
            "WHERE c.userid = :userid")

     Integer sumTotalPriceByUserId(
             @Param("userid") Long userid);


	List<Cart> findByUserid(int userid);
	
	

}
