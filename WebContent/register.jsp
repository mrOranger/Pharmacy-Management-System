<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"crossorigin="anonymous">
	    <link rel="stylesheet" href="css/registerStyle.css">
	    <title>Register</title>
	</head>
	
	<body>
	    <div class="d-flex justify-content-center align-items-center login-container">
	        <div class="login-form text-center">
	            <h1 class="mb-5 font-weight-light text-uppercase" id = "header">Register you account</h1>
	            <div class="form-group" id = "name-container">
	                <input id = "name" type="text" class="form-control rounded-pill form-control-lg" placeholder="Name">
	            </div>
	           	<div class="form-group" id = "surname-container">
	                <input id = "surname" type="text" class="form-control rounded-pill form-control-lg" placeholder="Surname">
	            </div>
	           	<div class="form-group" id = "date-container">
	                <input id = "date" type="date" class="form-control rounded-pill form-control-lg" placeholder="Date of birth">
	            </div>
	           	<div class="form-group" id = "username-container">
	                <input id = "username" type="text" class="form-control rounded-pill form-control-lg" placeholder="Username">
	            </div>
	            <div class="form-group" id = "password-container">
	                <input id = "password" type="password" class="form-control rounded-pill form-control-lg" placeholder="Password">
	            </div>
	            <button type="button" class="btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase" onclick = "submit()">Register</button>
	            <p class="mt-3 font-weight-normal">Have an account? <a href="/PharmacyManagementSystem/login.jsp"><strong>Login now</strong></a></p>
	        </div>
	    </div>
		
		<script type = "text/javascript" src = "script/registerScript.js"></script>
	    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"crossorigin="anonymous"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"crossorigin="anonymous"></script>
	    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"crossorigin="anonymous"></script>
	</body>
</html>