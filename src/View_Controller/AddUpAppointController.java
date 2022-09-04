/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import DBAccess.DBAppointments;
import static DBAccess.DBAppointments.getCustomerAppointments;
import DBAccess.DBContacts;
import static DBAccess.DBContacts.getAllContacts;
import DBAccess.DBCustomer;
import static DBAccess.DBCustomer.getAllCustomers;
import DBAccess.DBUsers;
import static DBAccess.DBUsers.getAllUsers;
import Lambda.LookupCustomer;
import Model.Appointment;
import Model.Contacts;
import Model.Customer;
import static Model.DatabaseApp.DeleteAppointment;
import Model.Users;
import com.mysql.cj.xdevapi.Table;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.lang.Exception;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.DBConnection;
import utils.DBQuery;

/**
 * FXML Controller class
 *
 * @author gianm
 */
public class AddUpAppointController implements Initializable {

    @FXML
    private TextField TextFieldID1;
    @FXML
    private Button CancelButton1;
    @FXML
    private Label MainTitleLabel;
    @FXML
    private TextField TextFieldTitle;
    @FXML
    private TextField TextFieldDescription;
    @FXML
    private TextField TextFielLocation;
    @FXML
    private TextField TextFieldType;
    @FXML
    private ComboBox<Contacts> ContactComboBox;
    @FXML
    private ComboBox<Users> UserIdComboBox;
    @FXML
    private ComboBox<Customer> CustomerIdComboBox;
    @FXML
    private Button AddUpdateButton;
    @FXML
    private ComboBox<LocalTime> StartTimeCB;
    @FXML
    private ComboBox<LocalTime> EndTimeCB;
    @FXML
    private DatePicker StartDate;
    @FXML
    private DatePicker EndDate;
     @FXML
    private Label CustomerNameLabel;
    @FXML
    private Label CustomerName;
                 
    @FXML
    private TableColumn<Appointment, Integer> ApIdCol;
    @FXML
    private TableColumn<Appointment, String> TitleCol;
    @FXML
    private TableColumn<Appointment, String> LocationCol;
    @FXML
    private TableColumn<Appointment, String> ContactCol;
    @FXML
    private TableColumn<Appointment, String> TypeCol;
    @FXML
    private TableColumn<Appointment, String > startDT;
    @FXML
    private TableColumn<Appointment, String> endDT;
    @FXML
    private TableView<Appointment> AppointmentTABLE;
     @FXML
    private Button AddUpdateButton2;
    @FXML
    private Button DeleteButton;
   
      //Get appointment for cancel button
                 int CusId ;
      //get appointment id for overlapUpdate
                 int APPOid;
  
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    
          LocalTime start = LocalTime.of(0, 0);
          LocalTime end = LocalTime.NOON;
        
          while(start.isBefore(end.plusSeconds(1))){
           
           StartTimeCB.getItems().add(start);
          
           EndTimeCB.getItems().add(start);
           start = start.plusMinutes(15);
           
          }
        
        
        
       
    }    

/**
 This method is the back button and takes the user back to the all appointments interface. 
 */
    @FXML//back button
    private void CancelButtonAction(ActionEvent event) throws IOException, SQLException {
        
            
               //These lines will let you see it on the display of the main menu
               Parent AddMenuParent = FXMLLoader.load(getClass().getResource("/View_Controller/AllAppointment.fxml"));
               Scene AddMenuScene = new Scene(AddMenuParent);
               Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
               window.setScene(AddMenuScene);
               window.show();
        
    }
    
