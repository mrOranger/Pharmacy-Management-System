package com.pharmacyManagementSystem.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.LinkedList;
import com.pharmacyManagementSystem.model.Patient;
import com.pharmacyManagementSystem.model.Prescription;
import com.pharmacyManagementSystem.model.Doctor;
import com.pharmacyManagementSystem.model.OracleConnection;
import com.pharmacyManagementSystem.model.BrandedMedicine;

/**
 * Servlet to insert a prescription in the Database
 */
@WebServlet(description = "Servlet to insert a prescription in the Database", urlPatterns = { "/insertPrescription" })
public class InsertPrescription extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private List<Patient> patients;
	private List<Doctor> doctors;
	private List<BrandedMedicine> medicines;
	private Prescription prescription;

	/**
	 * Method to initialize Servelt's components
	 */
	public void init(ServletConfig config) throws ServletException {
		this.patients = new LinkedList<Patient>();
		this.doctors = new LinkedList<Doctor>();
		this.medicines = new LinkedList<BrandedMedicine>();
		this.prescription = new Prescription();
	}

	/**
	 * Method to manage an HTTP GET request from the URL /insertPrescription,
	 * returning the patients, doctors and medicines values
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/insertPrescription.jsp");
		try {
			this.patients = (LinkedList<Patient>)OracleConnection.getInstance().getPatients();
			this.doctors = (LinkedList<Doctor>)OracleConnection.getInstance().getDoctors();
			this.medicines = (LinkedList<BrandedMedicine>)OracleConnection.getInstance().getMedicines();
			request.setAttribute("patients", this.patients);
			request.setAttribute("doctors", this.doctors);
			request.setAttribute("medicines", this.medicines);
			requestDispatcher.forward(request, response);
		} catch (SQLException e) {
			response.setStatus(500);
			e.printStackTrace();
		}
	}

	/**
	 * Method to manage an HTTP POST request from the URL /insertPrescription,
	 * inserting a new Prescription in the Database
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Check the validity of the request */
		if(request.getParameter("data") != null){
			try {
				/* Setting the Prescription attribute values */
				JSONObject jsonObject = new JSONObject(request.getParameter("data"));
				this.prescription.setDoctor(jsonObject.getString("doctor"));
				this.prescription.setNumberOfProducts(jsonObject.getInt("totalProducts"));
				Patient currPatient = new Patient();
				currPatient.setName(jsonObject.getJSONObject("patient").getString("name"));
				currPatient.setSurname(jsonObject.getJSONObject("patient").getString("surname"));
				currPatient.setTaxCode(jsonObject.getJSONObject("patient").getString("taxCode"));
				this.prescription.setPatient(currPatient);
				for(int i = 0; i < jsonObject.getJSONArray("products").length(); i++){
					BrandedMedicine currMedicine = new BrandedMedicine();
					currMedicine.setName(jsonObject.getJSONArray("products").getJSONObject(i).getString("name"));
					this.prescription.getMedicines().add(currMedicine);
				}
				if(OracleConnection.getInstance().insertPrescription(prescription)){
					response.setStatus(200); //Response status = ok
				}else{
					response.setStatus(500); //Response status = error
				}
			} catch (JSONException | SQLException e) {
				response.setStatus(500); //Response status = error
				e.printStackTrace();
			}
		}else{
			response.setStatus(500);
		}
	}
	
	/**
	 * Servlet to remove Servlet's components
	 */
	public void destroy() {
		this.patients = null;
		this.doctors = null;
		this.medicines = null;
		this.prescription = null;
	}

}
