package com.cognizant.vehiclereservationsystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.vehiclereservationsystem.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmail(String email);
	public List<User> findByIsApprovedAndVendorId(boolean isApproved,long vendorId);
	public List<User> findByIsApprovedAndVendorIdNot(boolean isApproved, long vendorId);
}
