package com.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.entity.User;
import com.project.repo.UserRepository;

public class UserServiceDetailsImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Fetching user from db
		
		User user = userRepository.getUserByName(username);
		if(user==null) {
			
			throw new UsernameNotFoundException("Could not found user");
		}
		
		CustomUserDetails customUserDetails=new CustomUserDetails(user);
		return customUserDetails;
	}

}
