<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.pharmacyManagementSystem.model.Sale" %>
<%@ page import = "java.util.LinkedList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"crossorigin="anonymous">
	    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	    <link rel="stylesheet" href="css/findSalesStyle.css">
	    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Find Sales</title>
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
			<h1>Sales contained in the Database</h1>
			<h3>Click on the red cross to delete the element from the database</h3>
			<div class = "container-fluid col-md-10">
				<div class = "form-container">
					<div class = "row">
						<div class = "col">
							<div class = "form-group">
								<input id = "date-1" type="date" class="form-control rounded-pill form-control-lg" placeholder="Min date" />
							</div>
						</div>
						<div class = "col">
							<div class = "form-group">
								<input id = "date-2" type="date" class="form-control rounded-pill form-control-lg" placeholder="Max date" />
							</div>
						</div>
					</div>
					<div class = "row">
						<div class = "col-md-3">
							<button id = "done-button" type = "button" class = "btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase" onclick = "submit()">Find</button>
						</div>
					</div>
				</div>
				<table class = "table table-bordered" id = "table-result">
					<thead>
						<tr>	
							<th>Sale code</th>
							<th>Sale quantity</th>
							<th>Day</th>
							<th>Total</th>
							<th>
								<table align = "center" class = "neste-table-head">
									<thead>
										<tr>
											<th>Product name</th>
											<th>Product quantity</th>
											<th>Product total</th>
										</tr>
									</thead>
								</table>
							</th>
							<th></th>
						</tr>				
					</thead>
					<tbody id = "body">
						<% 
						for(int index = 0; index < ((LinkedList<Sale>)request.getAttribute("sales")).size(); index++){
							%>
								<tr class = "table-row">
									<td><%=((LinkedList<Sale>)request.getAttribute("sales")).get(index).getSaleCode() %></td>
									<td><%=((LinkedList<Sale>)request.getAttribute("sales")).get(index).getSoldQuantity() %></td>
									<td><%=((LinkedList<Sale>)request.getAttribute("sales")).get(index).getDay() %></td>
									<td><%=((LinkedList<Sale>)request.getAttribute("sales")).get(index).getSoldTotal() %></td>
									<td>
										<%
											for(int j = 0; j < ((LinkedList<Sale>)request.getAttribute("sales")).get(index).getProductsSold().size(); j++){
												%>
												<table class = "nested-table" align = "center">
													<tbody>
														<tr>
															<td class = "nested-table-row"><%= ((LinkedList<Sale>)request.getAttribute("sales")).get(index).getProductsSold().get(j).getProductName() %></td>
															<td class = "nested-table-row"><%=  ((LinkedList<Sale>)request.getAttribute("sales")).get(index).getProductsSold().get(j).getQuantity() %></td>
															<td class = "nested-table-row"><%=  ((LinkedList<Sale>)request.getAttribute("sales")).get(index).getProductsSold().get(j).getTotal() %></td>
														</tr>
													</tbody>
												</table>	
												<% 	
											}
										%>
									</td>
									<td><i class="fa fa-trash nested-table-img" aria-hidden="false" onclick = "deleteElement(<%= index %>)"></i></td>
								</tr>					
							<%
							}
						%>
					</tbody>
				</table>
			</div>
		</div>
		<script type = "text/javascript" src = "script/findSalesScript.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"crossorigin="anonymous"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"crossorigin="anonymous"></script>
	    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"crossorigin="anonymous"></script>
	</body>
</html>