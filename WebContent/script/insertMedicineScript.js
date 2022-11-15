/* Medicine's information */
const nameField = document.getElementById('name');
const descriptionField = document.getElementById('description');
const licenceField = document.getElementById('licence');
const costField = document.getElementById('cost');
const isPrescriptedField = document.getElementById('checkbox');
const currentCompnayField = document.getElementById('select-company');

/* Company's information */
const companyNameField = document.getElementById('company-name');
const streetField = document.getElementById('street');
const streetCodeField = document.getElementById('street-code');
const cityField = document.getElementById('city');
const countryField = document.getElementById('country');

/**
 * Function that show the form for medicine input 
 * @returns
 */
function showMedicines(){
	if(document.getElementById('medicine-form').getAttribute('hidden') != null){
		document.getElementById('medicine-form').removeAttribute('hidden');
		document.getElementById('cosmetic-form').setAttribute('hidden', '');
	}
}

/**
 * Function that show the form for cosmetic input 
 * @returns
 */
function showCosmetics(){
	document.getElementById('medicine-form').setAttribute('hidden', '');
	if(document.getElementById('cosmetic-form').getAttribute('hidden') != null){
		document.getElementById('cosmetic-form').removeAttribute('hidden');
		document.getElementById('medicine-form').setAttribute('hidden', '');
	}
}

/**
 * Function that checks if the inputs are valid and perform the HTTP request POST
 * @returns
 */
function insert(){
	/* Submit a request for medicine */
	if(document.getElementById('medicine-radio').checked == true){
		/* If the elements are correctly inserted */
		if(checkFields()){
			let data = null;
			if(!selectCompany()){
				data = parseInputWithoutCompany();
			}else{
				if(checkFieldsCompany()){
					data = parseInputWithCompany();
				}
			}
			if(data != null){
				const xhttp = new XMLHttpRequest();	
				/* Building of the callback function on the readychagestate change */
				xhttp.onreadystatechange = function(){
					if(this.readyState == 4 && this.status == 500){
						alert('Error during the submission!');
					}
					if(this.readyState == 4 && this.status == 200){
						/* If the server answer with the 200 code then show a success alert */
						alert('Medicine insert success!');
					}
				}	
				/* Sending the request */
				xhttp.open('POST', '/PharmacyManagementSystem/insertElement', true); //True means that the request is asynch
				xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				xhttp.send(data);
			}
		}
	}
	/* Submit a request for cosmetic */
	if(document.getElementById('cosmetic-radio').checked == true){
		if(checkInputCosmetics()){
			const xhttp = new XMLHttpRequest();	
			/* Building of the callback function on the readychagestate change */
			xhttp.onreadystatechange = function(){
				if(this.readyState == 4 && this.status == 500){
					alert('Error during the submission!');
				}
				if(this.readyState == 4 && this.status == 200){
					/* If the server answer with the 200 code then show a success alert */
					alert('Cosmetic insert success!');
				}
			}	
			/* Sending the request */
			xhttp.open('POST', '/PharmacyManagementSystem/insertElement', true); //True means that the request is asynch
			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhttp.send(parseCosmeticData());
		}
	}
}

/**
 * Function that checks if the input fields of the Cosmetic are valid
 * @returns
 */
function checkInputCosmetics(){
	let cosmeticName = document.getElementById('cosmetic-name').value;
	let cosmeticDescription = document.getElementById('cosmetic-description').value;
	let cosmeticCost = document.getElementById('cosmetic-cost').value;
	let cosmeticType = document.getElementById('select-type').value;
	
	let checkName = true;
	let checkDescription = true;
	let checkCost = true;
	
	/* Add the error message to the name field */
	if(document.getElementById('error-name-cosmetic')){
		document.getElementById('error-name-cosmetic').remove();
		document.getElementById('cosmetic-name').classList.remove('border');
		document.getElementById('cosmetic-name').classList.remove('border-danger');
	}
	
	/* Add the error message to the description field */
	if(document.getElementById('error-description-cosmetic')){
		document.getElementById('error-description-cosmetic').remove();
		document.getElementById('cosmetic-description').classList.remove('border');
		document.getElementById('cosmetic-description').classList.remove('border-danger');
	}
	
	/* Add the error message to the cost field */
	if(document.getElementById('error-cost-cosmetic')){
		document.getElementById('error-cost-cosmetic').remove();
		document.getElementById('cosmetic-cost').classList.remove('border');
		document.getElementById('cosmetic-cost').classList.remove('border-danger');
	}
	
	/* Check the name field value */
	if(cosmeticName == '' || cosmeticName.length > 20){
		document.getElementById('cosmetic-name').classList.add('border');
		document.getElementById('cosmetic-name').classList.add('border-danger');
		let nameMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid name!');
		nameMessage.setAttribute('id', 'error-name-cosmetic');
		nameMessage.setAttribute('style', 'color: red');
		nameMessage.appendChild(textMessage);
		document.getElementById('cosmetic-name-container').appendChild(nameMessage);
		checkName = false;
	}
	
	/* Check the name description value */
	if(cosmeticDescription == '' || cosmeticDescription.length > 300){
		document.getElementById('cosmetic-description').classList.add('border');
		document.getElementById('cosmetic-description').classList.add('border-danger');
		let descriptionMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid description!');
		descriptionMessage.setAttribute('id', 'error-description-cosmetic');
		descriptionMessage.setAttribute('style', 'color: red');
		descriptionMessage.appendChild(textMessage);
		document.getElementById('cosmetic-description-container').appendChild(descriptionMessage);
		checkDescription = false;
	}
	
	/* Check the name cost value */
	if(cosmeticCost == ''){
		document.getElementById('cosmetic-cost').classList.add('border');
		document.getElementById('cosmetic-cost').classList.add('border-danger');
		let costMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid cost!');
		costMessage.setAttribute('id', 'error-cost-cosmetic');
		costMessage.setAttribute('style', 'color: red');
		costMessage.appendChild(textMessage);
		document.getElementById('cosmetic-cost-container').appendChild(costMessage);
		checkCost = false;
	}
	return checkName && checkDescription && checkCost;
}

