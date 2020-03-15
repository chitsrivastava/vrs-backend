package com.cognizant.vehiclereservationsystem.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.vehiclereservationsystem.models.Booking;
import com.cognizant.vehiclereservationsystem.models.Coupon;
import com.cognizant.vehiclereservationsystem.models.User;
import com.cognizant.vehiclereservationsystem.models.Vehicle;
import com.cognizant.vehiclereservationsystem.models.VehicleBooking;
import com.cognizant.vehiclereservationsystem.services.UserService;
import com.cognizant.vehiclereservationsystem.services.VehicleService;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

	
	@Autowired
	UserService userService;
	
	@Autowired
	VehicleService vehicleService;
	
	@GetMapping("/admin")
	public List<Vehicle> getAll(){
		return vehicleService.getAllVehicles();
	}
	
	@GetMapping
	public List<Vehicle> getAllAvailable(){
		return vehicleService.getVehicles();
	}
	@GetMapping("/id/{vehicleId}")
	public Vehicle get(@PathVariable long vehicleId) {
		return vehicleService.getVehicleById(vehicleId);
	}
	
	@GetMapping("/name/{name}")
	public List<Vehicle> getByName(@PathVariable String name) {
		return vehicleService.getVehicleByName(name);
	}
	
	@PutMapping
	public boolean update(@RequestBody Vehicle vehicle) {
		return vehicleService.updateVehicle(vehicle);
	}
	
	@PostMapping
	public boolean add(@RequestBody Vehicle vehicle) {
		return vehicleService.addVehicle(vehicle);
	}
	
	@DeleteMapping("/{vehicleId}")
	public boolean delete(@PathVariable long vehicleId) {
		return vehicleService.deleteVehicle(vehicleId);
	}
	
	@GetMapping("/coupons")
	public List<Coupon> getCoupons(){
		return vehicleService.getCoupons();
	}
	
	@PostMapping("/coupons")
	public boolean addCoupon(@RequestBody Coupon coupon) {
		return vehicleService.addCoupon(coupon);
	}
	
	@DeleteMapping("/coupons/{couponId}")
	public boolean removeCoupon(@PathVariable long couponId ) {
		return vehicleService.removeCoupon(couponId);
	}
	
	@GetMapping("/booking/{vehicleId}")
	public List<VehicleBooking> getBookings(@PathVariable long vehicleId){
		return vehicleService.getBookingForVehicle(vehicleId);
	}
	@GetMapping("/coupons/{code}")
	public boolean couponAvailable(@PathVariable String code) {
		System.out.println(code);
		return (vehicleService.couponTaken(code));
	}
	@GetMapping("/available/{username}")
	public boolean usernameAvailable(@PathVariable String username) {
		System.out.println(username);
		return (vehicleService.numberAvailable(username));
	}

}
