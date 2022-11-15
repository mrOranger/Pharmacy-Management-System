package com.pharmacyManagementSystem.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Company {
	private String name;
	private String streetName;
	private int streetCode;
	private String city;
	private String country;
	
	public Company(){
		this.name = new String();
		this.streetName = new String();
		this.streetCode = 0;
		this.city = new String();
		this.country = new String();
	}
	
	public Company(String name, String streetName, int streetCode, String city, String country){
		this.name = name;
		this.streetName = streetName;
		this.streetCode = streetCode;
		this.city = city;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public int getStreetCode() {
		return streetCode;
	}

	public void setStreetCode(int streetCode) {
		this.streetCode = streetCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public JSONObject toJson(){
		JSONObject json = new JSONObject();
		try {
			json.put("name", this.name);
			json.put("streetName", this.streetName);
			json.put("streetCode", this.streetCode);
			json.put("city", this.city);
			json.put("country", this.country);
		} catch (JSONException e) {
			e.printStackTrace();
			System.err.println("JSON format error!");
		}
		return json;
	}
	
	@Override
	public String toString(){
		return this.name + " " + this.streetName + " " + this.streetCode + " " + this.city + " " + this.country;
	}
	
}
