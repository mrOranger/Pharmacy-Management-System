package com.pharmacyManagementSystem.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pharmacyManagementSystem.model.OracleConnection;
import com.pharmacyManagementSystem.model.UnbrandedMedicine;

/**
 * Servlet to find medicines without any brand associated
 */
@WebServlet( description = "Servlet for find unbranded medicines", urlPatterns = {"/findUnbranded"})
public class FindUnbrandedMedicine extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private List<UnbrandedMedicine> medicines;

	/**
	 * Method for initializing the Servlet's components
	 */
	public void init(ServletConfig config) throws ServletException {
		this.medicines = new LinkedList<UnbrandedMedicine>(); 
	}

	/**
	 * Method to retrieve and send to the client page the Unbranded Medicines
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			this.medicines = (LinkedList<UnbrandedMedicine>)OracleConnection.getInstance().getUnbrandedMedicines();
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/findUnbranded.jsp");
			request.setAttribute("medicines", this.medicines);
			requestDispatcher.forward(request, response);
			response.setStatus(200);
		} catch (SQLException e) {
			response.setStatus(500);
			e.printStackTrace();
		}
	}

	/**
	 * Method to delete an Unbranded Medicine from the Database
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("name") != null){
			/* Remove the element from the database */
			String medicineName = request.getParameter("name");
			try {
				if(OracleConnection.getInstance().removeUnbrandedMedicine(medicineName)){
					response.setStatus(200);
				}
			} catch (SQLException e) {
				response.setStatus(500);
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method for destroying the Servlet's attributes
	 */
	public void destroy() {
		this.medicines = null;
	}	
}
