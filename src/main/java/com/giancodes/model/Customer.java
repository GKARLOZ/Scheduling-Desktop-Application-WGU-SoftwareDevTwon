/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giancodes.model;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

/**
 *Creates Customer objects
 * @author gianm
 */
public class Customer {
    
    
  
    
    private int id;
    private String name;
    
    private String phone;
    private String address;
    private String postalCode;
    private int DivisionID;
    
    private String Created_By;
    private String Last_Updated_By;
    
   // private String State;
    
   
   
    private LocalDateTime recordEntryDates;
    
    public Customer(int id, String name,String phone, String address, String postalCode,int DivisionID, String Created_By, String Last_Updated_By)
    {
    
    this.id = id;
    this.name = name;
  this.phone = phone;
  this.address = address;
  this.postalCode = postalCode;
  this.DivisionID = DivisionID;
  this.Created_By = Created_By;
  this.Last_Updated_By = Last_Updated_By;
  
    }
    
    /**returns id.
     @return id*/
    public int getId(){return id;}
    /**returns name.
     @return name*/
    public String getName(){return name;}
    /**returns phone.
     @return phone*/
    public String getPhone(){return phone;}
    /**returns address.
     @return address*/
    public String getAddress(){return address;}
    /**returns postalCode.
     @return postalCode*/
    public String getPostalCode(){return postalCode;}
    /**returns DivisionID.
     @return DivisionID*/
    public int getDivisionID(){return DivisionID;}
    /**returns Created by .
     @return Created by*/
    public String getCreatedBy(){ return Created_By;}
    /**returns last_updated _by
     @return last update by*/
    public String getLastUpdatedBy(){return Last_Updated_By;}
   
    
    /**
      his method will let you see just the name in the add combo box.
      @return id, name 
      */
@Override
public String toString(){
return("["+id+"]  "+name);
}
   
    
 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}//end of class
