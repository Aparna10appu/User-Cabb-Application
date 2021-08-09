package com.cabapplication.user.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabapplication.user.bl.MyRequestBL;
import com.cabapplication.user.bo.RequestBO;
import com.cabapplication.user.exception.handler.GlobalExceptionHandler;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/user/myrequest")

public class MyRequestController {

	
	
	
	
		@Autowired
		private MyRequestBL myrequestBL;
		
		@Autowired
		GlobalExceptionHandler globalExceptionHandler;
		
		
		//For getting the BookingRequest based on EmployeeId
	    @GetMapping(path = "/employeedetails/{id}")
	    public ResponseEntity<List<RequestBO>> getEmployeeDetails(@PathVariable("id") String employeeId, HttpServletRequest request) throws IOException
	    {
	        try {
				List<RequestBO> myRequest = this.myrequestBL.getHistoryTrips(employeeId);
				
				return ResponseEntity.status(HttpStatus.OK).body(myRequest);
			} catch (Exception e) {
				globalExceptionHandler.writeToExceptionFile(e, request);
				e.printStackTrace();
			}
			return null;
	    
	    }
	    
	    
	    
//	    @GetMapping(path = "/tripcabinfo/{dateOfTravel}")
//		public ResponseEntity<List<TripCabInfo>> getAllDates(@PathVariable("dateOfTravel")LocalDate dateOfTravel)
//	{
//			List<TripCabInfo> details = this.myrequestBL.getDate(dateOfTravel);
//			
//		return ResponseEntity.status(HttpStatus.OK).body(details);
//		}
				
}
