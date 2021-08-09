package com.cabapplication.user.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "DriverInfo")
public class Driver {

	@Id
	Long driverId;
	String driverName;
	String password;
	Long driverNumber;
	String licenseNumber;
    LocalDate expiryDate; 
    String createdBy;
	LocalDateTime createdDate;
	String modifiedBy;
	LocalDateTime modifiedDate;
	int isDeleted;
}
