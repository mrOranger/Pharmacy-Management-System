function deleteElement(index){
	const row = document.getElementById('row-'+index);
	const medicineName = row.children[0].outerText;
	const xhttp = new XMLHttpRequest();	
	/* Building of the callback function on the readychagestate change */
	xhttp.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 500){
			alert('Error during the submission!');
		}
		if(this.readyState == 4 && this.status == 200){
			/* If the server answer with the 200 we can delete the table's row */
			row.remove();
		}
	}	
	/* Sending the request */
	xhttp.open('POST', '/PharmacyManagementSystem/findMedicine', true); //True means that the request is asynch
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(parseInput(medicineName));
}

function parseInput(medicineName){
	return 'name=' + medicineName;
}