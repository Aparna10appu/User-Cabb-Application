package com.cabapplication.user.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "BookingRequest")
public class BookingRequest {

	@Id
	long bookingId;

	String employeeId;
	String employeeName;
	String source;
	String destination;
	String dropPoint;
	LocalTime bookingTime;
	LocalDate dateOfTravel;
	LocalTime timeSlot;
	int addedManually;
	long tripCabId;
	LocalTime startTime;
	LocalTime reachedTime;
	String complaintDescription;
	String remarks;
	String status;
	String createdBy;
	LocalDateTime createdDate;
	String modifiedBy;
	LocalDateTime modifiedDate;
	int isDeleted;

}
