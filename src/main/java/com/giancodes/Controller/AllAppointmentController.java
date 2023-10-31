/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giancodes.Controller;

import static com.giancodes.Controller.MainController.DeleteAppointment;
import static com.giancodes.dao.DBAppointments.getAllAppointments;
import static com.giancodes.dao.DBAppointments.getUserAppointments;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Node;

import com.giancodes.dao.DBAppointments;
import com.giancodes.dao.DBCustomer;
import com.giancodes.model.Appointment;
import com.giancodes.model.Customer;
import com.mysql.cj.xdevapi.Table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gianm
 */
public class AllAppointmentController implements Initializable {

    @FXML
    private Label TitleLabel;
    @FXML
    private TableView<Appointment> Table;
     @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;
    @FXML
    private TableColumn<Appointment, String> TitleCol;
    @FXML
    private TableColumn<Appointment, String> DescriptionCol;
    @FXML
    private TableColumn<Appointment, String> LocationCol;
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
    private RadioButton RBcustomer1;
    @FXML
    private ToggleGroup CusRecToogle1;
    @FXML
    private Label FAlert;
    @FXML
    private RadioButton RBAllAPP1;
    @FXML
    private RadioButton RBContacts1;
    @FXML
    private Button AddButton;
    @FXML
    private Button UpdateButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button ViewbyType;
    @FXML
    private ComboBox<Customer> CustomerNameCB;
    @FXML
    private Button AllAppointments;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      // TODO
        
         try{
            
             //populates the customer combo box
              ObservableList<Customer> CustomerList = DBCustomer.getAllCustomers();
               CustomerNameCB.setItems(CustomerList);
             

             //this method will display the 15min alert on the app
              NextAppointmentAlert();
             
             //Display all appointments on the table view
             ObservableList<Appointment> AppointmentList2 = DBAppointments.getAllAppointments();
            Table.setItems(AppointmentList2);
            
    appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));//need to be the same as inside the constructor of Appointment.java
    TitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
    DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
    LocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
    ContactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    TypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
    CustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
    StartDateTimeCol.setCellValueFactory(new PropertyValueFactory<>( "StartDate"));
    EndDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
    
    
      //Sort BY TYPE AND MONTH
      StartDateTimeCol.setSortType(TableColumn.SortType.ASCENDING);
      Table.getSortOrder().add(StartDateTimeCol);
      TypeCol.setSortType(TableColumn.SortType.ASCENDING);
      Table.getSortOrder().add(TypeCol);
      Table.sort();
        
        }
       catch(SQLException throwables){
         
          throwables.printStackTrace();
         
        }
         
    } 
    
   
