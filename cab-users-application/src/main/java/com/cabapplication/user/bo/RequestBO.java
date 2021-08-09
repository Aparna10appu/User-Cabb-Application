package com.cabapplication.user.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.cabapplication.user.entity.BookingRequest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component

public class RequestBO {

	BookingRequest details;
//	LocalDateTime dateOfTravel;
	LocalDate dateOfTravel;
	
}
