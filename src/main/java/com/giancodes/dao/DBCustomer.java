/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giancodes.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.giancodes.model.Customer;
import com.giancodes.utils.DBConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * retrieves customers from the database.
 * @author gianm
 */
public class DBCustomer {
    
    /**
     Retrieves customers from the database and adds them to a list as a customer objects.
     * @throws SQLException sql exception 
     * @return CustomerList a list of customers from the database.
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException{
    
        ObservableList<Customer> Customerlist = FXCollections.observableArrayList();
    try{
    
        //sql syntax
    String sql = " SELECT * FROM customers";
    //uses the sql syntax 
    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
    //executes the query and checks the database 
    ResultSet rs = ps.executeQuery();
    //checks every line 
    while(rs.next()){
    
        
        
    //the lines that are retrived from the database
     int CustomerID = rs.getInt("Customer_ID");
     String CustomerName = rs.getString("Customer_Name");
     String Address = rs.getString("Address");
     String PostalCode = rs.getString("Postal_Code");
     String Phone = rs.getString("Phone");
     
     int DivisionID = rs.getInt("Division_ID");
     
     String createdBy = rs.getString("Created_By");
     String LastUpdatedBy = rs.getString("Last_Updated_By");

     Customer St = new Customer(CustomerID,CustomerName,Phone,Address,PostalCode,DivisionID, createdBy, LastUpdatedBy);  //,RecordEntryDates
     Customerlist.add(St);
    
    }
    }catch(SQLException throwables){
       throwables.printStackTrace();
    
    }
    
    return Customerlist;
    
    }
   
}//end of class