/**
 This method adds or updates the text fields as an appointment. The method will determine weather to update or add and check for overlaps and convert date and times. 
 * 
 * 
 */
    @FXML
    private void AddUpdateButtonAction(ActionEvent event) throws SQLException, IOException {
        
        try{
        
        //Statement statement = DBQuery.getStatement();
        String Title = TextFieldTitle.getText();
        String Description  = TextFieldDescription.getText();//"444444";
        String Location = TextFielLocation.getText();//"1";
        String Type = TextFieldType.getText();//"now()";
        
       //GET values of the datepicker and combo boxes
       LocalDate startDate = StartDate.getValue();
       LocalDate endDate = EndDate.getValue();
       LocalTime startTime = StartTimeCB.getSelectionModel().getSelectedItem();
       LocalTime endTime = EndTimeCB.getSelectionModel().getSelectedItem();
       
       
       //The time picked will convert to utc time to store it in the database
       String StartDateTime = ConvertLDTtoUTC(startDate,startTime);
       String EndDateTime = ConvertLDTtoUTC(endDate,endTime);
       
      //===========================================================================================
        int CustomerId = CustomerIdComboBox.getSelectionModel().getSelectedItem().getId();
        int ContactId =  ContactComboBox.getSelectionModel().getSelectedItem().getId();
        int UserId = UserIdComboBox.getSelectionModel().getSelectedItem().getId();
        
        //----------------------------------------------------------Update Appointment---------------------------------------------------------
        //
       
        if( MainTitleLabel.getText() ==  "Update Appointment" ){
            
            
        Connection conn = DBConnection.getConnection();
         //DBQuery.setStatement(conn);
        
        String UpdateStatement = "UPDATE appointments SET Title = convert(?,char),Description=convert(?,char),Location=convert(?,char),Type=convert(?,char),Customer_ID  = ?, User_ID =?, Contact_ID = ?,Start =convert(?,datetime),End = ? WHERE Appointment_ID=? ";
        
        DBQuery.setPreparedStatement(conn,UpdateStatement);
        
        
        java.sql.PreparedStatement ps = DBQuery.getPreparedStatement();
        
        
        //Variable Insert
        //Statement statement = DBQuery.getStatement();
        String AppointmentId = TextFieldID1.getText();
       
        ps.setString(1, Title);
        ps.setString(2, Description);
        ps.setString(3, Location);
        ps.setString(4, Type);
        ps.setInt(5,CustomerId);
        ps.setInt(6,UserId);
        ps.setInt(7,ContactId);
        ps.setString(8,StartDateTime);
        ps.setString(9,EndDateTime);
         
        ps.setString(10,AppointmentId);
        
         //==============Testing  OverLapAppoint======
        //Check for any overlap appointments.
        //OverLapAppoint
        ZoneId Thezz = ZoneId.systemDefault();
        
        LocalDateTime sw = TestingConvertLDTtoUTC(startDate ,startTime,Thezz);
        LocalDateTime ew = TestingConvertLDTtoUTC(endDate ,endTime,Thezz);
      
        boolean OverLap = OverLapAppointUpdate(sw,ew,APPOid );
        
         //=================End Testing================
        // if there is no overlap it will update the appointment
        if(OverLap == true){
        
        //Excute SQL statement
        ps.execute();
        
        
        if(ps.getUpdateCount() > 0)
            System.out.println(ps.getUpdateCount() + " \n rows(s) affected!");
        else
            System.out.println("\n No change!");
        
        
    ObservableList<Appointment> AppointmentList2 = DBAppointments.getCustomerAppointments(CustomerIdComboBox.getSelectionModel().getSelectedItem().getId());
    AppointmentTABLE.setItems(AppointmentList2);
    ApIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));//need to be the same as inside the constructor of Appointment.java
    TitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
    LocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
    ContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
    startDT.setCellValueFactory(new PropertyValueFactory<>( "StartDate"));
    endDT.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
    
        }//end of if overlap 
        }//end of if 
        //--------------------------------------------------------Add Appointment-----------------------------------------------------------
        if( MainTitleLabel.getText() == "Add Appointment" )
        {
        
          Connection conn = DBConnection.getConnection();
         //DBQuery.setStatement(conn);
        
        String insertStatement = "insert into appointments(Title,Description,Location,Type,Customer_ID,User_ID,Contact_ID,Start,End)"
                                 +"values(?,?,?,?,?,?,?,?,?);";
        
        DBQuery.setPreparedStatement(conn,insertStatement);
        
        java.sql.PreparedStatement ps = DBQuery.getPreparedStatement();
        
         //Variable Insert
        
        ps.setString(1, Title);
        ps.setString(2, Description);
        ps.setString(3, Location);
        ps.setString(4, Type);
        ps.setInt(5,CustomerId);
        ps.setInt(6,UserId);
        ps.setInt(7,ContactId);
        ps.setString(8,StartDateTime);
        
        
        ps.setString(9,EndDateTime);
        
        
         //==============Testing  OverLapAppoint=
        //Check for any overlap appointments.
        //OverLapAppoint
        ZoneId Thezz = ZoneId.systemDefault();
        
        LocalDateTime sw = TestingConvertLDTtoUTC(startDate ,startTime,Thezz);
        LocalDateTime ew = TestingConvertLDTtoUTC(endDate ,endTime,Thezz);
        
        boolean OverLap = OverLapAppointAdd(sw,ew,CustomerId);
        
         //=================End Testing=========
        //if no overlap in the new appointment then it will add the new appointment
        if(OverLap == true){
        
        //Excute SQL statement
        
        ps.execute();
        
        if(ps.getUpdateCount() > 0)
            System.out.println(ps.getUpdateCount() + " \n rows(s) affected!");
        else
            System.out.println("\n No change!");
        
      //creating an object
        FXMLLoader loader = new FXMLLoader();
        //which fxml file to load
        
        loader.setLocation(getClass().getResource("/View_Controller/AddUpAppointView.fxml"));
        //call the load method
        loader.load();
        
        AddUpAppointController MController = loader.getController();
        
         MController.changeScreenTitle(2);
        Parent AddMenuParent = loader.getRoot();
        Scene AddMenuScene = new Scene(AddMenuParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();  
        window.setScene(AddMenuScene);
        window.show();
    
        }//end of if overlap
        }//end of if 
        
        }//end of try
        catch(Exception e){
            
          Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid values or it might be java.net.SocketTimeoutException: Read timed out");
            alert.showAndWait();
            System.out.println("Exception " );
    
        
        
        }//end of catch
        }//end of addButton
    

  //==============================Interface==lambda===========================================================Interface==lambda=====================================
    
    //public static Customer lookupCustomer(int CustomerId) throws SQLException{
    //This lambda expresion looks up a customer based on the customer id as a parameter.
    LookupCustomer LOkup =  ( CustomerId )  ->  {

for (Customer seachId : getAllCustomers()){
    
    
    if(seachId.getId() == CustomerId)
    {
        
        return seachId;
        
    
}
}
return null;
};
             
