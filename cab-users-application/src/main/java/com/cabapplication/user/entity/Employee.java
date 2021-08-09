package com.cabapplication.user.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Builder
@Document(collection = "EmployeeDetails")
public class Employee {
	
	@Id
	String employeeId;
	
	String employeeName;
	String employeeMail;
	long phoneNumber;
	
	int isAdmin;
	int isBlocked;
	LocalDateTime blockedDate;
	String domain;
	String domainLead;
	String projectName;
	String projectLead;
	String createdBy;
	LocalDateTime createdDate;
	String modifiedBy;
	LocalDateTime modifiedDate;
	int isDeleted;
	
	
}
