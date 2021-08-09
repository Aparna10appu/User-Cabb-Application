package com.cabapplication.user.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabapplication.user.bl.OnGoingTripSheetBL;
import com.cabapplication.user.bo.BookingRequestDTO;
import com.cabapplication.user.exception.handler.GlobalExceptionHandler;

@RestController
@RequestMapping(path="/api/v1/")
@CrossOrigin(origins = "*")
public class OnGoingTripSheetController {

	@Autowired
	private OnGoingTripSheetBL service;
	
	@Autowired
	GlobalExceptionHandler globalExceptionHandler;
	
	@GetMapping(path = "/requests/{employeeID}")
	public List<BookingRequestDTO> getOnGoingTripSheet(@PathVariable("employeeID") String employeeID, HttpServletRequest request) throws IOException {

		//Calls the getTripSheet() method of service layer
		try {
			return this.service.getOnGoingTripSheet(employeeID);
		} catch (Exception e) {
			globalExceptionHandler.writeToExceptionFile(e, request);
			e.printStackTrace();
		}
		return null;
	}

}
