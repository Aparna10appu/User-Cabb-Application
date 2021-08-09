package com.cabapplication.user.dl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cabapplication.user.bo.RequestBO;
import com.cabapplication.user.entity.BookingRequest;
import com.cabapplication.user.entity.TripCabInfo;
import com.cabapplication.user.repo.BookingRepository;
import com.cabapplication.user.repo.TripCabInfoRepository;

@Component
public class MyRequestDL {
	
	@Autowired
	private BookingRepository bookingrepo;
	
	@Autowired
	private TripCabInfoRepository triprepo;

  
	public  List<RequestBO>  getHistoryOfTrips(String employeeId) {
        List<RequestBO> resultobj= new ArrayList<RequestBO>();
		List<BookingRequest> details =this.bookingrepo.getCompletedTripDetails(employeeId);
		 for(BookingRequest eachRequest:details) {
//			 resultobj.add(new RequestBO(eachRequest,getDateOfTravel(eachRequest.getTripCabId())));
			 resultobj.add(new RequestBO(eachRequest, eachRequest.getDateOfTravel()));
		 }
		 
		 return resultobj;
	}
		 
	private LocalDate getDateOfTravel(long tripID) {

		TripCabInfo tripInfoObj = triprepo.findByTripCabId(tripID);
		return tripInfoObj.getDateOfTravel();

	}
		   
		//return this.triprepo.findByTripCabId(tripCabId);
	}



//	    public List<BookingRequest> getEmployeeDetails(String status) {
//	        
//	        return this.bookingRepo.findEmployeeDetailsByStatus(status);
//	    }
	// 






	


