package com.cognizant.vehiclereservationsystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.vehiclereservationsystem.models.Coupon;
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
	public Coupon findByCode(String code);
}
