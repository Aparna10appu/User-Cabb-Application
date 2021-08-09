
        
        
        var xhrSrc = new XMLHttpRequest();

        var xhrDest = new XMLHttpRequest();
        
        //On Load function tp call fetchSource() and fettchDestination()

        window.onload = validateBooking;

/* -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        
		var employee = JSON.parse(sessionStorage.getItem("employee"));
		
        var empId = employee.employeeId;
    	var empName = employee.employeeName;
	
		var jwtToken = sessionStorage.getItem("jwtToken")
    	
    	var time;
    								//seconds must be finalized
    	setInterval('validateBooking()',200000); //for every 10000ms, validateBooking() gets invoked
    	
    	var validateXhr = new XMLHttpRequest();
        
        //Validate whether the User has already made booking or not
	
        function validateBooking(){
			
			try {

				validateXhr.open("GET", "http://localhost:8080/user/booking/validate/" + empId, true);
				validateXhr.onreadystatechange = validationProcessResponse;
				//			validateXhr.setRequestHeader("Authorization", jwtToken);
				validateXhr.send(null);

				if (validateXhr.readyState == 4 && validateXhr.status == 200) {
					fetchSource();
					fetchDestination();
					// getTime();
				}
			}
			catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
		}
		
		var validationResponse;

		function validationProcessResponse(){
			
			try {
				//Not made Booking already

				if (validateXhr.readyState == 4 && validateXhr.status == 200) {
					fetchSource();
					fetchDestination();
					// getTime();
				}

				//Cab has been assigned

				if(validateXhr.readyState == 4 && validateXhr.status == 228){

					var responseValidate = JSON.parse(validateXhr.responseText);
					window.location.href = "cab-app-ongoingtrip.html?tripCabId=" + responseValidate.tripCabId + "?bookingId=" + responseValidate.bookingId;
				}

				//Already booked - yet to get assigned to a cab

				if(validateXhr.readyState == 4 && validateXhr.status == 227){

					validationResponse = JSON.parse(validateXhr.responseText);

					var srcOpt = document.createElement('option');
					srcOpt.innerText = validationResponse.source;
					srcOpt.selected = "selected";
					document.querySelector('#source-opt').appendChild(srcOpt);

					var destOpt = document.createElement('option');
					destOpt.innerText = validationResponse.destination;
					destOpt.selected = "selected";
					document.querySelector('#destination-opt').appendChild(destOpt);

					var dropOpt = document.createElement('option');
					dropOpt.innerText = validationResponse.dropPoint;
					dropOpt.selected = "selected";
					document.querySelector('#dropPoint-opt').appendChild(dropOpt);

					var timeOpt = document.createElement('option');
					var timeslot = validationResponse.timeSlot; //22:30:00
					timeOpt.innerText = timeFormatTo12Hr(timeslot, 0);
					timeOpt.selected = "selected";
					document.querySelector('#timeSlot-opt').appendChild(timeOpt);


					document.getElementById("screen-title").innerHTML = "Ongoing Trip";
					document.getElementById('clear-btn').style.display = "none";
					document.getElementById('bookACab-btn').style.display = "none";
					document.getElementById('cancel-btn').style.display = "block";

					document.querySelector('#source-opt').disabled = true;
					document.querySelector('#destination-opt').disabled = true;
					document.querySelector('#dropPoint-opt').disabled = true;
					document.querySelector('#timeSlot-opt').disabled = true;
				}
			}
			catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
			
		}
		
		//Validation ends here
/* -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/		

        //Fetches sources

        function fetchSource(){
			
			try {

				xhrSrc.open("GET", "http://localhost:8080/user/booking/sources", true);
				xhrSrc.onreadystatechange = processResponseSource;
				//			xhrSrc.setRequestHeader("Authorization", jwtToken);
				xhrSrc.send(null);
			}
			catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}

        }

        
        function processResponseSource(){
			
			try {
				if (xhrSrc.readyState == 4) {

					var sources = JSON.parse(xhrSrc.responseText);

					var clearSource = document.getElementById("source-opt");
					var srcLength = clearSource.options.length;

					for (i = srcLength - 1; i > 0; i--) {
						clearSource.options[i] = null;
					}

					for (var i = 0; i < sources.length; i++) {

						var opt = document.createElement("option");


						opt.innerHTML = sources[i].source;

						document.getElementById("source-opt").options.add(opt);

					}

				}
			}
			catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
        }

        //Fetch Source - Ends
        
/* -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

        //Fetches Destinations

        var destinations;

        function fetchDestination(){

			try {
				xhrDest.open("GET", "http://localhost:8080/user/booking/destinations", true);
				xhrDest.onreadystatechange = processResponseOfDestination;
				//			xhrDest.setRequestHeader("Authorization", jwtToken);
				xhrDest.send(null);
			}
			catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}

        }

        function processResponseOfDestination(){
			try {
				if (xhrDest.readyState == 4 && xhrDest.status == 200) {

					destinations = JSON.parse(xhrDest.responseText);

					var clearDestination = document.getElementById("destination-opt");
					var destLength = clearDestination.options.length;

					for (i = destLength - 1; i > 0; i--) {
						clearDestination.options[i] = null;
					}

					for (var i = 0; i < destinations.length; i++) {

						var opt = document.createElement("option");


						opt.innerHTML = destinations[i].destination;

						document.getElementById("destination-opt").options.add(opt);

					}

				}
			}
			catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
        }

        //Fetches Destinations - Ends
        
/* -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

        //For Populating DropPoints and TimeSlots based on Destination

        document.getElementById("destination-opt").addEventListener('change',function(){
        	
			try {
				//Clearing the options of DropPoint drop down 

				var clearDropPoint = document.getElementById("dropPoint-opt");
				var dropOptLength = clearDropPoint.options.length;

				for (i = dropOptLength - 1; i > 0; i--) {
					clearDropPoint.options[i] = null;
				}

				//Clearing the options of TimeSlot drop down

				var clearTimeSlot = document.getElementById("timeSlot-opt");
				var timeOptLength = clearTimeSlot.options.length;

				for (i = timeOptLength - 1; i > 0; i--) {
					clearTimeSlot.options[i] = null;
				}

				var selectedDestination = document.querySelector('#destination-opt').value;


				for (var i = 0; i < destinations.length; i++) {


					if ((destinations[i].destination) == selectedDestination) {

						for (var j = 0; j < destinations[i].dropPoints.length; j++) {

							//Binding options of DropPoint

							var dropPointOption = document.createElement("option");

							dropPointOption.innerHTML = destinations[i].dropPoints[j].dropPoint;

							document.getElementById("dropPoint-opt").options.add(dropPointOption);


						}

						var curDate = new Date();
						var curHour = curDate.getHours();
						var curMin = curDate.getMinutes();
						var curSec = curDate.getSeconds();
						//alert(curHour);
						for (var k = 0; k < destinations[i].timeSlots.length; k++) {

							//Binding options of TimeSlots

							var timeSlotOption = document.createElement("option");

							var slot = destinations[i].timeSlots[k].timeSlot; //22:30:00
							var slotSplitted = slot.split(":"); //[22,30,00]
							slotHour = slotSplitted[0];


							if (curHour < slotHour) {

								if (slotHour < 12) {

									if (slotHour == 00) {
										timeSlotOption.innerHTML = "12" + ":" + slotSplitted[1] + " AM";
									}
									else {
										timeSlotOption.innerHTML = slotHour + ":" + slotSplitted[1] + " AM";
									}

								}
								else {
									slotHour = slotHour - 12;
									if (slotHour == 0) {
										timeSlotOption.innerHTML = "12" + ":" + slotSplitted[1] + " PM";
									}
									else if (slotHour < 10) {
										timeSlotOption.innerHTML = "0" + slotHour + ":" + slotSplitted[1] + " PM";
									}
									else {
										timeSlotOption.innerHTML = slotHour + ":" + slotSplitted[1] + " PM";
									}

								}


								document.getElementById("timeSlot-opt").options.add(timeSlotOption);

							}
							else if (slotHour < 12) {
								if (slotHour == 00) {
									timeSlotOption.innerHTML = "12" + ":" + slotSplitted[1] + " AM";
								}
								else {
									timeSlotOption.innerHTML = slotHour + ":" + slotSplitted[1] + " AM";
								}
								document.getElementById("timeSlot-opt").options.add(timeSlotOption);
							}
							else {
								if (curMin < slotSplitted[1]) {

									if (slotHour < 12) {

										if (slotHour == 00) {
											timeSlotOption.innerHTML = "12" + ":" + slotSplitted[1] + " AM";
										}
										else {
											timeSlotOption.innerHTML = slotHour + ":" + slotSplitted[1] + " AM";
										}

									}
									else {
										slotHour = slotHour - 12;
										if (slotHour == 0) {
											timeSlotOption.innerHTML = "12" + ":" + slotSplitted[1] + " PM";
										}
										else if (slotHour < 10) {
											timeSlotOption.innerHTML = "0" + slotHour + ":" + slotSplitted[1] + " PM";
										}
										else {
											timeSlotOption.innerHTML = slotHour + ":" + slotSplitted[1] + " PM";
										}

									}


									document.getElementById("timeSlot-opt").options.add(timeSlotOption);

								}
							}
						}
					}
				}
			}
			catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
        	
        	
        });
        
      //End of Populating DropPoints and TimeSlots
      
/* -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        
      //Validate for Empty Fields	
        
        function checkEmptyFields(){
        	
        	
			try {
				if (document.getElementById("source-opt").selectedIndex == 0) {
					alert("Select Source");
					return false;
				}

				if (document.getElementById("destination-opt").selectedIndex == 0) {
					alert("Select Destination");
					return false;
				}


				if (document.getElementById("dropPoint-opt").selectedIndex == 0) {
					alert("Select DropPoint");
					return false;
				}

				if (document.getElementById("timeSlot-opt").selectedIndex == 0) {
					alert("Select TimeSlot");
					return false;
				}


				bookARideButtonClicked();
			}
			catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
        }
      
      //Validate for Empty Fields - Ends
      
/* -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
      
      //Booking Confirmation PopUp
      
     
      
      var sourceSelected;
      var destinationSelected;
      var dropPointSelected;
      var timeSlotSelected
      var currentDate;
      
       function addDays(date, days) {
  			var result = new Date(date);
  			result.setDate(result.getDate() + days);
 			 return result;
			}
			
    	  var date = new Date();
      
      function bookARideButtonClicked(){
    	 
		  try {
			  //Fetching server time

			  var xhrTime = new XMLHttpRequest();

			  xhrTime.open("GET", "http://localhost:8080/user/booking/time", false);
			  xhrTime.onreadystatechange = responseTime;
			  //		xhrTime.setRequestHeader("Authorization", jwtToken);
			  xhrTime.send(null);


			  function responseTime() {
				  if (xhrTime.readyState == 4 && xhrTime.status == 200) {

					  time = xhrTime.responseText;
					  //alert(time);
				  }
			  }

			  $("#popUp-content").empty();

			  sourceSelected = document.querySelector('#source-opt').value;
			  destinationSelected = document.querySelector('#destination-opt').value;
			  dropPointSelected = document.querySelector('#dropPoint-opt').value;
			  timeSlotSelected = document.querySelector('#timeSlot-opt').value;


			  var bookingTimeDiv = document.createElement('div');
			  bookingTimeDiv.className = " col-md-12 col-12 float-start confirm-booking-content";
			  time = time.split(" ");
			  //    	  var timeSplit = time[0].split(":");
			  //    	  var hours = timeSplit[0];

			  if (date.getDate() < 10) {
				  slotDate = "0" + date.getDate();
			  }
			  else {
				  slotDate = date.getDate();
			  }
			  if ((date.getMonth() + 1) < 10) {
				  slotMonth = "0" + (date.getMonth())
			  }
			  else {
				  slotMonth = (date.getMonth())
			  }
			  if (date.getHours() < 10) {
				  slotHour = "0" + date.getHours();
			  }
			  else {
				  slotHour = date.getHours();
			  }


			  if (timeSlotSelected.includes("AM")) {
				  //			if((Number(slotDate)+1)<10){
				  //				currentDate = "0"+(Number(slotDate)+1) + "-" + slotMonth + "-" +date.getFullYear();
				  //			}
				  //			else{
				  //				currentDate = (Number(slotDate)+1) + "-" + slotMonth + "-" +date.getFullYear();
				  //			}

				  var d = addDays(new Date(), 1);
				  if ((d.getDate()) < 10) {
					  if ((d.getMonth() + 1) < 10) {
						  currentDate = "0" + d.getDate() + "-" + "0" + (d.getMonth() + 1) + "-" + d.getFullYear();
					  }
					  else {
						  currentDate = "0" + d.getDate() + "-" + (d.getMonth() + 1) + "-" + d.getFullYear();
					  }
					  //currentDate = "0"+d.getDate() + "-" + (d.getMonth()+1) + "-" +d.getFullYear();

				  }
				  else {
					  //currentDate = d.getDate() + "-" + (d.getMonth()+1) + "-" +d.getFullYear();
					  if ((d.getMonth() + 1) < 10) {
						  currentDate = d.getDate() + "-" + "0" + (d.getMonth() + 1) + "-" + d.getFullYear();
					  }
					  else {
						  currentDate = d.getDate() + "-" + (d.getMonth() + 1) + "-" + d.getFullYear();
					  }
				  }

			  }
			  else {
				  var pmDate = addDays(new Date(), 0);
				  //currentDate = slotDate + "-" + slotMonth + "-" +date.getFullYear();
				  if (pmDate.getDate() < 10) {
					  if (pmDate.getMonth() < 10) {
						  currentDate = "0" + pmDate.getDate() + "-" + "0" + (pmDate.getMonth() + 1) + "-" + pmDate.getFullYear();
					  }
					  else {
						  currentDate = "0" + pmDate.getDate() + "-" + (pmDate.getMonth() + 1) + "-" + pmDate.getFullYear();
					  }
					  //currentDate = "0"+d.getDate() + "-" + (d.getMonth()+1) + "-" +d.getFullYear();

				  }
				  else {
					  //currentDate = d.getDate() + "-" + (d.getMonth()+1) + "-" +d.getFullYear();
					  if (pmDate.getMonth() < 10) {
						  currentDate = pmDate.getDate() + "-" + "0" + (pmDate.getMonth() + 1) + "-" + pmDate.getFullYear();
					  }
					  else {
						  currentDate = pmDate.getDate() + "-" + (pmDate.getMonth() + 1) + "-" + pmDate.getFullYear();
					  }
				  }
			  }

			  bookingTimeDiv.innerText = "Booking time: " + timeFormatTo12Hr(time[0], 0);



			  var sourceDiv = document.createElement('div');
			  sourceDiv.className = " col-md-12 col-12 float-start confirm-booking-content";
			  sourceDiv.innerText = "Source: " + sourceSelected;

			  var dropPointDiv = document.createElement('div');
			  dropPointDiv.className = " col-md-12 col-12 float-start confirm-booking-content";
			  dropPointDiv.innerText = "Drop Point: " + dropPointSelected;

			  var dateDiv = document.createElement('div');
			  dateDiv.className = " col-md-12 col-12 float-start confirm-booking-content";
			  dateDiv.innerText = "Date of Travel: " + currentDate;

			  var timeSlotDiv = document.createElement('div');
			  timeSlotDiv.className = " col-md-12 col-12 float-start confirm-booking-content";
			  timeSlotDiv.innerText = "Time Slot: " + timeSlotSelected;

			  var popUp = document.getElementById("popUp-content");

			  popUp.appendChild(bookingTimeDiv);
			  popUp.appendChild(sourceDiv);
			  popUp.appendChild(dropPointDiv);
			  popUp.appendChild(dateDiv);
			  popUp.appendChild(timeSlotDiv);


			  document.getElementById("bookACab-btn").setAttribute('data-target', '#confirmbooking');
		  }
		  catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
      }
      
     //Booking confirmation pop up ends here
      
/* -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
      
      //Posting Booking request using AJAX call
      
	  var xhrBooking = new XMLHttpRequest();
      var bookingTimeSlot;
      function okButtonClicked(){
    	  
		  try {
			  var splittedTimeSlot = timeSlotSelected.split(":");
			  minute = splittedTimeSlot[1].split(" ")[0];

			  if (splittedTimeSlot[1].includes("PM")) {
				  //seconds = splittedTimeSlot[2].split(" ");
				  //alert(Number(splittedTimeSlot[1]));
				  if (Number(splittedTimeSlot[0]) + 12 == 24) {
					  bookingTimeSlot = "12" + ":" + minute;
				  }
				  else {
					  splittedTimeSlotHour = Number(splittedTimeSlot[0]) + 12;
					  bookingTimeSlot = splittedTimeSlotHour + ":" + minute;
				  }

			  }
			  else {
				  seconds = splittedTimeSlot[1].split(" ");
				  if (Number(splittedTimeSlot[0]) == 12) {
					  bookingTimeSlot = "00" + ":" + minute;
				  }
				  else if (Number(splittedTimeSlot[0]) < 10) {
					  bookingTimeSlot = "0" + Number(splittedTimeSlot[0]) + ":" + minute;
				  }
				  else {
					  bookingTimeSlot = Number(splittedTimeSlot[0]) + ":" + minute;
				  }

			  }


			  //slotDate and bookingDate

			  //booking time
			  var dateBook = new Date();
			  if (dateBook.getDate() < 10) {
				  curDate = "0" + dateBook.getDate();
			  }
			  else {
				  curDate = dateBook.getDate();
			  }
			  if ((dateBook.getMonth() + 1) < 10) {
				  curMonth = "0" + (dateBook.getMonth() + 1)
			  }
			  else {
				  curMonth = (dateBook.getMonth() + 1)
			  }
			  if (dateBook.getHours() < 10) {
				  bookHour = "0" + dateBook.getHours();
			  }
			  else {
				  bookHour = dateBook.getHours();
			  }
			  if (dateBook.getMinutes() < 10) {
				  bookMin = "0" + dateBook.getMinutes();
			  }
			  else {
				  bookMin = dateBook.getMinutes();
			  }
			  if (dateBook.getSeconds() < 10) {
				  bookSec = "0" + dateBook.getSeconds();
			  }
			  else {
				  bookSec = dateBook.getSeconds();
			  }
			  var bookDate = curDate + "-" + curMonth + "-" + dateBook.getFullYear() + " " + bookHour + ":" + bookMin + ":" + bookSec;

			  //slot time
			  var dateSlot = currentDate + " " + bookingTimeSlot + ":00";


			  var request = { "employeeId": empId, "employeeName": empName, "source": sourceSelected, "destination": destinationSelected, "dropPoint": dropPointSelected, "timeSlot": bookingTimeSlot, "slotDate": dateSlot, "bookingDate": bookDate };

			  xhrBooking.open("POST", "http://localhost:8080/user/booking/bookacab", true);
			  xhrBooking.onreadystatechange = bookingResponse;

			  xhrBooking.setRequestHeader("Content-Type", "application/json");
			  //		  xhrBooking.setRequestHeader("Authorization", jwtToken);
			  xhrBooking.send(JSON.stringify(request));
		  }
		  catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
    	  
      }
      
      var bookedResponseObj;
      
      function bookingResponse(){
  		
		  try {
			  if (xhrBooking.readyState == 4 && xhrBooking.status == 200) {
				  validationResponse = JSON.parse(xhrBooking.responseText);
				  //alert(bookingResponseObj.bookingId);
				  document.getElementById("screen-title").innerHTML = "Ongoing Trip";
				  document.getElementById('clear-btn').style.display = "none";
				  document.getElementById('bookACab-btn').style.display = "none";
				  document.getElementById('cancel-btn').style.display = "block";

				  document.querySelector('#source-opt').disabled = true;
				  document.querySelector('#destination-opt').disabled = true;
				  document.querySelector('#dropPoint-opt').disabled = true;
				  document.querySelector('#timeSlot-opt').disabled = true;
			  }

			  if (xhrBooking.readyState == 4 && xhrBooking.status == 231) {

				  alert("You can't book a Cab! Invalid Time Slot Given (OR) You should book 20 mins before the required time slot!");
			  }
		  }
		  catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
  	}
  	
  	//Booking made
  	
/* -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/  
      
      //Cancel the ride
      
      var cancelXhr = new XMLHttpRequest();
      
      function cancelTheRideButtonClicked(){
    	  
		  try {
			  cancelXhr.open("PUT", "http://localhost:8080/user/booking/cancel/" + validationResponse.bookingId, true);
			  cancelXhr.onreadystatechange = cancelProcessResponse;
			  //		  cancelXhr.setRequestHeader("Authorization", jwtToken);
			  cancelXhr.send(null);
		  }
		  catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
      }
      
      function cancelProcessResponse(){
    	  
		  try {
			  if (cancelXhr.readyState == 4 && cancelXhr.status == 200) {

				  window.location.href = "cab-application-masterpage.html";
			  }

			  if (cancelXhr.readyState == 4 && cancelXhr.status == 228) {

				  alert("Cab has been assigned for you! You can't cancel your booking!");
				  window.location.reload();

			  }
		  }
		  catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
		  
      }
     
	  //Cancelling the ride ends here
	  
/* --------------------------------------------------------------------------------------------------------------------------------------------- */


