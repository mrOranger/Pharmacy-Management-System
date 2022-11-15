const usernameField = document.getElementById('username');
const passwordField = document.getElementById('password');
const usernameContainer = document.getElementById('username-container');
const passwordContainer = document.getElementById('password-container');
const header = document.getElementById('header');

/**
 * Function that submit the request to the Server,
 * making use of validate() and parseInput() functions
 */
function submit(){
	if(validate(usernameField, passwordField)){
		const xhttp = new XMLHttpRequest();	
		/* Building of the callback function on the readychagestate change */
		xhttp.onreadystatechange = function(){
			if(this.readyState == 4 && this.status == 500){
				alert('Error during the submission!');
			}
			if(this.readyState == 4 && this.status == 401){
				/* If the server response with the 401 message add a message error*/
				if(!document.getElementById('error-message')){
					header.innerHTML += `<br><p id = "error-message">Username or password wrong!</p>`;
					document.getElementById('error-message').setAttribute('style', 'color: red');
				}else{
					document.getElementById('error-message').remove();
					document.getElementsByTagName('br')[0].remove();
					header.innerHTML += `<br><p id = "error-message">Username or password wrong!</p>`;
					document.getElementById('error-message').setAttribute('style', 'color: red');
				}
			}
			if(this.readyState == 4 && this.status == 200){
				/* If the server answer with the 200 code then redirect the user to the right page */
				if(xhttp.responseText == 'Ok'){
					location.href = 'http://localhost:8080/PharmacyManagementSystem/homePage.jsp';
				}
			}
		}	
		/* Sending the request */
		xhttp.open('POST', '/PharmacyManagementSystem/login', true); //True means that the request is asynch
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send(parseInput());
	}
}

/**
 * Function that checks if the inputs are valid or not,
 * if they aren't add an error message to the form
 * @param usernameField
 * @param passwordField
 * @returns Boolean
 */
function validate(usernameField, passwordField){
	
	let checkUsername = true;
	let checkPassword = true;
	
	/* Add the error message to the username field */
	if(document.getElementById('error-username') != null){
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
	
	/* Check the username field value */
	if(usernameField.value == ''){
		usernameField.classList.add('border');
		usernameField.classList.add('border-danger');
		let usernameMessage = document.createElement('p');
		let textMessage = document.createTextNode('Please, insert a valid username!');
		usernameMessage.setAttribute('id', 'error-username');
		usernameMessage.appendChild(textMessage);
		usernameContainer.appendChild(usernameMessage);
		checkUsername = false;
	}
	
	/* Check the password field value */
	if(passwordField.value == ''){
		passwordField.classList.add('border');
		passwordField.classList.add('border-danger');
		let passwordMessage = document.createElement('p');
		let textMessage = document.createTextNode('Password field cannot be empty!');
		passwordMessage.setAttribute('id', 'error-password');
		passwordMessage.appendChild(textMessage);
		passwordContainer.appendChild(passwordMessage);
		checkPassword = false;
	}
	return checkUsername && checkPassword;
}

/**
 * Function that generate the string containing the values to send to the server
 * @param usernameField
 * @param passwordField
 * @returns String
 */
function parseInput(){
	return 'username=' + usernameField.value + '&password=' + passwordField.value;
}