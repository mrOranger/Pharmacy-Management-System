package com.pharmacyManagementSystem.model;

import java.util.LinkedList;
import java.util.List;

public class Prescription {

	private int code;
	private int numberOfProducts;
	private String doctor;
	private Patient patient;
	private List<BrandedMedicine> medicines;
	
	public Prescription(){
		this.numberOfProducts = 0;
		this.doctor = new String();
		this.patient = new Patient();
		this.medicines = new LinkedList<BrandedMedicine>();
	}
	
	public Prescription(int numberOfProducts, String doctor){
		this.numberOfProducts = numberOfProducts;
		this.doctor = doctor;
	}

	public int getNumberOfProducts() {
		return numberOfProducts;
	}

	public void setNumberOfProducts(int numberOfProducts) {
		this.numberOfProducts = numberOfProducts;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public List<BrandedMedicine> getMedicines() {
		return medicines;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMedicines(List<BrandedMedicine> medicines) {
		this.medicines = medicines;
	}
	
	@Override
	public String toString(){
		String str = this.numberOfProducts + " " + this.doctor + " " + this.patient.toString() + '\n';
		for(int i = 0; i < this.getMedicines().size(); i++){
			str += this.getMedicines().get(i).getName() + " ";
		}
		str += '\n';
		return str;
 	}
}
