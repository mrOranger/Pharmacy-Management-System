package com.pharmacyManagementSystem.model;

import org.json.JSONObject;

public abstract class Medicine {
	
	private String name;
	private float cost;
	private boolean isPrescripted;
	
	public Medicine(){
		this.name = new String();
		this.cost = 0;
		this.isPrescripted = false;
	}
	
	public Medicine(String name, float cost, int prescription){
		this.name = name;
		this.cost = cost;
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

	public boolean isPrescripted() {
		return isPrescripted;
	}

	public void setPrescripted(boolean isPrescripted) {
		this.isPrescripted = isPrescripted;
	}
	
	public abstract JSONObject toJson();
	
	@Override
	public String toString(){
		return this.name + " " + this.cost + " " + this.isPrescripted; 
	}
	
}
