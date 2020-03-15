package com.cognizant.vehiclereservationsystem.services;

import java.util.List;
import java.util.Map;

import com.cognizant.vehiclereservationsystem.models.Booking;
import com.cognizant.vehiclereservationsystem.models.Coupon;
import com.cognizant.vehiclereservationsystem.models.User;
import com.cognizant.vehiclereservationsystem.models.Vehicle;
import com.cognizant.vehiclereservationsystem.models.VehicleBooking;

public interface VehicleService {

	public List<Vehicle> getVehicles();

	public List<Vehicle> getAllVehicles();

	public List<Vehicle> getVehicleByName(String vehicleName);

	public Vehicle getVehicleById(long vehicleId);

	public boolean updateVehicle(Vehicle vehicle);

	public boolean deleteVehicle(long vehicleId);

	public boolean addVehicle(Vehicle vehicle);

	public List<Coupon> getCoupons();
	public boolean couponTaken(String code);


	public boolean addCoupon(Coupon coupon);

	public boolean removeCoupon(long couponId);

	public List<VehicleBooking> getBookingForVehicle(long vehicleId);

	public boolean numberAvailable(String username);

}
