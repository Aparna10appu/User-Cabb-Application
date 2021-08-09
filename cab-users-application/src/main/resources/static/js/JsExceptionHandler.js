function jsExceptionHandling(e) {
	var jsExceptionXhr = new XMLHttpRequest();
	jsExceptionXhr.open("PUT", "http://localhost:8080/user/jsExceptions/" + e, true);
	jsExceptionXhr.send();
	jsExceptionXhr.onreadystatechange = function(){
		
	};
}