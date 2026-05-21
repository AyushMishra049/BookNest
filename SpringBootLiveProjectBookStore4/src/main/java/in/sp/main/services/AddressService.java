package in.sp.main.services;

import in.sp.main.entities.Address;

public interface AddressService {
	
	public boolean Addaddress(Address a, int id);
	public Address getbyid(int id);
	public Address GetAddress(int id);
	

}
