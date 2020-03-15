package com.cognizant.vehiclereservationsystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.vehiclereservationsystem.models.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

	public List<Vehicle> findByName(String name);

	public List<Vehicle> findByAvailability(boolean b);

	public List<Vehicle> findByNumber(String number);
}
