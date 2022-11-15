const doctorCode = document.getElementById('doctor-code');
const doctorName = document.getElementById('doctor-name');
const doctorSurname = document.getElementById('doctor-surname');

function submit(){
	if(checkInput()){
		let data = parseData();
		if(data != null){
			const xhttp = new XMLHttpRequest();	
			/* Building of the callback function on the readychagestate change */
			xhttp.onreadystatechange = function(){
				if(this.readyState == 4 && this.status == 500){
					alert('Error during the submission!');
				}
				if(this.readyState == 4 && this.status == 200){
					/* If the server answer with the 200 code then add the result */
					if(JSON.parse(this.response).prescriptions.length == 0){
						if(document.getElementById('table-body').children.length > 0){
							for(let i = 0; i < document.getElementById('table-body').children.length; i++){
								document.getElementById('table-body').removeChild(document.getElementById('table-body').children[i]);
							}
						}
						document.getElementById('table').setAttribute('hidden', '');
						alert('No data found!');
					}else{
						appendData(JSON.parse(this.response));	
					}
				}
			}	
			/* Sending the request */
			xhttp.open('POST', '/PharmacyManagementSystem/printPrescriptions', true); //True means that the request is asynch
			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhttp.send(data);
		}
	}
}

function appendData(data){
	document.getElementById('table').removeAttribute('hidden');
	if(document.getElementById('table-body').children.length > 0){
		for(let i = 0; i < document.getElementById('table-body').children.length; i++){
			document.getElementById('table-body').removeChild(document.getElementById('table-body').children[i]);
		}
	}
	for(let i = 0; i < data.prescriptions.length; i++){
		let tableRow = document.createElement('tr');
		let tableRowCode = document.createElement('td');
		let tableRowProducts = document.createElement('td');
		let tableRowMedicine = document.createElement('td');
		tableRowCode.innerHTML = data.prescriptions[i].code;
		tableRowProducts.innerHTML = data.prescriptions[i].medicine.length;
		for(let j = 0; j < data.prescriptions[i].medicine.length; j++){
			tableRowMedicine.innerHTML += '<p>' + data.prescriptions[i].medicine[j].name + '</p>';
		}
		tableRow.append(tableRowCode);
		tableRow.append(tableRowProducts);
		tableRow.append(tableRowMedicine);
		document.getElementById('table-body').append(tableRow)
	}
}

function parseData(){
	return "code=" + doctorCode.value + "&name=" + doctorName.value + "&surname=" + doctorSurname.value; 
}

function checkInput(){
	let checkName = true;
	let checkSurname = true;
	let checkCode = true;
	
	/* Add the error message to the password field */
	if(document.getElementById('error-name')){
		document.getElementById('error-name').remove();
		document.getElementById('doctor-name').classList.remove('border');
		document.getElementById('doctor-name').classList.remove('border-danger');
	}
	if(document.getElementById('doctor-name').value == ''){
		document.getElementById('doctor-name').classList.add('border');
		document.getElementById('doctor-name').classList.add('border-danger');
		let message = document.createElement('p');
		let textMessage = document.createTextNode('Name field cannot be empty!');
		message.setAttribute('id', 'error-name');
		message.appendChild(textMessage);
		document.getElementById('doctor-name-container').appendChild(message);
		checkName = false;
	}
	
	/* Add the error message to the password field */
	if(document.getElementById('error-surname')){
		document.getElementById('error-surname').remove();
		document.getElementById('doctor-surname').classList.remove('border');
		document.getElementById('doctor-surname').classList.remove('border-danger');
	}
	if(document.getElementById('doctor-surname').value == ''){
		document.getElementById('doctor-surname').classList.add('border');
		document.getElementById('doctor-surname').classList.add('border-danger');
		let message = document.createElement('p');
		let textMessage = document.createTextNode('Surame field cannot be empty!');
		message.setAttribute('id', 'error-surname');
		message.appendChild(textMessage);
		document.getElementById('doctor-surname-container').appendChild(message);
		checkSurame = false;
	}
	
	/* Add the error message to the password field */
	if(document.getElementById('error-code')){
		document.getElementById('error-code').remove();
		document.getElementById('doctor-code').classList.remove('border');
		document.getElementById('doctor-code').classList.remove('border-danger');
	}
	if(document.getElementById('doctor-code').value == ''){
		document.getElementById('doctor-code').classList.add('border');
		document.getElementById('doctor-code').classList.add('border-danger');
		let message = document.createElement('p');
		let textMessage = document.createTextNode('Code field cannot be empty!');
		message.setAttribute('id', 'error-code');
		message.appendChild(textMessage);
		document.getElementById('doctor-code-container').appendChild(message);
		checkCode = false;
	}
	
	return checkName && checkSurname && checkCode;
}