//================================end Interface=================================================================end Interface============================================
 
    /**
     This method will display the appointment that's been selected in the interface before this screen. The method will display the info of the chosen Appointment. 
     * The method also help to retrieve the customer id and appointment id for other methods. 
     * @param choosenOne the appointment that will be updated
     * @throws SQLException sql exception
     */
    public void displayAppointmentUpdateInfo(Appointment choosenOne) throws SQLException{
    
        TextFieldID1.setText(String.valueOf(choosenOne.getAppointmentId()));
        TextFieldTitle.setText(String.valueOf(choosenOne.getTitle()));
        TextFieldDescription.setText(String.valueOf(choosenOne.getDescription()));
        TextFielLocation.setText(String.valueOf(choosenOne.getLocation()));
        TextFieldType.setText(String.valueOf(choosenOne.getType()));
        
      
                 StartTimeCB.setValue(choosenOne.getStartDate().toLocalTime());
                 StartDate.setValue(choosenOne.getStartDate().toLocalDate());
                 
                 EndTimeCB.setValue(choosenOne.getEndDate().toLocalTime());
                 EndDate.setValue(choosenOne.getEndDate().toLocalDate());
                 
                
                Users user = lookupUser(choosenOne.getUserId());
                UserIdComboBox.setValue(user);
           //=============================interface=Lambda==================================================================interface=Lambda=============
                           //Customer TheChoosenOne = lookupCustomer(choosenOne.getCustomerId());

                  
                  //Lambda expresion that will lookup a customer. 
                 Customer TheChoosenOne =  LOkup.LookUpCustomerMethod(choosenOne.getCustomerId());
                 CustomerIdComboBox.setValue(TheChoosenOne);
                
                
                
           //=============================end interface===============================================================end interface==================================================
                Contacts contact = lookupContact(choosenOne.getContactId());
                ContactComboBox.setValue(contact);
                
                
             
       //FOR CANCEL BUTTON
       CusId = choosenOne.getCustomerId();
     
      // for the overlap check
       APPOid = choosenOne.getAppointmentId();
        
 
   ObservableList<Appointment> AppointmentList2 = DBAppointments.getCustomerAppointments(CusId);
    AppointmentTABLE.setItems(AppointmentList2);
            
    ApIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));//need to be the same as inside the constructor of Appointment.java
    TitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
    LocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
    ContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
    startDT.setCellValueFactory(new PropertyValueFactory<>( "StartDate"));
    endDT.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
   
  CustomerName.setText(CustomerIdComboBox.getSelectionModel().getSelectedItem().getName());
 
 
    }
       
 /**
  This method will change the title, button and text fields depending on the int inserted in the method.This method changes the names of the buttons or titles to add or update an appointment.
  * @param num the int will determine the type of interface
  * @throws SQLException sql exception
  */
 public void changeScreenTitle(int num) throws SQLException{
     
                 //The following observable list fills the combo box with the according types.
                 ObservableList<Contacts> ContactList = DBContacts.getAllContacts();
                 ContactComboBox.setItems(ContactList);
               
                ObservableList<Customer> CustomerList = DBCustomer.getAllCustomers();
                CustomerIdComboBox.setItems(CustomerList);
               
                ObservableList<Users> UsersList = DBUsers.getAllUsers();
                UserIdComboBox.setItems(UsersList);
               
    if(num == 1)
    {MainTitleLabel.setText("Update Appointment");
    AddUpdateButton.setText(" Update ");
    AddUpdateButton2.setText("Add");
    
    }//end of if

   if(num == 2) {
       
       //The res of this method will set the text for the text field,buttons or labels.
       
   MainTitleLabel.setText("Add Appointment");
   
    AddUpdateButton.setText(" Add ");
    AddUpdateButton2.setText("Update");
    
    StartDate.setValue(LocalDate.now());
    EndDate.setValue(LocalDate.now());
           
    StartTimeCB.setValue(LocalTime.of(8, 0));
    EndTimeCB.setValue(LocalTime.of(12,0));
    
   //Customers name to the label 
    CustomerNameLabel.setText("All Appointments");
               
    ObservableList<Appointment> AppointmentList2 = DBAppointments.getAllAppointments();
    AppointmentTABLE.setItems(AppointmentList2);
            
    ApIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));//need to be the same as inside the constructor of Appointment.java
    TitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
    LocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
    ContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
    startDT.setCellValueFactory(new PropertyValueFactory<>( "StartDate"));
    endDT.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
                 
     
   }
    
 }   
  /**
   This method looks up for the customer based on the int inserted in the method
   * @param CustomerId the id the wil be looked up
   * @throws SQLException sql exception 
   * @return seachId the customer object
   */

 public static Customer lookupCustomer(int CustomerId) throws SQLException{

for (Customer seachId : getAllCustomers()){
    
    
    if(seachId.getId() == CustomerId)
    {
        
        return seachId;
        
    
}
}
return null;
}
/**
 This method looks up for the user based on the int inserted in the method.
 * @param uSERId the id the will be looked up
 * @return seachId returns a User object
 * @throws SQLException sql exception
 */
  public static Users lookupUser(int uSERId) throws SQLException{

for (Users seachId : getAllUsers()){
    
    
    if(seachId.getId() == uSERId)
    {
        
        return seachId;
        
    
}
}
return null;
}

  /**
   This method looks up for the contact based on the int inserted in the method. 
   * @param ContactId the id that will be  looked up
   * @return seachId a contacts objects
   * @throws SQLException sql exception
   */
  public static Contacts lookupContact(int ContactId) throws SQLException{

for (Contacts seachId : getAllContacts()){
    
    
    if(seachId.getId() == ContactId)
    {
        
        return seachId;
        
    
}
}
return null;
}
  
  

