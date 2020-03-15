package com.cognizant.vehiclereservationsystem.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.vehiclereservationsystem.models.Notification;
import com.cognizant.vehiclereservationsystem.models.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
	List<Notification> findByUser(User user);
	List<Notification> findByUserAndMessage(User user,String message);
}
