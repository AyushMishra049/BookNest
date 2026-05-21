package in.sp.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sp.main.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

	public Address findByUserid(int id);

	void deleteByUserid(int id);

	

}
