function deleteElement(elementIndex){
	const row = document.getElementById('row-'+elementIndex);
	const medicineName = row.children[0].outerText;
	const xhttp = new XMLHttpRequest();	
	/* Building of the callback function on the readychagestate change */
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 500){
			alert('Error during the submission!');
		}
		if(this.readyState == 4 && this.status == 200){
			/* If the server answer with the 200 code then remove the field */
			row.remove();
		}
	}	
	/* Sending the request */
	xhttp.open('POST', '/PharmacyManagementSystem/findUnbranded', true); //True means that the request is asynch
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(parseName(medicineName));
}

function parseName(name){
	return 'name=' + name;
}