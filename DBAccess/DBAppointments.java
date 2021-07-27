/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import Model.Appointment;
import utils.DBConnection;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import static java.time.Clock.offset;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;


/**
 *Retrieves the appointments from the database.
 * @author gianm
 */
public class DBAppointments {
    
    /**
     Creates a list of all the appointments in the database.
     * @throws SQLException sql exception 
     * @return AppointmentList returns a list of appointments
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException{
    
        ObservableList<Appointment> Appointmentlist = FXCollections.observableArrayList();
    try{
    
        //sql syntax
    String sql = " SELECT * FROM appointments";
    //String sql = "select *  from appointments,contacts,customers where appointments.Contact_ID = contacts.Contact_ID and appointments.Customer_ID = customers.Customer_ID";
    
            
           //gets connection and adds the sql statement
    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
    //executes the query
    ResultSet rs = ps.executeQuery();
    
    while(rs.next()){
    
        
        
    
 int appointmentid = rs.getInt("Appointment_ID");
 String Title = rs.getString("Title");
 String Description = rs.getString("Description");
 String Location = rs.getString("Location");
 String Type = rs.getString("Type");
 int contactId = rs.getInt("Contact_ID");
 int userId = rs.getInt("User_ID");
 LocalDate StartDate = rs.getDate("Start").toLocalDate();
 LocalDate EndDate = rs.getDate("End").toLocalDate();
 LocalTime StartTime = rs.getTimestamp("Start").toLocalDateTime().toLocalTime();//toLocalDateTime().atZone(localZoneid).toLocalTime();
  LocalTime EndTime = rs.getTimestamp("End").toLocalDateTime().toLocalTime();//toLocalDateTime().atZone(localZoneid).toLocalTime();
  
 
 LocalDateTime StartLDT = LocalDateTime.of(StartDate, StartTime);
 LocalDateTime EndLDT = LocalDateTime.of(EndDate, EndTime);

 int CustomerId = rs.getInt("Customer_ID");
 
 
 //===================================================
 
        //Converts the dateTime to the zoneid dateTime  
 LocalDateTime startldt = rs.getTimestamp("Start").toLocalDateTime();
 LocalDateTime endldt = rs.getTimestamp("End").toLocalDateTime();
 
  //System.out.println("DBAppointments.java   startldt: "+startldt);

 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
 String formattedSDateTime = startldt.format(dtf);
 String formattedEDateTime = endldt.format(dtf);
 
 
 
 //==================================================
     
     
  Appointment St =  new Appointment ( appointmentid,  Title , Description,Location,contactId,Type, CustomerId, userId, StartLDT,EndLDT);
  Appointmentlist.add(St);
    
    }
    }catch(SQLException throwables){
       throwables.printStackTrace();
    
    }
    
    return Appointmentlist;
    
    }//end of getAllAppointments
    
    
    
    /**
     Creates a list of appointments of the customer. The customers id need to the in the parameters.
     * @param id the customers id 
     * @return AppointmentList2 a list of appointment of the customer.
     * @throws SQLException sql exception 
     */
    
