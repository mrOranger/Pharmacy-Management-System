package com.pharmacyManagementSystem.model;

import java.util.LinkedList;
import java.util.List;

public class Sale implements Comparable<Sale>{

	private int saleCode;
	private int soldQuantity;
	private String day;
	private float soldTotal;
	private List<Product> productsSold;
	
	public Sale(){
		this.saleCode = 0;
		this.soldQuantity = 0;
		this.day = new String();
		this.soldTotal = 0;
		this.productsSold = new LinkedList<Product>();
	}
	
	public Sale(int saleCode, int soldQuantity, String day, float soldTotal){
		this.saleCode = saleCode;
		this.soldQuantity = soldQuantity;
		this.day = day;
		this.soldTotal = soldTotal;
		this.productsSold = new LinkedList<Product>();
	}
	
	public Sale(int saleCode, int soldQuantity, String day, float soldTotal, List<Product> productsSold){
		this.saleCode = saleCode;
		this.soldQuantity = soldQuantity;
		this.day = day;
		this.soldTotal = soldTotal;
		this.productsSold = productsSold;
	}

	public int getSaleCode() {
		return saleCode;
	}

	public void setSaleCode(int saleCode) {
		this.saleCode = saleCode;
	}

	public int getSoldQuantity() {
		return soldQuantity;
	}

	public void setSoldQuantity(int soldQuantity) {
		this.soldQuantity = soldQuantity;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public float getSoldTotal() {
		return soldTotal;
	}

	public void setSoldTotal(float soldTotal) {
		this.soldTotal = soldTotal;
	}

	public List<Product> getProductsSold() {
		return productsSold;
	}

	public void setProductsSold(List<Product> productsSold) {
		this.productsSold = productsSold;
	}

	@Override
	public int compareTo(Sale arg0) {
		if(this.getSaleCode() > arg0.getSaleCode()){
			return 1;
		}
		if(this.getSaleCode() < arg0.getSaleCode()){
			return -1;
		}
		return 0;
	}
	
	@Override
	public String toString(){
		String str = this.getSaleCode() + " " + this.getSoldQuantity() + " " + this.getDay() + " " + this.getSoldTotal() + '\n';
		for(Product p : this.getProductsSold()){
			str += p.getProductName() + " " + p.getQuantity() + " " + p.getTotal() + '\n';
		}
		return str;
	}
	
	
	
}
