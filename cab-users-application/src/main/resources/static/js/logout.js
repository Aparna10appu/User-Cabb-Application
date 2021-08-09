/**
 * 
 */

var LOGOUT_API = "http://localhost:8080/logout";

var logoutBtn = document.getElementById("logout");

logoutBtn.addEventListener("click", performLogout);

function performLogout() {
	
	sessionStorage.clear();
	window.location.href = "/logout"
		
}