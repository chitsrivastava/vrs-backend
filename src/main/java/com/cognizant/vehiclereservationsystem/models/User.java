package com.cognizant.vehiclereservationsystem.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "us_id")
	private long userId;

	@Column(name = "us_email", unique = true)
	private String email;

	@Column(name = "us_firstname")
	private String firstName;

	@Column(name = "us_lastname")
	private String lastName;

	@Column(name = "us_password")
	private String password;

	@Column(name = "us_vendor_id")
	private long vendorId;

	@Column(name = "us_age")
	private int age;

	@Column(name = "us_gender")
	private String gender;

	@Column(name = "us_contact_no")
	private long contactNumber;

	@Column(name = "us_is_approved")
	private boolean isApproved;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Wallet wallet;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Transaction> transactionList;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Booking> bookingList;

	@OneToMany(mappedBy="user",cascade=CascadeType.ALL)
	private List<Notification> notificationList;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "ur_us_id"), inverseJoinColumns = @JoinColumn(name = "ur_ro_id"))
	private Set<Role> roleList;

	public User(long userId, String email, String firstName, String lastName, String password, long vendorId, int age,
			String gender, long contactNumber, boolean isApproved, Wallet wallet) {
		super();
		this.userId = userId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.vendorId = vendorId;
		this.age = age;
		this.gender = gender;
		this.contactNumber = contactNumber;
		this.isApproved = isApproved;
		this.wallet = wallet;
	}

	public User(String email, String firstName, String lastName, String password, long vendorId, int age, String gender,
			long contactNumber, boolean isApproved, Wallet wallet) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.vendorId = vendorId;
		this.age = age;
		this.gender = gender;
		this.contactNumber = contactNumber;
		this.isApproved = isApproved;
		this.wallet = wallet;
	}

	public User() {
		super();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getVendorId() {
		return vendorId;
	}

	public void setVendorId(long vendorId) {
		this.vendorId = vendorId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public List<Transaction> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

	public List<Booking> getBookingList() {
		return bookingList;
	}

	public void setBookingList(List<Booking> bookingList) {
		this.bookingList = bookingList;
	}

	public Set<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(Set<Role> roleList) {
		this.roleList = roleList;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public List<Notification> getNotificationList() {
		return notificationList;
	}

	public void setNotificationList(List<Notification> notificationList) {
		this.notificationList = notificationList;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", vendorId=" + vendorId + ", age=" + age + ", gender=" + gender
				+ ", contactNumber=" + contactNumber + ", isApproved=" + isApproved + "]";
	}

}
