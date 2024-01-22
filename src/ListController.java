import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import myfileio.MyFileIO;

public class ListController {
	private ArrayList<Employee> employees;
	private static final boolean DEBUG = false;
	private MyFileIO fileIO;
	
	public ListController () {
		Employee.resetStaticID();
		employees = new ArrayList<Employee>();
		fileIO = new MyFileIO();
		initializeData();
	}

	// adds a new employee
	String addEmployee(String firstName, String lastName, String SSN, String age, String pronouns, String salary, String years, String dept) {
		// TODO #7
		// controller needs to convert the numeric string data -
		// use Integer.parseInt() or Double.parseDouble() for ints and doubles
		// years should be int, salary should be a double....
		// Then, add the new employee to the employees list!
		// for initial demo and debugging, set DEBUG to true;
		
		try {
			if (!firstName.equals("") && !lastName.equals("") && !SSN.equals("") && !age.equals("") && !salary.equals("") && !years.equals("") && !dept.equals("")) {
				
					double salaryI = Double.parseDouble(salary);
					int yearsI = Integer.parseInt(years);	
					int ageI = Integer.parseInt(age);
					
					if (SSN.matches("\\d{3}\\-\\d{2}\\-\\d{4}")) { // Regex to figure out SSn
						for (int i = 0; i < employees.size(); i++) {
							if (SSN.equals(employees.get(i).getSSN())) { // Check for duplicates s
								return "Duplicate SSN";
							}
						}
						
						if (ageI - yearsI > 16) { // Have to be older than 16
							if (ageI > 75) {
								System.out.println("Too Old to Apply");
							}
							
							if (salaryI > 30000) { // More than 30k
								if (salaryI > 30_000_000) {
									System.out.println("Salary is too high");
								}
								employees.add(new Employee(firstName, lastName, SSN, ageI, pronouns, salaryI, yearsI, dept));
								if (DEBUG) System.out.println(employees);
								
								return ""; // no errors
							} else {
								return "Salary is too Low (Minimum is 30,000)";
							}
						} else {
							return "You're too young to apply";
						}
					} else {
						return "Make sure that your SSN is formatted correctly ###-##-####";
					}
				} else {
					return "Make sure that all fields are populated";
				}
			
		} catch (NumberFormatException e) {
			return "Improper input";
		}
			
		
		
		
	//	if (DEBUG) System.out.println(employees);
		
		// return "Succesful"; // any errors;

	}
	
	public void makeAlert(String errorString) {
	    		Alert alert = new Alert(AlertType.WARNING);
	    		alert.setHeaderText("Add Employee Failed");
	    		alert.setContentText("Make sure that all fields are populated");
	    		alert.showAndWait();
	}
	    	
	    	
	    	
	
	
	  public void saveEmployeeData() {
		sortByID();
		String line = "";
		MyFileIO fileIO = new MyFileIO();
		File fh = fileIO.getFileHandle("empDB.dat");
		if (fileIO.checkTextFile(fh, false) == MyFileIO.FILE_OK || fileIO.checkTextFile(fh, false) == MyFileIO.WRITE_EXISTS) {
			BufferedWriter bw = fileIO.openBufferedWriter(fh);
			
			for (int i = 0; i < employees.size(); i++) {
				try {
					line = String.valueOf(employees.get(i).getEmpID()) + "|,|" + employees.get(i).getFirstName() + "|,|" + employees.get(i).getLastName() + "|,|" 
							+ employees.get(i).getSSN() + "|,|" + String.valueOf(employees.get(i).getAge())+ "|,|" + employees.get(i).getPronouns() 
							+ "|,|" + String.valueOf(String.format("%.2f", ((long) (employees.get(i).getSalary()*100)/100.0))) + "|,|" + String.valueOf(employees.get(i).getYears())+ "|,|" + employees.get(i).getDept() + "\n";
					System.out.println(line); // Needed the long line to differentiate, there might be a better way. 
					bw.write(line);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				bw.close();
			//	System.out.println("saved");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
	public void initializeData() {
		MyFileIO fileIO = new MyFileIO();
		File fh = fileIO.getFileHandle("empDB.dat");
		
		if (fh.canRead()) {
			BufferedReader br = fileIO.openBufferedReader(fh);
			
	
				try {
					String line = br.readLine();
					String[] array = line.split("(\\|\\,\\|)+");
					while (line != null) {
						
						array = line.split("(\\|\\,\\|)+"); // Have to split at this point
						System.out.println(Arrays.toString(array));
						if (array.length < 9) {
							addEmployee(array[1], array[2], array[3], array[4], "", array[5], array[6], array[7]);
						} else {
							addEmployee(array[1], array[2], array[3], array[4], array[5], array[6], array[7], array[8]);
						}
					//	System.out.println("hi");
					//	System.out.println(getNumEmployees());
						line = br.readLine();
						
					}
					
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	//		}
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void sortByID() {
		Collections.sort(employees, new ByID());
	}
	
	private class ByID implements Comparator<Employee> {

		@Override
		public int compare(Employee o1, Employee o2) {
			// TODO Auto-generated method stub
			return Integer.compare(o1.getEmpID(), o2.getEmpID());
		}
		
	}
	
	public void sortByName() {
		Collections.sort(employees, new ByName());
	}
	
	private class ByName implements Comparator<Employee> {

		@Override
		public int compare(Employee o1, Employee o2) {
			// TODO Auto-generated method stub
			if (o1.getLastName().toLowerCase().equals(o2.getLastName().toLowerCase())) { //Need to make it lower case
				return o1.getFirstName().toLowerCase().compareTo(o2.getFirstName().toLowerCase());
			} else {
				return o1.getLastName().toLowerCase().compareTo(o2.getLastName().toLowerCase());
			}
			
		}
		
	}

	public void sortBySalary() {
		Collections.sort(employees, new BySalary());
	}
	
	private class BySalary implements Comparator<Employee> {

		@Override
		public int compare(Employee o1, Employee o2) {
			// TODO Auto-generated method stub
			return Double.compare(o1.getSalary(), o2.getSalary());
		}
		
	}
	
	// returns a string array of the employee information to be viewed
	public String[] getEmployeeDataStr() {
		// temporary placeholder for compilation reasons - will remove later...
		String[] str = new String[employees.size()];
		for (int i = 0; i < employees.size(); i++) {
			str[i] = employees.get(i).toString();
		}
		return str;
		
	}
	
	public int getNumEmployees() {
		
		return employees.size();
	}
	
	
	
	
	
	
	
	
}
 
