<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.LinkedList" %>
<%@ page import = "com.pharmacyManagementSystem.model.Doctor" %>
<%@ page import = "com.pharmacyManagementSystem.model.Patient" %>
<%@ page import = "com.pharmacyManagementSystem.model.BrandedMedicine" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"crossorigin="anonymous">
	    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	   	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
	    <link rel="stylesheet" href="css/insertPrescriptionStyle.css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert Prescription</title>
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
		</nav>		<!-- Main container -->
		<div class = "d-flex justify-content-center align-items-center insert-container">
			<div class = "card">
				<div class = "card-body vh-100">
					<div class = "insert-form">
						<div class = "row">
							<div class = "col-md-3">
								<h4 id = "total-products">Total products: 1</h4>
							</div>
							<div class = "col">
								<button type="button" class="btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase" onclick = "submit()">Register</button>
							</div>
						</div>
						<hr />
						<h4 id = "header">Patient's information</h4>
						<div class = "row">
							<div class = "col" id = "user-code">
									<select class = "form-control rounded-pill form-control-lg" id = "select-user-code">
										<%
											for(Patient p : (LinkedList<Patient>)request.getAttribute("patients")){
												%>
													<option><%=p.getTaxCode()%></option>
												<% 
											}
										%>
										<option>Select patient's code</option>
									</select>
							</div>
							<div class = "col" id = "user-name">
								<input id = "user-name-input" type="text" class="form-control rounded-pill form-control-lg" placeholder="Name">
							</div>
							<div class = "col" id = "user-surname">
								<input id = "user-surname-input" type="text" class="form-control rounded-pill form-control-lg" placeholder="Surname">
							</div>
						</div>
						<hr />
						<div id = "insert-form">
							<div class = "row" id = "row-1 ">
								<div class = "col" id = "product-name-container-1">
									<input id = "product-name-1" type="text" class="form-control rounded-pill form-control-lg product-name" placeholder="Product name" onchange = "setValueName(1)">
								</div>
								<div class = "col-md-4 add-icon " id = "add-1">
									<i class="rounded-pill form-control-lg product-cost fa-icon fas fa-plus" onclick = "addProduct()"></i>
								</div>
							</div>
						</div>
						<hr />
							<div class = "col" id = "doctor-code">
								<h4 id = "header">Doctor's code</h4>	
								<select class = "form-control rounded-pill form-control-lg" id = "select-doctor-code">
								<%
								for(Doctor p : (LinkedList<Doctor>)request.getAttribute("doctors")){
									%>
									<option><%=p.getRegistrationNumber()%></option>
									<% 
								}
								%>
							</select>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<script type = "text/javascript" src = "script/insertPrescriptionScript.js"></script>
	    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"crossorigin="anonymous"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"crossorigin="anonymous"></script>
	    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"crossorigin="anonymous"></script>
	</body>
</html>