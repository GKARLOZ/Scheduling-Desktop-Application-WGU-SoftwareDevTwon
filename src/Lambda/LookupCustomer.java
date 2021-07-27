/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lambda;

import Model.Customer;
import java.sql.SQLException;

/**
 *Lambda expression that Look for the Customer of the customers id.
 * @author gianm
 */
public interface LookupCustomer {
    
    
    
    
    /**
      Lambda Expression that Looks for the customer that matches the id in the method.It is located in the AddUpAppointController.java class.
      * It is on top of the display displayAppointmentUpdateInfo method and it is used inside that method look for the customer that matches the customer id 
      * from that appointment.
     * @param CustId the id that needs to be searched
     * @return Customer object
     * @throws SQLException 
     */
    Customer LookUpCustomerMethod(int CustId)throws SQLException;
    
}//end of method
