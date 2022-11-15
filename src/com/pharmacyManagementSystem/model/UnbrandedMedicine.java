package com.pharmacyManagementSystem.model;

import org.json.JSONException;
import org.json.JSONObject;

public class UnbrandedMedicine extends Medicine{
	
	private String description;
	private String brandedMedicine;
	
	public UnbrandedMedicine(){
		super();
		this.description = new String();
		this.brandedMedicine = new String();
	}
	
	public UnbrandedMedicine(String name, float cost, int prescription, String description, String brandedMedicine){
		super(name, cost, prescription);
		this.description = description;
		this.brandedMedicine = brandedMedicine;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrandedMedicine() {
		if(this.brandedMedicine == null || this.brandedMedicine == "null"){
			return "No medicine associated!";
		}
		return brandedMedicine;
	}

	public void setBrandedMedicine(String brandedMedicine) {
		this.brandedMedicine = brandedMedicine;
	}

	@Override
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("name", super.getName());
			json.put("cost", super.getCost());
			json.put("prescription", super.isPrescripted());
			json.put("description", this.getDescription());
			json.put("medicine", this.getBrandedMedicine());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public String toString() {
		return super.toString() + " " + this.getDescription() + " " + this.getBrandedMedicine();
	}
}
