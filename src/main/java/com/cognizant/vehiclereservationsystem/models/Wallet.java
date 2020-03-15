package com.cognizant.vehiclereservationsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="wallet")
public class Wallet {

	@Id
	@Column(name="wa_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long walletId;
	
	@Column(name="wa_amount", nullable = false)
	private float amount;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="wa_us_id", nullable = false,unique = true)
	private User user;

	public Wallet() {
		super();
	}

	public Wallet(long walletId, float amount, User user) {
		super();
		this.walletId = walletId;
		this.amount = amount;
		this.user = user;
	}

	public Wallet(float amount, User user) {
		super();
		this.amount = amount;
		this.user = user;
	}

	public long getWalletId() {
		return walletId;
	}

	public void setWalletId(long walletId) {
		this.walletId = walletId;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Wallet [walletId=" + walletId + ", amount=" + amount + ", user=" + user + "]";
	}
	
	
}
