/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import static DBAccess.DBAppointments.getAllAppointments;
import static DBAccess.DBAppointments.getCustomerAppointments;
import static DBAccess.DBAppointments.getUserAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomer;
import static DBAccess.DBCustomer.getAllCustomers;
import static DBAccess.DBFirstDivision.getUpdateStates;
import Model.Appointment;
import Model.Contacts;
import Model.Customer;
import static Model.DatabaseApp.DeleteCustomerRecord;
import Model.FirstDivision;
import java.io.IOException;
import java.net.URL;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.DBConnection;
import utils.DBQuery;
import java.sql.PreparedStatement;
import static java.time.LocalDateTime.now;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;
import Lambda.LookupCredentialsInterface;
import java.time.LocalDate;

/**
 * FXML Controller class
 *
 * @author gianm
 */
public class CustomerRecordsController implements Initializable {

    @FXML
    private Button UpdateButton;
    @FXML
    private TableColumn<Customer, Integer> StudentID;
    @FXML
    private TableColumn<Customer, String> StudentName;
    @FXML
    private TableColumn<Customer, String> Phone;
    @FXML
    private TableColumn<Customer,Integer> RecordEntryDateS;//the second value was LocalDateTime instead of intager
    @FXML
    TableView<Customer> CustomerRecordTable;
    @FXML
    private TableColumn<Customer, String> Address;
    @FXML
    private TableColumn<Customer, String> PostolCode;
    @FXML
    private Button AddButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Label TitleLabel;
    @FXML
    private RadioButton RBcustomer;
    @FXML
    private RadioButton RBContacts;
    @FXML
    private RadioButton RBAllAPP;
    @FXML
    private ToggleGroup CusRecToogle;
    @FXML
    private Label FAlert;
    @FXML
    private Button countryReportButton;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          // TODO
          try {
              
              
         // in order to get connection you need to start the connection
         Connection conn = DBConnection.getConnection();
         DBQuery.getPreparedStatement();//create statement object
         
         
         //This will populate the table with customers and to their right place
             ObservableList<Customer> CustomerList2;
            CustomerList2 = DBCustomer.getAllCustomers();
             CustomerRecordTable.setItems(CustomerList2);
            StudentID.setCellValueFactory(new PropertyValueFactory<>("id"));//Write exactly like in the Customer.java 
            StudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
            Phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            Address.setCellValueFactory(new PropertyValueFactory<>("address"));
            PostolCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            RecordEntryDateS.setCellValueFactory(new PropertyValueFactory<>("DivisionID"));
          
            
        if(CusRecToogle.getSelectedToggle().equals(RBContacts))
        {
            
          // TableView<Customer> CustomerRecordTable = new TableView<>();
        
        }
        
        //15min alert before an appointment
        NextAppointmentAlert();
        
        }
        catch(SQLException e){
        
        
        }
            
    } 
    
    /** 
     
     This method is for a button that will add a new customer. 
     @param event
     
     */
    @FXML
    private void AddButtonAction(ActionEvent event) throws SQLException, IOException {
        
         
        //creating an object
        FXMLLoader loader = new FXMLLoader();
        //which fxml file to load
        
        loader.setLocation(getClass().getResource("/View_Controller/UpdateCustomerRecord.fxml"));
        //call the load method
        loader.load();
        
        //Create an object before it loads
        UpdateCustomerRecordController MController = loader.getController();
      //Add a title to the object 
       MController.changeScreenTitle(2);
        
        Parent AddMenuParent = loader.getRoot();
        Scene AddMenuScene = new Scene(AddMenuParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();  
        window.setScene(AddMenuScene);
        window.show();
        
    }

    /**
     
    * This method will update any customer added in the database. 
    *@param event
     
     */
    @FXML
    private void UpdateButtonAction(ActionEvent event) {
       
        
        //displayInfo(CustomerRecordTable.getSelectionModel().getSelectedItem());
         try{
         
        if(CustomerRecordTable.getSelectionModel().isEmpty())
          {
            throw new Exception();
           
          }
         
        //creating an object
        FXMLLoader loader = new FXMLLoader();
        //which fxml file to load
        
        loader.setLocation(getClass().getResource("/View_Controller/UpdateCustomerRecord.fxml"));
        //call the load method
        loader.load();
        
        //This will create an object before displaying it 
        UpdateCustomerRecordController MController = loader.getController();
        MController.displayUpdateInfo(CustomerRecordTable.getSelectionModel().getSelectedItem());
        MController.changeScreenTitle(1);
        
        
        Parent AddMenuParent = loader.getRoot();
        Scene AddMenuScene = new Scene(AddMenuParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();  
        window.setScene(AddMenuScene);
        window.show();
        
         }
    catch (Exception e){
    
     Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a Customer to Update. ");
            alert.showAndWait();
            System.out.println("Exception " );
    
    }
       
    }

    /**
     * This method will Delete a customer from the customer database. 
     * The customer could only be deleted if the customer doesnt have any appointments.
     * @param event
     */
    @FXML
    private void DeleteButtonAction(ActionEvent event) {
         
         Alert alert; 
         
         try{
             
          // if a product is not select, it throws an exception    
       if(CustomerRecordTable.getSelectionModel().isEmpty()) 
         {throw new Exception();}
       
       //this if statement will check if the customer has any appointments. 
       if((getCustomerAppointments(CustomerRecordTable.getSelectionModel().getSelectedItem().getId()).size() == 0)){
                  
           int CustId = CustomerRecordTable.getSelectionModel().getSelectedItem().getId();
           alert =  new Alert(Alert.AlertType.CONFIRMATION,"\n Do you want to delete Id "+CustId+" from the Customer Records?");
           Optional<ButtonType> result = alert.showAndWait();
       
     
        if(result.isPresent() && result.get() == ButtonType.OK)
        {
        
             DeleteCustomerRecord(CustId);//deletes the record from the database.
              ObservableList<Customer> CustomerList3 = DBCustomer.getAllCustomers();//this will repopulate the table view
              CustomerRecordTable.setItems(CustomerList3);
        
        }
       }
       else{
           alert = new Alert(Alert.AlertType.WARNING,"Customer can't be deleted until all the associated \nappointments are removed from this Customer");
            Optional<ButtonType>  result2 = alert.showAndWait();
       }
       } //end of try
   catch( Exception e ){

             alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a Customer to Delete. ");
            alert.showAndWait();
            
            System.out.println("Exception " + e.getMessage() );


   }
        
    }   
    
  
    /**
     *This will change the display to Customer records and display all customers. 
     * @param event
     */
    @FXML
    private void RBcustomerAction(ActionEvent event) throws SQLException {
            
          //tHIS WILL POPULATE THE TABLE VIEW WITH ALL THE CUSTOMER FROM THE DATABASE     
        ObservableList<Customer> CustomerList2 = DBCustomer.getAllCustomers();
            CustomerRecordTable.setItems(CustomerList2);
            StudentID.setCellValueFactory(new PropertyValueFactory<>("id"));//Write exactly like in the Customer.java 
            StudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
            Phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            Address.setCellValueFactory(new PropertyValueFactory<>("address"));
            PostolCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            RecordEntryDateS.setCellValueFactory(new PropertyValueFactory<>("DivisionID"));
            
        
    }
    
    /**
     *This will change the display to the contact controller. 
     * @param event
     */
    @FXML 
    private void RBContactsAction(ActionEvent event) throws SQLException, IOException {
             
         //creating an object
        FXMLLoader loader = new FXMLLoader();
        //which fxml file to load
        
        loader.setLocation(getClass().getResource("/View_Controller/AllContacts.fxml"));
        //call the load method
        loader.load();
       
        Parent AddMenuParent = loader.getRoot();
        Scene AddMenuScene = new Scene(AddMenuParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();  
        window.setScene(AddMenuScene);
        window.show();
        
    }
    
         /**
         *This radio button will change the display to all application. 
         * @param event
         */
    @FXML
    private void RBALLAppAction(ActionEvent event) throws IOException {
        
        //creating an object
        FXMLLoader loader = new FXMLLoader();
        //which fxml file to load
        
        loader.setLocation(getClass().getResource("/View_Controller/AllAppointment.fxml"));
        //call the load method
        loader.load();
       
        Parent AddMenuParent = loader.getRoot();
        Scene AddMenuScene = new Scene(AddMenuParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();  
        window.setScene(AddMenuScene);
        window.show();
       
    }

     /**
      This will display the next appointment within the next 15 mins or past the 5 mins.
      * @throws SQLException sql exception 
      
      */
   public void NextAppointmentAlert() throws SQLException{
      
         FAlert.setText("No appointments within 15 mins");
     int id = LoginController.getUser().getId();
   
   for(Appointment appointment : getUserAppointments(id)){
       
        if(appointment.getStartDate().toLocalDate().equals(LocalDate.now())){
       
         LocalTime startTime = appointment.getStartDate().toLocalTime();
         LocalTime currentTime = LocalTime.now();
         
          //Counts the minutes between the startTime and currentTime
        long timeDifference = ChronoUnit.MINUTES.between(startTime, currentTime);
        
          long interval = timeDifference * -1;
         
         if(interval > 0 && interval <= 15){
             //This will display on the FAlert LABEL THE AMOUNT OF TIME LEFT TO THE APPOINTMENT
             FAlert.setText("Appointment Id: " +appointment.getAppointmentId()+ " start date/time: "+appointment.getStartDate().toLocalDate()+", "+startTime+" will start in " +interval+ " minutes!");
        
        }
        
        else if(interval <= 1 && interval >= -10){
             //This will display on the FAlert LABEL THE AMOUNT OF time after the appointment time 
             FAlert.setText("Appointment Id: " +appointment.getAppointmentId()+" start date/time: "+appointment.getStartDate().toLocalDate()+","+startTime+ " started "+interval * -1 + " minutes ago!");
               
         
                }
        } 
   }
   } 
   
   /**
    *This method will show the total amount of customers for each country.
    */
    @FXML
    private void countryReportButton(ActionEvent event) throws SQLException {
        
       
        int US = 0,Canada = 0, UK = 0;
        
       ObservableList<Customer> CustomerList2 = DBCustomer.getAllCustomers();
        // CustomerRecordTable.setItems(CustomerList2);
       
       for(Customer customer : getAllCustomers()){
           
     
        int fd = customer.getDivisionID();
        
        for(FirstDivision states : getUpdateStates()){
        
        if(states.getId() == fd){
        
            
            if(states.getCountry().equals("U.S")){  US ++; }
            
            if(states.getCountry().equals("Canada")){ Canada ++; }
            
            if(states.getCountry().equals("UK")){UK ++;}
           
        }
        
        }
    }
        
         Alert  alert =  new Alert(Alert.AlertType.CONFIRMATION,"\n Customers per Country:\n US:"+US+"\n Canada:"+Canada+"\n UK:"+UK+""  );
           Optional<ButtonType> result = alert.showAndWait();
       
    }
   
}//end of class
 
