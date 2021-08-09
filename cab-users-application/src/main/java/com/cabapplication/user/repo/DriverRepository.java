package com.cabapplication.user.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cabapplication.user.entity.Driver;

public interface DriverRepository extends MongoRepository<Driver, Long> {

	Driver findByDriverId(long driverID);
}