/**
 This method will convert the localdateTime to UTC into a String. 
 * @param SD the local date time that will be converted.
 * @param ST THE LOCAL DATE TIME THAT WILL BE CONVERTED.
 * @return formattedDateTime String of the converted time
 * 
 */
  public static String ConvertLDTtoUTC (LocalDate SD,LocalTime ST){
  
  
        //Get the localdatetime you need to know what zone
        ZoneId UserZoneId = ZoneId.systemDefault();
        
         //Convert date and time into one unit datetime
        LocalDateTime FirstLDT = LocalDateTime.of(SD,ST);
        
        //This will convert the DATE AND TIME to your zone date time
          ZonedDateTime UserLDT = ZonedDateTime.of(FirstLDT, UserZoneId);
         
        //This will convert the zone date time to UTC 
          Instant LocalToUTC = UserLDT.toInstant();
         
          //This line will convert the Instant to a string 
           String DATEtIME = LocalToUTC.toString();
          
           //converst to zoneddatetime 
          ZonedDateTime zdt = ZonedDateTime.parse(DATEtIME);
          
           //converts to localdateTime so the database can read it
          LocalDateTime ldt = zdt.toLocalDateTime();
          
          DateTimeFormatter Startformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
          String formattedDateTime = ldt.format(Startformatter);
          
  return formattedDateTime;
  }
  
  
  
  /**
    This method converts the local dateTime to UTC AND the utc time will be saved as a local date time VARIABLE. Gets the zone id and converts that time to UTC.
    * The UTC time is then stored into a LOCAL DATE TIME variable.
    * @param SD local date
    * @param ST LOCAL TIME
    * @param zId zoned id 
    * @return ldt a localdatetime object
    */
  public static LocalDateTime TestingConvertLDTtoUTC (LocalDate SD,LocalTime ST,ZoneId zId){
  
  
  //Get the localdatetime you need to know what zone
        ZoneId UserZoneId = zId;//ZoneId.systemDefault();//of("Asia/Tokyo");//systemDefault();
         
         //Convert date and time into one unit datetime
        LocalDateTime FirstLDT = LocalDateTime.of(SD,ST);
        
        //This will convert the DATE AND TIME to your zone date time
          ZonedDateTime UserLDT = ZonedDateTime.of(FirstLDT, UserZoneId);
         
        //This will convert the zone date time to UTC 
          Instant LocalToUTC = UserLDT.toInstant();
          
          //This line will convert the Instant to a string 
           String DATEtIME = LocalToUTC.toString();
          
           //converst to zoneddatetime 
          ZonedDateTime zdt = ZonedDateTime.parse(DATEtIME);
          
          // converts to localdateTime so the database can read it
          LocalDateTime ldt = zdt.toLocalDateTime();
         
  return ldt;
  }
  
 /**
     This method checks for any overlaps with other appointments when a new appointment is added or updated. The method will check the startdateTime and end dataTime then compare then to other appointments. 
     * If any of the appointments overlap in any way, it wont be added or updated and will display a message informing the user what happen and with appointment it overlaps. 
     * @param start local date time
     * @param end local date time
     * @param id int of the appointment
     * @throws SQLException sql exception 
     * @return false false if not true 
     */
  public  boolean OverLapAppointAdd(LocalDateTime start, LocalDateTime end,int id) throws SQLException{ //LocalDateTime MightLap){
       
    
          ZoneId TheZ = ZoneId.systemDefault();
          Alert alert;
          //The for loop will check each appointment of the customer
         for (Appointment Overlap : getCustomerAppointments(id)){
            //check time overlap
           
            //This will turn the Overlap starttime back to utc time
            LocalDateTime ins = TestingConvertLDTtoUTC(Overlap.getStartDate().toLocalDate(),Overlap.getStartDate().toLocalTime(),TheZ);
             LocalDateTime eins = TestingConvertLDTtoUTC(Overlap.getEndDate().toLocalDate(),Overlap.getEndDate().toLocalTime(),TheZ);
               
             //this if condition will check the if the start time is after the other appointments and the end is before the other appointments. Also checks if the other appointments are inside the start and end date times.
                if((start.isAfter(ins.minusMinutes(1))  && end.isBefore(eins.plusMinutes(1)) ) || (ins.isAfter(start.minusMinutes(1)) && eins.isBefore(end.plusMinutes(1)))){
                    
                 alert =  new Alert(Alert.AlertType.CONFIRMATION,"Your appoint time overlaps Appointment Id: " + Overlap.getAppointmentId() + "\n Please select a different time. ");
                 Optional<ButtonType> result = alert.showAndWait();
               
                return false;
            
                }
            //this if condition will check the if the start time is after the other appointments and the end is before the other appointments. Also checks if the other appointments are inside the start and end date times.    
            if((start.isAfter(ins) && start.isBefore(eins) && end.isAfter(eins)) || (start.isBefore(ins) && end.isBefore(eins) && end.isAfter(ins ))){//(ins.isAfter(start.minusMinutes(1)) && eins.isBefore(end.plusMinutes(1))){   // || (start.isAfter(ins.minusMinutes(1)) && end.isAfter(eins.plusMinutes(1)))  ){
                  
                  
                         
                 alert =  new Alert(Alert.AlertType.CONFIRMATION,"Your appoint time overlaps Appointment Id: " + Overlap.getAppointmentId() + "\n Please select a different time.(2) ");
                 Optional<ButtonType> result = alert.showAndWait();
               
                return false;
            
                }
             
           }//end of for
          // After checking for any overlap it will check if the end if before the start time so it could make sence.
         if(end.isBefore(start)){
         
         
          alert =  new Alert(Alert.AlertType.CONFIRMATION,"Your start time needs to be befor your end time and on the same day");
                 Optional<ButtonType> result = alert.showAndWait();
               
                return false;
         
         
         }
         //The rest of this method will check if the hours selected are within EST business hours OR anything in the zone variable.
          LocalTime BuisnessOpen = LocalTime.of(8,0);
            LocalDate Sdate = start.toLocalDate();////   //LocalDate.of(1993, 1,4);
            LocalTime  BuisnessClosed = LocalTime.of(22, 00);
            LocalDate Edate = start.toLocalDate();//.plusDays(1);//LocalDate.of(2022, 5,6);////// EndDate.getValue();
            LocalDate Esdate = end.toLocalDate().minusDays(1);
            ZoneId zone = ZoneId.of( "US/Eastern");//
          
            LocalDateTime SdateTime = TestingConvertLDTtoUTC(Sdate,BuisnessOpen,zone);
            LocalDateTime EdateTime = TestingConvertLDTtoUTC(Edate,BuisnessClosed,zone);
            LocalDateTime EsdateTime = TestingConvertLDTtoUTC(Esdate,BuisnessClosed,zone);
          
             //if true, the date time is within business hours else 
         if ((start.isAfter(SdateTime.minusMinutes(1)) && end.isBefore(EdateTime.plusMinutes(1)))){ 
          
                 alert =  new Alert(Alert.AlertType.CONFIRMATION,"Your date and time does not overlap and are within business hours!");
                 Optional<ButtonType> result = alert.showAndWait();
               
                  System.out.println("\nYour date and time does not overlap and are within business hours!");
                  if(result.isPresent() && result.get() == ButtonType.OK) {
                  return true;}
                  else{return false;}
          
           }
         else{
         alert =  new Alert(Alert.AlertType.CONFIRMATION,"The time needs to be within EST business hours. \nMake sure the dates match.");
                   Optional<ButtonType> result = alert.showAndWait();
                    return false;
         }
         
     
    }
