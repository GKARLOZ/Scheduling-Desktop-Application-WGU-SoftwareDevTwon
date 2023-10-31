/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giancodes.model;

/**
 *Creates Country objects
 * @author gianm
 */
public class Countries {
    private int id;
    private String name;
    
    public Countries(int id, String name){
    
    this.id = id;
    this.name = name;
    
    
    }
    
    /**returns id. 
     @return id
     */
    public int getId(){return id;}
    /**
     returns name.
     * @return name
     */
    public String getName(){return name;}

    /**
    //This methor will let you see just the name in the add combo box.
      @return name.
      */
@Override
public String toString(){
return(name);
}


}//end of class
