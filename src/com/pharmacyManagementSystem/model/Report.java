package com.pharmacyManagementSystem.model;

import java.util.LinkedList;
import java.util.List;

public class Report {
	
	private int semester;
	private int year;
	private float amount;
	private List<Sale> sales;
	
	public Report(){
		this.sales = new LinkedList<Sale>();
		this.semester = 0;
		this.year = 0;
	}
	
	public Report(int semester, int year){
		this.semester = semester;
		this.year = year;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
	
	@Override
	public String toString(){
		String str = this.semester + " " + this.year + '\n';
		for(int i = 0; i < this.getSales().size(); i++){
			str += this.getSales().get(i).getDay() + " " + this.getSales().get(i).getSoldQuantity() + " " + this.getSales().get(i).getSoldTotal() + '\n';
		}
		return str;
	}

}
