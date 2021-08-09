package com.cabapplication.user.bo;

import java.time.LocalTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
public class TripSheetBO {
	String employeeId;
	String emlpoyeeName;
	//String employeeEmail;
	String dropPoint;
	LocalTime reachedTime;
}
