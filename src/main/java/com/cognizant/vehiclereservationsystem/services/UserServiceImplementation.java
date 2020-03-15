package com.cognizant.vehiclereservationsystem.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.xml.stream.events.EndDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.vehiclereservationsystem.models.Booking;
import com.cognizant.vehiclereservationsystem.models.Coupon;
import com.cognizant.vehiclereservationsystem.models.Notification;
import com.cognizant.vehiclereservationsystem.models.Transaction;
import com.cognizant.vehiclereservationsystem.models.User;
import com.cognizant.vehiclereservationsystem.models.Vehicle;
import com.cognizant.vehiclereservationsystem.models.Wallet;
import com.cognizant.vehiclereservationsystem.repositories.BookingRepository;
import com.cognizant.vehiclereservationsystem.repositories.CouponRepository;
import com.cognizant.vehiclereservationsystem.repositories.TransactionRepository;
import com.cognizant.vehiclereservationsystem.repositories.UserRepository;
import com.cognizant.vehiclereservationsystem.repositories.VehicleRepository;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	CouponRepository couponRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	VehicleRepository vehicleRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	BookingRepository bookingRepository;

	@Override
	public boolean signUp(User user) {
		userRepository.save(user);
		return true;
	}

	@Override
	public List<Notification> getUserNotification(String email) {

		User user = userRepository.findByEmail(email);
//		if(user !=null) {
//			List<Booking> bookingList = user.getBookingList();
//			for(Booking booking : bookingList) {
//				
//				long startDateInteval = ChronoUnit.DAYS.between(LocalDate.now(), booking.getStartDate());
//				long endDateInteval = ChronoUnit.DAYS.between(LocalDate.now(), booking.getEndDate());
//				if(booking.getStatus().equalsIgnoreCase("booked")||booking.getStatus().equalsIgnoreCase("active")) {
//					if(startDateInteval==1) {
//						Notification notification = new Notification("You booking starts tomorrow !!");
//						notification.setUser(user);
//						notificationRepository.save(notification);
//					}
//					else if(startDateInteval==0) {
//						Notification notification = new Notification("You booking starts today !!");
//						notification.setUser(user);
//						notificationRepository.save(notification);
//					}
//					if(endDateInteval==1) {
//						Notification notification = new Notification("You booking ends tomorrow !!");
//						notification.setUser(user);
//						notificationRepository.save(notification);
//					}
//					else if(endDateInteval==0) {
//						Notification notification = new Notification("You booking ends today !!");
//						notification.setUser(user);
//						notificationRepository.save(notification);
//					}
//				}
//			}
//		}
		return user.getNotificationList();
	}

	@Override
	public User getUser(String email) {
		try {
			User user = userRepository.findByEmail(email);

			return user;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public User getUser(long userId) {
		return null;
	}

	public boolean usernameAvailable(String email) {
		return userRepository.findByEmail(email) == null ? true : false;
	}

	@Override
	public Wallet addMoney(float amountTotal, String email, long bookingId, String type, Coupon coupon) {
		float amount = amountTotal;
		User user = userRepository.findByEmail(email);
		Wallet wallet = user.getWallet();
		if (type.equals("debit")) {
			if (wallet != null) {
				amount = amountTotal - wallet.getAmount();
			} else {
				amount = amountTotal;
			}
		}

		List<Transaction> transactionList = user.getTransactionList();
		Booking booking = bookingRepository.findById(bookingId).get();
		if (transactionList.size() == 0) {
			Transaction transaction = new Transaction(booking, user, type, amount, coupon);
			transactionList.add(transaction);
			user.setTransactionList(transactionList);
		}
		if (wallet != null) {
			if (type.equals("debit")) {
				wallet.setAmount(0);
			} else if (type.equals("credit")) {
				wallet.setAmount(wallet.getAmount() + amount);
			}
			Transaction transaction = new Transaction(booking, user, type, amount, coupon);
			transactionList.add(transaction);

		} else if (wallet == null) {
			wallet = new Wallet(0, user);
		}
		user.setWallet(wallet);
		userRepository.save(user);
		return wallet;
	}

	// USER APPROVAL RELATED
	@Override
	public List<User> getApprovedUsers() {
		return userRepository.findByIsApprovedAndVendorId(true, 0);
	}

	@Override
	public List<User> getNotApprovedUsers() {
		return userRepository.findByIsApprovedAndVendorId(false, 0);
	}

	@Override
	public List<User> getApprovedAdmin() {
		return userRepository.findByIsApprovedAndVendorIdNot(true, 0);
	}

	@Override
	public List<User> getNotApprovedAdmin() {
		return userRepository.findByIsApprovedAndVendorIdNot(false, 0);
	}

	@Override
	public boolean approveUser(String email) {
		try {
			User user = userRepository.findByEmail(email);
			user.setApproved(true);

			Notification notification = new Notification("Account Approved !!");
			notification.setUser(user);
			notificationRepository.save(notification);

			userRepository.save(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// BOOKING VEHICLE RELATED

	@Transactional
	@Override
	public Map<String, String> bookVehicle(String email, long vehicleId, LocalDate startDate, int numberOfDays,
			long couponId) {
		Coupon coupon = null;
		if (couponId != 0) {
			coupon = couponRepository.findById(couponId).get();
		}
		User user = userRepository.findByEmail(email);
		List<Notification> notificationList = user.getNotificationList();
		Map<String, String> response = new HashMap<String, String>();
		List<Transaction> transactionList = user.getTransactionList();
		Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
		List<Booking> vehicleBookingList = vehicle.getBoookingList();
		System.out.println(vehicleBookingList.size());
		List<Booking> bookingList = user.getBookingList();
		LocalDate currentDate = LocalDate.now();
		boolean isAvailable = checkIfVehicleIsAvailable(vehicleBookingList, startDate,
				startDate.plusDays(numberOfDays));
		for (Booking bookingCheck : bookingList) {
			if ((bookingCheck.getStatus().equals("active") || bookingCheck.getStatus().equals("booked"))) {
				response.put("status", "unsuccessfull");
				response.put("reason", "You already have booked a ride.");
				return response;
			}
		}
		if (!isAvailable) {
			response.put("status", "unsuccessfull");
			response.put("reason", "Select another date.");
			return response;
		}

		Wallet wallet = user.getWallet();

		Booking booking = new Booking(user, vehicle, startDate, startDate.plusDays(numberOfDays), currentDate,
				"pending payment");

		bookingList.add(booking);

		Booking getBooking = bookingRepository.save(booking);

		float amount = numberOfDays * (vehicle.getPrice());

		if (coupon != null) {
			amount -= coupon.getValue() * amount / 100;
		}

		if (wallet == null || wallet.getAmount() < amount) {
			addMoney(amount, email, getBooking.getBookingId(), "debit", coupon);
		} else {
			wallet.setAmount(wallet.getAmount() - amount);
			Transaction transaction = new Transaction(booking, user, "debit", amount, coupon);
			transactionList.add(transaction);
			user.setWallet(wallet);
		}

		vehicle.setBoookingList(vehicleBookingList);
		if (startDate.isAfter(currentDate)) {
			booking.setStatus("booked");
		} else {
			booking.setStatus("active");
		}
		bookingRepository.save(booking);

		Notification notification = new Notification("Booking successful for "+vehicle.getName()+" !! Refer in the bookings section for complete details.");
		notification.setUser(user);
		notificationRepository.save(notification);
		response.put("status", "successfull");

		return response;
	}

	private boolean checkIfVehicleIsAvailable(List<Booking> bookingList, LocalDate startDate, LocalDate endDate) {
		boolean valid = false;
		if (bookingList == null || bookingList.size() == 0) {
			return true;
		}
		for (Booking booking : bookingList) {
			if ((startDate.isBefore(booking.getStartDate()) && endDate.isBefore(booking.getStartDate()))
					|| startDate.isAfter(booking.getEndDate()) && endDate.isAfter(booking.getEndDate())) {
				valid = true;
				continue;
			} else {
				if ((!booking.getStatus().equals("active") && !booking.getStatus().equals("booked"))) {
					valid = true;
					continue;
				}
				return false;
			}
		}
		return valid;
	}

	@Override

	public Map<String, String> cancelBooking(long bookingId) {

		Booking booking = bookingRepository.findById(bookingId).get();
		User user = userRepository.findById(booking.getUser().getUserId()).get();
		Map<String, String> response = new HashMap<String, String>();

		LocalDate startDate = booking.getStartDate();
		int difference = startDate.compareTo(LocalDate.now());
		if (!booking.getStatus().equals("active") && !booking.getStatus().equals("booked")) {
			response.put("status", "unsuccessfull");
			response.put("reason", "Booking not active.");
			return response;
		}
		float deduction = 0;

		if (difference >= 2) {
			deduction = 0.1f;
		} else if (difference == 1) {
			deduction = 0.2f;
		} else if (difference == 0) {
			deduction = 0.3f;
		}
		List<Transaction> transactions = getTransactionByBooking(user.getEmail(), bookingId);
		System.out.println(transactions.size());
		float transactionAmount = 0;
		for (Transaction transaction2 : transactions) {
			if (transaction2.getType().equals("debit")) {
				transactionAmount = transaction2.getAmount();
				break;
			}
		}
		float refundAmount = transactionAmount * (1 - deduction);

		addMoney(refundAmount, user.getEmail(), bookingId, "credit", null);

		booking.setStatus("cancelled");
		bookingRepository.save(booking);

		response.put("status", "booking cancelled successfully !");

		Notification notification = new Notification("Booking - bookingId: " + bookingId + " for "
				+ booking.getVehicle().getName() + " successfully Cancelled !!");
		notification.setUser(user);
		notificationRepository.save(notification);
		
		Notification notificationRefund = new Notification("Refund of Rs."+refundAmount+" for bookingId:"+ bookingId + " for "
				+ booking.getVehicle().getName() + " has been received.");
				notificationRefund.setUser(user);
		notificationRepository.save(notificationRefund);
		
		return response;
	}

	@Override
	public List<Transaction> getTransactions(String email) {
		User user = userRepository.findByEmail(email);
		try {
			return user.getTransactionList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Booking> getBookingList(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			return user.getBookingList();
		}
		return null;
	}

	@Override
	public List<Transaction> getTransactionByBooking(String email, long bookingId) {

		try {
			Booking booking = bookingRepository.findById(bookingId).get();
			if (booking != null) {
				return transactionRepository.findByBooking(booking);
			}
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public boolean endRide(long bookingId) {

		try {
			Booking booking = bookingRepository.findById(bookingId).get();
			booking.setStatus("complete");
			bookingRepository.save(booking);
			Notification notification = new Notification("Ride Ended successfully for "+booking.getVehicle().getName()+" !!");
			notification.setUser(booking.getUser());
			notificationRepository.save(notification);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean removeUser(String email) {
		try {
			User user = userRepository.findByEmail(email);
			// userRepository.delete(user);
			user.setApproved(false);

			Notification notification = new Notification("Account Disapproved !!");
			notification.setUser(user);
			notificationRepository.save(notification);

			userRepository.save(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// NOTIFICATION RELATED

	@Override
	public boolean deleteNotification(long notificationId) {
		try {
			Notification notification = notificationRepository.findById(notificationId).get();
			notificationRepository.delete(notification);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void notifyForInsuranceAndService(String email) {
		List<Vehicle> vehicleList = vehicleRepository.findAll();

		for (Vehicle vehicle : vehicleList) {
			boolean notify = false;
			boolean notifyService = false;

			long intervalDays = ChronoUnit.DAYS.between(LocalDate.now(), vehicle.getInsuranceExpiryDate());
			long intervalDaysService = ChronoUnit.DAYS.between(LocalDate.now(), vehicle.getServiceDueDate());

			String message = "";
			String messageService = "";

			if (intervalDaysService < 0) {
				messageService = "has passed";
				notifyService = true;
			} else if (intervalDaysService == 0) {
				messageService = "is today";
				notifyService = true;
			} else if (intervalDaysService == 1) {
				messageService = "is tomorrow";
				notifyService = true;
			} else if (intervalDaysService <= 15) {
				messageService = "due in" + intervalDays + " days.";
				notifyService = true;
			}

			if (intervalDays < 0) {
				message = "has expired";
				notify = true;
			} else if (intervalDays == 0) {
				message = "expires today";
				notify = true;
			} else if (intervalDays == 1) {
				message = "expires tomorrow";
				notify = true;
			} else if (intervalDays <= 15) {
				message = "expires in" + intervalDays + " days.";
				notify = true;
			}

			User admin = userRepository.findByEmail(email);
				if (notify) {
					String notificationMessage="Insurance for " + vehicle.getName() + " (vehicleId: "
							+ vehicle.getVehicleId() + ") " + message +" ("+vehicle.getInsuranceExpiryDate()+")";
					Notification notification = new Notification(notificationMessage);
					notification.setUser(admin);
					
					if(notificationRepository.findByUserAndMessage(admin, notificationMessage).size()==0) {
						notificationRepository.save(notification);						
					}
				}
				if (notifyService) {
					String notificationMessage = "Service for " + vehicle.getName() + " (vehicleId: "
							+ vehicle.getVehicleId() + ") " + messageService +" ("+vehicle.getServiceDueDate()+")";
					Notification notification = new Notification(notificationMessage);
					notification.setUser(admin);
					if(notificationRepository.findByUserAndMessage(admin, notificationMessage).size()==0) {
						notificationRepository.save(notification);						
					}
				}

		}
	}

	@Override
	public boolean clearNotifications(String email) {
		try {
			User user = userRepository.findByEmail(email);
			List<Notification> notifications = notificationRepository.findByUser(user);
			for (Notification notification : notifications) {
				notificationRepository.delete(notification);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
