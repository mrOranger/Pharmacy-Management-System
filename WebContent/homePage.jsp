<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"crossorigin="anonymous">
	    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	    <link rel="stylesheet" href="css/homeStyle.css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Home Page</title>
	</head>
	<body>
		<%
			if(request.getSession().getAttribute("username") == null){
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/");
				requestDispatcher.forward(request, response);
			}
		%>
		<nav class = "navbar navbar-expand-lg navbar-light bg-light">
			<div class = "collapse navbar-collapse">
				<ul class = "navbar-nav mr-auto">
					<li class = "nav-item active"><a class="nav-link" href="/PharmacyManagementSystem/findMedicine">Find Medicines<span class="sr-only">(current)</span></a></li>
					<li class = "nav-item active"><a class="nav-link" href="/PharmacyManagementSystem/findUnbranded">Find Unbranded Medicines<span class="sr-only">(current)</span></a></li>
					<li class = "nav-item active"><a class="nav-link" href="/PharmacyManagementSystem/findSales">Print Sales<span class="sr-only">(current)</span></a></li>
					<li class = "nav-item active"><a class="nav-link" href="/PharmacyManagementSystem/printStatistics">Print Statistics<span class="sr-only">(current)</span></a></li>
					<li class = "nav-item active"><a class="nav-link" href="/PharmacyManagementSystem/printPrescriptions">Find Prescription <span class="sr-only">(current)</span></a></li>
				</ul>
				<a class="navbar-brand" href="/PharmacyManagementSystem/login"><%= request.getSession().getAttribute("username") %></a>
			</div>
		</nav>
		<div class = "container-fluid">
			<div class = "row card-container" >
				<div class = "col-md-4" id = "card-container1">
					<div class = "card w3-container w3-center w3-animate-left">
						<div class = "card-body">
							<h3 class = "card-title">Insert Medicine/Cosmetics</h3>
							<p class = "card-text">Insert a new Medicine or a new Cosmetics into the Database. You must specify all the required values. You can also add the Pharmaceutical Company that produces it.</p>
							<div class = "row justify-content-center align-items-center">
								<div class = "col-md-4">
									<a href="/PharmacyManagementSystem/insertElement" class="btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase">Go</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class = "col-md-4" id = "card-container2">
					<div class = "card w3-container w3-center w3-animate-left">
						<div class = "card-body">
							<h3 class = "card-title">Insert Prescription</h3>
							<p class = "card-text">Insert a new Prescription into the Database. You must specify also the related Medicines present into the Database.</p>
							<div class = "row justify-content-center align-items-center">
								<div class = "col-md-4">
									<a href="/PharmacyManagementSystem/insertPrescription" class="btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase">Go</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class = "col-md-4" id = "card-container3">
					<div class = "card w3-container w3-center w3-animate-left">
						<div class = "card-body">
							<h3 class = "card-title">Insert Sale</h3>
							<p class = "card-text">Register a new Sale into the Database. You must specify the products involved in the Sale and the cost's specifications</p>
							<div class = "row justify-content-center align-items-center">
								<div class = "col-md-4">
									<a href="/PharmacyManagementSystem/insertSale" class="btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase">Go</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class = "row justify-content-center align-items-center">
				<div class = "col-md-2">
					<button id = "logout-button" type = "button" class = "btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase" onclick = "logout()">Logout</button>
				</div>
			</div>
		</div>
		<script type = "text/javascript" src = "script/homeScript.js"></script>
	    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"crossorigin="anonymous"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"crossorigin="anonymous"></script>
	    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"crossorigin="anonymous"></script>
	</body>
</html>