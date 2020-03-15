package com.cognizant.vehiclereservationsystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cognizant.vehiclereservationsystem.models.Booking;
import com.cognizant.vehiclereservationsystem.models.Vehicle;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByVehicle(Vehicle vehicle);

}
