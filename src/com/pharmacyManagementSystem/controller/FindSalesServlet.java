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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.LinkedList;

import com.pharmacyManagementSystem.model.OracleConnection;
import com.pharmacyManagementSystem.model.Sale;

/**
 * Servlet implementation class FindSalesServlet
 */
@WebServlet(description = "Servlet to find the Sales contained in the Database", urlPatterns = { "/findSales" })
public class FindSalesServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private List<Sale> sales;
	private String minDate;
	private String maxDate;
	
	/**
	 * Init method to initialize Servlet's methods
	 */
	public void init(ServletConfig config) throws ServletException {
		this.sales = new LinkedList<Sale>();
		this.minDate = new String();
		this.maxDate = new String();
	}

	/**
	 * Method that retrieve and return all the sales recorded into the Database
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			this.sales = OracleConnection.getInstance().getSales();
			request.setAttribute("sales", this.sales);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/findSales.jsp");
			dispatcher.forward(request, response);
			response.setStatus(200);
		} catch (SQLException e) {
			response.setStatus(500);
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("code") != null){
			/* Request concerning a delete of an element */
			int code = Integer.parseInt(request.getParameter("code"));
			try {
				if(OracleConnection.getInstance().deleteSale(code)){
					response.setStatus(200);
				}else{
					response.setStatus(500);
				}
			} catch (SQLException e) {
				response.setStatus(500);
				e.printStackTrace();
			}
		}
		if(request.getParameter("data_min") != null && request.getParameter("data_max") != null){
			/* Request concerning the finding of a date */
			this.minDate = request.getParameter("data_min");
			this.maxDate = request.getParameter("data_max");
			try {
				this.sales = OracleConnection.getInstance().findSalesByDate(this.minDate, this.maxDate);
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				for(int i = 0; i < this.sales.size(); i++){
					JSONObject currObject = new JSONObject();
					currObject.put("code", this.sales.get(i).getSaleCode());
					currObject.put("quantity", this.sales.get(i).getSoldQuantity());
					currObject.put("day", this.sales.get(i).getDay());
					currObject.put("total", this.sales.get(i).getSoldTotal());
					JSONArray products = new JSONArray();
					for(int j = 0; j < this.sales.get(i).getProductsSold().size(); j++){
						JSONObject productObject = new JSONObject();
						productObject.put("name", this.sales.get(i).getProductsSold().get(j).getProductName());
						productObject.put("quantity", this.sales.get(i).getProductsSold().get(j).getQuantity());
						productObject.put("total", this.sales.get(i).getProductsSold().get(j).getTotal());
						products.put(productObject);
					}
					currObject.put("products", products);
					jsonArray.put(currObject);
				}
				jsonObject.put("sales", jsonArray);
				response.getWriter().print(jsonObject);
				response.setStatus(200);
			} catch (SQLException | JSONException e) {
				e.printStackTrace();
				response.setStatus(500);
			}
			response.setStatus(200);
		}
	}
	
	/**
	 * Method to destroy Servlet's attributes
	 */
	public void destroy() {
		this.sales = null;
		this.maxDate = null;
		this.minDate = null;
	}

}
