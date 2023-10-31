/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giancodes.Controller;

import static com.giancodes.dao.DBAppointments.getAllAppointments;
import static com.giancodes.dao.DBAppointments.getUserAppointments;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import com.giancodes.dao.DBAppointments;
import com.giancodes.dao.DBContacts;
import com.giancodes.model.Appointment;
import com.giancodes.model.Contacts;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gianm
 */
public class AllContactsController implements Initializable {

    @FXML
    private Label TitleLabel;
    @FXML
    private RadioButton RBcustomer1;
    @FXML
    private ToggleGroup CusRecToogle1;
    @FXML
    private Label FAlert;
    @FXML
    private RadioButton RBAllAPP1;
    @FXML
    private TableView<Appointment> Table;
     @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;
    @FXML
    private TableColumn<Appointment, String> TitleCol;
    @FXML
    private TableColumn<Appointment, String> DescriptionCol;
    @FXML
    private TableColumn<Appointment, Integer> ContactCol;
    @FXML
    private TableColumn<Appointment, String> TypeCol;
    @FXML
    private TableColumn<Appointment, String> StartDateTimeCol;
    @FXML
    private TableColumn<Appointment, String> EndDateTimeCol;
    @FXML
    private TableColumn<Appointment, Integer> CustomerIdCol;
    @FXML
    private ComboBox<Contacts> ContactComboBox1;
    @FXML
    private Button contactButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        try{
            
            //This method will display an alert on the interface.
            NextAppointmentAlert();
            
            //This list will display contacts in the combo box
            ObservableList<Contacts> ContactList = DBContacts.getAllContacts();
            ContactComboBox1.setItems(ContactList);
            
            //Display all appointments on the table view
            ObservableList<Appointment> AppointmentList2 = DBAppointments.getAllAppointments();
            Table.setItems(AppointmentList2);
     
            //This will display the appointment info to each text field.
    appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));//need to be the same as inside the constructor of Appointment.java
    TitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
    DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
    ContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
    CustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
    StartDateTimeCol.setCellValueFactory(new PropertyValueFactory<>( "StartDate"));
    EndDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
    
    
      //This will sort the table by contact 
       ContactCol.setSortType(TableColumn.SortType.ASCENDING);
       StartDateTimeCol.setSortType(TableColumn.SortType.ASCENDING);
       Table.getSortOrder().add(ContactCol);
       Table.getSortOrder().add(StartDateTimeCol);
       Table.sort();
    
        }
        catch(SQLException throwables){
        
         throwables.printStackTrace();
        
        }
        
    }    

    /**
     *This will change the display to Customer records and display all customers. 
     * @param event
     */
    @FXML
    private void RBcustomerAction(ActionEvent event) throws IOException {
        
         //creating an object
        FXMLLoader loader = new FXMLLoader();
        //which fxml file to load
        
        loader.setLocation(getClass().getResource("/View/CustomerRecords.fxml"));
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
        
        loader.setLocation(getClass().getResource("/View/AllAppointment.fxml"));
        //call the load method
        loader.load();
        
        Parent AddMenuParent = loader.getRoot();
        Scene AddMenuScene = new Scene(AddMenuParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();  
        window.setScene(AddMenuScene);
        window.show();
        
    }
    
/**
 This will sort the table view. The table view will show ascending view of the dateTime and contact.
 */
    @FXML
    private void contactButtonAction(ActionEvent event) throws SQLException {
        
        
        ContactComboBox1.getSelectionModel().clearSelection();
        
         //This will sort the table by contact 
       ContactCol.setSortType(TableColumn.SortType.ASCENDING);
       StartDateTimeCol.setSortType(TableColumn.SortType.ASCENDING);
      Table.getSortOrder().add(ContactCol);
       Table.getSortOrder().add(StartDateTimeCol);
      
       Table.sort();
    
    }
    
    /**
     This method will display the appointments of the contact selected. 
     */

    @FXML
    private void contactComboBoxPull(ActionEvent event) throws SQLException {
     
        if(ContactComboBox1.getSelectionModel().isEmpty()){
            
               ObservableList<Appointment> AppointmentList2 = DBAppointments.getAllAppointments();
               Table.setItems(AppointmentList2);
            
        }
        else{
            
              int conId = lookupContactApp(ContactComboBox1.getSelectionModel().getSelectedItem().getId());
              ObservableList<Appointment> AppointmentListcon = DBAppointments.getContactAppointments(conId);
              Table.setItems(AppointmentListcon);
            
             }
        
       StartDateTimeCol.setSortType(TableColumn.SortType.ASCENDING);
       Table.getSortOrder().add(ContactCol);
       Table.getSortOrder().add(StartDateTimeCol);
       Table.sort();
    }

    /**
     This method will look for the contacts appointments when it is chosen in the comboBox.
     * @param ContactId is the id of the contact that is looked up.
     * @return n returns the appointment that match the id. 
     * @throws SQLException sql exception
     */
    public int lookupContactApp(int ContactId) throws SQLException{
        
        int n = -1 ;
    for(Appointment searchContactId : getAllAppointments())
    {
    
    if (searchContactId.getContactId() == ContactId){
    n =  searchContactId.getContactId();
    return n;
    
    }
    
    }
    
   return n;
    
    }
    
    /**
     This method will display an alert on the interface. 
     *The method will display the appointment that is 15min away or 5mins after. 
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
                 //This will display on the FAlert LABEL THE AMOUNT OF TIME LEFT TO THE APPOINTMENT
                 FAlert.setText("Appointment Id: " +appointment.getAppointmentId()+" start date/time: "+appointment.getStartDate().toLocalDate()+","+startTime+ " started "+interval * -1 + " minutes ago!");
               
         
                }
       
   
   }
   }
   
   } 
    
    
}//end of class
