package com.cognizant.vehiclereservationsystem.models;

public class VehicleBooking {

	private String email;
	private long contactNumber;
	private Booking booking ;
	public VehicleBooking(String email, long contactNumber, Booking booking) {
		super();
		this.email = email;
		this.contactNumber = contactNumber;
		this.booking = booking;
	}
	public VehicleBooking() {
		super();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
}
