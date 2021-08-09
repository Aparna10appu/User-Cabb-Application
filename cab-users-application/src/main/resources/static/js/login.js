var LOGIN_API = "http://localhost:8080/authenticate";
var EMP_DETAILS_API = "http://localhost:8080/empDetails"

//if gateway added
//var LOGIN_API = "http://localhost:8081/user/authenticate";
//var EMP_DETAILS_API = "http://localhost:8081/user/empDetails";

//get admin contacts and load cookies
window.onload = onLoadFunctions;

function onLoadFunctions() {
	getAdminContacts();
	loadCookies();
}

function getAdminContacts() {

	try {
		var xhrGetAdmins = new XMLHttpRequest();
		xhrGetAdmins.open("GET", "http://localhost:8080/admins", true);
		xhrGetAdmins.onreadystatechange = function() {
			if (xhrGetAdmins.readyState == 4 && xhrGetAdmins.status == 200) {
				sessionStorage.setItem("admins", xhrGetAdmins.responseText);
			}
		};
		xhrGetAdmins.send(null);
	}
	catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			} 
}

//*********************************************************//

function loadCookies() {
	
	try {
		//reduce the cookies from array to name and values
		var cookies = document.cookie
			.split(';')
			.map(cookie => cookie.split('='))
			.reduce((total, [key, value]) => ({ ...total, [key.trim()]: value }), {});

		//if cookies not present
		//Added for conflict

		if (cookies.email == undefined || cookies.pass == undefined) {
			return false;
		}

		document.getElementById("email").value = cookies.email;
		//password stored in cookie is base64 encoded
		document.getElementById("password").value = window.atob(cookies.pass);

		document.getElementById('rememberCheck').checked = true;
	}
	catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			} 
}

//*********************************************************//

var loginBut = document.getElementById("loginBut");

var emailId;
var password;

var isPasswordHidden = true;

loginBut.addEventListener('click', checkEmptyFields);

//check empty fields
function checkEmptyFields() {
	
	try {
		//trim empty spaces
		emailId = document.getElementById("email").value.trim();
		//encode password before sending and before saving cookie
		password = window.btoa(document.getElementById("password").value.trim());


		if (emailId == "" || emailId == undefined) {
			alert("Kindly enter your email Id");
			document.getElementById("email").focus();
			return false;
		}

		if (password == "" || password == undefined) {
			alert("Kindly enter the password");
			document.getElementById("password").focus();
			return false;
		}

		checkEmailFormat();
	}
	catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
}
//*********************************************************//

//check email format
function checkEmailFormat() {
	
	try {
		var domain = emailId.split("@");

		if (domain[1] != "avasoft.com") {
			//		alert("Email Id entered is invalid");
			//		document.getElementById("email").focus();
			//		return false;
		}

		validateUser();
	}
	catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
	
}
//*********************************************************//

//also send api keys to say user accessing web application
//validate user credentials
function validateUser() {
	
	try {
		//loading anime starts
		var loadingAnime = document.getElementById("loadingAnime");
		loadingAnime.style.visibility = "visible";
		loginBut.disabled = true;
		loginBut.style.backgroundColor = "darkgrey";
		//ends

		var xhr = new XMLHttpRequest();
		xhr.open("POST", LOGIN_API, true);
		xhr.setRequestHeader("Content-Type", "application/json");

		//email should be case sensitive?
		var employee = {
			"employeeMail": emailId.toLowerCase(),
			"password": password
		};

		xhr.send(JSON.stringify(employee));

		xhr.onreadystatechange = function() {

			loadingAnime.style.visibility = "hidden";
			loginBut.disabled = false;
			loginBut.style.backgroundColor = "#F8BB10";

			//for invalid credential
			if (xhr.readyState == 4 && xhr.status == 560) {
				var invUserDiv = document.getElementById("invalidUser");
				invUserDiv.style.float = "left";
				invUserDiv.style.color = "red";
				invUserDiv.style.width = "100%";
				invUserDiv.style.fontWeight = "bolder";

				invUserDiv.innerHTML = "Bad Credentials";

				return false;
			}

			//if user blocked status 560 will be returned - show popup
			if (xhr.readyState == 4 && xhr.status == 561) {
				$("#actionblock").modal("show");
				return false;
			}

			if (xhr.readyState == 4 && xhr.status == 200) {

				//employee details will be returned with token in response header
				sessionStorage.setItem("jwtToken", xhr.getResponseHeader("Authorization"));

				//dont parse when storing in session
				var employeeDetails = xhr.responseText;

				sessionStorage.setItem("employee", employeeDetails);

				//save cookie only if user is valid
				saveCookie(emailId, password);

				//			redirect to book a cab page
				window.location.href = "cab-application-masterpage.html";

				//			getEmployeeDetails();

			}
		}
	}
	catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
}
//*********************************************************//

//get employee details
function getEmployeeDetails() {
	
	try {
		var url = "http://localhost:8080/empDetails";

		var xhr = sendAjaxRequest("GET", url, null, true);

		if (xhr.readyState == 4 && xhr.status == 200) {
			var employee = JSON.parse(xhr.responseText);
			console.log(employee);
		}
	}
	catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
	
}
//*********************************************************//

//save cookies
function saveCookie(emailId, password) {
	
	try {
		//save cookies
		if (document.getElementById('rememberCheck').checked == true) {

			var d = new Date();
			d.setTime(d.getTime() + 24 * 60 * 60 * 10000);
			d.toUTCString();

			document.cookie = "email=" + emailId + ";" + "expires=" + d + ";path=/";
			document.cookie = "pass=" + password + ";" + "expires=" + d + ";path=/";

		}

		//delete cookies if remember me not checked
		else {
			document.cookie = "email=" + ";" + "expires=" + (new Date() - 24 * 60) + ";path=/";
			document.cookie = "pass=" + ";" + "expires=" + (new Date() - 24 * 60) + ";path=/";
		}
	}
	catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
	
}
//*********************************************************//

//display password
var showPassBut = document.getElementById("showPassword");
showPassBut.addEventListener("click", displayPassword);

function displayPassword() {
	try {
		if (isPasswordHidden) {
			isPasswordHidden = false;
			document.getElementById("password").setAttribute("type", "text");
			showPassBut.innerText = "Hide Password";
		} else {
			isPasswordHidden = true;
			document.getElementById("password").setAttribute("type", "password");
			showPassBut.innerText = "Show Password";
		}
	}
	catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
}
//*********************************************************//

//on press of enter key perform login
var email = document.getElementById("email");
var password = document.getElementById("password");

email.addEventListener("keyup", function() {
	try {
		if (event.keyCode == 13) {
			enterButtonClicked();
		}
	}
	catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
});

password.addEventListener("keyup", function() {
	try {
		if (event.keyCode == 13) {
			enterButtonClicked();
		}
	}
	catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
});

//enter button function
function enterButtonClicked() {
	try {
		event.preventDefault();
		checkEmptyFields();
	}
	catch (e) {
				alert(e.message);
				jsExceptionHandling(e);
			}
}
//*********************************************************//






