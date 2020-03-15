package com.cognizant.vehiclereservationsystem.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "coupon")
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cp_id")
	private long couponId;

	@Column(name = "cp_code", nullable = false)
	private String code;

	@Column(name = "cp_value", nullable = false)
	private int value;

	@JsonIgnore
	@OneToMany(mappedBy = "coupon")
	private List<Transaction> transactionList;

	public Coupon() {
		super();
	}

	public Coupon(long couponId, String code, int value, List<Transaction> transactionList) {
		super();
		this.couponId = couponId;
		this.code = code;
		this.value = value;
		this.transactionList = transactionList;
	}

	public Coupon(String code, int value, List<Transaction> transactionList) {
		super();
		this.code = code;
		this.value = value;
		this.transactionList = transactionList;
	}

	public Coupon(String code, int value) {
		super();
		this.code = code;
		this.value = value;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public List<Transaction> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", code=" + code + ", value=" + value + ", transactionList="
				+ transactionList + "]";
	}
}
