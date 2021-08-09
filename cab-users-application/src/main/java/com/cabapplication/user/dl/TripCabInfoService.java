package com.cabapplication.user.dl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabapplication.user.entity.TripCabInfo;
import com.cabapplication.user.repo.TripCabInfoRepository;

@Service(value ="tripCabInfoService")
public class TripCabInfoService {

	@Autowired
	private TripCabInfoRepository repository;
	
	public TripCabInfo saveTrip(TripCabInfo cabInfo) {
		
		return this.repository.save(cabInfo);
	}
	
	public TripCabInfo findByTripCabID(long tripCabID) {
		
		return this.repository.findByTripCabId(tripCabID);
	}
}
