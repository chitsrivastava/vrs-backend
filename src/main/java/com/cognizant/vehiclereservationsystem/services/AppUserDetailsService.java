package com.cognizant.vehiclereservationsystem.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.vehiclereservationsystem.exception.UserAlreadyExistsException;
import com.cognizant.vehiclereservationsystem.models.AppUserDetails;
import com.cognizant.vehiclereservationsystem.models.Notification;
import com.cognizant.vehiclereservationsystem.models.Role;
import com.cognizant.vehiclereservationsystem.models.User;
import com.cognizant.vehiclereservationsystem.repositories.RoleRepository;
import com.cognizant.vehiclereservationsystem.repositories.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	
	
	public AppUserDetailsService() {
		super();
	}

	public AppUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public AppUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		AppUserDetails appUser;
		if (user == null) {
			throw new UsernameNotFoundException("Username not found");
		} else {
			appUser = new AppUserDetails(user);
		}
		return appUser;
	}
	
	public User signUp(User newUser) throws UserAlreadyExistsException{
		User user;
		user = userRepository.findByEmail(newUser.getEmail());
		if(user==null) {
			long roleId = newUser.getVendorId() == 0 ? 2 : 1;
			//newUser.setIsApproved(roleId == 1 ? true : false);
			Role role = roleRepository.findByRoleId(roleId);
			Set<Role> roleList = new HashSet<Role>();
			roleList.add(role);
			newUser.setRoleList(roleList);
			String password = newUser.getPassword();
			newUser.setPassword(passwordEncoder().encode(password));
			
			Notification notificationUser = new Notification("Welcome to Vehicle reservation system.");
			notificationUser.setUser(user);

			notificationRepository.save(notificationUser);

			List<User> adminList = userRepository.findByIsApprovedAndVendorIdNot(true, 0);
			for (User admin : adminList) {
				Notification notification = new Notification("New User "+newUser.getFirstName() +" is waiting for your approval !!");
				notification.setUser(admin);
				notificationRepository.save(notification);			
			}
			userRepository.save(newUser);
			return newUser;
		}else {
			throw new UserAlreadyExistsException();
		}
	}
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public boolean resetPassword(String password, String email) {

		try{
			User user = userRepository.findByEmail(email);
			user.setPassword(passwordEncoder().encode(password));
			userRepository.save(user);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	

}
