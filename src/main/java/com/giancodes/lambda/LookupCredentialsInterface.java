/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.giancodes.lambda;

import java.time.ZoneId;

/**
 *Lambda expression checks the credentials 
 * @author gianm
 */
public interface LookupCredentialsInterface {
    
    
    /**
     Looks up the credentials entered in the login interface. It is located in the LoginController.java class.
     * Under the loginbutton method. The method is then used inside the loginbutton to check the credential match the ones
     * in the database.
     * @param user the user string 
     * @param password the string in the password text field.
     * @return true is the credentials check out. 
     */
     boolean LookUpCredetialsMethod(String user,String password);
    
}//end of class 
