let currentItems = 1;
let totalRow = 1;
const array = new Array();
let totalProducts = 1;

function addProduct(){
	document.getElementsByClassName('fa-icon')[0].remove();
	currentItems++;
	document.getElementById('insert-form').innerHTML += `	
	<hr id = "separator-`+ currentItems +`"/>
	<div class = "row" id = "row-` + currentItems + `">
		<div class = "col" id = "product-name-container-` + currentItems + `">
			<input id = "product-name-` + currentItems + `" type="text" class="form-control rounded-pill form-control-lg product-name" placeholder="Product name" onchange = "setValueName(1)">
		</div>
		<div class = "col-md-2 add-icon" id = "add-` + currentItems + `">
			<i class="rounded-pill form-control-lg product-cost fa-icon fas fa-plus" onclick = "addProduct()"></i>
		</div>
		<div class = "col-md-2 remove-icon" id = "remove-container-` + currentItems + `">
			<i class="fa fa-trash" aria-hidden="false" onclick = "deleteElement(` + currentItems + `)"></i>
		</div>
	</div>`;
	totalProducts++;
	array.push(currentItems);
	updateTotalProducts();
}

function deleteElement(index){
	document.getElementById('row-' + index).remove();
	document.getElementById('separator-'+index).remove();
	if(index == currentItems && (array.length > 1)){
		/* The element to delete is the last in the list */
		console.log("Ripristino il bottone a: " + (index - 1));
		document.getElementById('add-'+(index - 1)).innerHTML += `<i class="rounded-pill form-control-lg product-cost fa-icon fas fa-plus" onclick = "addProduct()"></i>`;
	}else{
		if(array.length == 1){
			/* If the items in the list are onyl two */
			document.getElementById('add-1').innerHTML += `<i class="rounded-pill form-control-lg product-cost fa-icon fas fa-plus" onclick = "addProduct()"></i>`;
		}else{
			console.log(array[array.length-2]);
			document.getElementById('add-' + array[array.length-2]).innerHTML += `<i class="rounded-pill form-control-lg product-cost fa-icon fas fa-plus" onclick = "addProduct()"></i>`;
		}
	}
	array.splice(array.indexOf(index), 1);
	totalProducts--;
	updateTotalProducts();
}

function setValueName(index){
	document.getElementById('product-name-' + index).setAttribute('value', document.getElementById('product-name-' + index).value);
}

function updateTotalProducts(){
	document.getElementById('total-products').innerHTML = "Total products: " + totalProducts;
}

function submit(){
	if(checkNameAndSurname() && checkValues()){	
		let data = 'data=' + JSON.stringify(parseJSONData());
		const xhttp = new XMLHttpRequest();	
		/* Building of the callback function on the readychagestate change */
		xhttp.onreadystatechange = function(){
			if(this.readyState == 4 && this.status == 500){
				alert('Error during the submission!');
			}
			if(this.readyState == 4 && this.status == 200){
				/* If the server answer with the 200 code then show a success alert */
				alert('Prescription insert success!');
			}
		}	
		/* Sending the request */
		xhttp.open('POST', '/PharmacyManagementSystem/insertPrescription', true); //True means that the request is asynch
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send(data);
	}
}

function parseJSONData(){
	let jsonObject = {
		"patient": {
			"taxCode": "",
			"name": "",
			"surname": ""
		},
		"products": [
			
		],
		"doctor": "",
		"totalProducts": totalProducts
	};
	jsonObject.patient.taxCode = document.getElementById('select-user-code').value;
	jsonObject.patient.name = document.getElementById('user-name-input').value;
	jsonObject.patient.surname = document.getElementById('user-surname-input').value;
	jsonObject.doctor = document.getElementById('select-doctor-code').value;
	for(let i = 1; i <= currentItems; i++){
		if(document.getElementById('product-name-'+i) != null && document.getElementById('product-name-'+i) != 'undefined'){
			let objName = document.getElementById('product-name-'+i).value;
			jsonObject.products.push({"name": objName});	
		}
	}
	return jsonObject;
}

function checkNameAndSurname(){
	let checkName = true;
	let checkSurname = true;
	
	if(document.getElementById('error-user-name-1')){
		document.getElementById('error-user-name-1').remove();
		document.getElementById('user-name-input').classList.remove('border');
		document.getElementById('user-name-input').classList.remove('border-danger');
	}
	if(document.getElementById('user-name-input').value == ''){
		document.getElementById('user-name-input').classList.add('border');
		document.getElementById('user-name-input').classList.add('border-danger');
		let message = document.createElement('p');
		let textMessage = document.createTextNode('Name field cannot be empty!');
		message.setAttribute('id', 'error-user-name-1');
		message.appendChild(textMessage);
		document.getElementById('user-name').appendChild(message);
		checkName = false;
	}
	
	if(document.getElementById('error-user-surname-1')){
		document.getElementById('error-user-surname-1').remove();
		document.getElementById('user-surname-input').classList.remove('border');
		document.getElementById('user-surname-input').classList.remove('border-danger');
	}
	
	if(document.getElementById('user-surname-input').value == ''){
		document.getElementById('user-surname-input').classList.add('border');
		document.getElementById('user-surname-input').classList.add('border-danger');
		let message = document.createElement('p');
		let textMessage = document.createTextNode('Surname field cannot be empty!');
		message.setAttribute('id', 'error-user-surname-1');
		message.appendChild(textMessage);
		document.getElementById('user-surname').appendChild(message);
		checkSurname = false;
	}
	
	return checkName && checkSurname;
}

function checkValues(){
	let check = true;
	
	if(document.getElementById('error-name-1')){
		document.getElementById('error-name-1').remove();
		document.getElementById('product-name-1').classList.remove('border');
		document.getElementById('product-name-1').classList.remove('border-danger');
	}
	if(document.getElementById('product-name-1').value == ''){
		document.getElementById('product-name-1').classList.add('border');
		document.getElementById('product-name-1').classList.add('border-danger');
		let message = document.createElement('p');
		let textMessage = document.createTextNode('Name field cannot be empty!');
		message.setAttribute('id', 'error-name-1');
		message.appendChild(textMessage);
		document.getElementById('product-name-container-1').appendChild(message);
		check = false;
	}
	
	for(let i = 0; i < array.length; i++){
		/* Add the error message to the password field */
		if(document.getElementById('error-name-'+array[i])){
			document.getElementById('error-name-'+array[i]).remove();
			document.getElementById('product-name-'+array[i]).classList.remove('border');
			document.getElementById('product-name-'+array[i]).classList.remove('border-danger');
		}
		if(document.getElementById('product-name-'+array[i]).value == ''){
			document.getElementById('product-name-'+array[i]).classList.add('border');
			document.getElementById('product-name-'+array[i]).classList.add('border-danger');
			let message = document.createElement('p');
			let textMessage = document.createTextNode('Name field cannot be empty!');
			message.setAttribute('id', 'error-name-'+array[i]);
			message.appendChild(textMessage);
			document.getElementById('product-name-container-'+array[i]).appendChild(message);
			check = false;
		}
	}
	return check;
}
