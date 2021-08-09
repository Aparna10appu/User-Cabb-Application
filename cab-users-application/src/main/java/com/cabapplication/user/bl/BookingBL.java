package com.cabapplication.user.bl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.cabapplication.user.bo.BookingRequestDTO;
import com.cabapplication.user.dl.BookingDL;
import com.cabapplication.user.entity.BookingRequest;
import com.cabapplication.user.entity.Destination;
import com.cabapplication.user.entity.Email;
import com.cabapplication.user.entity.Source;

@Component
public class BookingBL {
	
	@Autowired
	private BookingDL bookingDl;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	String fromMail;
	
	Email email = new Email();

	
	//For getting Booking request
	public List<BookingRequest> getBookingRequests()
	{
		return this.bookingDl.getBookingRequests();
	}
	
	//For storing incoming Booking Request
	public BookingRequest storeBookingRequest(BookingRequestDTO request) throws Exception
	{
		BookingRequest bookingMade = this.bookingDl.storeBookingRequest(request);
		
		
//		if(bookingMade != null) {
//			
//			Employee employee = this.bookingDl.getEmployee(bookingMade.getEmployeeId());
//			
//			email.setFrom(fromMail);
//			email.setTo(employee.getEmployeeMail());
//			email.setSubject("Booking Confirmation");
//			email.setMessage("Hey! " +employee.getEmployeeName()+ "\n" + 
//	  					     " You've booked a cab \n" + 
//	  					     " From: " + bookingMade.getSource()+ "\n" + 
//	  					     " To: " +bookingMade.getDropPoint()+ "\n" + 
//	  					     " Time Slot: " +bookingMade.getTimeSlot()+ "\n" + 
//	  						 " at: " + bookingMade.getBookingTime());
//			//sendEmail();
//		}
		
		 return bookingMade;
	}
	
	//For fetching all the destinations
	public List<Destination> fetchDestinationDetails()
	{
		List<Destination> destinations = this.bookingDl.fetchDestinationDetails();	
		
		if(destinations.isEmpty()) {
			throw new NoSuchElementException();
		}
		
		return destinations;
	}
	
	//For fetching all the sources
	public List<Source> fetchSourceDetails()
	{
		List<Source> sources = this.bookingDl.fetchSourceDetails();	
		
		if(sources.isEmpty()) {
			throw new NoSuchElementException();
		}
		
		return sources;
	}
	
	//For Canceling the Ride
	public BookingRequest cancelTheRide(long bookingId)
	{
		BookingRequest bookingCancelled = this.bookingDl.cancelTheRide(bookingId);
		
//		if(bookingCancelled != null) {
//			
//			Employee employee = this.bookingDl.getEmployee(bookingCancelled.getEmployeeId());
//			
//			email.setFrom(fromMail);
//			email.setTo(employee.getEmployeeMail());
//			email.setSubject("Booking Cancelled");
//			email.setMessage("Hey! " +employee.getEmployeeName()+ "\n" + 
//	  					     " You've cancelled your booking \n" + 
//	  					     " From: " + bookingCancelled.getSource()+ "\n" + 
//	  					     " To: " +bookingCancelled.getDropPoint()+ "\n" + 
//	  					     " Time Slot: " + bookingCancelled.getTimeSlot());
//			//sendEmail();
//		}
		
		return bookingCancelled;
		
	}
	
	//For validating Whether the user has already booked or not
	public BookingRequest validateBooking(String employeeId) throws Exception
	{
		
		if(employeeId==null) {
			throw new Exception("Invalid employeeId");
		}
		
		return this.bookingDl.validateBooking(employeeId);
		
	}
	
	//For sending confirmation email
	public void sendEmail() {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom(email.getFrom());
		message.setTo(email.getTo());
		message.setSubject(email.getSubject());
		message.setText(email.getMessage());
		
		mailSender.send(message);
	}

	public String getBookingTime() {
		
		return this.bookingDl.getBookingTime();
	}

}
