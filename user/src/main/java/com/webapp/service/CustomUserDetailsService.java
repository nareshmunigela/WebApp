package com.webapp.service;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.webapp.dao.CustomUserDetails;
import com.webapp.dao.UserRepository;
import com.webapp.domain.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepo;
     
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }
    
    public void processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
         
        userRepo.save(user);
	}

	public void deleteUser(Long id) {
		userRepo.deleteById(id);
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}
	
}