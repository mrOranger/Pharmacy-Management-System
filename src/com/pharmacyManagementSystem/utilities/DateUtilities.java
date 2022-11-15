package com.pharmacyManagementSystem.utilities;

public class DateUtilities {

	private String currentDate;
	
	public DateUtilities(){
		this.currentDate = new String();
	}
	
	public DateUtilities(String date){
		this.currentDate = date;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	
	public String formatDate(String date){
		String day = date.split("-")[2];
		String month = date.split("-")[1];
		String year = date.split("-")[0];
		return day + "-" + this.convertMonth(month) + "-" + this.convertYear(year);
	}
	
	public String toString(){
		return this.currentDate;
	}
	
	private String convertYear(String year){
		return new String(String.valueOf(year.charAt(2)) + String.valueOf(year.charAt(3))); 
	}
	
	private String convertMonth(String date){
		if(date.equals("01")){
			return "JAN";
		}
		if(date.equals("02")){
			return "FEB";
		}
		if(date.equals("03")){
			return "MAR";
		}
		if(date.equals("04")){
			return "APR";
		}
		if(date.equals("05")){
			return "MAY";
		}
		if(date.equals("06")){
			return "JUN";
		}
		if(date.equals("07")){
			return "JUL";
		}
		if(date.equals("08")){
			return "AUG";
		}
		if(date.equals("09")){
			return "SEP";
		}
		if(date.equals("10")){
			return "OCT";
		}
		if(date.equals("11")){
			return "NOV";
		}
		if(date.equals("12")){
			return "DEC";
		}else{
			return "";
		}
	}
	
}
