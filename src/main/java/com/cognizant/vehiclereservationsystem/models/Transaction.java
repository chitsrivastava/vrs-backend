package com.cognizant.vehiclereservationsystem.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.cognizant.vehiclereservationsystem.models.Booking;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tr_id")
	private long transactionId;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="tr_bk_id")
	private Booking booking;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="tr_us_id")
	private User user;
	
	@Column(name="type", nullable = false)
	private String type;
	
	@Column(name="amount", nullable = false)
	private float amount;
	
	@ManyToOne
	@JoinColumn(name="tr_cp_id")
	private Coupon coupon;

	public Transaction() {
		super();
	}
	
	public Transaction(long transactionId, Booking booking, User user,
			String type, float amount, Coupon coupon) {
		super();
		this.transactionId = transactionId;
		this.booking = booking;
		this.user = user;
		this.type = type;
		this.amount = amount;
		this.coupon = coupon;
	}


	public Transaction(Booking booking, User user, String type,
			float amount, Coupon coupon) {
		super();
		this.booking = booking;
		this.user = user;
		this.type = type;
		this.amount = amount;
		this.coupon = coupon;
	}

	public Transaction(String type, float amount, Coupon coupon) {
		super();
		this.type = type;
		this.amount = amount;
		this.coupon = coupon;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", Booking=" + booking + ", user=" + user + ", type="
				+ type + ", amount=" + amount + ", coupon=" + coupon + "]";
	}
	
	
}
