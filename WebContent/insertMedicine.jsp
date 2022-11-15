<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.LinkedList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"crossorigin="anonymous">
	    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	    <link rel="stylesheet" href="css/insertMedicineStyle.css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert Medicine/Cosmetic</title>
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
			<div class = "card-container">
				<div class = "card w3-container w3-center w3-animate-left">
					<div class = "card-body">
						<h3 class = "card-title">Insert Medicine/Cosmetics</h3>
						<p class = "card-text">Chose the element to insert and complete the form</p>
						<div class="btn-group btn-group-toggle" data-toggle="buttons">
							<label class="btn btn-secondary active">
							    <input type="radio" name="options" id="medicine-radio" autocomplete="off" onchange = "showMedicines()"> Medicines
							</label>
							<label class="btn btn-secondary">
							    <input type="radio" name="options" id="cosmetic-radio" autocomplete="off" onchange = "showCosmetics()"> Cosmetics
						  	</label>
						</div>
						<div id = "cosmetic-form" class = "form-container" hidden>
							<div class = "insert-form text-center">
								<div class="form-group" id = "cosmetic-name-container">
					                <input id = "cosmetic-name" type="text" class="form-control rounded-pill form-control-lg" placeholder="Name">
					            </div>
								<div class="form-group" id = "cosmetic-description-container">
					                <input id = "cosmetic-description" type="text" class="form-control rounded-pill form-control-lg" placeholder="Description">
					            </div>
								<div class="form-group" id = "cosmetic-cost-container">
								    <input id = "cosmetic-cost" type="number" class="form-control rounded-pill form-control-lg" placeholder="Cost" min = "1">
								</div>
								<div class = "form-group">
									<label for = "select-company">Select the type</label>
									<select class = "form-control rounded-pill form-control-lg" id = "select-type">
										<option>Cosmetic</option>
										<option>Hygiene Product</option>
										<option>Baby Product</option>
									</select>
								</div>
							</div>
						</div>
						<div id = "medicine-form" class = "form-container" hidden>
						 	<div class="insert-form text-center">
					            <div class="form-group" id = "name-container">
					                <input id = "name" type="text" class="form-control rounded-pill form-control-lg" placeholder="Name">
					            </div>
					            <div class="form-group" id = "description-container">
					                <input id = "description" type="text" class="form-control rounded-pill form-control-lg" placeholder="Description">
					            </div>
					            <div class = "form-group">
					            	<div class = "row justify-content-center align-items-center">
										<div class = "col justify-content-center align-items-center">
						            		<div class="form-group" id = "licence-container">
								                <input id = "licence" type="number" class="form-control rounded-pill form-control-lg" placeholder="Licence" min = "1" max = "5">
								            </div>
						            	</div>
						            	<div class = "col justify-content-center align-items-center">
						            		<div class="form-group" id = "cost-container">
								                <input id = "cost" type="number" class="form-control rounded-pill form-control-lg" placeholder="Cost" min = "1">
								            </div>
						            	</div>
					            	</div>
					            </div>
					            <div class="btn-group-toggle" data-toggle="buttons">
								  	<label class="btn btn-primary active" id = "label-prescript">
								    	<input id = "checkbox" type="checkbox" checked autocomplete="off" value = "Prescripted" onclick = "changeCheck()">Prescripted
								  	</label>
								</div>
								<div class = "form-group">
									<label for = "select-company">Select the company</label>
									<select class = "form-control rounded-pill form-control-lg" id = "select-company" onclick = "selectCompany()">
										<%
											for(String s : (LinkedList<String>)request.getAttribute("names")){
												%>
													<option><%=s%></option>
												<% 
											}
										%>
										<option>Insert a new company</option>
									</select>
								</div>
								<div class = "form-container" id = "company-container" hidden>
									<div class = "insert-form text-center container">
										<div class="form-group" id = "company-name-container">
							                <input id = "company-name" type="text" class="form-control rounded-pill form-control-lg company-field" placeholder="Company name">
							            </div>
							            <div class="form-group" id = "street-container">
							                <input id = "street" type="text" class="form-control rounded-pill form-control-lg company-field" placeholder="Street name">
							            </div>
							            <div class="form-group" id = "street-code-container">
							                <input id = "street-code" type="number" class="form-control rounded-pill form-control-lg company-field" placeholder="Street code" min = 1>
							            </div>
							            <div class="form-group" id = "city-container">
							                <input id = "city" type="text" class="form-control rounded-pill form-control-lg company-field" placeholder="City name">
							            </div>
							            <div class="form-group" id = "country-container">
							                <input id = "country" type="text" class="form-control rounded-pill form-control-lg company-field" placeholder="Country name">
							            </div>
									</div>
								</div>
					        </div>
						</div>
						<div class = "row justify-content-center align-items-center">
							<div class = "col-md-2">
								<button id = "done-button" type = "button" class = "btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase" onclick = "insert()">Done</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<script type = "text/javascript" src = "script/insertMedicineScript.js"></script>
	    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"crossorigin="anonymous"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"crossorigin="anonymous"></script>
	    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"crossorigin="anonymous"></script>
	</body>
</html>