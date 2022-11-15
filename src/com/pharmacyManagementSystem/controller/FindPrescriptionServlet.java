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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pharmacyManagementSystem.model.BrandedMedicine;
import com.pharmacyManagementSystem.model.OracleConnection;
import com.pharmacyManagementSystem.model.Prescription;

/**
 * Servlet implementation class FindPrescriptionServlet
 */
@WebServlet(description = "Servlet to find some prescriptions", urlPatterns = { "/printPrescriptions" })
public class FindPrescriptionServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private List<Prescription> prescriptions;
	private String name;
	private String code;
	private String surname;

	/**
	 * Method to initialize Servlet's attributes
	 */
	public void init(ServletConfig config) throws ServletException {
		this.prescriptions = new LinkedList<Prescription>();
		this.name = new String();
		this.surname = new String();
		this.code = new String();
	}

	/**
	 * Method to manage the HTTP GET methods
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/findPrescriptions.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Method to response to an HTTP POST method
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkParameters(request)){
			this.code = request.getParameter("code");
			this.surname = request.getParameter("surname");
			this.name = request.getParameter("name");
			try {
				this.prescriptions = (LinkedList<Prescription>)OracleConnection.getInstance().getPrescriptions(this.name, this.surname, this.code);
				JSONObject jsonObject = new JSONObject();
				JSONArray prescriptionsArray = new JSONArray();
				for(Prescription p : this.prescriptions){
					JSONObject currObject = new JSONObject();
					currObject.put("code", p.getCode());
					currObject.put("products", p.getNumberOfProducts());
					JSONArray medicineArray = new JSONArray();
					for(BrandedMedicine b : p.getMedicines()){
						JSONObject currMedicine = new JSONObject();
						currMedicine.put("name", b.getName());
						medicineArray.put(currMedicine);
					}
					currObject.put("medicine", medicineArray);
					prescriptionsArray.put(currObject);
				}
				jsonObject.put("prescriptions", prescriptionsArray);
				response.getWriter().print(jsonObject);
				response.setStatus(200);
			} catch (SQLException | JSONException e) {
				e.printStackTrace();
				response.setStatus(500);
			}
		}else{
			response.setStatus(500);
		}
	}
	
	private boolean checkParameters(HttpServletRequest request){
		return (request.getParameter("code") != null) && (request.getParameter("name") != null) && (request.getParameter("surname") != null);
	}
	
	/**
	 * Method to destroy Servlet's attributes
	 */
	public void destroy() {
		this.prescriptions = null;
		this.name = null;
		this.surname = null;
		this.code = null;
	}

}
