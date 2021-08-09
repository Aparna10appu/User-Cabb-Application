// Completed Trip Js
	
	 var querystr = window.location.search; // search the details from the previous screen
	 
	 var id = querystr.split("="); //split the details
	 var tripId = id[1];	
	 var bookId = id[2];
	 var emplID = id[3];

	var jwtToken = sessionStorage.getItem("jwtToken")
	 
	window.onload = getCompletedTrip;
	var xhr = new XMLHttpRequest();
	function getCompletedTrip() {
		try {
			xhr.open("GET", "http://localhost:8080/api/v1/completedTrip/" + tripId, true);
			xhr.onreadystatechange = processResponse;
			xhr.setRequestHeader("Authorization", jwtToken);
			xhr.send(null);
		}
		catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}

	}
	var completedTrip;
	var dateFormat;
	function processResponse() {
		try {
			if (xhr.readyState == 4 && xhr.status == 200) {
				completedTrip = JSON.parse(this.responseText);

				// place the details from the database.
				document.getElementById('cabNumber').innerText = completedTrip.cabNumber;
				document.getElementById('driverName').innerText = completedTrip.driverName;
				document.getElementById('driverContact').innerText = completedTrip.driverNumber;
				document.getElementById('destination').innerText = completedTrip.destination;


				var date = completedTrip.dateOfTravel;  // date format
				var dateOfTravel = date.split("\-");
				dateFormat = dateOfTravel[2] + "-" + dateOfTravel[1] + "-" + dateOfTravel[0];
				document.getElementById("date").innerHTML = dateFormat;

				// TimeSlot Format 
				var timeSlotOption = document.getElementById('timeSlot');
				var slot = completedTrip.timeSlot;
				timeSlotOption.innerHTML = timeFormatTo12Hr(slot, 0)


				var employeeReachedTime;
				var length = completedTrip.tripList.length;
				var tbody = document.getElementById("tableBody");

				for (i = 0; i < length; i++) {
					$("#tablebody").empty();
					var trow = document.createElement('tr');
					trow.className = "row-bg-style";

					var td0 = document.createElement('td');
					td0.className = "spacing";
					td0.innerHTML = i + 1;
					trow.appendChild(td0);

					var td1 = document.createElement('td');
					td1.className = "spacing";
					td1.innerHTML = completedTrip.tripList[i].employeeId;
					trow.appendChild(td1);

					var td2 = document.createElement('td');
					td2.className = "spacing";
					td2.innerHTML = completedTrip.tripList[i].emlpoyeeName;
					trow.appendChild(td2);

					var td3 = document.createElement('td');
					td3.className = "spacing";
					td3.innerHTML = completedTrip.tripList[i].dropPoint;
					trow.appendChild(td3);
					tbody.appendChild(trow);

					// To get reached time for the login employee 

					if (completedTrip.tripList[i].employeeId == emplID) {
						employeeReachedTime = completedTrip.tripList[i].reachedTime;
					}
				}

				// no.of employees
				document.getElementById("passenger").innerText = "No.Of Passengers : " + length;

				// Reached Time of the Login Employee.
				var reachedTime = document.getElementById('reachedTime');

				var slot1 = employeeReachedTime;
				reachedTime.innerText = timeFormatTo12Hr(slot1, 0);

			}
		}
		catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
	}

	
//------------------------------- When User Clicks the Raise a Complaint-------------------------------------//
	
		var raiseBtn = document.getElementById("raiseBtn");
		raiseBtn.addEventListener("click", popUpDetails)
		// popUp details
		
		function popUpDetails() {  // get the details from the front end for the popUp Screen.
			try {
				document.getElementById("PopupDate").innerHTML = "Date: " + dateFormat;

				var timeSlotPop = document.getElementById("PopupTimeslot");
				var time = completedTrip.timeSlot;

				timeSlotPop.innerHTML = "Time Slot: " + timeFormatTo12Hr(time, 0); // call the timeformatter Js


				document.getElementById("PopupCabNo.").innerHTML = "Cab Number: " + completedTrip.cabNumber;
				document.getElementById("PopDriverName").innerHTML = "Driver Name: " + completedTrip.driverName;
				document.getElementById("PopupDriverNo").innerHTML = "Driver Number: " + completedTrip.driverNumber;
				document.getElementById("PopupDestination").innerHTML = "Destination: " + completedTrip.destination;

				getDropdown();  // get dropdown method triggered here !
			}
			catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}

	}
		// get Complaints in dropDown
		var xhrGetComplaints = new XMLHttpRequest();

		function getDropdown() {
			try {
				xhrGetComplaints.open("GET", "http://localhost:8080/api/v1/complaints", true);
				xhrGetComplaints.onreadystatechange = getComplaints;
				xhrGetComplaints.setRequestHeader("Authorization", jwtToken);
				xhrGetComplaints.send(null);
			}
			catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
		}

		function getComplaints() { 								// get the complaints from the DB.
			try {
				if (xhrGetComplaints.readyState == 4 && xhrGetComplaints.status == 200) {
					var complaintsList = JSON.parse(this.responseText);
					var reasonSel = document.getElementById("dropdown");
					var len = complaintsList.length;

					// for avoid the repeatness in the dropdown.
					var optLength = reasonSel.options.length; // i=2;
					for (i = optLength - 1; i > 0; i--) {// it will work in reverse that reduce the length=null;
						reasonSel.options[i] = null;
					}

					// create the dropdownlist
					for (var i = 0; i < len; i++) {
						var list = complaintsList[i];
						var ele = document.createElement("option");
						ele.innerHTML = list.complaintDescription;
						reasonSel.appendChild(ele);
					}

				}
			}
			catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}

	}

//-----------------------------Update the Complaints in The Database-------------------------------//
		
		
	var xhrComplaints = new XMLHttpRequest();
	var popup = document.getElementById("raiseComPopUp");
	popup.onclick = validateComplaints;
	
	function validateComplaints() {  			// to validate the complaint 
		try {
			var reasonSelec = document.getElementById("dropdown");
			var value = reasonSelec.options[reasonSelec.selectedIndex].value; // get the selected index value,
			//if its 0 then its Invalid.

			if (value == 0) {
				alert("Invalid Complaint");
				return false;
			}
			Popraisebtn();    // trigger the popRaise a complaint btn.
		}
		catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			} 
	}

	function Popraisebtn() {   

		try {
			var reasonSelec = document.getElementById("dropdown");
			var value = reasonSelec.options[reasonSelec.selectedIndex].value;
			xhrComplaints.open("PUT", "http://localhost:8080/api/v1/updateComplaints/" + bookId + "/" + value, true);
			xhrComplaints.setRequestHeader("Authorization", jwtToken);
			xhrComplaints.onreadystatechange = updateComplaint;


			function updateComplaint() {
				if (xhrComplaints.readyState == 4 && xhrComplaints.status == 201) {
					var obj = JSON.parse(xhrComplaints.responseText);
					alert("Complaints registered Successfully!");
					$('#raise-complaint').modal('hide');
				}
				if (xhrComplaints.readyState == 4 && xhrComplaints.status == 501) {
					alert("Complaints Already registerd !");


				}
			}

			xhrComplaints.send();
		}
		catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}  
		
		

	}

