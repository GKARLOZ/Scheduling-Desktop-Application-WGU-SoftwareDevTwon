/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.*;

/**
 *Connects the app to the database.
 * @author gianm
 */
public class DBConnection {
    
    //jdbc url parts 
    
    private static final String protocol   = "jdbc" ;
    private static final String vendorName = ":mysql:";
    private static final String ipAddress  = "//wgudb.ucertify.com:3306/WJ06vcF";
   
    //jdbc url 
    private static final String jdbcURL = protocol + vendorName + ipAddress;//v8.0.22
    
    //driver interface reference
    private static final String     MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static       Connection conn            = null;
    
    private static final String username = "U06vcF"; //username
    private static final String password = "53688882132";//password
    
    
    /**
     Start a connection to a database
     * @return conn a connection 
     */
    //Start a connection to a database 
    public static Connection startConnection()
    {
     try{
    Class.forName(MYSQLJDBCDriver);
    conn =(Connection)DriverManager.getConnection(jdbcURL,username,password);
    System.out.print("Connection Successfull");
    }
     catch(ClassNotFoundException e){
       e.printStackTrace();//more explicit vs e.getMess....
     //System.out.print(e.getMessage());
     
     }
     catch(SQLException e){
     e.printStackTrace(); // more explicit vs e.getM....
     //System.out.print(e.getMessage());
     
     }
    return conn;
    }
    /**
     This method will continue a connection without a reconnection when needed.
     * @return conn a connection 
     */
    //This method will continue a connection without reconnecting when needed. 
    public static Connection getConnection(){
        
        return conn;
        
    }
    
    /**
     Method to close a connection 
     * 
     */
    //method to close a connection 
    public static void closeConnection(){
        
        
        try{
            conn.close();
        }
        catch(Exception e){
            
            //do nothing
        }
    }
    
    
//end of class
}
