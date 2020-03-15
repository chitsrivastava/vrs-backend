package com.cognizant.vehiclereservationsystem.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.vehiclereservationsystem.exception.UserAlreadyExistsException;
import com.cognizant.vehiclereservationsystem.models.Booking;
import com.cognizant.vehiclereservationsystem.models.Notification;
import com.cognizant.vehiclereservationsystem.models.Transaction;
import com.cognizant.vehiclereservationsystem.models.User;
import com.cognizant.vehiclereservationsystem.models.Wallet;
import com.cognizant.vehiclereservationsystem.services.AppUserDetailsService;
import com.cognizant.vehiclereservationsystem.services.UserService;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	UserService userService;

	@Autowired
	AppUserDetailsService appUserDetailsService;

	@PostMapping
	public User signUp(@RequestBody User user) throws UserAlreadyExistsException {
		return appUserDetailsService.signUp(user);
	}
	
	@PutMapping("/reset/{email}")
	public boolean resetPassword(@RequestBody String password, @PathVariable String email) {
		return appUserDetailsService.resetPassword(password,email);
	}
	
	@GetMapping("/numberValid/{email}/{number}")
	public boolean numberValid(@PathVariable String email,@PathVariable long number) {
		return (userService.getUser(email).getContactNumber() == number)? true : false;
	}

//	@PutMapping("/{email}/{amount}")
//	public Wallet addMoney(@PathVariable String email, @PathVariable float amount) {
//		try {
//			return userService.addMoney(amount, email,bookingId);
//		} catch (Exception e) {
//			return null;
//		}
//	}

	@GetMapping("/get/{email}")
	public User getUser(@PathVariable String email) {
		try {
			return userService.getUser(email);
		} catch (Exception e) {
			return null;
		}
	}
	@GetMapping("/notification/{email}")
	public List<Notification> getNotification(@PathVariable String email) {
		try {
			return userService.getUserNotification(email);
		} catch (Exception e) {
			return null;
		}
	}

	@GetMapping("/{username}")
	public boolean usernameAvailable(@PathVariable String username) {
		System.out.println(username);
		return (userService.usernameAvailable(username));
	}

	@GetMapping("/approve/{email}")
	public boolean approveUser(@PathVariable String email) {
		return (userService.approveUser(email));
	}

	@GetMapping("/approved")
	public List<User> approvedUsers() {
		return (userService.getApprovedUsers());
	}

	@GetMapping("/not-approved")
	public List<User> NotApprovedUsers() {
		return (userService.getNotApprovedUsers());
	}

	@GetMapping("/transactions/{email}")
	public List<Transaction> get(@PathVariable String email) {
		return userService.getTransactions(email);
	}

	@GetMapping("/bookings/{email}")
	public List<Booking> getBookings(@PathVariable String email) {
		return userService.getBookingList(email);
	}

	@GetMapping("/transactions/{email}/{bookingId}")
	public List<Transaction> getTransactions(@PathVariable String email, @PathVariable long bookingId) {
		return userService.getTransactionByBooking(email, bookingId);
	}

	@PostMapping("/booking/{email}/{vehicleId}/{startDate}/{days}/{id}")
	public Map<String, String> bookVehicle(@PathVariable String email,@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate startDate , @PathVariable long vehicleId,
			@PathVariable int days,@PathVariable long id) {
		return userService.bookVehicle(email, vehicleId, startDate,days,id);
	}
	
	@DeleteMapping("/booking/{bookingId}")
	public Map<String, String> cancelBooking(@PathVariable long bookingId) {
		return userService.cancelBooking(bookingId);
	}
	
	@GetMapping("/booking/end/{bookingId}")
	public boolean endRide(@PathVariable long bookingId) {
		return userService.endRide(bookingId);
	}
	
	@DeleteMapping("/notification/{notificationId}")
	public boolean deleteNotification(@PathVariable long notificationId) {
		return userService.deleteNotification(notificationId);
	}
	
	@DeleteMapping("/notification-bulk/{email}")
	public boolean clearNotifications(@PathVariable String email) {
		return userService.clearNotifications(email);
	}
}