/**
 This method will display the back to the customer interface. 
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

    @FXML
    private void RBALLAppAction(ActionEvent event) {
    }
/**
 This method adds a new appointment to the database.
 */
    @FXML
    private void AddButtonAction(ActionEvent event) throws IOException, SQLException {
        
         //creating an object
        FXMLLoader loader = new FXMLLoader();
        //which fxml file to load
        
        loader.setLocation(getClass().getResource("/View/AddUpAppointView.fxml"));
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
/**
 This method will update an appointment that was selected. 
 */
    @FXML
    private void UpdateButtonAction(ActionEvent event) {
        
         try{
         
        if(Table.getSelectionModel().isEmpty())
          {
            throw new Exception();
           
          }
         
        //creating an object
        FXMLLoader loader = new FXMLLoader();
        //which fxml file to load
        
        loader.setLocation(getClass().getResource("/View/AddUpAppointView.fxml"));
        //call the load method
        loader.load();
        
        AddUpAppointController MController = loader.getController();
        MController.changeScreenTitle(1);
        MController.displayAppointmentUpdateInfo(Table.getSelectionModel().getSelectedItem());
       
        
        Parent AddMenuParent = loader.getRoot();
        Scene AddMenuScene = new Scene(AddMenuParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();  
        window.setScene(AddMenuScene);
        window.show();
      
         }
    catch (Exception e){
             //displays the exception in the alert.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select an Appointment to Update. ");
            alert.showAndWait();
            System.out.println("Exception " );
   
    }
       
    }
    
    /**
     This method will display the next appointment within 15 minutes.
     * This will display the next 15 minutes or 5 minutes.
     * @throws SQLException sql exception
     */
    public void NextAppointmentAlert() throws SQLException{
        
         FAlert.setText("No appointments within 15 mins");
        
         LocalTime currentTime = LocalTime.now();
        int id = LoginController.getUser().getId();
   
   for(Appointment appointment : getUserAppointments(id)){
       
       
       if(appointment.getStartDate().toLocalDate().equals(LocalDate.now())){
       
         LocalTime startTime = appointment.getStartDate().toLocalTime();
         
         
         
          //gets the minutes left between the startTime and the currentTime
        long timeDifference = ChronoUnit.MINUTES.between(startTime, currentTime);
        // System.out.println("\n Appointment id:"+appointment.getAppointmentId()+" DatETIme: "+startTime+"Time left:" +timeDifference);
         
        
        
          long interval = timeDifference * -1;
         // System.out.println("\n Appointment id:"+appointment.getAppointmentId()+"Time left:" +interval);
         if(interval > 0 && interval <= 15){
             //adds the appointment with the right info
             FAlert.setText("Appointment Id: " +appointment.getAppointmentId()+ " start date/time: "+appointment.getStartDate().toLocalDate()+", "+startTime+"\n will start in " +interval+ " minutes!");
        
        }
        
          if(interval <= 1 && interval >= -10){
                         //adds the appointment with the right info
                 FAlert.setText("Appointment Id: " +appointment.getAppointmentId()+" start date/time: "+appointment.getStartDate().toLocalDate()+", "+startTime+ "\n started "+interval * -1 + " minutes ago!");
               
               }
   
       }//end of if date
   }//end of for
   
   }//end of method 
/**
 This method will display the contacts interface.
 */
    @FXML
    private void RBContactsAction(ActionEvent event) throws IOException {
        
           //creating an object
        FXMLLoader loader = new FXMLLoader();
        //which fxml file to load
        
        loader.setLocation(getClass().getResource("/View/AllContacts.fxml"));
        //call the load method
        loader.load();
      
        Parent AddMenuParent = loader.getRoot();
        Scene AddMenuScene = new Scene(AddMenuParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();  
        window.setScene(AddMenuScene);
        window.show();
    }
/**
 This method will delete the selected the appointment.
 */
    @FXML
    private void DeleteButtonAction(ActionEvent event) {
        
         Alert alert; 
         
         try {
             
       if(Table.getSelectionModel().isEmpty())// if a product is not select, it throws an exception  
       { throw new Exception();}
       
       
            int AppointmentId = Table.getSelectionModel().getSelectedItem().getAppointmentId();
            String type = Table.getSelectionModel().getSelectedItem().getType();
            int CustId = Table.getSelectionModel().getSelectedItem().getCustomerId();
            alert =  new Alert(Alert.AlertType.WARNING,"\n Do you want to delete Appointment Id "+AppointmentId+"?");
            Optional<ButtonType> result = alert.showAndWait();
            
        if(result.isPresent() && result.get() == ButtonType.OK){
        
            //This will delete an appointment from the database
                       DeleteAppointment(AppointmentId);
                       
                        NextAppointmentAlert();
                       
             //checks if the combo box is empty
             if(CustomerNameCB.getSelectionModel().isEmpty()){
                      ObservableList<Appointment> AppointmentList = DBAppointments.getAllAppointments();
                      Table.setItems(AppointmentList);
        
                      
                      Alert alerat =  new Alert(Alert.AlertType.CONFIRMATION," Appointmennd Id: "+AppointmentId+" Type: "+type+""
                              + "\n has been canceled. ");
                      Optional<ButtonType> resulto = alerat.showAndWait();
                 
                      
        }
             else{
                       
                       ObservableList<Appointment> AppointmentList = DBAppointments.getCustomerAppointments(CustomerNameCB.getSelectionModel().getSelectedItem().getId());
                      Table.setItems(AppointmentList);
        
             }
        }
       }
   
    //end of try
   catch( Exception e ){

            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select an Appointment to Delete. ");
            alert.showAndWait();
            System.out.println("Exception :" + e.getMessage() );

   }
       
    }
    /**
     This method will show a report of the total customers with a certain month.
     */
    private void ViewByMonth(ActionEvent event) throws SQLException {
        
        int jan = 0, feb = 0,mar = 0,april = 0,may = 0,june = 0,july = 0,aug = 0,sept = 0,oct = 0,nov = 0,dec = 0;
        
        Month month ;// = LocalDate.now().getMonth();
        
        for(Appointment appMonth : getAllAppointments()){
       // while(Appointment appMont: getAllAppointments()){
        month = appMonth.getStartDate().getMonth();
       
        //This will chose witch month is 
          if(month == LocalDate.now().getMonth().JANUARY)
            jan  ++;
        if(month == LocalDate.now().getMonth().FEBRUARY)
            feb  ++;
         if(month == LocalDate.now().getMonth().MARCH)
            mar ++;
         if(month == LocalDate.now().getMonth().APRIL)
            april ++;
         if(month == LocalDate.now().getMonth().MAY)
            may  ++;
         if(month == LocalDate.now().getMonth().JUNE)
            june ++;
         if(month == LocalDate.now().getMonth().JULY)
            july ++;
         if(month == LocalDate.now().getMonth().AUGUST)
            aug ++;
         if(month == LocalDate.now().getMonth().SEPTEMBER)
            sept ++;
         if(month == LocalDate.now().getMonth().OCTOBER)
            oct ++;
         if(month == LocalDate.now().getMonth().NOVEMBER)
            nov ++;
         if(month == LocalDate.now().getMonth().DECEMBER)
            dec ++;
        }
    
        
        
        
        
        
          Alert  alert =  new Alert(Alert.AlertType.CONFIRMATION,"\n Total number of customer by month"+
                  "\nJanuary: "+jan+"\nFebruary: "+feb+" \nMarch: "+mar+"\nApril: "+april+"\nMay: "+may+"\nJune: "+june+"\nJuly: "+july+"\nAugust: "+aug
          +"\nSeptember: "+sept+"\nOctober: "+oct+"\nNovember: "+nov+"\nDecember: "+dec);
           Optional<ButtonType> result = alert.showAndWait();
        
      
    }
/**
 This method will show a report of the total customers with a certain type and month of appointment. 
 */
    @FXML
    private void ViewByType(ActionEvent event) throws SQLException {
        
         
        //Two list to make the comparisons 
           ObservableList<String>       List11         = FXCollections.observableArrayList();
           ObservableList<String>      TotalTypes         = FXCollections.observableArrayList();
           
           //The final list 
             ObservableList<String>       TypeNames         = FXCollections.observableArrayList();
        
             //This for loop will make the first two list to compare later.
         for(Appointment appMonth : getAllAppointments()){
             
              String typeS = appMonth.getType();
              String monthS = appMonth.getStartDate().getMonth().toString();
         
             //This string is import. This string will compare what ever is inside. You could compare anything with this string 
             //This string will go inside the two list
          String typeMonth = "\n\nMonth: "+monthS+" Type: "+typeS;
         
          List11.add(typeMonth);
          TotalTypes.add(typeMonth);
          
          }
         
          TypeNames.add("Report of Appointments by Type and Month\n");
         
          //The count is for the amount for each month for every type
           int count = 0;
           //The index is to keep track which one to compare it to
           int index = 0;
           //String sst = "dnt work";
           String removeType = "no no no";//ignore the no no no 
           String insertType = "yes yes yes";//ignore the yes yes yes
           
         
           //This is the first list it will compare it to. It will get the first one and then 
           //compare it to the whole list of List11 and then it will get the second one and compare 
           // it to the whole list List11 again. and so on...
         for(String ss : TotalTypes){
             
                  for(String appMonth : List11){
                  
                     
                         //This will determine if the two string match.
                      if( appMonth.equals( TotalTypes.get(index))){
                      
                          
                     count ++;
                       
                       //This will insert the string to TypeNames if it is equal
                       insertType =  TotalTypes.get(index) + " Count: "+count;
                       
                       //if the type is repeated and has more then one count; it will be removed and replaced with a new count
                   if(count > 1 ){
                       removeType = TotalTypes.get(index) + " Count: "+(count - 1);
                       TypeNames.remove(removeType);
                      
                       boolean caught = false;
                       
                       //This is the final list and will update if the count is higher then the previous count.
                       //if it is then it will remove it and the new one will replace it. 
                         for (String hg : TypeNames)
                       {
                            
                           if(hg.equals(insertType)){
                          caught = true;
                           }
                       
                       }
                       if(caught == true){
                       TypeNames.remove(insertType);
                       }
                       
                       
                   }
                   //This will replace the one removed if it had a smaller count if not it will just add to the final list
                    TypeNames.add(insertType);
                      
                      }//end of if for equal type
                      
                      }//end of for of appMonth
                       count = 0;//need to be here in order to start over 
                       index ++;//needs to be here in order to keep track of the right index
                      
         }//end of totalTypes
                        
          
          Alert  alert =  new Alert(Alert.AlertType.CONFIRMATION,""+TypeNames);
          
          Optional<ButtonType> result = alert.showAndWait();
         
          ObservableList<Appointment> AppointmentList2 = DBAppointments.getAllAppointments();
          Table.setItems(AppointmentList2);
               
        TypeCol.setSortType(TableColumn.SortType.ASCENDING);
        Table.getSortOrder().add(TypeCol);
        Table.sort();
       
    }
/**
 This method will display the appointments of the customer selected. 
 * 
 */
    @FXML
    private void CustomerNamePull(ActionEvent event) throws SQLException {
        
        
        //since the combo box can't be empty cus of the .getCustomerAppointments method, we need to get all appointments this way
           if(CustomerNameCB.getSelectionModel().isEmpty()){
           
            ObservableList<Appointment> AppointmentList2 = DBAppointments.getAllAppointments();
            Table.setItems(AppointmentList2);
           
           
           }
           else{ //this will display the customers appointments
               ObservableList<Appointment> AppointmentListx = DBAppointments.getCustomerAppointments(CustomerNameCB.getSelectionModel().getSelectedItem().getId());
               Table.setItems(AppointmentListx);
              
           }
        //This will sort the table view in a ascending    
      StartDateTimeCol.setSortType(TableColumn.SortType.ASCENDING);
     Table.getSortOrder().add(StartDateTimeCol);
     TypeCol.setSortType(TableColumn.SortType.ASCENDING);
     Table.getSortOrder().add(TypeCol);
     Table.sort();
           
        
    }
/**
 This method will display all appointments to the table view.
 */
    @FXML
    private void AllAppointmentsAction(ActionEvent event) throws SQLException {
        
          
      CustomerNameCB.getSelectionModel().clearSelection();
      
       ObservableList<Appointment> AppointmentList2 = DBAppointments.getAllAppointments();
       Table.setItems(AppointmentList2);
        
      StartDateTimeCol.setSortType(TableColumn.SortType.ASCENDING);
      Table.getSortOrder().add(StartDateTimeCol);
      TypeCol.setSortType(TableColumn.SortType.ASCENDING);
      Table.getSortOrder().add(TypeCol);
      Table.sort();
      
    }

    @FXML
    private void sortBYMonth(ActionEvent event) throws SQLException {
        
        
        
            ObservableList<Appointment> AppointmentList2 = DBAppointments.getAllAppointments();
             ObservableList<Appointment>       List11      = FXCollections.observableArrayList();
            
            //<Month> aaa = new ArrayList<>();
            Month currentMonth = LocalDate.now().getMonth();
            
            
            for(Appointment aa : getAllAppointments()){
            
                if(aa.getStartDate().getMonth().equals(currentMonth)){
                           List11.add(aa);
                }
            }      
            
           
        
         Table.setItems(List11);
         
      StartDateTimeCol.setSortType(TableColumn.SortType.ASCENDING);
      Table.getSortOrder().add(StartDateTimeCol);
      Table.getSortOrder().add(StartDateTimeCol);
      TypeCol.setSortType(TableColumn.SortType.ASCENDING);
      Table.getSortOrder().add(TypeCol);
      Table.sort(); 
        
        }
    

    @FXML
    private void sortByWeek(ActionEvent event) throws SQLException {
        
        
             ObservableList<Appointment> AppointmentList2 = DBAppointments.getAllAppointments();
             ObservableList<Appointment>       List11      = FXCollections.observableArrayList();
             
             
               Calendar currentDateMinus1week = Calendar.getInstance();//This stament was copiend from the mkyong.com
              
            LocalDate ldt = LocalDateTime.now().toLocalDate();
            
            int aff;
             
              for(Appointment aa : getAllAppointments()){
             

                if(aa.getStartDate().toLocalDate().isAfter(ldt.minusDays(1)) && aa.getStartDate().toLocalDate().isBefore(ldt.plusDays(7))){
                           List11.add(aa);
                           //System.out.println(aa.getStartDate().getDayOfWeek().toString());
                }
                
            }     
           
         Table.setItems(List11);
         
      StartDateTimeCol.setSortType(TableColumn.SortType.ASCENDING);
      Table.getSortOrder().add(StartDateTimeCol);
      Table.getSortOrder().add(StartDateTimeCol);
      TypeCol.setSortType(TableColumn.SortType.ASCENDING);
      Table.getSortOrder().add(TypeCol);
      Table.sort(); 
        
      
      
        
    }

}//end of xlass