    public static ObservableList<Appointment> getCustomerAppointments(int id) throws SQLException{
             
            
          ObservableList<Appointment> Appointmentlist2 = FXCollections.observableArrayList();
    try{
    //sql syntax, this syntax is unic in order to retrieve the correct data
    String sql = "SELECT * FROM appointments,customers where appointments.Customer_ID =" +id
               + " and customers.Customer_ID="+id;
    
   // String sql = "SELECT * FROM appointments,contacts where appointments.Contact_ID ="+id+" and contacts.Contact_ID ="+id;
    //get connection and adds the sql statement
    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
    
    while(rs.next()){
    
        
 int appointmentid = rs.getInt("Appointment_ID");
 String Title = rs.getString("Title");
 String Description = rs.getString("Description");
 String Location = rs.getString("Location");
 String Type = rs.getString("Type");
 int contactId = rs.getInt("Contact_ID");
 int userId = rs.getInt("User_ID");
 
 
 LocalDate StartDate = rs.getDate("Start").toLocalDate();
 LocalDate EndDate = rs.getDate("End").toLocalDate();
 
 //Regardless of what you put as a zone id, when you retrieve that time from the database it will change it to systemDefault cus of local time. 
  LocalTime StartTime = rs.getTimestamp("Start").toLocalDateTime().toLocalTime();//toLocalDateTime().atZone(localZoneid).toLocalTime();
  LocalTime EndTime = rs.getTimestamp("End").toLocalDateTime().toLocalTime();//toLocalDateTime().atZone(localZoneid).toLocalTime();
  
   LocalDateTime StartLDT = LocalDateTime.of(StartDate, StartTime);//.atZone(localZoneid).toLocalDateTime();
   LocalDateTime EndLDTd = LocalDateTime.of(EndDate, EndTime);
  
 int CustomerId = rs.getInt("Customer_ID");
 
     Appointment St =  new Appointment ( appointmentid,  Title , Description,Location,contactId,  Type, CustomerId,userId,StartLDT,EndLDTd);
     Appointmentlist2.add(St);
    
    }
    }catch(SQLException throwables){
       throwables.printStackTrace();
    
    }
    
    return Appointmentlist2;
}//end of get customer appointments
    
    
    
    
    /**
     Creates a list of contact appointments. The contact id needs to be in the parameters
     * @param id the id of the contact
     * @return AppointmentList2 a list of appointment of the contact
     * @throws SQLException sql exception 
     */
      public static ObservableList<Appointment> getContactAppointments(int id) throws SQLException{
             
            
          ObservableList<Appointment> Appointmentlist2 = FXCollections.observableArrayList();
    try{
    
    //String sql = "SELECT * FROM appointments,customers where appointments.Customer_ID =" +id
               //+ " and customers.Customer_ID="+id;
    //a unic sql statment in order to retrieve the correct data
    String sql = "SELECT * FROM appointments,contacts where appointments.Contact_ID ="+id+" and contacts.Contact_ID ="+id;
    
    //get connection and adds the sql statement 
    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
    
    while(rs.next()){
    
       //checks every statement  
 int appointmentid = rs.getInt("Appointment_ID");
 String Title = rs.getString("Title");
 String Description = rs.getString("Description");
 String Location = rs.getString("Location");
 String Type = rs.getString("Type");
 int contactId = rs.getInt("Contact_ID");
 int userId = rs.getInt("User_ID");
 
 
 LocalDate StartDate = rs.getDate("Start").toLocalDate();
 LocalDate EndDate = rs.getDate("End").toLocalDate();
 
 //Regardless of what you put as a zone id, when you retrieve that time from the database it will change it to systemDefault cus of local time. 
  LocalTime StartTime = rs.getTimestamp("Start").toLocalDateTime().toLocalTime();//toLocalDateTime().atZone(localZoneid).toLocalTime();
  LocalTime EndTime = rs.getTimestamp("End").toLocalDateTime().toLocalTime();//toLocalDateTime().atZone(localZoneid).toLocalTime();
  
   LocalDateTime StartLDT = LocalDateTime.of(StartDate, StartTime);//.atZone(localZoneid).toLocalDateTime();
   LocalDateTime EndLDTd = LocalDateTime.of(EndDate, EndTime);
  
 int CustomerId = rs.getInt("Customer_ID");
 
     Appointment St =  new Appointment ( appointmentid,  Title , Description,Location,contactId,  Type, CustomerId,userId,StartLDT,EndLDTd);
     Appointmentlist2.add(St);
    
    }
    }catch(SQLException throwables){
       throwables.printStackTrace();
    
    }
    
    return Appointmentlist2;
}//end of contact appointments
    
      
    /**
     Creates a list of contact appointments. The contact id needs to be in the parameters
     * @param id the id of the contact
     * @return AppointmentList2 a list of appointment of the user
     * @throws SQLException sql exception 
     */
      public static ObservableList<Appointment> getUserAppointments(int id) throws SQLException{
             
            
          ObservableList<Appointment> Appointmentlist2 = FXCollections.observableArrayList();
    try{
    
    //String sql = "SELECT * FROM appointments,customers where appointments.Customer_ID =" +id
               //+ " and customers.Customer_ID="+id;
    //a unic sql statment in order to retrieve the correct data
    String sql = "SELECT * FROM appointments where appointments.User_ID ="+id;
    
    //get connection and adds the sql statement 
    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
    
    while(rs.next()){
    
       //checks every statement  
 int appointmentid = rs.getInt("Appointment_ID");
 String Title = rs.getString("Title");
 String Description = rs.getString("Description");
 String Location = rs.getString("Location");
 String Type = rs.getString("Type");
 int contactId = rs.getInt("Contact_ID");
 int userId = rs.getInt("User_ID");
 
 
 LocalDate StartDate = rs.getDate("Start").toLocalDate();
 LocalDate EndDate = rs.getDate("End").toLocalDate();
 
 //Regardless of what you put as a zone id, when you retrieve that time from the database it will change it to systemDefault cus of local time. 
  LocalTime StartTime = rs.getTimestamp("Start").toLocalDateTime().toLocalTime();//toLocalDateTime().atZone(localZoneid).toLocalTime();
  LocalTime EndTime = rs.getTimestamp("End").toLocalDateTime().toLocalTime();//toLocalDateTime().atZone(localZoneid).toLocalTime();
  
   LocalDateTime StartLDT = LocalDateTime.of(StartDate, StartTime);//.atZone(localZoneid).toLocalDateTime();
   LocalDateTime EndLDTd = LocalDateTime.of(EndDate, EndTime);
  
 int CustomerId = rs.getInt("Customer_ID");
 
     Appointment St =  new Appointment ( appointmentid,  Title , Description,Location,contactId,  Type, CustomerId,userId,StartLDT,EndLDTd);
     Appointmentlist2.add(St);
    
    }
    }catch(SQLException throwables){
       throwables.printStackTrace();
    
    }
    
    return Appointmentlist2;
}//end of contact appointments
    
   
}//end of class
