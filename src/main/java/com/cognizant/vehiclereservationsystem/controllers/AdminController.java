package com.cognizant.vehiclereservationsystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.vehiclereservationsystem.models.User;
import com.cognizant.vehiclereservationsystem.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	UserService userService;

	@GetMapping("/approve/{email}")
	public boolean approveUser(@PathVariable String email) {
		return (userService.approveUser(email));
	}

	@GetMapping("/approved-users")
	public List<User> approvedUsers() {
		return (userService.getApprovedUsers());
	}

	@GetMapping("/not-approved-users")
	public List<User> NotApprovedUsers() {
		return (userService.getNotApprovedUsers());
	}
	@GetMapping("/approved-admin")
	public List<User> approvedAdmins() {
		return (userService.getApprovedAdmin());
	}

	@GetMapping("/not-approved-admin")
	public List<User> NotApprovedAdmins() {
		return (userService.getNotApprovedAdmin());
	}
	@GetMapping("remove/{email}")
	public boolean removeUser(@PathVariable String email) {
		return userService.removeUser(email);
	}
	@GetMapping("/get/notify-insurance/{email}")
	public void getInsuranceNotification(@PathVariable String email) {
		System.out.println("notify called");
		userService.notifyForInsuranceAndService(email);
	}
}