/**
 * Function that checks the fields in input
 * @returns
 */
function checkFields(){
	let nameValue = nameField.value;
	let descriptionValue = descriptionField.value;
	let costValue = costField.value;
	let licenceValue = licenceField.value;
	
	let checkName = true;
	let checkDescription = true;
	let checkCost = true;
	let checkLicence = true;
	
	/* Add the error message to the name field */
	if(document.getElementById('error-name')){
		console.log(document.getElementById('error-name'));
		console.log(nameField);
		document.getElementById('error-name').remove();
		nameField.classList.remove('border');
		nameField.classList.remove('border-danger');
	}
	
	/* Add the error message to the description field */
	if(document.getElementById('error-description')){
		document.getElementById('error-description').remove();
		descriptionField.classList.remove('border');
		descriptionField.classList.remove('border-danger');
	}
	
	/* Add the error message to the cost field */
	if(document.getElementById('error-cost')){
		document.getElementById('error-cost').remove();
		costField.classList.remove('border');
		costField.classList.remove('border-danger');
	}
	
	/* Add the error message to the licence field */
	if(document.getElementById('error-licence')){
		document.getElementById('error-licence').remove();
		licenceField.classList.remove('border');
		licenceField.classList.remove('border-danger');
	}
	
	/* Check the name field value */
	if(nameValue == '' || nameValue.length > 20){
		nameField.classList.add('border');
		nameField.classList.add('border-danger');
		let nameMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid name!');
		nameMessage.setAttribute('id', 'error-name');
		nameMessage.setAttribute('style', 'color: red');
		nameMessage.appendChild(textMessage);
		document.getElementById('name-container').appendChild(nameMessage);
		checkName = false;
	}
	
	/* Check the description field value */
	if(descriptionValue == '' || descriptionValue.length > 300){
		descriptionField.classList.add('border');
		descriptionField.classList.add('border-danger');
		let descriptionMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid description!');
		descriptionMessage.setAttribute('id', 'error-description');
		descriptionMessage.setAttribute('style', 'color: red');
		descriptionMessage.appendChild(textMessage);
		document.getElementById('description-container').appendChild(descriptionMessage);
		checkDescription = false;
	}
	
	/* Check the cost field value */
	if(costValue == ''){
		costField.classList.add('border');
		costField.classList.add('border-danger');
		let costMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid cost!');
		costMessage.setAttribute('id', 'error-cost');
		costMessage.setAttribute('style', 'color: red');
		costMessage.appendChild(textMessage);
		document.getElementById('cost-container').appendChild(costMessage);
		checkCost = false;
	}
	
	/* Check the name field value */
	if(licenceValue == ''){
		licenceField.classList.add('border');
		licenceField.classList.add('border-danger');
		let licenceMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid licence period!');
		licenceMessage.setAttribute('id', 'error-licence');
		licenceMessage.setAttribute('style', 'color: red');
		licenceMessage.appendChild(textMessage);
		document.getElementById('licence-container').appendChild(licenceMessage);
		checkLicence = false;
	}
	
	return checkName && checkDescription && checkCost && checkLicence;
}

/**
 * Function that check if the input fields for the company's information are correct
 * @returns
 */
