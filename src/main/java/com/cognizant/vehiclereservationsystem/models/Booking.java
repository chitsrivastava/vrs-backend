package com.cognizant.vehiclereservationsystem.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "booking")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bk_id")
	private long bookingId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "bk_us_id")
	private User user;

	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "bk_ve_id")
	private Vehicle vehicle;
	
	@JsonIgnore
	@OneToMany(mappedBy="booking", cascade = CascadeType.ALL)
	private List <Transaction> transactionList;

	@Column(name = "bk_start_date", nullable = false)
	private LocalDate startDate;
	
	@Column(name = "bk_end_date", nullable = false)
	private LocalDate endDate;
	
	@Column(name = "bk_booking_date")
	private LocalDate bookingDate;
	
	@Column(name = "bk_status")
	private String status;

	public Booking() {
		super();
	}

	public Booking(User user, Vehicle vehicle, LocalDate startDate, LocalDate endDate, LocalDate bookingDate,
			String status) {
		super();
		this.user = user;
		this.vehicle = vehicle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.bookingDate = bookingDate;
		this.status = status;
	}

	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public List<Transaction> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", user=" + user.getEmail() + ", vehicle=" + vehicle.getVehicleId() + ", transactionList="
				+ transactionList.size() + ", startDate=" + startDate + ", endDate=" + endDate + ", bookingDate=" + bookingDate
				+ ", status=" + status + "]";
	}
}
