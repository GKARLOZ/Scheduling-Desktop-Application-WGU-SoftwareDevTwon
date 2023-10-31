/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giancodes.model;

import java.time.LocalDateTime;

/**
 *Creates Contact objects
 * @author gianm
 */
public class Contacts {
    
    
  
    
    private int id;
    private String name;
    
    private String email;
   
    
   
   
    private LocalDateTime recordEntryDates;
    
    public Contacts(int id, String name,String email)
    {
    
    this.id = id;
    this.name = name;
  this.email = email;
   
    
    }
    /**returns id
     @return id
     */
    public int getId(){return id;}
    /**returns name
     @return name
     */
    public String getName(){return name;}
   /**
    returns email.
    * @return email
    */
    public String getEmail(){return email;}
    
    
    /**
   //This methor will let you see just the name in the add combo box.
   * 
   @return id, name
   */
@Override
public String toString(){
return("["+id+"]  "+name);
}
   
   
}//end of class

    

