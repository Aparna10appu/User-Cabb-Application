var xhrMyRequest = new XMLHttpRequest();

//localStorage.setItem("empId","2038");
//var empId = localStorage.getItem("empId");

var employee = JSON.parse(sessionStorage.getItem("employee"));
var empId = employee.employeeId;
var jwtToken = sessionStorage.getItem("jwtToken")

window.onload = getMyRequest; // on load the function would be called!

function getMyRequest() {

	try {
		xhrMyRequest.open("GET", "http://localhost:8080/user/myrequest/employeedetails/" + empId, true);
		xhrMyRequest.setRequestHeader("Authorization", jwtToken);
		xhrMyRequest.onreadystatechange = processResponse;
		xhrMyRequest.send(null);
	}
	catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}  
}


var rowCounter;
var arr;
var timeSplit;
var time;
var hours
var dateFormat;
var dateTravel;


function processResponse() {
	try{
		if (xhrMyRequest.readyState == 4 && xhrMyRequest.status == 200) {
		//$("#tableBody").empty(); // make the table empty for every ajax call
		 arr = JSON.parse(xhrMyRequest.responseText);
		 console.log(arr);
		 rowCounter = 0;

		 for (var i = 0; i < arr.length; i++) {



			// creating row and data.

			var trow = document.createElement('tr');
			trow.className = "row-bg-style";             // display-shadow       // addingStyle class
			trow.id = "tr" + rowCounter++;


			var divObj = document.createElement('td');
			divObj.className = "spacing";
			divObj.innerText = i + 1;
			

			var divObj1 = document.createElement('td');
			divObj1.className = "spacing";
			
			
        	var date = arr[i].dateOfTravel;
          	//var newDate = date.split("T");   // date of travel
			var dateTravel = date.split('-')
            dateFormat = dateTravel[2] + "-" + dateTravel[1] + "-"+ dateTravel[0];
			divObj1.innerText=dateFormat;
      	    dateOfTravel = dateFormat;
        
        
			var divObj2 = document.createElement('td'); // time travel
			divObj2.className = "spacing";
//			var timeSplit = arr[i].details.timeSlot.split(":");
//			//var timeSplit = time.split(":");
//			var hours = timeSplit[0];
//
//				// time slot format
//			if (hours < 12) {
//				if (hours == 00) {
//					divObj2.innerHTML = "12" + ":" + timeSplit[1] + " AM";
//				} else {
//					divObj2.innerHTML = hours + ":" + timeSplit[1] + " AM";
//				}
//			} 
//			else
//			{
//				hours = hours - 12;
//				if (hours < 10) {
//					divObj2.innerHTML = "0" + hours + ":" + timeSplit[1] + " PM";
//				} if (hours == 0) {
//					divObj2.innerHTML = "12" + ":" + timeSplit[1] + " PM";
//				}
//				else {
//					divObj2.innerHTML = hours + ":" + timeSplit[1] + " PM";
//				}
//			}

			//jithendra timeFormatter
			var timeSlot = timeFormatTo12Hr(arr[i].details.timeSlot, 1);
			divObj2.innerHTML = timeSlot;
					
			var divObj3 = document.createElement('td');
			divObj3.className = "spacing";
			

			var divObj4 = document.createElement('td');
			divObj4.className = "spacing";
			divObj4.id = "action"+i;
			
		
			var divObj5 = document.createElement('td');
			divObj5.className = "spacing";
			divObj5.id = "tripId"+i;
			divObj5.style.display = 'none';
    
			
			var divObj6 =document.createElement('td');
			divObj6.className ="spacing";
			divObj6	.id = "bookId"+i;
			divObj6.style.display = 'none';
      
		    var divObj7 = document.createElement('td');
		    divObj7.className ="spacing";
		    divObj7.id = "empId"+i;
		    divObj7.style.display = 'none';
		      
		    
		    
	   	   	divObj3.innerText = arr[i].details.status; // status
			if(divObj3.innerText=="Cancelled" || divObj3.innerText=="Noshow")  // if the status is in cancelled the onclick event should not be trigger.
			{
				divObj4.innerHTML = "<a class='view-icon'><img src='images/view.png' class='fade-out' alt='viewicon'</a>";
	
			}
			else{
			
	           divObj4.innerHTML = "<a class='view-icon'><img src='images/view.png' class='viewicon' alt='viewicon' onclick = 'navigate(this)'</a>";
		       
		       }
	
			// required info for next page   
           divObj5.innerHTML=arr[i].details.tripCabId;
           divObj6.innerHTML =arr[i].details.bookingId;
           divObj7.innerHTML = arr[i].details.employeeId;
           

            // append the data in the trow
			
			trow.appendChild(divObj);
			trow.appendChild(divObj1);
			trow.appendChild(divObj2);
			trow.appendChild(divObj3);
			trow.appendChild(divObj4);
			trow.appendChild(divObj5);
			trow.appendChild(divObj6);
			trow.appendChild(divObj7);

			// append the rows in the tablebody.
			document.getElementById("tableBody").appendChild(trow);
          
		}
		
		// view for the total records
   	 var count = document.createElement('div');
	count.class = "header-left py-md-3";
	document.getElementById("totalrecord").innerText ='Total record  '+ rowCounter+" out of " +rowCounter;

   
	}	
	}
	catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}  
}

 var getId;
 var actionRow;
 
 function navigate(navigateRow)          // after user clicks the action button
	{
	 try {
		 getId = navigateRow.closest("td").id;  // get the Id of the Action button

		 // it will replace the action0 into only that 0 .
		 var actionbtn = getId.replace("action", "");

		 actionRow = document.getElementById("tr" + actionbtn); // get the entire row

		 //get the Required value from the row
		 var tripId = actionRow.getElementsByTagName("td")[5].innerHTML;
		 var bookId = actionRow.getElementsByTagName("td")[6].innerHTML;
		 var empId = actionRow.getElementsByTagName("td")[7].innerHTML;


		 // heading towards the next screen

		 window.location.href = "http://localhost:8080/cab-app-completedtrip.html?tripCabId =" + tripId + "=" + bookId + "=" + empId;
	 } 
	 catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			} 
	};

//

/*var MyRequest;

function processResponse() {
	alert(xhr.readyState);
	if (xhr.readyState == 4 && xhr.status == 200) {
		alert("10");
		MyRequest = JSON.parse(xhr.responseText);

		var length = MyRequest.length;
		alert(length);

		var tbody = document.getElementById("tableBody");
		for (i = 0; i < length; i++) {
			var trow = document.createElement('tr');
			trow.className = "row-bg-style";

			var td0 = document.createElement('td');
			td0.className = "spacing";
			td0.innerHTML = i + 1;
			trow.appendChild(td0);

			var td1 = document.createElement('td');
			td1.className = "spacing";
			td1.innerHTML = MyRequest[i].dateOfTravel;
			trow.appendChild(td1);

			var td2 = document.createElement('td');
			td2.className = "spacing";
			td2.innerHTML = MyRequest.details[i].timeSlot;
			trow.appendChild(td2);

			var td3 = document.createElement('td');
			td3.className = "spacing";
			td3.innerHTML = MyRequest.details[i].status;
			trow.appendChild(td3);

			var td4 = document.createElement('td');
			td4.className = "spacing";
			//td4.innerHTML = MyRequest[i].action;
			td4.innerHTML = "<img src='images/view.png' class='viewicon' alt='viewicon'>";
			//	window.location.href="cab-app-completedtrip.html";
			trow.appendChild(td4);
			tbody.appendChild(trow);

		}

	}

}*/

