package com.cabapplication.user.bl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cabapplication.user.bo.BookingRequestDTO;
import com.cabapplication.user.dl.TripSheetService;

@Component
public class OnGoingTripSheetBL {

	@Autowired
	private TripSheetService service;
	
	public List<BookingRequestDTO> getOnGoingTripSheet(String employeeID) {
		
		return this.service.getTripSheet(employeeID);
	}
}