function checkFieldsCompany(){
	let companyNameValue = companyNameField.value;
	let streetNameValue = streetField.value;
	let streetCodeValue = streetCodeField.value;
	let cityNameValue = cityField.value;
	let countryValue = countryField.value;
	
	let checkName = true;
	let checkStreetName = true;
	let checkStreetCode = true;
	let checkCityName = true;
	let checkCountryName = true;
	
	/* Add the error message to the name field */
	if(document.getElementById('error-name-company')){
		document.getElementById('error-name-company').remove();
		companyNameField.classList.remove('border');
		companyNameField.classList.remove('border-danger');
	}
	
	/* Add the error message to the street field */
	if(document.getElementById('error-street-name')){
		document.getElementById('error-street-name').remove();
		streetField.classList.remove('border');
		streetField.classList.remove('border-danger');
	}
	
	/* Add the error message to the street code */
	if(document.getElementById('error-street-code')){
		document.getElementById('error-street-code').remove();
		streetCodeField.classList.remove('border');
		streetCodeField.classList.remove('border-danger');
	}
	
	/* Add the error message to the city field */
	if(document.getElementById('error-city')){
		document.getElementById('error-city').remove();
		cityField.classList.remove('border');
		cityField.classList.remove('border-danger');
	}
	
	/* Add the error message to the country field */
	if(document.getElementById('error-country')){
		document.getElementById('error-country').remove();
		countryField.classList.remove('border');
		countryField.classList.remove('border-danger');
	}
	
	/* Check the name field value */
	if(companyNameValue == '' || companyNameValue.length > 20){
		companyNameField.classList.add('border');
		companyNameField.classList.add('border-danger');
		let nameMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid name!');
		nameMessage.setAttribute('id', 'error-name-company');
		nameMessage.setAttribute('style', 'color: red');
		nameMessage.appendChild(textMessage);
		document.getElementById('company-name-container').appendChild(nameMessage);
		checkName = false;
	}
	
	/* Check the street name value */
	if(streetNameValue == '' || streetNameValue.length > 30){
		streetField.classList.add('border');
		streetField.classList.add('border-danger');
		let streetMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid name!');
		streetMessage.setAttribute('id', 'error-street-name');
		streetMessage.setAttribute('style', 'color: red');
		streetMessage.appendChild(textMessage);
		document.getElementById('street-container').appendChild(streetMessage);
		checkStreetName = false;
	}
	
	/* Check the cost field value */
	if(streetCodeValue == ''){
		streetCodeField.classList.add('border');
		streetCodeField.classList.add('border-danger');
		let streetMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid code!');
		streetMessage.setAttribute('id', 'error-street-code');
		streetMessage.setAttribute('style', 'color: red');
		streetMessage.appendChild(textMessage);
		document.getElementById('street-code-container').appendChild(streetMessage);
		checkStreetCode = false;
	}
	
	/* Check the name field value */
	if(cityNameValue == ''){
		cityField.classList.add('border');
		cityField.classList.add('border-danger');
		let cityMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid street!');
		cityMessage.setAttribute('id', 'error-city');
		cityMessage.setAttribute('style', 'color: red');
		cityMessage.appendChild(textMessage);
		document.getElementById('city-container').appendChild(cityMessage);
		checkCityName = false;
	}
	
	/* Check the name field value */
	if(countryValue == ''){
		countryField.classList.add('border');
		countryField.classList.add('border-danger');
		let countryMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid name!');
		countryMessage.setAttribute('id', 'error-country');
		countryMessage.setAttribute('style', 'color: red');
		countryMessage.appendChild(textMessage);
		document.getElementById('country-container').appendChild(countryMessage);
		checkCountryName = false;
	}

	return checkName && checkStreetName && checkStreetCode && checkCityName && checkCountryName;
}

/**
 * Function that manage the option to insert a new company or an existing company
 * @returns
 */
function selectCompany(){
	let currentOption = currentCompnayField.selectedOptions[0].text;
	if(currentOption == "Insert a new company"){
		document.getElementById('company-container').removeAttribute('hidden');
		return true;
	}else{
		document.getElementById('company-container').setAttribute('hidden', '');
		return false;
	}
}

/**
 * Function that parse the input data to provide to the POST request
 * @returns
 */
function parseInputWithoutCompany(){
	let isPrescripted = document.getElementById('checkbox').checked;
	let currentOption = currentCompnayField.selectedOptions[0].text;
	return 'medicineName=' + nameField.value + '&description=' + descriptionField.value + '&licenceYears=' + licenceField.value + '&cost=' + costField.value + '&prescripted=' + isPrescripted + '&companyName=' + currentOption; 
}

/**
 * Function that parse the input data to provide to the POST request without the Company's information
 * @returns
 */
function parseInputWithCompany(){
	let isPrescripted = document.getElementById('checkbox').checked;
	return 'medicineName=' + nameField.value + '&description=' + descriptionField.value + '&licenceYears=' + licenceField.value + '&cost=' + costField.value + '&prescripted=' + isPrescripted + '&companyName=' + companyNameField.value + '&streetName=' + streetField.value + '&streetCode=' + streetCodeField.value + '&city=' + cityField.value + '&country=' + countryField.value;
}

/**
 * Function that parse the input data to provide to the POST 
 * @returns
 */
function parseCosmeticData(){
	let cosmeticName = document.getElementById('cosmetic-name').value;
	let cosmeticDescription = document.getElementById('cosmetic-description').value;
	let cosmeticCost = document.getElementById('cosmetic-cost').value;
	let cosmeticType = document.getElementById('select-type').value;
	return 'cosmeticName=' + cosmeticName + '&description=' + cosmeticDescription + '&cost=' + cosmeticCost + '&type=' + cosmeticType;
}