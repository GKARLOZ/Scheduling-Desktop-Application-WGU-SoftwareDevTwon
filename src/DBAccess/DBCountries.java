/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;
import utils.DBConnection;
import Model.Countries;
//import java.security.Timestamp;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;


/**
 *Retrieves the countries from the database. 
 * 
 * @author gianm
 */
public class DBCountries {
    
    /**
     Retrieves the data from the database and adds them to a list
     * @throws SQLException sql exception 
     * @return clist a list of countries.
     */
    public static ObservableList<Countries> getAllCountries() throws SQLException{
    
    ObservableList<Countries> clist = FXCollections.observableArrayList();
    
    try{
    //sql syntax
        String sql = "SELECT * from countries";
        //it adds the sql query 
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        //executes the sql query 
        ResultSet rs = ps.executeQuery();
        //checks every line in the query 
        while(rs.next()){
          int countryId = rs.getInt("Country_ID");
          String countryName = rs.getString("Country");
          Countries C = new Countries(countryId, countryName);
          clist.add(C);
          
        
        
        }
    
    }catch(SQLException throwables){
        throwables.printStackTrace();
    
    }
    
    return clist;
    
    
    }
    
    
    public static void checkDateConversion(){
    System.out.println("CREATE DATE TEST");
    String sql = "select Create_Date from countries";
    
    try{
    
    PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
    while(rs.next()){
    Timestamp ts = rs.getTimestamp("Create_Date");
    System.out.println("CD: " + ts.toLocalDateTime().toString());
    
    }
    
    }catch(SQLException throwables){
    
       throwables.printStackTrace();
    
    }
    
    
    
    }
    
}//end of class
