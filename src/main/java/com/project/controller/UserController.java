package com.project.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.project.entity.Contact;
import com.project.entity.User;
import com.project.repo.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	
	//method for common data
	@ModelAttribute
	public void addcommondata(Model model, Principal principal) {
		String userName=principal.getName();
		System.out.println("Username="+userName);
		//get the user using username(email)
		User user= userRepository.getUserByName(userName);
		
		System.out.println("User details are ="+user);
		
		model.addAttribute("user", user);
		
	}
	
	//dashboard home
	@RequestMapping("index")
	public String dashboard(Model model, Principal principal) {
		
		model.addAttribute("title","User Dashboard");
		return "normal/user_dashboard";
	}
	
	//open and add form controller
	@GetMapping("add-contact")
	public String openAddContactForm(Model model) {
		
		model.addAttribute("title","Add Contact");
		model.addAttribute("contact",new Contact());
		return "normal/add_contact";
	}
	
	//Add contact form
	@PostMapping("/process-contact")
	public String processcontact(@ModelAttribute Contact contact) {
		
		System.out.println("DATA="+contact);
		return "normal/add_contact";
	}
	
}
