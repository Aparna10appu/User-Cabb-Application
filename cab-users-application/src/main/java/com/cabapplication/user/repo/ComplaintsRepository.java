package com.cabapplication.user.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cabapplication.user.entity.Complaints;

public interface ComplaintsRepository extends MongoRepository<Complaints ,Integer> {
	

}
