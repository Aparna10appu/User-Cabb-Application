package com.cabapplication.user.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabapplication.user.bl.CompletedTripBL;
import com.cabapplication.user.bo.CompletedTripBO;
import com.cabapplication.user.entity.BookingRequest;
import com.cabapplication.user.entity.Complaints;
import com.cabapplication.user.exception.handler.GlobalExceptionHandler;
import com.cabapplication.user.repo.BookingRepository;
import com.cabapplication.user.status.CustomStatus;

@RestController
@RequestMapping(path = "/api/v1")
public class CompletedTripController {

	@Autowired
	CompletedTripBL complBL;
	@Autowired
	BookingRepository repo;
	
	@Autowired
	GlobalExceptionHandler globalExceptionHandler;
	
	
		// get CompletedTrip details.
	@GetMapping(path = "/completedTrip/{tripCabId}")
	public ResponseEntity<CompletedTripBO> getCompletedTrip(@PathVariable("tripCabId") long tripCabId, HttpServletRequest httpRequest) throws IOException {
		try {
			CompletedTripBO completedTrip = this.complBL.getCompletedTrip(tripCabId);
			return ResponseEntity.status(HttpStatus.OK).body(completedTrip);
		} catch (Exception e) {
			globalExceptionHandler.writeToExceptionFile(e, httpRequest);
			e.printStackTrace();
		}
		return null;
	}
	
	
		//get List of Complaints
	@GetMapping(path = "/complaints")
	public ResponseEntity<List<Complaints>> getComplaints(HttpServletRequest httpRequest) throws IOException {
		try {
			List<Complaints> complaints = this.complBL.getComplaints();
			return ResponseEntity.status(HttpStatus.OK).body(complaints);
		} catch (Exception e) {
			globalExceptionHandler.writeToExceptionFile(e, httpRequest);
			e.printStackTrace();
		}
		return null;
	}
	
	
		// update the complaints in DB
	@PutMapping(path = "/updateComplaints/{bookingId}/{complaint}")
	public ResponseEntity<BookingRequest> updateComplaints(@PathVariable("complaint") String complaintDes,
			@PathVariable("bookingId") long bookingId, HttpServletRequest httpRequest) throws IOException {

		try {
			BookingRequest updatedComplaints = this.complBL.updateComplaints(bookingId, complaintDes);
			BookingRequest updateComplaints = this.repo.findByBookingId(bookingId);
			
			// get the complaints in DB
			String cmpDes = updateComplaints.getComplaintDescription();
			
			BookingRequest updatedCompl = null;

			if (cmpDes == null) {
				
				// send mail to User ---> Admin or HR
//			complBL.sendEmail(bookingId, complaintDes); // this line is responsible for the mail Uncomment the line to send the mail function.
				//System.out.println("hello");
				updateComplaints.setComplaintDescription(complaintDes);
				updatedCompl = repo.save(updateComplaints);

				

			} else {
			return  ResponseEntity.status(CustomStatus.ALREADY_REGISTERED).body(null);// Usage of custom status.
}

			return ResponseEntity.status(HttpStatus.CREATED).body(updatedComplaints);
		} catch (Exception e) {
			globalExceptionHandler.writeToExceptionFile(e, httpRequest);
			e.printStackTrace();
		}
		return null;
	}
}
