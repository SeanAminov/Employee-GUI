/**
 * 
 */


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Scott
 *
 */
public class EmpDataGUI extends Application {
   
	private GridPane main = new GridPane();
    private ListController controller = new ListController();
    private TextField lastName = new TextField();
    private TextField firstName = new TextField();
    private TextField ssn = new TextField();
    private TextField age = new TextField();
    private TextField pro = new TextField();
    private TextField salary = new TextField();
    private TextField years = new TextField();
    private BorderPane view = new BorderPane();
    private BorderPane view2 = new BorderPane();
    private ScrollPane scroll = new ScrollPane();
    private RadioButton test = new RadioButton();
    private RadioButton eng = new RadioButton();
    private Scene scene2 = new Scene(view2, 400, 500);
    // TODO #1:
    
    // create private TextField variables for Name, SSN, Salary and Years
    // so that they can be accessed directly by methods inside this class.
	
    @Override
    public void start(Stage primaryStage) {
    	Scene scene = new Scene(view, 400, 500);
    	view.setCenter(main); // Main view, main is the grid
    	view2.setCenter(scroll); // This is the second view, you need scroll 
    	
	// TODO #2:
    	// create Labels for Name, SSN, Salary and Years
    	Label lastNameL = new Label("Last Name");
    	Label firstNameL = new Label("First Name");
    	Label ssnL = new Label("SSN");
    	Label ageL = new Label("Age");
    	Label proL = new Label("Pronouns");
    	Label salaryL = new Label("Salary");
    	Label yearsL = new Label("Years");
    	Label dept = new Label("Dept:");
    	
    	Label data = new Label("Employee Data");
  
    	
	// TODO #4
    	// instantiate (new) TextFields (already declared above) for Name, SSN, Salary and Years
    	Text text = new Text(100, 200, "EMPLOYEE DATA ENTRY");
    	firstName = new TextField();
    	lastName = new TextField();
        ssn = new TextField();
        age = new TextField();
        pro = new TextField();
        salary = new TextField();
        years = new TextField();
	// TODO #5
        // Create Add Employee Button, and write the setOnAction handler to call the controller
    	// to add the new Employee data
        
        Button back = new Button("Back");
        back.setOnAction(e -> primaryStage.setScene(scene));
        
        Button sortName = new Button("Sort By Name");
        sortName.setOnAction(e -> {controller.sortByName(); viewEmployeeDB();});
        
        Button sortID = new Button("Sort By ID");
        sortID.setOnAction(e -> {controller.sortByID(); viewEmployeeDB();});
        
        Button sortSalary = new Button("Sort By Salary");
        sortSalary.setOnAction(e -> {controller.sortBySalary(); viewEmployeeDB();});
        
        Button viewEmployees = new Button("View Employees");
        viewEmployees.setOnAction((e) -> {primaryStage.setScene(scene2); viewEmployeeDB();}); // you have to do { for double calls on a button
   
        
        
        Button saveDB = new Button("Save DB");
        saveDB.setOnAction(e -> controller.saveEmployeeData()); 
        
        
        eng = new  RadioButton("Engineering");
		RadioButton mark= new  RadioButton("Marketing/Sales");
		RadioButton manu = new  RadioButton("Manufacturing");
		RadioButton fina = new  RadioButton("Finance");
		RadioButton hr = new  RadioButton("Human Resources");
		RadioButton customer = new  RadioButton("Customer Support");
		RadioButton manage = new  RadioButton("Management");
		
		
		eng.setSelected(true);
		VBox vbox = new VBox();
		vbox.getChildren().addAll(eng, mark, manu, fina, hr, customer, manage); // This is for the radiobuttons
		
		ToggleGroup tg = new ToggleGroup();
		eng.setToggleGroup(tg);
		mark.setToggleGroup(tg);
		manu.setToggleGroup(tg);
		fina.setToggleGroup(tg);
		hr.setToggleGroup(tg);
		customer.setToggleGroup(tg);
		manage.setToggleGroup(tg);
		
		test = (RadioButton) tg.getSelectedToggle();
		
		Button addEmployee = new Button("Add Employee");
        addEmployee.setOnAction((e) -> clearTextFields()); // clearTextFields calls the method in List Controlelr
		
        
        HBox hbox = new HBox();
		hbox.getChildren().addAll(addEmployee, viewEmployees, saveDB);
		
		view.setBottom(hbox);
		
		HBox hboxView = new HBox();
		hboxView.getChildren().addAll(back,sortName, sortID, sortSalary);
		
		
		
        
        
	// TODO #6
    	// add all the labels, textfields and button to gridpane main. refer to the slide
    	// for ordering...
		view.setTop(text);
        
        main.add(lastNameL, 0, 1);
        main.add(firstNameL, 0, 2);
        main.add(ssnL, 0, 3);
        main.add(ageL, 0, 4);
        main.add(proL, 0, 5);
        main.add(salaryL, 0, 6);
        main.add(yearsL, 0, 7);
        main.add(dept, 0, 11);
        
        main.add(lastName, 1, 1);
        main.add(firstName, 1, 2);
        main.add(ssn, 1, 3);
        main.add(age, 1, 4);
        main.add(pro, 1, 5);
        main.add(salary, 1, 6);
        main.add(years, 1, 7);
        main.add(vbox, 1, 11);
        
       
        view2.setTop(data);// The name for the second view
        view2.setBottom(hboxView); // The buttons
        
    	
    	primaryStage.setTitle("Employees");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // don't worry about this yet - part of part2
    private void viewEmployeeDB() {
    	
    	String[] empDataStr = controller.getEmployeeDataStr();
    	ListView<String> lv = new ListView<>(FXCollections.observableArrayList(empDataStr));
    	lv.setPrefWidth(400);
    	scroll.setContent(lv);
    }
    
    private void clearTextFields() {
    	 String text = controller.addEmployee(firstName.getText(), lastName.getText(), ssn.getText(), age.getText(), pro.getText(), salary.getText(), years.getText(), test.getText());
    	if (text.equals("")) {
    		lastName.setText("");
    		firstName.setText("");
        	ssn.setText("");
        	age.setText("");
        	pro.setText("");
        	salary.setText("");
        	years.setText("");
        	eng.setSelected(true);
    	} else {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setHeaderText("Add Employee Failed");
    		alert.setContentText(text);
    		alert.showAndWait();
    	}
    	
    	
    	
    }
    
    
    
  /**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}

} ;
