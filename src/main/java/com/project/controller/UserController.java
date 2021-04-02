package com.project.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.User;
import com.project.repo.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("index")
	public String dashboard(Model model, Principal principal) {
		
		String userName=principal.getName();
		System.out.println("Username="+userName);
		//get the user using username(email)
		User user= userRepository.getUserByName(userName);
		
		System.out.println("User details are ="+user);
		
		model.addAttribute("user", user);
		return "normal/user_dashboard";
	}
	
}
