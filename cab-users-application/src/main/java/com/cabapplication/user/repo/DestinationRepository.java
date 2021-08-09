package com.cabapplication.user.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cabapplication.user.entity.Destination;

public interface DestinationRepository extends MongoRepository<Destination, Integer> {

	@Query(value = "{isDeleted:0}")
	List<Destination> findByIsDeleted();

}
