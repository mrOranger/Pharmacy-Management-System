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
import com.pharmacyManagementSystem.model.Report;

/**
 * Servlet that retrieve the information about the reports of sales
 */
@WebServlet(description = "Servlet to retrieve all the statistics associated to a Report", urlPatterns = { "/printStatistics" })
public class PrintStatisticsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private List<Report> reports;

	/**
	 * Method that initialize the Servlet's attribute
	 */
	public void init(ServletConfig config) throws ServletException {
		this.reports = new LinkedList<Report>();
	}

	/**
	 * Method that response to an HTTP GET about the report's view
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/printStatistics.jsp");
		try {
			this.reports = (LinkedList<Report>)OracleConnection.getInstance().getReports();
			if(this.reports != null){
				request.setAttribute("reports", this.reports);
				dispatcher.forward(request, response);
				response.setStatus(200);
			}else{
				response.setStatus(500);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(500);
		}
	}
	
	/**
	 * Method that destroy the Servlet's attributes
	 */
	public void destroy() {
		this.reports = null;
	}

}
