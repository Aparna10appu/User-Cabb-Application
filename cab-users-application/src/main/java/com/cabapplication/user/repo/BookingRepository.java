package com.cabapplication.user.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cabapplication.user.entity.BookingRequest;

public interface BookingRepository extends MongoRepository<BookingRequest, Long> {

	@Query(value = "{employeeId:?0 , status:{$nin:[null , Cancelled , Reached , Noshow ]}}")
	BookingRequest findBookingRequestByEmployeeId(String id);
	
	@Query(value = "{employeeId : ?0 , status:{$in:[Assigned, Ongoing]}}")
	// Fetches all the employee's requests where status is not equal to any of the values defined in the query array
	Optional<BookingRequest> findByEmployeeID(String employeeID); 
	
	@Query(value = "{tripCabId : ?0,status:{$ne:Cancelled}}")
	// Fetches all the requests of employees having same tripCabID as the user
	List<BookingRequest> findRequestByTripCabID(Long tripCabID);
	
	List<BookingRequest> findByTripCabId(long tripCabId);

	@Query(value = "{employeeId : ?0 , status:{$nin : [Assigned, Ongoing, Booked]}}")  
	List<BookingRequest> getCompletedTripDetails(String employeeId);
	
	BookingRequest findByBookingId(long bookingId);

}
