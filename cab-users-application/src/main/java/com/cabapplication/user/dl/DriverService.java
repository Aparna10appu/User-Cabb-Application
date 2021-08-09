package com.cabapplication.user.dl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabapplication.user.entity.Driver;
import com.cabapplication.user.repo.DriverRepository;

@Service(value = "driverService")
public class DriverService {

	@Autowired
	private DriverRepository repository;
	
	public Driver saveDriverInfo(Driver driverInfo) {
		
		return this.repository.save(driverInfo);
	}
	
	public Driver getDriverInfoByID(long driverID) {
		
		return this.repository.findByDriverId(driverID);
	}
}
