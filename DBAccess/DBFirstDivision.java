/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import Model.FirstDivision;
import View_Controller.UpdateCustomerRecordController;
import utils.DBConnection;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;





/**
 *Retrieves all the states from the database
 * @author gianm
 */
public class DBFirstDivision {
    
    
    /**
     Retrieves the data and stores them in a list.
     * @return FDList a list of state objects
     * @throws SQLException sql exception 
     * @param countryId id of the country 
     */
    public static ObservableList<FirstDivision> getAllStates(int countryId) throws SQLException{
    
        ObservableList<FirstDivision> FDList = FXCollections.observableArrayList();    
    
    try{

      //sql syntax
String sql = "select Division_ID,Country,Division from countries, first_level_divisions where countries.Country_ID ="+countryId+" and first_level_divisions.COUNTRY_ID="+countryId;

//uses the sql syntax
PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

ResultSet rs = ps.executeQuery();
//checks for every line 
while(rs.next()){
int  DivisionId = rs.getInt("Division_ID");
String Country = rs.getString("Country");
String State = rs.getString("Division");
FirstDivision fd = new FirstDivision(DivisionId,Country,State);
FDList.add(fd);

}

        

    }
    
catch(SQLException ex){
        ex.printStackTrace();
        }
        
        
return FDList;

        }

   /**
    Puts the states in the list
    * @throws SQLException sql exception 
    * @return FDList list of states
    */
       public static ObservableList<FirstDivision> getUpdateStates() throws SQLException{
    
        ObservableList<FirstDivision> FDList = FXCollections.observableArrayList();    
    
    try{

        //sql syntax
String sql = "select Division_ID,Country,Division from countries, first_level_divisions where countries.Country_ID = first_level_divisions.COUNTRY_ID";
//uses the sql syntax to search to the database
PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

ResultSet rs = ps.executeQuery();
//checks every line
while(rs.next()){
int  DivisionId = rs.getInt("Division_ID");
String Country = rs.getString("Country");
String State = rs.getString("Division");
FirstDivision fd = new FirstDivision(DivisionId,Country,State);
FDList.add(fd);

}
    }
    
catch(SQLException ex){
        ex.printStackTrace();
        }
       
        
return FDList;

        }
    
    
    
    
    
}
