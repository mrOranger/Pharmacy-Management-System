package com.pharmacyManagementSystem.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pharmacyManagementSystem.model.BrandedMedicine;
import com.pharmacyManagementSystem.model.OracleConnection;

/**
 * Servlet to find the medicines in the database
 */
@WebServlet(urlPatterns = { "/findMedicine" })
public class FindMedicinesServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private List<BrandedMedicine> medicines;
	private String medicineDelelteName;
	
	/**
	 * Init method to initialize Servlet's attributes
	 */
	@Override
	public void init() throws ServletException {
		this.medicines = new LinkedList<BrandedMedicine>();
		this.medicineDelelteName = new String();
	}

	/**
	 * Method used to manage a GET request, to return all the medicines into the Database
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/findMedicines.jsp");
			this.medicines = (LinkedList<BrandedMedicine>)OracleConnection.getInstance().getMedicines();
			request.setAttribute("medicines", this.medicines);
			dispatcher.forward(request, response);
			response.setStatus(200);
		} catch (SQLException e) {
			response.setStatus(500);
			e.printStackTrace();
		}
	}

	/**
	 * Method used to manage a POST request, to delete a medicine in the database
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* If the parameters are setted */
		if(request.getParameter("name") != null){
			this.medicineDelelteName = request.getParameter("name");
			try {
				if(OracleConnection.getInstance().removeMedicine(this.medicineDelelteName)){
					response.setStatus(200);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.setStatus(500);
			}
		}
	}

	@Override
	public void destroy() {
		this.medicines = null;
		this.medicineDelelteName = null;
	}
}
