/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
/**
 *SETS A CONNECTION AND ADDS A SQL STRING 
 * @author gianm
 */

//This class will let the program create sql objects and return them as well
public class DBQuery {
    
    
    private static PreparedStatement statement;//statement reference
    
    
    /**
     Create statement object, will have a connect reference.
     * @param conn the connection 
     * @param sqlStatement  the sql statement
     * @throws SQLException sql exception 
     */
    //Create Statement object, will have a connect reference
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException
    {
    
        statement = conn.prepareStatement(sqlStatement);
        
    }
    
    
/**
 
 returns a sql object 
 * @return statement PreparedStatement object
 */
    public static PreparedStatement getPreparedStatement()
    {
    
    return statement;
    
    }
    
}