/**
 no function
 */
    @FXML
    private void EndTimePull(ActionEvent event) {
    }
 
   /**
   This method will convert the local date time to a different zone id local date time. zId is the LocaldateTime and then to UTC and then to the convertoZ zone id. 
   * @param SD local date
   * @param ST local time
   * @param zId ZoneId 
   * @param convertoZ zoneId
   * @return ldt local date time object
    */
      public static LocalDateTime ConvertLDTtoUTCtoLDT (LocalDate SD,LocalTime ST,ZoneId zId,ZoneId convertoZ){
  
  
  //Get the localdatetime you need to know what zone
        ZoneId UserZoneId = zId;//ZoneId.systemDefault();//of("Asia/Tokyo");//systemDefault();
         
         //Convert date and time into one unit datetime
        LocalDateTime FirstLDT = LocalDateTime.of(SD,ST);
        
        //This will convert the DATE AND TIME to your zone date time
          ZonedDateTime UserLDT = ZonedDateTime.of(FirstLDT, UserZoneId);
         
        //This will convert the zone date time to UTC 
          Instant LocalToUTC = UserLDT.toInstant();
          
          //This line will convert the Instant to a string 
           String DATEtIME = LocalToUTC.toString();
          
           //converst to zoneddatetime 
          ZonedDateTime zdt = ZonedDateTime.ofInstant(LocalToUTC, convertoZ);
          
          // converts to localdateTime so the database can read it
          LocalDateTime ldt = zdt.toLocalDateTime();
         
  return ldt;
  }
     
    /**
     This method checks for any overlaps with other appointments when a new appointment is added or updated. The method will check the startdateTime and end dataTime then compare then to other appointments. 
     * If any of the appointments overlap in any way, it wont be added or updated and will display a message informing the user what happen and with appointment it overlaps. 
     * @param start local date time 
     * @param end local date time 
     * @param APPiD int ot the appointment
     * @throws SQLException SQL exception
     * @return false if not true
     */
      public  boolean OverLapAppointUpdate(LocalDateTime start, LocalDateTime end,int APPiD) throws SQLException{ //LocalDateTime MightLap){
       
     
     //start and end should be already converted to utc before using this method or the compareing will be wrong.
    
          ZoneId TheZ = ZoneId.systemDefault();
          Alert alert;
          
          
          // this for loop will check all the appointments of the selected customer. 
         for (Appointment Overlap : getCustomerAppointments(CustomerIdComboBox.getSelectionModel().getSelectedItem().getId())){
            //check time overlap
           //This will turn the Overlap starttime back to utc time
            LocalDateTime ins = TestingConvertLDTtoUTC(Overlap.getStartDate().toLocalDate(),Overlap.getStartDate().toLocalTime(),TheZ);
             LocalDateTime eins = TestingConvertLDTtoUTC(Overlap.getEndDate().toLocalDate(),Overlap.getEndDate().toLocalTime(),TheZ);
             
            
           //this if condition will check the if the start time is after the other appointments and the end is before the other appointments. Also checks if the other appointments are inside the start and end date times.
            if((start.isAfter(ins.minusMinutes(1))  && end.isBefore(eins.plusMinutes(1)) ) || (ins.isAfter(start.minusMinutes(1)) && eins.isBefore(end.plusMinutes(1)))){//(start.isAfter(ins.minusMinutes(1)) && start.isBefore(eins.plusMinutes(1))&&end.isAfter(eins.plusMinutes(1)) )  ){
                    
                //This if statement avoids the loop from selected the same id so it could be updated,
                     if( APPiD != Overlap.getAppointmentId()){
                         
                      alert =  new Alert(Alert.AlertType.CONFIRMATION,"Your appoint time overlaps Appointment Id: " + Overlap.getAppointmentId() + "\n Please select a different time. ");
                      Optional<ButtonType> result = alert.showAndWait();
               
                       return false;
            }
            }
             //this if condition will check the if the start time is after the other appointments and the end is before the other appointments. Also checks if the other appointments are inside the start and end date times.
            if((start.isAfter(ins) && start.isBefore(eins) && end.isAfter(eins)) || (start.isBefore(ins) && end.isBefore(eins) && end.isAfter(ins ))){
                      //This if statement avoids the loop from selected the same id so it could be updated,
                     if( APPiD != Overlap.getAppointmentId()){
                         
                      alert =  new Alert(Alert.AlertType.CONFIRMATION,"Your appoint time overlaps Appointment Id: " + Overlap.getAppointmentId() + "\n Please select a different time.(2) ");
                      Optional<ButtonType> result = alert.showAndWait();
               
                      return false;
            }
            }
                
            }//end of for
         
            // After checking for any overlap it will check if the end if before the start time so it could make sence.
           if(end.isBefore(start)){
         
            alert =  new Alert(Alert.AlertType.CONFIRMATION,"Your start time needs to be befor your end time and dates need to match.");
            Optional<ButtonType> result = alert.showAndWait();
               
            return false;
         
         }
           //The rest of this method will check if the hours selected are within EST business hours OR anything in the zone variable. 
            LocalTime BuisnessOpen = LocalTime.of(8,0);
            LocalDate Sdate = start.toLocalDate();////   //LocalDate.of(1993, 1,4);
            LocalTime  BuisnessClosed = LocalTime.of(22, 00);
            LocalDate Edate = start.toLocalDate();//.plusDays(1);//LocalDate.of(2022, 5,6);////// EndDate.getValue();
            ZoneId zone = ZoneId.of( "US/Eastern");//
          
         
            LocalDateTime SdateTime = TestingConvertLDTtoUTC(Sdate,BuisnessOpen,zone);
            LocalDateTime EdateTime = TestingConvertLDTtoUTC(Edate,BuisnessClosed,zone);
          
            //if true, the date time is within business hours else 
         if ((start.isAfter(SdateTime.minusMinutes(1)) && end.isBefore(EdateTime.plusMinutes(1)))){  
          
                 alert =  new Alert(Alert.AlertType.CONFIRMATION,"DateTime does not overlap and are within EST business hours!");
                 Optional<ButtonType> result = alert.showAndWait();
               
                 System.out.println("\nYour date and time does not overlap and are within business hours!");
                 
                  if(result.isPresent() && result.get() == ButtonType.OK) {
                   return true;}
                  
                  else{return false;}
          
           }
         else{
                   alert =  new Alert(Alert.AlertType.CONFIRMATION,"The time needs to be within EST business hours \nDates need to match.");
                   Optional<ButtonType> result = alert.showAndWait();
                    return false;
         }
         
         
 }

