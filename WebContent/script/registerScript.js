const nameField = document.getElementById('name');
const surnameField = document.getElementById('surname');
const dateField = document.getElementById('date');
const usernameField = document.getElementById('username');
const passwordField = document.getElementById('password');
const header = document.getElementById('header');
/**
 * Function that make an HTTP POST request to the Server sending the input values 
 * @returns
 */
function submit(){
	if(checkInput()){
		if(document.getElementById('error-message')){
			document.getElementById('error-message').remove();
			document.getElementsByTagName('br')[0].remove();
		}
		if(document.getElementById('ok-message')){
			document.getElementById('ok-message').remove();
			document.getElementsByTagName('br')[0].remove();
		}
		/* AJAX call to the Server RegistrationServlet */
		const xhttp = new XMLHttpRequest();	
		/* Building of the callback function on the readychagestate change */
		xhttp.onreadystatechange = function(){
			if(this.readyState == 4 && this.status == 500){
				alert('Error during the submission!');
			}
			if(this.readyState == 4 && this.status == 401){
				/* If the server response with the 401 message add a message error*/
				if(!document.getElementById('error-message')){
					header.innerHTML += `<br><p id = "error-message">The user is already registered!</p>`;
					document.getElementById('error-message').setAttribute('style', 'color: red');
				}else{
					document.getElementById('error-message').remove();
					document.getElementsByTagName('br')[0].remove();
					header.innerHTML += `<br><p id = "error-message">The user is already registered!</p>`;
					document.getElementById('error-message').setAttribute('style', 'color: red');
				}
			}
			if(this.readyState == 4 && this.status == 200){
				/* If the server answer with the 200 code then redirect the user to the right page */
				if(xhttp.responseText == 'Ok'){
					if(!document.getElementById('ok-message')){
						header.innerHTML += `<br><p id = "ok-message">Submission completed!</p>`;
						document.getElementById('ok-message').setAttribute('style', 'color: green');
					}else{
						document.getElementById('ok-message').remove();
						document.getElementsByTagName('br')[0].remove();
						header.innerHTML += `<br><p id = "ok-message">Submission completed!</p>`;
						document.getElementById('ok-message').setAttribute('style', 'color: green');
					}
				}
			}
		}	
		/* Sending the request */
		xhttp.open('POST', '/PharmacyManagementSystem/register', true); //True means that the request is asynch
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send(parseInput());
	}
}

/**
 * Function that checks if the input fields are correct
 * @returns
 */
function checkInput(){
	let nameValue = nameField.value;
	let surnameValue = surnameField.value;
	let dateValue = dateField.value;
	let usernameValue = usernameField.value;
	let passwordValue = passwordField.value;
	
	let checkName = true;
	let checkSurname = true;
	let checkDate = true;
	let checkUsername = true;
	let checkPassword = true;
	
	/* Add the error message to the name field */
	if(document.getElementById('error-name')){
		document.getElementById('error-name').remove();
		nameField.classList.remove('border');
		nameField.classList.remove('border-danger');
	}
	
	/* Add the error message to the surname field */
	if(document.getElementById('error-surname')){
		document.getElementById('error-surname').remove();
		surnameField.classList.remove('border');
		surnameField.classList.remove('border-danger');
	}
	
	/* Add the error message to the date field */
	if(document.getElementById('error-date')){
		document.getElementById('error-date').remove();
		dateField.classList.remove('border');
		dateField.classList.remove('border-danger');
	}
	
	/* Add the error message to the username field */
	if(document.getElementById('error-username')){
		document.getElementById('error-username').remove();
		usernameField.classList.remove('border');
		usernameField.classList.remove('border-danger');
	}
	
	/* Add the error message to the password field */
	if(document.getElementById('error-password')){
		document.getElementById('error-password').remove();
		passwordField.classList.remove('border');
		passwordField.classList.remove('border-danger');
	}
	
	/* Check the name field value */
	if(nameValue == ''){
		nameField.classList.add('border');
		nameField.classList.add('border-danger');
		let nameMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid name!');
		nameMessage.setAttribute('id', 'error-name');
		nameMessage.appendChild(textMessage);
		document.getElementById('name-container').appendChild(nameMessage);
		checkName = false;
	}
	
	/* Check the name field value */
	if(dateValue == ''){
		dateField.classList.add('border');
		dateField.classList.add('border-danger');
		let dateMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid date!');
		dateMessage.setAttribute('id', 'error-date');
		dateMessage.appendChild(textMessage);
		document.getElementById('date-container').appendChild(dateMessage);
		checkDate = false;
	}
	
	/* Check the surname field value */
	if(surnameValue == ''){
		surnameField.classList.add('border');
		surnameField.classList.add('border-danger');
		let surnameMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid surname!');
		surnameMessage.setAttribute('id', 'error-surname');
		surnameMessage.appendChild(textMessage);
		document.getElementById('surname-container').appendChild(surnameMessage);
		checkSurname = false;
	}
	
	/* Check the name field value */
	if(passwordValue == ''){
		passwordField.classList.add('border');
		passwordField.classList.add('border-danger');
		let passwordMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid password!');
		passwordMessage.setAttribute('id', 'error-password');
		passwordMessage.appendChild(textMessage);
		document.getElementById('password-container').appendChild(passwordMessage);
		checkPassword = false;
	}
	
	/* Check the name field value */
	if(usernameValue == ''){
		usernameField.classList.add('border');
		usernameField.classList.add('border-danger');
		let usernameMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid username!');
		usernameMessage.setAttribute('id', 'error-username');
		usernameMessage.appendChild(textMessage);
		document.getElementById('username-container').appendChild(usernameMessage);
		checkUsername = false;
	}
	
	return checkName && checkUsername && checkPassword && checkDate && checkSurname;
}

/**
 * Function that parse the input fields for the HTTP Request
 * @returns
 */
function parseInput(){
	return 'name=' + nameField.value + '&surname=' + surnameField.value + '&date=' + dateField.value + '&username=' + usernameField.value + '&password=' + passwordField.value;
}
