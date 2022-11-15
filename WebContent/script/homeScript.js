function logout(){
	const xhttp = new XMLHttpRequest();	
	/* Building of the callback function on the readychagestate change */
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			/* If the server answer with the 200 code then redirect the user to the right page */
			location.href = 'http://localhost:8080/PharmacyManagementSystem/login.jsp';
		}
	}	
	/* Sending the request */
	xhttp.open('GET', '/PharmacyManagementSystem/login', true); //True means that the request is asynch
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("");
}