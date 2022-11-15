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

import com.pharmacyManagementSystem.model.Cryptograph;
import com.pharmacyManagementSystem.model.OracleConnection;

/**
 * Servlet for the authentification of an User 
 */
@WebServlet(description = "Servlet for authentify an user", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private Cryptograph crypto;
      
	/**
	 * Init server to initialize the class' attributes
	 */
	public void init(ServletConfig config) throws ServletException {
		this.username = new String();
		this.password = new String();
		try {
			this.crypto = new Cryptograph();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Method to manage the GET request made by the page homePage.jsp
	 * the method check if the session's value is settet and then provide to destroy it
	 * to garantee the logout.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("username") != null){
			request.getSession().removeAttribute("username");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.jsp");
			requestDispatcher.forward(request, response);
			response.setStatus(200);
		}else{
			/* Unauthorized status */
			response.setStatus(401);
		}
	}

	/**
	 * Method to manage the POST request made by the page login.jsp
	 * the method check if the username and password field are correct 
	 * and then response to the client with an Ok message or an Error message
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(this.username != null && this.password != null){
			this.username = request.getParameter("username").toString();
			this.password = request.getParameter("password").toString();
			try {
				String cryptedPassword = OracleConnection.getInstance().getPassword(this.username);
				if(this.crypto.decrypt(cryptedPassword).equals(this.password)){
					/* If the insert password is correct, answer to the page with status 200 */
					request.getSession().setAttribute("username", this.username); //Set the session
					response.setStatus(200);
					response.getWriter().print("Ok");
				}else{
					/* Else answer to the page with 401, wrong password */
					response.setStatus(401);
					response.getWriter().print("Error");
				}
			} catch (SQLException e) {
				/* If an exception is raised, then an error code 500 is returned */
				response.setStatus(500);
				response.getWriter().print("Error");
				e.printStackTrace();
			}
		}
	}
	
	

	/**
	 * Destroy method to delete the class' attributes
	 */
	public void destroy() {	
		this.username = null;
		this.password = null;
	}

}
