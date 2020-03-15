package com.cognizant.vehiclereservationsystem.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "vehicle")
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ve_id")
	private long vehicleId;

	@Column(name="ve_no", nullable=false, unique=true)
	private String number;
	
	@Column(name = "ve_name", nullable = false)
	private String name;

	@Column(name = "ve_image_url", nullable = false)
	private String imageUrl;

	@Column(name = "ve_type", nullable = false)
	private String type;

	@Column(name = "ve_insurance_expiry_date", nullable = false)
	private LocalDate insuranceExpiryDate;

	@Column(name = "ve_last_service_date", nullable = false)
	private LocalDate lastServiceDate;

	@Column(name = "ve_service_due_date", nullable = false)
	private LocalDate serviceDueDate;

	@Column(name = "ve_availability", columnDefinition = "boolean default true")
	private boolean availability;

	@Column(name = "ve_price", nullable = false)
	private float price;

	@JsonIgnore
	@OneToMany(mappedBy = "vehicle")
	private List<Booking> boookingList;

	public Vehicle() {
		super();
	}

	public Vehicle(String name, String imageUrl, String type, LocalDate insuranceExpiryDate, LocalDate lastServiceDate,
			LocalDate serviceDueDate, boolean availability, float price, List<Booking> boookingList) {
		super();
		this.name = name;
		this.imageUrl = imageUrl;
		this.type = type;
		this.insuranceExpiryDate = insuranceExpiryDate;
		this.lastServiceDate = lastServiceDate;
		this.serviceDueDate = serviceDueDate;
		this.availability = availability;
		this.price = price;
		this.boookingList = boookingList;
	}

	public long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDate getInsuranceExpiryDate() {
		return insuranceExpiryDate;
	}

	public void setInsuranceExpiryDate(LocalDate insuranceExpiryDate) {
		this.insuranceExpiryDate = insuranceExpiryDate;
	}

	public LocalDate getLastServiceDate() {
		return lastServiceDate;
	}

	public void setLastServiceDate(LocalDate lastServiceDate) {
		this.lastServiceDate = lastServiceDate;
	}

	public LocalDate getServiceDueDate() {
		return serviceDueDate;
	}

	public void setServiceDueDate(LocalDate serviceDueDate) {
		this.serviceDueDate = serviceDueDate;
	}

	public boolean getAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public List<Booking> getBoookingList() {
		return boookingList;
	}

	public void setBoookingList(List<Booking> boookingList) {
		this.boookingList = boookingList;
	}

	@Override
	public String toString() {
		return "Vehicle [vehicleId=" + vehicleId + ", name=" + name + ", imageUrl=" + imageUrl + ", type=" + type
				+ ", insuranceExpiryDate=" + insuranceExpiryDate + ", lastServiceDate=" + lastServiceDate
				+ ", serviceDueDate=" + serviceDueDate + ", availability=" + availability + ", price=" + price
				+ ", boookingList=" + boookingList + "]";
	}

	
	
}
