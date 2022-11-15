package com.pharmacyManagementSystem.model;

public class Doctor extends Person implements Comparable<Doctor>{
	
	private String registrationNumber;
	
	public Doctor(){
		super();
		this.registrationNumber = new String();
	}
	
	public Doctor(String number, String name, String surname){
		super(name, surname);
		this.registrationNumber = number;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	
	@Override
	public String toString(){
		return this.registrationNumber + " " + super.toString();
	}

	@Override
	public int compareTo(Doctor o) {
		return this.getRegistrationNumber().compareTo(o.getRegistrationNumber());
	}

}
