package com.cognizant.vehiclereservationsystem.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.cognizant.vehiclereservationsystem.models.Booking;
import com.cognizant.vehiclereservationsystem.models.Coupon;
import com.cognizant.vehiclereservationsystem.models.Notification;
import com.cognizant.vehiclereservationsystem.models.Transaction;
import com.cognizant.vehiclereservationsystem.models.User;
import com.cognizant.vehiclereservationsystem.models.Wallet;

public interface UserService {

	public boolean signUp(User user);

	public User getUser(long userId);

	public boolean usernameAvailable(String username);

	public User getUser(String email);

	public List<User> getApprovedUsers();

	public List<User> getNotApprovedUsers();

	public boolean approveUser(String email);

	//public Map<String,String> bookVehicle(String email,long vehicleId, LocalDate startDate,int numberOfDays);
	
	public Map<String, String> cancelBooking(long bookingId);

	public List<Transaction> getTransactions(String email);
	
	public List<Transaction> getTransactionByBooking(String email, long bookingId);

	public List<Booking> getBookingList(String email);

	//Wallet addMoney(float amount, String email, long bookingId, String type);
	
	public boolean endRide(long bookingId);

	public boolean removeUser(String email);

	public List<User> getApprovedAdmin();

	public List<User> getNotApprovedAdmin();

	public Map<String, String> bookVehicle(String email, long vehicleId, LocalDate startDate, int numberOfDays, long couponId);

	public Wallet addMoney(float amountTotal, String email, long bookingId, String type, Coupon coupon);

	public boolean deleteNotification(long notificationId);
	
	public void notifyForInsuranceAndService(String email);
	
	public boolean clearNotifications(String email);

	public List<Notification> getUserNotification(String email);
}
