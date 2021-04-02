package com.project.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.entity.User;
import com.project.helper.Message;
import com.project.repo.UserRepository;

@Controller
public class HomeController {
	
   @Autowired
   private BCryptPasswordEncoder passwordencoder;	
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public String home(Model model) {
		
		model.addAttribute("title", "Home - Smart Contact Management");
		return "Home";
	}

	
	@GetMapping("/about")
	public String about(Model model) {
		
		model.addAttribute("title", "About - Smart Contact Management");
		return "About";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		
		model.addAttribute("title", "Register - Smart Contact Management");
		model.addAttribute("user", new User() );
		return "signup";
	}
	
	//This handler for registering user
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1, @RequestParam(value="agreement", defaultValue = "false") boolean agreement,  Model model, HttpSession session) {
			
		try {
			
			if(!agreement)
			{
				System.out.println("You have not agreed terms and conditions");
				throw new Exception("You have not agreed terms and conditions");
				
			}
			
			if(result1.hasErrors()) {
				System.out.println("ERROR" +result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}
		    user.setRole("ROLE_USER");	
		    user.setEnabled(true);
		    user.setImageUrl("default.png");
		    
		    user.setPassword(passwordencoder.encode(user.getPassword()));
			
			System.out.println("Agreement="+agreement);
			
			User result =this.userRepository.save(user);
			model.addAttribute("user", result);
			System.out.println("User="+user);
			model.addAttribute("user", new User());
			
			session.setAttribute("message", new Message("Successfully registered", "alert-success"));
			
			return "signup";
			
			
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message", new Message("Something went wrong!!"+e.getMessage(), "alert-danger"));
			
			return "signup";
		}
		
	
	}
	
	@GetMapping("/signin")
	public String customLogin(Model model) {
		model.addAttribute("Title","LoginPage");
		return "Login";
		
		
	}


}

