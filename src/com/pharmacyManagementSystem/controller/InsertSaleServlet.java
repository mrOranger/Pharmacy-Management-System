package com.pharmacyManagementSystem.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.pharmacyManagementSystem.model.OracleConnection;
import com.pharmacyManagementSystem.model.Product;
import com.pharmacyManagementSystem.model.Sale;

/**
 * Servlet to register a new sale in the database
 */
@WebServlet(description = "Servlet to register a new sale in the database", urlPatterns = { "/insertSale" })
public class InsertSaleServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private LinkedList<Sale> sales;
	
	/**
	 * Method to initialize Servlet's attributes
	 */
	public void init(ServletConfig config) throws ServletException {
		this.sales = new LinkedList<Sale>();
	}

	/**
	 * Method responding to an HTTP GET request, redirecting to the correct .jsp page
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/insertSale.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * Method responding to an HTTP POST request, inserting the values given in input
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("data") != null){
			try {
				JSONObject json = new JSONObject(request.getParameter("data"));
				Sale currentSale = new Sale(0, json.getInt("totalProducts"), "", (float)json.getDouble("total"));
				for(int i = 0; i < json.getJSONArray("sales").length(); i++){
					String name = json.getJSONArray("sales").getJSONObject(i).getString("name");
					float total = (float)json.getJSONArray("sales").getJSONObject(i).getDouble("cost");
					int quantity = json.getJSONArray("sales").getJSONObject(i).getInt("quantity");
					currentSale.getProductsSold().add(new Product(name, total, quantity));
				}
				try {
					if(OracleConnection.getInstance().registerASale(currentSale)){
						response.setStatus(200);
					}else{
						response.setStatus(500);
					}
				} catch (SQLException e) {
					response.setStatus(500);
					e.printStackTrace();
				}
			} catch (JSONException e) {
				response.setStatus(500);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method to destroy Servlet's attributes
	 */
	public void destroy() {
		this.sales = null;
	}

}
