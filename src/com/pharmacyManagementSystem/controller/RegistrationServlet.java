package com.pharmacyManagementSystem.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pharmacyManagementSystem.model.Cryptograph;
import com.pharmacyManagementSystem.model.OracleConnection;
import com.pharmacyManagementSystem.utilities.DateUtilities;

/**
 * Servlet that manage HTTP POST request from the page register.jsp
 */
@WebServlet(description = "Servlet to managet POST requesto to register an User", urlPatterns = { "/register" })
public class RegistrationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;
	private String date;
	private String username;
	private String password;
	private Cryptograph cypto;
	private DateUtilities dateUtilites;

	/**
	 * Function that initialize the Servlet's attributes
	 */
	@Override
	public void init() throws ServletException {
		this.name = new String();
		this.surname = new String();
		this.date = new String();
		this.username = new String();
		this.password = new String();
		try {
			this.cypto = new Cryptograph();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.dateUtilites = new DateUtilities();
	}
       
	/**
	 * Method that manage the POST request from the page register.jsp,
	 * checks if the current User doesn't exist yet
	 * and provide to create a new User
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(this.checkValues()){
			this.name = request.getParameter("name").toString();
			this.surname = request.getParameter("surname").toString();
			this.date = this.dateUtilites.formatDate(request.getParameter("date").toString());
			this.username = request.getParameter("username").toString();
			this.password = this.cypto.encrypt(request.getParameter("password").toString());
			boolean registration;
			try {
				registration = OracleConnection.getInstance().registerUser(this.name, this.surname, this.date, this.username, this.password);
				if(registration){
					response.setStatus(200);
					response.getWriter().print("Ok");
				}else{
					response.setStatus(401);
					response.getWriter().print("Error");
				}
			} catch (SQLException e) {
				response.setStatus(500);
				response.getWriter().print("Error");
				e.printStackTrace();
			}
		}else{
			response.setStatus(500);
			response.getWriter().print("Error");
		}
	}
	
	/**
	 * Function that check the validity of the Servlet's fields
	 * @return boolan
	 */
	private boolean checkValues(){
		return this.name != null && this.surname != null && this.date != null && this.username != null && this.password != null && this.cypto != null && this.dateUtilites != null;
	}

	/**
	 * Function that destroy the Servlet's attributes
	 */
	@Override
	public void destroy() {
		this.name = null;
		this.surname = null;
		this.date = null;
		this.username = null;
		this.password = null;
		this.cypto = null;
		this.dateUtilites = null;
	}
}
