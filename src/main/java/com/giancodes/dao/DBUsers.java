/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giancodes.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.giancodes.model.Users;
import com.giancodes.utils.DBConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Retrieves data from the database
 * @author gianm
 */
public class DBUsers {
    
      /**
       This method will retrieve the data and put it in a user observable list.
       * @return UsersList observableList list of user objects
       */
    public static ObservableList<Users> getAllUsers(){
    
        ObservableList<Users> UsersList = FXCollections.observableArrayList();
    try{
    
        //The sql syntax 
    String sql = " SELECT * FROM users";
    
    //uses the sql syntax to search for the data
    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
    
    ResultSet rs = ps.executeQuery();
    
    //gets all the data 
    while(rs.next()){
    
 int UserID = rs.getInt("User_ID");
 String Name= rs.getString("User_Name");
 String password = rs.getString("Password");
 
     
     //adds the data to the list
     Users St =  new Users ( UserID,Name,password);
     UsersList.add(St);
    
    }
    }catch(SQLException throwables){
       throwables.printStackTrace();
    
    }
    
    return UsersList;
    
    }
    
    

}//end of class
    
    
    
    
    
    
    
    
    
    
    
    

