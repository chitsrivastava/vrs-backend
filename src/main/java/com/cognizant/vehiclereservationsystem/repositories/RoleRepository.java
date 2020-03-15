package com.cognizant.vehiclereservationsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.vehiclereservationsystem.models.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
Role findByRoleId(long roleId);
}
