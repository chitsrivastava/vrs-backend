package com.cognizant.vehiclereservationsystem.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.vehiclereservationsystem.models.Booking;
import com.cognizant.vehiclereservationsystem.models.Coupon;
import com.cognizant.vehiclereservationsystem.models.User;
import com.cognizant.vehiclereservationsystem.models.Vehicle;
import com.cognizant.vehiclereservationsystem.models.VehicleBooking;
import com.cognizant.vehiclereservationsystem.repositories.BookingRepository;
import com.cognizant.vehiclereservationsystem.repositories.CouponRepository;
import com.cognizant.vehiclereservationsystem.repositories.VehicleRepository;

@Service
public class VehicleServiceImplementation implements VehicleService {

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	VehicleRepository vehicleRepository;

	@Autowired
	CouponRepository couponRepository;

	@Override
	public List<Vehicle> getVehicles() {
		return vehicleRepository.findByAvailability(true);
	}

	@Override
	public List<Vehicle> getAllVehicles() {
		return vehicleRepository.findAll();
	}

	@Override
	public List<Vehicle> getVehicleByName(String vehicleName) {
		return vehicleRepository.findByName(vehicleName);
	}

	@Override
	public Vehicle getVehicleById(long vehicleId) {
		return vehicleRepository.findById(vehicleId).get();
	}

	@Override
	public boolean updateVehicle(Vehicle updatedVehicle) {
		try {
			Vehicle vehicle = getVehicleById(updatedVehicle.getVehicleId());
			vehicle.setAvailability(updatedVehicle.getAvailability());
			vehicle.setImageUrl(updatedVehicle.getImageUrl());
			vehicle.setInsuranceExpiryDate(updatedVehicle.getInsuranceExpiryDate());
			vehicle.setLastServiceDate(updatedVehicle.getLastServiceDate());
			vehicle.setName(updatedVehicle.getName());
			vehicle.setPrice(updatedVehicle.getPrice());
			vehicle.setServiceDueDate(updatedVehicle.getServiceDueDate());
			vehicle.setType(updatedVehicle.getType());
			vehicle.setNumber(updatedVehicle.getNumber());
			vehicleRepository.save(vehicle);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean deleteVehicle(long vehicleId) {
		try {
			Vehicle vehicle = getVehicleById(vehicleId);
			vehicle.setAvailability(!vehicle.getAvailability());
			vehicleRepository.save(vehicle);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean addVehicle(Vehicle vehicle) {
		try {
			vehicleRepository.save(vehicle);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Coupon> getCoupons() {
		return couponRepository.findAll();
	}
	@Override
	public boolean couponTaken(String code) {
		if (couponRepository.findByCode(code) == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean addCoupon(Coupon coupon) {
		try {
			couponRepository.save(coupon);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean removeCoupon(long couponId) {
		try {
			Coupon coupon = couponRepository.findById(couponId).get();
			couponRepository.delete(coupon);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<VehicleBooking> getBookingForVehicle(long vehicleId) {
		List<VehicleBooking> vehicleBookingList = new ArrayList<VehicleBooking>();
		try {
			Vehicle vehicle = vehicleRepository.findById(vehicleId).get();
			List<Booking>bookingList =  bookingRepository.findByVehicle(vehicle);
			for (Booking booking : bookingList) {
				VehicleBooking vehicleBooking = new VehicleBooking(booking.getUser().getEmail(),
						booking.getUser().getContactNumber(),booking);
				vehicleBookingList.add(vehicleBooking);
			}
			return vehicleBookingList;
		} catch (

		Exception e) {
			return null;
		}
	}

	@Override
	public boolean numberAvailable(String number) {
		try {
if(vehicleRepository.findByNumber(number).size()==0) {
	return true;
}
return false;
		}catch (Exception e) {
			return false;
		}
	}

}
