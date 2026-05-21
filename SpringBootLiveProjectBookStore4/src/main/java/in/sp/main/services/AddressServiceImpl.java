package in.sp.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.entities.Address;
import in.sp.main.repositories.AddressRepository;
import jakarta.transaction.Transactional;

@Service
public class AddressServiceImpl implements AddressService{
	
	@Autowired
	private AddressRepository addressrepository;

	@Override
	public boolean Addaddress(Address a, int id) {

	    boolean b = false;

	    try {

	        Address a1 = addressrepository.findByUserid(id);

	        if(a1 != null) {
	            addressrepository.delete(a1);
	        }

	        a.setUserid(id);

	        addressrepository.save(a);

	        b = true;

	    } catch(Exception e) {

	        e.printStackTrace();

	        b = false;
	    }

	    return b;
	}


	@Override
	public Address getbyid(int id) {
		
		return addressrepository.findByUserid(id);
	
	
	}

	@Override
	public Address GetAddress(int id) {
		return addressrepository.getById(id);
		
	}

	




	
	

}