/**
 no function.
 */
    @FXML
    private void StartTimePull(ActionEvent event) {}
    
    /**
     This method gets the selected customer from the combo box and displays the appointments of the selected customer. 
     */
    @FXML
    private void CustomerPull(ActionEvent event) throws SQLException {
        
        
         //Customers name to the label 
        CustomerNameLabel.setText("Customer Name: ");
        
   ObservableList<Appointment> AppointmentList2 = DBAppointments.getCustomerAppointments(CustomerIdComboBox.getSelectionModel().getSelectedItem().getId());
   AppointmentTABLE.setItems(AppointmentList2);
            
    ApIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));//need to be the same as inside the constructor of Appointment.java
    TitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
    LocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
    ContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
    startDT.setCellValueFactory(new PropertyValueFactory<>( "StartDate"));
    endDT.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
    
    CustomerName.setText(CustomerIdComboBox.getSelectionModel().getSelectedItem().getName());

        
    }
    /**
     This method will determine to add or update depending on the title. 
     * If the title is update, than it will update the selected appointment. If the title is add it will add a new appointment.
     */
    @FXML
    private void AddUpdateButtonAction2(ActionEvent event) {
        
        
           try{
             
         if(  MainTitleLabel.getText() == "Add Appointment"){
         
        if(AppointmentTABLE.getSelectionModel().isEmpty())
          {
            throw new Exception();
           
          }
         
        //creating an object
        FXMLLoader loader = new FXMLLoader();
        //which fxml file to load
        
        loader.setLocation(getClass().getResource("/View_Controller/AddUpAppointView.fxml"));
        //call the load method
        loader.load();
        
        AddUpAppointController MController = loader.getController();
        MController.changeScreenTitle(1);
        MController.displayAppointmentUpdateInfo(AppointmentTABLE.getSelectionModel().getSelectedItem());
        
        Parent AddMenuParent = loader.getRoot();
        Scene AddMenuScene = new Scene(AddMenuParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();  
        window.setScene(AddMenuScene);
        window.show();
         }//end of first if
      
             if(MainTitleLabel.getText() ==  "Update Appointment")
        {
        
        //creating an object
        FXMLLoader loader = new FXMLLoader();
        //which fxml file to load
        
        loader.setLocation(getClass().getResource("/View_Controller/AddUpAppointView.fxml"));
        //call the load method
        loader.load();
        AddUpAppointController MController = loader.getController();
        
        MController.changeScreenTitle(2);
        Parent AddMenuParent = loader.getRoot();
        Scene AddMenuScene = new Scene(AddMenuParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();  
        window.setScene(AddMenuScene);
        window.show();
        
    }
    }
    catch (Exception e){
    
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select an Appointment to Update. ");
            alert.showAndWait();
            System.out.println("Exception " );
    
    }
     
    }
/**
 This method will delete an appointment from the database. The appointment needs to be selected before it is selected. 
 */
    @FXML
    private void DeleteButton(ActionEvent event) {
        
        
         Alert alert; 
         
         try
         {
       if(AppointmentTABLE.getSelectionModel().isEmpty())// if a product is not select, it throws an exception  
       { throw new Exception();}
       
       
            int AppointmentId = AppointmentTABLE.getSelectionModel().getSelectedItem().getAppointmentId();
            String type = AppointmentTABLE.getSelectionModel().getSelectedItem().getType();
            int CustId = AppointmentTABLE.getSelectionModel().getSelectedItem().getCustomerId();
            
            //displays a message to confirm the appointment that will be delete
            alert =  new Alert(Alert.AlertType.CONFIRMATION,"\n Do you want to delete Appointment Id "+AppointmentId+" from Appointment");
            Optional<ButtonType> result = alert.showAndWait();
            
        if(result.isPresent() && result.get() == ButtonType.OK)
        {
        
            //This line will delete the selected appointment.
             DeleteAppointment(AppointmentId);
             
             if (  CustomerNameLabel.getText() != "All Appointments"){
              ObservableList<Appointment> AppointmentList = DBAppointments.getCustomerAppointments(CustId);
                      AppointmentTABLE.setItems(AppointmentList);
             }
             else{ 
                    ObservableList<Appointment> AppointmentList = DBAppointments.getAllAppointments();
                      AppointmentTABLE.setItems(AppointmentList);             
        }
             
           
                      
                      Alert alerat =  new Alert(Alert.AlertType.CONFIRMATION," Appointmennd Id: "+AppointmentId+" Type: "+type+""
                              + "\n has been canceled. ");
                      Optional<ButtonType> resulto = alerat.showAndWait();
             
             
        }
       
       else{
           alert = new Alert(Alert.AlertType.WARNING,"Select an appointment to delete from the table view");
            Optional<ButtonType>  result2 = alert.showAndWait();
       }
       } //end of try
   catch( Exception e ){

             alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select an Appointment to Delete. ");
            alert.showAndWait();
            System.out.println("Exception " + e.getMessage() );


   }
       
    }

    
}//end of class
