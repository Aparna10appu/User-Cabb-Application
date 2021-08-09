package com.cabapplication.user.dl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabapplication.user.bo.BookingRequestDTO;
import com.cabapplication.user.entity.BookingRequest;
import com.cabapplication.user.entity.Driver;
import com.cabapplication.user.entity.TripCabInfo;
import com.cabapplication.user.repo.BookingRepository;
import com.cabapplication.user.repo.DriverRepository;
import com.cabapplication.user.repo.TripCabInfoRepository;

@Service(value = "tripSheetService")
public class TripSheetService {

	@Autowired
	private BookingRepository repository;
	
	@Autowired
	private TripCabInfoRepository cabRepo;
	
	@Autowired
	private DriverRepository driverRepo;
	
	public BookingRequest save(BookingRequest entity) {
		
		return this.repository.save(entity);
	}
	
	public List<BookingRequestDTO> getTripSheet(String employeeID) {
		
		Optional<BookingRequest> request = this.repository.findByEmployeeID(employeeID); // Returns the request details of an employee/user
		
		if(request.isEmpty()) {
			return null;
		}
		
		long tripCabID = request.get().getTripCabId(); // Get's the TripCabID of the employee
		return this.getAllTripSheet(tripCabID); 
	}
	
	public List<BookingRequestDTO> getAllTripSheet(Long tripCabID) {

		// Fetches all the requests of employees having the same TripCabID as the user
		List<BookingRequest> OngoingTripSheet = this.repository.findRequestByTripCabID(tripCabID); 
		TripCabInfo cabInfo = this.cabRepo.findByTripCabId(tripCabID); // Fetches the Cab details using TripCabID
		
		long driverID = cabInfo.getDriverId(); // Fetches the driverID of that particular trip
		Driver driverInfo = this.driverRepo.findByDriverId(driverID); // Fetches the driver details using driverID
		
		List<BookingRequestDTO> ongoingListBO =  new ArrayList<>();
		// Sets the cabDetails and driverDetails fetched using tripCabID and driverID
		for(BookingRequest request : OngoingTripSheet) {
			
			BookingRequestDTO eachDto = new BookingRequestDTO();
			eachDto.setBookingId(request.getBookingId());
			eachDto.setEmployeeId(request.getEmployeeId());
			eachDto.setEmployeeName(request.getEmployeeName());
			eachDto.setSource(request.getSource());
			eachDto.setDestination(request.getDestination());
			eachDto.setDropPoint(request.getDropPoint());
			eachDto.setBookingTime(request.getBookingTime());
			eachDto.setTimeSlot(request.getTimeSlot());
			eachDto.setTripCabId(request.getTripCabId());
			eachDto.setStartTime(request.getStartTime());
			eachDto.setReachedTime(request.getReachedTime());
			eachDto.setStatus(request.getStatus());
			eachDto.setDriverDetails(driverInfo);
			eachDto.setCabDetails(cabInfo);
			
			ongoingListBO.add(eachDto);
		}
		
		return ongoingListBO;
	}	
}
