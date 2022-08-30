/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;
import java.io.*;
import DBAccess.DBCountries;
import DBAccess.DBUsers;
import static DBAccess.DBUsers.getAllUsers;
import Model.Countries;
import Model.Users;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;
import java.util.Locale;
import java.time.ZoneId;
import javafx.scene.control.Label;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import static java.time.LocalDateTime.now;
import java.time.LocalTime;
import java.util.Scanner;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import Lambda.LookupCredentialsInterface;
/**
 * FXML Controller class
 *
 * @author gianm
 */
public class LoginController implements Initializable{
    @FXML
    private Label SignInLabel1;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Button LoginButton;
   
    private Label SignInLabel;         
    @FXML
    private Label Zlabel;
    
    //number of unsuccessful logins 
    int unsuccessfulCount = 0;
    private static Users user; 
    

    /**
     * Initializes the controller class. The initializes will set The Zlabel,will determine the language.
     * @param url URL
     * @param rb  ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
      
      
      try{    
   
         //=======tESTING ERASE LATER============================
         
        System.out.println("Inside Login Controller \n");       
         //System.out.println("\n Remember to change the overlap method in the update in the addupappointcontroller.java " +LocalTime.now());
         
         //=======================================
          
    //This bloock of code is for the Location label on Login 
        ZoneId zoneId = ZoneId.systemDefault();
        String theZone = zoneId.getId();
        Zlabel.setText("ZoneId: "+theZone);
        
        
          
        
     //Chnage language to the default in the system 
       // Locale.setDefault(new Locale("fr"));
       // Locale locale = Locale.getDefault();
        
        //This if statement will determine the language if it is spanish or french 
        if(Locale.getDefault().getLanguage().equals("es") || Locale.getDefault().getLanguage().equals("fr") )
         {
             
             
           rb = ResourceBundle.getBundle("Model/Nat", Locale.getDefault());
           
           //This will display a prompt text in the rights places.
           
           usernameInput.setPromptText(rb.getString(usernameInput.getPromptText()));
           passwordInput.setPromptText(rb.getString(passwordInput.getPromptText()));
          
           LoginButton.setText(rb.getString(LoginButton.getText()));
            SignInLabel1.setText(rb.getString(SignInLabel1.getText()));
          
           
         }
      }
      catch(Exception e){
      
      
      }
    
    } 
    
    
 /** 
  *This button will login if the credentials are correct and has the lambda expression. If the credentials are correct or not it will record it on the login_activity.txt.
  *@param event 
  
  */
    @FXML
    private void LoginButton(ActionEvent event ) throws IOException{
        
     
      
        try{
            
        String filename = "login_activity.txt",userInfo;
        Scanner keyboard = new Scanner(System.in);
        FileWriter fwriter = new FileWriter(filename, true);
        
        //creat and open file
        PrintWriter outputFile = new PrintWriter(fwriter);
        
           
          //Info for the login activity file   
        ZoneId zone = ZoneId.systemDefault();
        LocalDate date = LocalDate.now(ZoneId.systemDefault());
        LocalTime time = LocalTime.now(ZoneId.systemDefault());
            
            
            
            
      //-------------------------------------Interface- lambda------------------------------------------------------------    
             
              //boolean Credentials = lookupCredetials(usernameInput.getText(),passwordInput.getText());
              
               String user1 = usernameInput.getText();
               String password1 = passwordInput.getText();
               
               //Lambda Expression 
               boolean Credentials =  LUCm.LookUpCredetialsMethod(user1, password1);
             
      //---------------===================----end Interface-------------------------------------------------------------      
             
             
             
            if(Credentials == true)
              {
             
        //get items and write to the file 
        outputFile.println("Successfull login  [Name: "+usernameInput.getText()+" ] [Date: "+date+" ] [Time: "+time+" ] [Zone: "+zone+" ]");
        outputFile.close();
        
        System.out.print("Valid Password\n");
               
               
               //These lines will let you see it on the display of the main menu
               Parent AddMenuParent = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerRecords.fxml"));
               Scene AddMenuScene = new Scene(AddMenuParent);
               Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
               window.setScene(AddMenuScene);
               window.show();
             
               
              
              }
            
          else{
        unsuccessfulCount ++;
          //get items and write to the file
        outputFile.println("Unsuccessfull login attempt: "+unsuccessfulCount+" [Name: "+usernameInput.getText()+" ] [Date: "+date+" ] [Time: "+time+" ] [Zone: "+zone+" ]\n");
        outputFile.close();
                 
        
                
               System.out.println("Invalid Password");
               throw new ArithmeticException("Invalid Username or Password");
              
              }
        
              }//end of try
        
         catch(ArithmeticException e )
             {
               
              //This whole block will convert the error message to spanish(es) if that is the difault language
              if(Locale.getDefault().getLanguage().equals("es") || Locale.getDefault().getLanguage().equals("fr") ){
              ResourceBundle rb = ResourceBundle.getBundle("Model/Nat", Locale.getDefault());
              Alert alert = new Alert(Alert.AlertType.WARNING);
              alert.setTitle(rb.getString("Error") + " " + rb.getString("Dialog"));
              alert.setContentText(rb.getString("Invalid") + " " + rb.getString("Username")
                                 + " " + rb.getString("or")+ " " + rb.getString("Password"));
              
               alert.showAndWait();
              
              }
              else{
                 
              Alert alert = new Alert(Alert.AlertType.WARNING);
              alert.setTitle("Error Dialog");
              alert.setContentText(e.getMessage());
              alert.showAndWait();
              
              }
                 
        }
    
    }
    
    
//==========================Interface======================================================== 
    
//Lambda expression that will lookup the credentials 
    LookupCredentialsInterface LUCm = ( user1, password1) -> { 
             
         ObservableList<Users> UsersList = DBUsers.getAllUsers();
         for(Users searchUser : getAllUsers())
         {
         
         if(searchUser.getName().equals(user1)  && searchUser.password().equals(password1))
         {   
             setUser(searchUser);
             return true;}
         
         }
    
          return false;
    
          };
    
//=========================EndInterface======================================================== 

    
    /**
     This method will set the user that loged in. 
     
     * @param user a Users object
     */
    public void setUser(Users user){
        
        this.user = user;
        
       }
   /**
    This method returns the user that loged in.
    * @return user Users object
    */
    public static Users getUser(){ return user;}
    
    
}//end of class
