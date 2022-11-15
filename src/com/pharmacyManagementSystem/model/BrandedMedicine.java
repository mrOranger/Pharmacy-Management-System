package com.pharmacyManagementSystem.model;

import org.json.JSONException;
import org.json.JSONObject;

public class BrandedMedicine extends Medicine implements Comparable<BrandedMedicine>{
	
	private String name;
	private boolean isPrescripted;
	private float cost;
	private int years;
	private String company;
	
	public BrandedMedicine(){
		super();
		this.name = new String();
		this.cost = 0;
		this.isPrescripted = false;
		this.years = 0;
		this.company = new String();
	}
	
	public BrandedMedicine(String name, float cost, int prescription, int years, String comp){
		this.name = name;
		this.cost = cost;
		this.years = years;
		this.company = comp;
		if(prescription == 1){
			this.isPrescripted = true;
		}else{
			this.isPrescripted = false;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	public boolean isPrescripted() {
		return isPrescripted;
	}

	public void setPrescripted(boolean isPrescripted) {
		this.isPrescripted = isPrescripted;
	}
	
	@Override
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("name", this.name);
			json.put("cost", this.cost);
			json.put("years", this.years);
			json.put("company", this.company);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@Override
	public int compareTo(BrandedMedicine medicine) {
		return this.name.compareTo(medicine.getName());
	}
	
	@Override
	public String toString(){
		return super.toString() + " " + this.years + " " + this.company;
	}
}
