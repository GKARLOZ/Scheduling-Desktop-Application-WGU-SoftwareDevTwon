/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

   
import Model.Appointment;
import Model.Contacts;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;


/**
 *Retrieves the Contacts from the database. 
 * @author gianm
 */
public class DBContacts {
    
 
      /**
       Creates a list of contacts retrieved from the database.
       * @throws SQLException sql exception 
       * @return ContactList a list of contacts
       */
    public static ObservableList<Contacts> getAllContacts() throws SQLException{
    
        ObservableList<Contacts> ContactList = FXCollections.observableArrayList();
    try{
    
        //sql syntax
    String sql = " SELECT * FROM contacts";
    
    //gets connection to the database a add the sql statement
    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
    //executes the query 
    ResultSet rs = ps.executeQuery();
    //check every line 
    while(rs.next()){
    
        
        
    
 int ContactID = rs.getInt("Contact_ID");
 String Name= rs.getString("Contact_Name");
 String Email = rs.getString("Email");
 
     
     
     Contacts St =  new Contacts ( ContactID,Name,Email);
     ContactList.add(St);
    
    }
    }catch(SQLException throwables){
       throwables.printStackTrace();
    
    }
    
    return ContactList;
    
    }
}//end of class
