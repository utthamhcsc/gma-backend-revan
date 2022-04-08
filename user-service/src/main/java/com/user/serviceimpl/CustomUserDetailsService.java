package com.user.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.user.entities.CustomeUserDetails;
import com.user.entities.User;
import com.user.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userDao;

	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> exist = userDao.findByEmailOrPhoneNumber(username, username);
	    if (exist.isPresent()) {
	    	
return new CustomeUserDetails(exist.get());
	    } else {
	        throw new UsernameNotFoundException(
	                "Unable to find user with username provided!!");
	    }
	    
	}
	
	

}
