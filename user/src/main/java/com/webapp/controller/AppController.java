package com.webapp.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.dao.UserRepository;
import com.webapp.domain.User;
import com.webapp.service.CustomUserDetailsService;
 
@Controller
public class AppController {
 
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CustomUserDetailsService custService;
     
    @GetMapping("/home")
    public String viewHomePage() {
        return "index.html";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
         
        return "signup_form";
    }
    
    @PostMapping("/process_register")
    public String processRegister(User user) {
    	custService.processRegister(user);
        return "register_success";
 
    }

	
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = custService.getAllUsers();
        model.addAttribute("listUsers", listUsers);
         
        return "users";
    }
    
    
    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
    	
    	custService.deleteUser(id);
    	return "delete_success";
    }
}