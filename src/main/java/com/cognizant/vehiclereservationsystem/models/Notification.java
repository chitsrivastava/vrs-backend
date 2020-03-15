package com.cognizant.vehiclereservationsystem.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table
@Entity
public class Notification{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="nt_id")
	private long id;
	
	@Column(name="nt_message")
	private String message;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="nt_us_id")
	private User user;
	
	@Column(name="nt_date_time")
	private LocalDateTime date;

	
	public Notification() {
		super();
	}


	public Notification(String message) {
		super();
		this.message = message;
		this.date = LocalDateTime.now() ;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", message=" + message + ", user=" + user + ", date=" + date + "]";
	}
	
	
}
