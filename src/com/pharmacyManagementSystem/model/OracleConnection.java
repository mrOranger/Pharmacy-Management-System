package com.pharmacyManagementSystem.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.pharmacyManagementSystem.utilities.DateUtilities;

public class OracleConnection {
	
	private static OracleConnection INSTANCE;
	private Connection connection;
	private final String Url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private final String Username = "yellowcom1";
	private final String Password = "yellowcom";
	private final String DriverName = "oracle.jdbc.driver.OracleDriver"; 
	
	private OracleConnection(){
		try {
			Class.forName(this.DriverName);
			this.connection = DriverManager.getConnection(Url, Username, Password);
		} catch (Exception e) {
			System.err.println("Error in the creation of a connection to the database");
			e.printStackTrace();
		}
		
	}
	
	public Connection getConnection(){
		return this.connection;
	}
	
	public static OracleConnection getInstance() throws SQLException{
		if(OracleConnection.INSTANCE == null){
			OracleConnection.INSTANCE = new OracleConnection();
		}else if(OracleConnection.INSTANCE.getConnection().isClosed()){
			OracleConnection.INSTANCE = new OracleConnection();
		}
		return OracleConnection.INSTANCE;
	}
	
	public String getPassword(String username){
		final String query = "SELECT Password FROM Users WHERE Nickname = '" + username + "'";
		String password = new String();
		PreparedStatement statement;
		try {
			statement = this.getConnection().prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()){
				password = resultSet.getString(1);
			}
		} catch (SQLException e) {
			System.err.println("Error in the execution of the query!");
			e.printStackTrace();
		}
		return password;
	}
	
	public boolean registerUser(String name, String surname, String date, String username, String password){
		final String callProcedure = "{call InsertNewUser(?, ?, ?, ?, ?)}";
		try {
			CallableStatement callStatement = this.getConnection().prepareCall(callProcedure);
			callStatement.setString(1, name);
			callStatement.setString(2, surname);
			callStatement.setString(3, date);
			callStatement.setString(4, username);
			callStatement.setString(5, password);
			callStatement.execute();
			return true;
		} catch (SQLException e) {
			System.err.println("SQL throw an exception!");
			System.err.println("Error code: " + e.getErrorCode());
			System.err.println("SQL message: " + e.getMessage());
		}
		return false;
	}
	
	public List<String> getCompaniesNames(){
		final String query = "SELECT Name FROM Companies";
		LinkedList<String> names = new LinkedList<String>();
		try {
			PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				names.add(new String(resultSet.getString(1)));
			}
		} catch (SQLException e) {
			System.err.println("SQL throw an exception!");
			System.err.println("Error code: " + e.getErrorCode());
			System.err.println("SQL message: " + e.getMessage());
			return null;
		}
		return names;
	}
	
	public boolean insertNewMedicine(String name, String description, float cost, int prescription, int licence, String company){
		final String callProcedure = "{call InsertNewMedicine(?, ?, ?, ?, ?, ?)}";
		try {
			CallableStatement callStatement = this.getConnection().prepareCall(callProcedure);	
			callStatement.setString(1, name);
			callStatement.setString(2, description);
			callStatement.setFloat(3, cost);
			callStatement.setInt(4, prescription);
			callStatement.setInt(5, licence);
			callStatement.setString(6, company);
			callStatement.execute();
			return true;
		} catch (SQLException e) {
			System.err.println("SQL throw an exception!");
			System.err.println("Error code: " + e.getErrorCode());
			System.err.println("SQL message: " + e.getMessage());
		}
		return false;
	}
	
	public boolean insertNewMedicine(String name, String description, float cost, int prescription, int licence, String company, String streetName, int streetCode, String city, String country){
		final String callProcedure = "{call InsertNewMedicineWithCompany(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
		try {
			CallableStatement callStatement = this.getConnection().prepareCall(callProcedure);
			callStatement.setString(1, name);
			callStatement.setString(2, description);
			callStatement.setFloat(3, cost);
			callStatement.setInt(4, prescription);
			callStatement.setInt(5, licence);
			callStatement.setString(6, company);
			callStatement.setString(7, streetName);
			callStatement.setInt(8, streetCode);
			callStatement.setString(9, city);
			callStatement.setString(10, country);
			callStatement.execute();
			return true;
		} catch (SQLException e) {
			System.err.println("SQL throw an exception!");
			System.err.println("Error code: " + e.getErrorCode());
			System.err.println("SQL message: " + e.getMessage());
		}
		return false;
	}
	
	public boolean insertNewCosmetic(String name, String description, float cost, String type){
		final String callProcedure = "{call InsertCosmetic(?, ?, ?, ?)}";
		try {
			CallableStatement callStatement = this.getConnection().prepareCall(callProcedure);
			callStatement.setString(1, name);
			callStatement.setString(2, description);
			callStatement.setFloat(3, cost);
			callStatement.setString(4, type);
			callStatement.execute();
			return true;
		} catch (SQLException e) {
			System.err.println("SQL throw an exception!");
			System.err.println("Error code: " + e.getErrorCode());
			System.err.println("SQL message: " + e.getMessage());
		}
		return false;
	}
	
	public List<BrandedMedicine> getMedicines(){
		LinkedList<BrandedMedicine> medicines = new LinkedList<BrandedMedicine>();
		final String query = "SELECT * FROM PrintMedicine WHERE ROWNUM < 10";
		try {
			PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				medicines.add(new BrandedMedicine(resultSet.getString(1), resultSet.getFloat(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getString(5)));
				
			}
		} catch (SQLException e) {
			System.err.println("SQL throw an exception!");
			System.err.println("Error code: " + e.getErrorCode());
			System.err.println("SQL message: " + e.getMessage());
			return null;
		}
		return medicines;
	}
	
	public boolean removeMedicine(String name){
		final String query = "DELETE FROM BrandedMedicines WHERE Name = '" + name + "'";
		try {
			PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			System.err.println("SQL throw an exception!");
			System.err.println("Error code: " + e.getErrorCode());
			System.err.println("SQL message: " + e.getMessage());
		}
		return false;
	}
	
	public List<UnbrandedMedicine> getUnbrandedMedicines(){
		LinkedList<UnbrandedMedicine> medicines = new LinkedList<UnbrandedMedicine>();
		final String query = "SELECT * FROM UnbrandeMedicineEquivalent";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = this.getConnection().prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				medicines.add(new UnbrandedMedicine(resultSet.getString(1), resultSet.getFloat(3), resultSet.getInt(4), resultSet.getString(2), resultSet.getString(5)));
			}
		} catch (SQLException e) {
			System.err.println("SQL throw an exception!");
			System.err.println("Error code: " + e.getErrorCode());
			System.err.println("SQL message: " + e.getMessage());
			return null;
		}
		return medicines;
	}
	
	public boolean removeUnbrandedMedicine(String name){
		final String query = "DELETE FROM UnbrandedMedicines WHERE Name = '" + name + "'";
		try {
			PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
			preparedStatement.execute();
		} catch (SQLException e) {
			System.err.println("SQL throw an exception!");
			System.err.println("Error code: " + e.getErrorCode());
			System.err.println("SQL message: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	public List<Sale> getSales(){
		LinkedList<Sale> sales = new LinkedList<Sale>();
		final String query = "SELECT * FROM SalesRange WHERE ROWNUM < 50";
		try {
			PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				Sale currentSale = new Sale(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getFloat(4));
				int foundIndex = this.getElementIndex(currentSale, sales);
				if(foundIndex == -1){
					currentSale.getProductsSold().add(new Product(resultSet.getString(7), resultSet.getFloat(6), resultSet.getInt(5)));
					sales.add(currentSale);
				}else{
					sales.get(foundIndex).getProductsSold().add(new Product(resultSet.getString(7), resultSet.getFloat(6), resultSet.getInt(5)));
				}
			}
		} catch (SQLException e) {
			System.err.println("SQL throw an exception!");
			System.err.println("Error code: " + e.getErrorCode());
			System.err.println("SQL message: " + e.getMessage());
			return null;
		}
		return sales;
	}
	
	public boolean deleteSale(int code){
		final String query = "DELETE FROM Sales WHERE Code = " + code;
		try {
			PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
			preparedStatement.execute();
		} catch (SQLException e) {
			System.err.println("SQL throw an exception!");
			System.err.println("Error code: " + e.getErrorCode());
			System.err.println("SQL message: " + e.getMessage());
			return false;
		}
		return true;
		
	}
	
	public boolean registerASale(Sale sale){
		final String callFunction = "{call ? := InsertNewSale(?, ?, ?, ?, ?)}";
		try {
			CallableStatement callStatement = this.getConnection().prepareCall(callFunction);
			callStatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callStatement.setInt(2, sale.getSoldQuantity());
			callStatement.setFloat(3, sale.getSoldTotal());
			callStatement.setInt(4, sale.getProductsSold().get(0).getQuantity());
			callStatement.setFloat(5, sale.getProductsSold().get(0).getTotal());
			callStatement.setString(6, sale.getProductsSold().get(0).getProductName());
			callStatement.execute();
			int code = callStatement.getInt(1);
			if(this.inserNewSale(code, sale)){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			System.err.println("SQL throw an exception!");
			System.err.println("Error code: " + e.getErrorCode());
			System.err.println("SQL message: " + e.getMessage());
		}
		return false;
	}
	
	private boolean inserNewSale(int code, Sale sale){
		final String callProcedure = "{call RegistareNewSale(?, ?, ?, ?)}";
		CallableStatement callStatementFunction;
		for(int i = 1; i < sale.getProductsSold().size(); i++){
			try {
				System.out.println("Registro il prodotto: " + code);
				System.out.println("Quantità: " + sale.getProductsSold().get(i).getQuantity());
				System.out.println("Totale: " + sale.getProductsSold().get(i).getTotal());
				System.out.println("Nome: " + sale.getProductsSold().get(i).getProductName());
				callStatementFunction = this.getConnection().prepareCall(callProcedure);
				callStatementFunction.setInt(1, code);
				callStatementFunction.setInt(2, sale.getProductsSold().get(i).getQuantity());
				callStatementFunction.setFloat(3, sale.getProductsSold().get(i).getTotal());
				callStatementFunction.setString(4, sale.getProductsSold().get(i).getProductName());
				callStatementFunction.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public List<Patient> getPatients(){
		final String query = "SELECT * FROM Patients";
		LinkedList<Patient> patients = new LinkedList<Patient>();
		try {
			PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				patients.add(new Patient(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return patients;
	}
	
	public List<Doctor> getDoctors(){
		final String query = "SELECT * FROM Doctors";
		LinkedList<Doctor> doctors = new LinkedList<Doctor>();
		try {
			PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				doctors.add(new Doctor(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return doctors;
	}
	
	public boolean insertPrescription(Prescription prescription){
		final String callFunction = "{call ? := InsertNewPrescription(?, ?, ?, ?)}";
		try {
			CallableStatement callStatement = this.getConnection().prepareCall(callFunction);
			callStatement.registerOutParameter(1, java.sql.Types.INTEGER);
			callStatement.setInt(2,  prescription.getNumberOfProducts());
			callStatement.setString(3, prescription.getDoctor());
			callStatement.setString(4, prescription.getPatient().getTaxCode());
			callStatement.setString(5, prescription.getMedicines().get(0).getName());
			System.out.println("Paziente: " + prescription.getPatient().getTaxCode());
			callStatement.execute();
			int code = callStatement.getInt(1);
			boolean result = this.registerMedicine(code, prescription);
			return result;
		} catch (SQLException e) {
			System.err.println("SQL throw an exception!");
			System.err.println("Error code: " + e.getErrorCode());
			System.err.println("SQL message: " + e.getMessage());
			return false;
		}
	}
	
	public List<Report> getReports(){
		LinkedList<Report> reports = new LinkedList<Report>();
		final String query = "SELECT * FROM ReportsStatistics";
		try {
			PreparedStatement statement = this.getConnection().prepareStatement(query);
			ResultSet result = statement.executeQuery();
			int currReportSemester = 0;
			int currReportYear = 0;
			while(result.next()){
				int saleQuantity = result.getInt(2);
				String saleDay = result.getString(3);
				float saleTotal = result.getFloat(4);
				float reportAmount = result.getFloat(7);
				if((result.getInt(5) != currReportSemester) && (result.getInt(6) != currReportYear)){
					currReportSemester = result.getInt(5);
					currReportYear = result.getInt(6);
					Report currReport = new Report();
					currReport.setAmount(reportAmount);
					currReport.setSemester(currReportSemester);
					currReport.setYear(currReportYear);
					Sale currSale = new Sale();
					currSale.setDay(saleDay);
					currSale.setSoldQuantity(saleQuantity);
					currSale.setSoldTotal(saleTotal);
					currReport.getSales().add(currSale);
					reports.add(currReport);
				}else{
					if(this.getReportByDate(currReportSemester, currReportYear, reports) != -1){
						Sale currSale = new Sale();
						currSale.setDay(saleDay);
						currSale.setSoldQuantity(saleQuantity);
						currSale.setSoldTotal(saleTotal);
						reports.get(this.getReportByDate(currReportSemester, currReportYear, reports)).getSales().add(currSale);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return reports;
	}
	
	public List<Prescription> getPrescriptions(String doctorName, String doctorSurname, String doctorCode){
		List<Prescription> prescription = new LinkedList<Prescription>();
		final String query = "SELECT * FROM GenerateList WHERE DoctorName = '" + doctorName + "' AND DoctorSurname = '" + doctorSurname + "' AND DoctorNumber = '" + doctorCode + "'";
		try {
			PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			int currentPrescriptionCode = -1;
			while(resultSet.next()){
				if(resultSet.getInt(1) != currentPrescriptionCode){
					/* Insert a new Prescription in the result list */
					Prescription currPrescription = new Prescription();
					currPrescription.setCode(resultSet.getInt(1));
					currPrescription.setNumberOfProducts(resultSet.getInt(2));
					BrandedMedicine medicine = new BrandedMedicine();
					medicine.setName(resultSet.getString(3));
					currPrescription.getMedicines().add(medicine);	
					prescription.add(currPrescription);
					currentPrescriptionCode = resultSet.getInt(1);
				}else{
					BrandedMedicine medicine = new BrandedMedicine();
					medicine.setName(resultSet.getString(3));
					if(getPrescriptionByCode(prescription, currentPrescriptionCode) != -1){
						if(this.getMedicineByName(prescription.get(getPrescriptionByCode(prescription, currentPrescriptionCode)).getMedicines(), medicine.getName()) == -1){
							prescription.get(getPrescriptionByCode(prescription, currentPrescriptionCode)).getMedicines().add(medicine);
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return prescription;
	}
	
	public List<Sale> findSalesByDate(String minDate, String maxDate){
		DateUtilities date = new DateUtilities();
		final String query = "SELECT * FROM SalesRange WHERE Day < '" + date.formatDate(maxDate) + "' AND Day > '" + date.formatDate(minDate) + "'";
		LinkedList<Sale> sales = new LinkedList<Sale>();
		try {
			PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				Sale currentSale = new Sale(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getFloat(4));
				int foundIndex = this.getElementIndex(currentSale, sales);
				if(foundIndex == -1){
					currentSale.getProductsSold().add(new Product(resultSet.getString(7), resultSet.getFloat(6), resultSet.getInt(5)));
					sales.add(currentSale);
				}else{
					sales.get(foundIndex).getProductsSold().add(new Product(resultSet.getString(7), resultSet.getFloat(6), resultSet.getInt(5)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return sales;
	}
	
	private int getMedicineByName(List<BrandedMedicine> medicines, String name){
		for(int index = 0; index < medicines.size(); index++){
			if(medicines.get(index).getName().equals(name)){
				return index;
			}
		}
		return -1;
	}
	
	private int getPrescriptionByCode(List<Prescription> prescriptions, int code){
		for(int index = 0; index < prescriptions.size(); index++){
			if(prescriptions.get(index).getCode() == code){
				return index;
			}
		}
		return -1;
	}
	
	
	private boolean registerMedicine(int code, Prescription prescription){
		final String callProcedure = "{call RegisterNewProduct(?, ?)}";
		try {
			for(int i = 1; i < prescription.getMedicines().size(); i++){
				CallableStatement callStatement = this.getConnection().prepareCall(callProcedure);
				callStatement.setInt(1, code);
				callStatement.setString(2, prescription.getMedicines().get(i).getName());
				callStatement.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private int getElementIndex(Sale sale, LinkedList<Sale> sales){
		for(int index = 0; index < sales.size(); index++){
			if(((Sale)sales.get(index)).getSaleCode() == sale.getSaleCode()){
				return index;
			}
		}
		return -1;
	}
	
	private int getReportByDate(int semester, int year, LinkedList<Report> reports){
		for(int index = 0; index < reports.size(); index++){
			if((reports.get(index).getSemester() == semester) && (reports.get(index).getYear() == year)){
				return index;
			}
		}
		return -1;
	}
}
