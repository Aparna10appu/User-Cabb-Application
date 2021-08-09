package com.cabapplication.user.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cabapplication.user.entity.TripCabInfo;

public interface TripCabInfoRepository extends MongoRepository<TripCabInfo, Long> {

	TripCabInfo findByTripCabId(long tripCabID);
}
