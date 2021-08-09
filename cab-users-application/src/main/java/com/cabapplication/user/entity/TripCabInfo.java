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
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "TripCabInfo")
public class TripCabInfo {

	@Id
	Long tripCabId;
	String cabNumber;
	String cabModel;
	Long driverId;
	String source;
	String destination;
	LocalDate dateOfTravel;
	LocalTime timeSlot;
	int totalSeats;
	int remainingSeats;
	int allocatedSeats;
	String status;
	LocalTime startTime;
	LocalTime endTime;
	String createdBy;
	LocalDateTime createdDate;
	String modifiedBy;
	LocalDateTime modifiedDate;
	int isDeleted;
}
