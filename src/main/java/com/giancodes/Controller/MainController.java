package com.giancodes.Controller;




// import java.time.Instant;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.LocalTime;
// import java.time.ZoneId;
// import java.time.ZonedDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.TimeZone;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.giancodes.utils.DBConnection;
import com.giancodes.utils.DBQuery;

// import View_Controller.AddUpAppointController;
// import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *This is the main class. 
 * @author gianm
 */
public class MainController extends Application{

    
    /**
     * Creates a connection , launches the app and ends the connection.
     * @param args the command line arguments
     * @throws SQLException sql exception
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
       
        DBConnection.startConnection();//starts the connect from the application to the database 

        launch (args); //lauches the login in application 
        DBConnection.closeConnection();//closes the connection from the application to the database.
    
    }

    /**
     This method will load the Login interface. 
     */
    @Override
    public void start(Stage stage) throws Exception {
        
      
        System.out.println("Start method in DatabaseApp before initialzing parent root \n");

         Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Login.fxml"));

        System.out.println("Done parent root  \n");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
        System.out.print("\nLogin Working\n");
        
   
    }
    /**
     This method will delete a customer from the database. 
     * The matching id will be deleted.
     * @param id int of the appointment that will be deleted
     * @return false
     * @throws SQLException sql exception
     */
    public static boolean DeleteCustomerRecord(int id) throws SQLException{
        
        String deleteStatement = "DELETE FROM customers WHERE Customer_ID = "+id;
            
            Connection conn = DBConnection.getConnection();
            DBQuery.setPreparedStatement(conn,deleteStatement);//create statement object
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();//executes the deleStatement 
            
            
              return false;
    
}
    /**
     The method will return true if the id matched an appointment and has been deleted from the database.
     * @param id int of the appointment that will be deleted.
     * @return false
     * @throws SQLException sql exception
     */
     public static boolean DeleteAppointment(int id) throws SQLException{
        
        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = "+id;
            
            Connection conn = DBConnection.getConnection();
            DBQuery.setPreparedStatement(conn,deleteStatement);//create statement object
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            
            
              return false;
    
}
 
}//end of class
