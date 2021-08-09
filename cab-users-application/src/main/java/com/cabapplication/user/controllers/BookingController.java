package com.cabapplication.user.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabapplication.user.bl.BookingBL;
import com.cabapplication.user.bo.BookingRequestDTO;
import com.cabapplication.user.entity.BookingRequest;
import com.cabapplication.user.entity.Destination;
import com.cabapplication.user.entity.Source;
import com.cabapplication.user.exception.handler.GlobalExceptionHandler;
import com.cabapplication.user.repo.BookingRepository;
import com.cabapplication.user.status.CustomStatus;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/user/booking/")
public class BookingController {
	
	@Autowired
	private BookingBL bookingBl;
	
	@Autowired
	private GlobalExceptionHandler globalExceptionHandler;

	
//	//For getting Booking request
//	@GetMapping(path = "/bookings")
//	public ResponseEntity<List<BookingRequest>> getBookings()
//	{
//		List<BookingRequest> requests = this.bookingBl.getBookingRequests();
//		
//		return ResponseEntity.status(HttpStatus.OK).body(requests);
//	}
	
//--------------------------------------------------------------------------------------------

	//For storing BookingRequest
	@PostMapping(path = "/bookacab")
	public ResponseEntity<BookingRequest> storeBookingrequest(@RequestBody BookingRequestDTO request, HttpServletRequest httpRequest) throws Exception
	{
		try {
			//Validating whether the user has already made a booking or not
			
			BookingRequest requestValidate = this.bookingBl.validateBooking(request.getEmployeeId());
			if(requestValidate!=null) 
			{
				if(requestValidate.getStatus().equalsIgnoreCase("Booked"))
				{
					throw new Exception("Already booked");
				}
				else if(requestValidate.getStatus().equalsIgnoreCase("Ongoing") || requestValidate.getStatus().equalsIgnoreCase("Assigned"))
				{
					throw new Exception("Already Booked and cab has been assigned");
				}
			}

			//Storing request
			
			BookingRequest savedRequest = this.bookingBl.storeBookingRequest(request);
			
			if(savedRequest == null) {
				
				return ResponseEntity.status(CustomStatus.TIMEOUT).body(null);
			}
			
					
			return ResponseEntity.status(HttpStatus.OK).body(savedRequest);
		} catch (Exception e) {
			globalExceptionHandler.writeToExceptionFile(e, httpRequest);
			e.printStackTrace();
		}
		return null;
	}
	
//-------------------------------------------------------------------------------------------------------------------------------
	
	//For fetching all the destinations
	@GetMapping(path = "/destinations")
	public ResponseEntity<List<Destination>> fetchDestinationDetails(HttpServletRequest httpRequest) throws IOException
	{
		List<Destination> destinations;
		try {
			destinations = this.bookingBl.fetchDestinationDetails();
			return ResponseEntity.status(HttpStatus.OK).body(destinations);
		} catch (Exception e) {
			globalExceptionHandler.writeToExceptionFile(e, httpRequest);
			e.printStackTrace();
		}
		return null;
		
	}
	
//----------------------------------------------------------------------------------------------------------------------------------------
	
	//For fetching all the sources
	@GetMapping(path = "/sources")
	public ResponseEntity<List<Source>> fetchSourceDetails(HttpServletRequest httpRequest) throws IOException
	{
		try {
			List<Source> sources = this.bookingBl.fetchSourceDetails();
			
			return ResponseEntity.status(HttpStatus.OK).body(sources);
		} catch (Exception e) {
			globalExceptionHandler.writeToExceptionFile(e, httpRequest);
			e.printStackTrace();
		}
		return null;
		
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------
	
	//For Canceling the Ride
	@PutMapping(path = "/cancel/{id}")
	public ResponseEntity<BookingRequest> cancelTheRide(@PathVariable("id") long bookingId,HttpServletRequest httpRequest) throws IOException
	{
		try {
			BookingRequest canceledReq = this.bookingBl.cancelTheRide(bookingId);
			
			if(canceledReq==null) {
				return ResponseEntity.status(CustomStatus.INPROGRESS).body(null);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(canceledReq);
		} catch (Exception e) {
			globalExceptionHandler.writeToExceptionFile(e, httpRequest);
			e.printStackTrace();
		}
		return null;
		
	}
	
//------------------------------------------------------------------------------------------------------------------------------
	
	
	//For validating Whether the user has already booked or not
	@GetMapping(path = "/validate/{id}")
	public ResponseEntity<BookingRequest> validateBooking(@PathVariable("id") String employeeId,HttpServletRequest httpRequest) throws Exception
	{
		
		try {
			BookingRequest request = this.bookingBl.validateBooking(employeeId);
			if(request!=null) 
			{
				if(request.getStatus().equalsIgnoreCase("booked"))
				{
					return ResponseEntity.status(CustomStatus.PENDING).body(request);
				}
				else if(request.getStatus().equalsIgnoreCase("Ongoing") || request.getStatus().equals("Assigned"))
				{
					return ResponseEntity.status(CustomStatus.INPROGRESS).body(request);
				}
			}
			
			
			return ResponseEntity.status(HttpStatus.OK).body(request);
		} catch (Exception e) {
			globalExceptionHandler.writeToExceptionFile(e, httpRequest);
			e.printStackTrace();
		}
		return null;
	}
	
//-----------------------------------------------------------------------------------------------------------------------------------------
	
//	@Autowired
//	BookingRepository repo;
//	
//	@GetMapping(path = "/filters")
//	public List<BookingRequest> filter(@RequestBody BookingRequest book)
//	{
//		//Criteria c = Criteria.where(getBookingTime()).alike(Example.of(book));
//		return this.repo.findAll(Example.of(book));
//	} LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))+" "+LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
	
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@GetMapping(path = "/time")
	public ResponseEntity<String> getBookingTime(HttpServletRequest httpRequest) throws IOException
	{
		try {
			String serverTime = this.bookingBl.getBookingTime();
			return ResponseEntity.status(HttpStatus.OK).body(serverTime);
		} catch (Exception e) {
			globalExceptionHandler.writeToExceptionFile(e, httpRequest);
			e.printStackTrace();
		}
		return null;
		
	}
		
	
}
