/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giancodes.model;

/**
 *Creates FirstDivision objects (states)
 * @author gianm
 */
public class FirstDivision {
    
    
    private int id;
    private String Country;
    private String FDName;
    
    public FirstDivision(int id,String Country,String FDName){
    
    this.id = id;
    this.FDName = FDName;
    this.Country = Country;
    
    }
    
    
    //public int getId(){return id;}
    /**
     * returns FDName
     * @return FDName
     */
    public String getFDName(){return FDName;}
    /**returns Country 
     @return Country*/
    public String getCountry(){return Country;}
    /**returns id
     @return id*/
    public int    getId(){return id;}
    
    
    
    /** populates the combo box
     @return FDName*/
    @Override
    public String toString(){
    
    return FDName;
    
    }
    
}
