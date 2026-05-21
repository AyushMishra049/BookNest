package in.sp.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.entities.User;
import in.sp.main.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userrepository;

	@Override
	public boolean AddStd(User u) {
		boolean status=false;
		User u1=userrepository.findByEmail(u.getEmail());
		try {
			if(u1!=null) {
				status=false;
			}
			else {
				userrepository.save(u);
				status=true;
			}

		}
		catch(Exception e){
			e.printStackTrace();
			status=false;

		}
		return status;
	}

	@Override
	public User LoginVerify(String role, String email, String password) {
		User s=userrepository.findByEmail(email);
		if(s!=null && s.getEmail().equals(email) && s.getPassword().equals(password) && s.getRole().equals(role)) {
			return s;
		}
		return null;
	}

	@Override
	public User GetUser(int id) {
		
		return userrepository.getById(id);
	}



	@Override
	public boolean UpdateUser(User u) {
		System.out.println(u.getId());
		User e=userrepository.findById(u.getId()).orElseThrow(()-> new RuntimeException("Not found"));
		
		e.setName(u.getName());
		e.setEmail(u.getEmail());
		e.setPhone(u.getPhone());
		if(u.getPassword() != null) {
		    e.setPassword(u.getPassword());
		}
		return userrepository.save(e) != null;

		
	}

	@Override
	public long CountUser(String status) {
		return userrepository.countByRole(status);
	}

	@Override
	public List<User> GetUsers(String s) {
		
		return userrepository.findByRole(s);
	}
	

}
