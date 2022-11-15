package com.pharmacyManagementSystem.model;

public class Patient extends Person implements Comparable<Patient>{
	
	private String taxCode;
	
	public Patient(){
		super();
		this.taxCode = new String();
	}
	
	public Patient(String taxCode, String name, String surname){
		super(name, surname);
		this.taxCode = taxCode;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	
	@Override
	public String toString(){
		return this.taxCode + " "+ super.toString();
	}

	@Override
	public int compareTo(Patient arg0) {
		return this.taxCode.compareTo(arg0.getTaxCode());
	}
	
}
