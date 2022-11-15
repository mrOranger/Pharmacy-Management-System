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

import com.pharmacyManagementSystem.model.OracleConnection;

/**
 * Servlet for manage the insertion of a new Medicine/Cosmetic
 */
@WebServlet(urlPatterns = { "/insertElement" }) 
public class InsertElementServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	/* Medicine's attribute */
	private List<String> companies;
	private String medicineName;
	private String medicineDescription;
	private String medicineLicence;
	private String medicineCost;
	private int medicinePrescription;
	private String companyName;
	private String streetName;
	private int streetCode;
	private String city;
	private String country;
	
	/* Cosmetic's attributes */
	private String cosmeticName;
	private String cosmeticDescription;
	private float cosmeticCost;
	private String cosmeticType;
	
	/**
	 * Method that initialize the Servlet's attributes
	 */
	@Override
	public void init() throws ServletException {
		this.companies = new LinkedList<String>();
		this.medicineName = new String();
		this.medicineDescription = new String();
		this.medicineLicence = new String();
		this.medicineCost = new String();
		this.medicinePrescription = 0;
		this.companyName = new String();
		this.streetCode = 0;
		this.streetName = new String();
		this.city = new String();
		this.country = new String();
		this.cosmeticName = new String();
		this.cosmeticDescription = new String();
		this.cosmeticCost = 0;
		this.cosmeticType = new String();
	}

	/**
	 * Method that responde to an HTTP GET method redirecting the user to the page
	 * insertMedicine.jsp, sending it also the values needed
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/insertMedicine.jsp");
			this.companies = (LinkedList<String>)OracleConnection.getInstance().getCompaniesNames();
			request.setAttribute("names", this.companies);
			dispatcher.forward(request, response); //Redirection to the JSP page
		} catch (SQLException e) {
			System.err.println("SQL throw an exception!");
			System.err.println("Error code: " + e.getErrorCode());
			System.err.println("SQL message: " + e.getMessage());
			response.setStatus(500);
		}
	}

	/**
	 * Method that response to an HTTP POST method, inserting the new element into the database
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("cosmeticName") != null){
			/* The request involves a Cosmetic */
			this.cosmeticName = request.getParameter("cosmeticName");
			this.cosmeticDescription = request.getParameter("description");
			this.cosmeticCost = Float.parseFloat(request.getParameter("cost"));
			this.cosmeticType = request.getParameter("type");
			try {
				if(OracleConnection.getInstance().insertNewCosmetic(this.cosmeticName, this.cosmeticDescription, this.cosmeticCost, this.cosmeticType)){
					response.setStatus(200);
				}else{
					response.setStatus(500);
				}
			} catch (NumberFormatException | SQLException e) {
				response.setStatus(500);
				e.printStackTrace();
			}
		}else{
			/* The request involves a Medicine */
			this.medicineName = request.getParameter("medicineName");
			this.medicineDescription = request.getParameter("description");
			this.medicineLicence = request.getParameter("licenceYears");
			this.medicineCost = request.getParameter("cost");
			this.medicinePrescription = this.convertPrescription(request.getParameter("prescripted"));
			this.companyName = request.getParameter("companyName");
			if(this.checkInputFields(request) == true){
				/* The received request regard only the medicine insertion */
				try {
					if(OracleConnection.getInstance().insertNewMedicine(this.medicineName, this.medicineDescription, Float.parseFloat(this.medicineCost), this.medicinePrescription, Integer.parseInt(this.medicineLicence), this.companyName)){
						response.setStatus(200);
					}else{
						response.setStatus(500);
					}
				} catch (NumberFormatException | SQLException e) {
					response.setStatus(500);
					e.printStackTrace();
				}
			}else{
				/* The received request regard also the companie's information */
				this.streetName = request.getParameter("streetName");
				this.streetCode = Integer.parseInt(request.getParameter("streetCode"));
				this.city = request.getParameter("city");
				this.country = request.getParameter("country");
				try {
					if(OracleConnection.getInstance().insertNewMedicine(this.medicineName, this.medicineDescription, Float.parseFloat(this.medicineCost), this.medicinePrescription, Integer.parseInt(this.medicineLicence), this.companyName, this.streetName, this.streetCode, this.city, this.country)){
						response.setStatus(200);
					}else{
						response.setStatus(500);
					}
				} catch (NumberFormatException | SQLException e) {
					response.setStatus(500);
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Method that check the input fields to manage different request based on the information given
	 * @param HttpServletRequest
	 * @return boolean
	 */
	private boolean checkInputFields(HttpServletRequest request){
		return request.getParameter("streetName") == null || request.getParameter("streetCode") == null || request.getParameter("city") == null || request.getParameter("country") == null;
	}
	
	private int convertPrescription(String prescription){
		if(prescription.equals("true")){
			return 1;
		}
		return 0;
	}

	/**
	 * Method that destroy all the Servlet's attributes
	 */
	@Override
	public void destroy() {
		this.companies = null;
		this.medicineName = null;
		this.medicineDescription = null;
		this.medicineLicence = null;
		this.medicineCost = null;
		this.medicinePrescription = 0;
		this.companyName = null;
		this.streetCode = 0;
		this.streetName = null;
		this.city = null;
		this.country = null;
		this.cosmeticName = null;
		this.cosmeticDescription = null;
		this.cosmeticCost = 0;
		this.cosmeticType = null;
	}
	
	
	

}
