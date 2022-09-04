/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import DBAccess.DBCountries;
import static DBAccess.DBCountries.getAllCountries;
import static DBAccess.DBCustomer.getAllCustomers;
import DBAccess.DBFirstDivision;
import static DBAccess.DBFirstDivision.getAllStates;
import static DBAccess.DBFirstDivision.getUpdateStates;
import Model.Countries;
import Model.Customer;
import Model.FirstDivision;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DBConnection;
import utils.DBQuery;
import java.sql.PreparedStatement;
import java.time.ZoneId;
import static java.util.Locale.US;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import Lambda.LookupCredentialsInterface;



/**
 * FXML Controller class
 *
 * @author gianm
 */
public class UpdateCustomerRecordController implements Initializable {

    @FXML
    private TextField TextFieldID1;
    @FXML
    private TextField TextFieldName1;
    @FXML
    private TextField TextFieldAddress1;
    @FXML
    private TextField TextFieldPC1;
    @FXML
    private TextField TextFieldPhone1;
    @FXML
    private Button UpdateButton1;
    @FXML
    private Button CancelButton1;
    @FXML
    private Label ScreenTitle;
    @FXML
    private ComboBox<Countries> ComboCountry;
    @FXML
    private ComboBox<FirstDivision> ComboState;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        
        try{
        // This will display the countries in the country combo box.
             Countries US = lookupCountry(11);
            ObservableList<Countries> CountryList = DBCountries.getAllCountries();
            ComboCountry.setItems(CountryList);
            ComboCountry.setValue(US);
           
            //This will display the states on the states combo box
            ObservableList<FirstDivision> FDList = DBFirstDivision.getAllStates( ComboCountry.getSelectionModel().getSelectedItem().getId());
            ComboState.setItems(FDList);
            ComboState.getSelectionModel().selectFirst();
            
            
         }
         catch(SQLException throwables){
         
          throwables.printStackTrace();
         
         
         }
        
    }    
     /**
      This will update or add the customer to the database. 
      * @param event
      */
    @FXML
    private void UpdateButtonAction(ActionEvent event) throws SQLException, IOException {
        
           try{
        if(ScreenTitle.getText() ==  "Update Customer Records"){
            
          
        //This will get the connection    
        Connection conn = DBConnection.getConnection();
        
        String UpdateStatement = "UPDATE customers SET Customer_Name = convert(?,char),Address=convert(?,char),Postal_Code=convert(?,char),Phone=convert(?,char),Division_ID  = ?,Created_By=convert('script2',char),Last_Updated_By=convert('script2',char) WHERE Customer_ID=? ";
        
        DBQuery.setPreparedStatement(conn,UpdateStatement);
        
        java.sql.PreparedStatement ps = DBQuery.getPreparedStatement();
        
        //this will get the string from each text field. 
        String ID = TextFieldID1.getText();
        if(TextFieldName1.getText().isBlank() || TextFieldName1.getText().isEmpty())
        {TextFieldName1.setText("no name");}
        
        String Name = TextFieldName1.getText();
        String Phone  = TextFieldPhone1.getText();
        String address = TextFieldAddress1.getText();
        String postalCode = TextFieldPC1.getText();
        
          int state = ComboState.getSelectionModel().getSelectedItem().getId();
          int Country = ComboCountry.getSelectionModel().getSelectedItem().getId();
        
        ps.setString(1, Name);
        ps.setString(4, Phone);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setInt(5,state );
        ps.setString(6, ID);
       
        
        //Excute SQL statement
        ps.execute();
        
        if(ps.getUpdateCount() > 0)
            System.out.println(ps.getUpdateCount() + " \n rows(s) affected!");
        else
            System.out.println("\n No change!");
        
                //These lines will let you see it on the display of the main menu
               Parent AddMenuParent = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerRecords.fxml"));
               Scene AddMenuScene = new Scene(AddMenuParent);
               Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
               window.setScene(AddMenuScene);
               window.show();
        }//end of if for update
        
        
        //this if statement will add instead of updating the info.
        if(ScreenTitle.getText() == "Add Customer")
        {
       
        Connection conn = DBConnection.getConnection();
       
        String insertStatement = "insert into customers(Customer_Name, Phone, Address,Postal_Code,Division_ID,Created_By,Last_Updated_By)"
                                 +"values(?,?,?,?,?,'script1','script1');";
        
        DBQuery.setPreparedStatement(conn,insertStatement);
        
        PreparedStatement ps = DBQuery.getPreparedStatement();
         if(TextFieldName1.getText().isBlank() || TextFieldName1.getText().isEmpty())
         {  TextFieldName1.setText("no name");}
      
         
         String Name = TextFieldName1.getText();
       
        String Phone  = TextFieldPhone1.getText();
        String address = TextFieldAddress1.getText();
        String postalCode = TextFieldPC1.getText();
        int state;
        int Country = ComboCountry.getSelectionModel().getSelectedItem().getId();       
      
        if(ComboState.getSelectionModel().isEmpty())
        state = -1;
        
        else
       state = ComboState.getSelectionModel().getSelectedItem().getId();
        
        ps.setString(1, Name);
        ps.setString(2, Phone);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setInt(5,state);
        
        //Excute SQL statement
        ps.execute();
        
        if(ps.getUpdateCount() > 0)
            System.out.println(ps.getUpdateCount() + " \n rows(s) affected!");
        else
            System.out.println("\n No change!");
        
        
              //These lines will let you see it on the display of the main menu
               Parent AddMenuParent = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerRecords.fxml"));
               Scene AddMenuScene = new Scene(AddMenuParent);
               Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
               window.setScene(AddMenuScene);
               window.show();
       
        }//end of if for add
        
        }//end of try
         catch(Exception throwables){
         
          throwables.printStackTrace();
         
         
         }
             
    }//end of method
    
    /**
     This will take the user back to the main display. 
     */

    @FXML
    private void CancelButtonAction(ActionEvent event) throws IOException {
        
        //These lines will let you see it on the display of the main menu
               Parent AddMenuParent = FXMLLoader.load(getClass().getResource("/View_Controller/CustomerRecords.fxml"));
               Scene AddMenuScene = new Scene(AddMenuParent);
               Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
               window.setScene(AddMenuScene);
               window.show();
        
    }
    
    /**
     This method will display the info in each text field accordingly. The method will display the info select before the controller is displayed.
     * @param choosenOne. the appointment object
     * @throws SQLException sql exception
     */
     public void displayUpdateInfo(Customer choosenOne) throws SQLException{
    
        TextFieldID1.setText(String.valueOf(choosenOne.getId()));
        TextFieldName1.setText(String.valueOf(choosenOne.getName()));
        TextFieldAddress1.setText(String.valueOf(choosenOne.getAddress()));
        TextFieldPC1.setText(String.valueOf(choosenOne.getPostalCode()));    
        TextFieldPhone1.setText(String.valueOf(choosenOne.getPhone()));
      
            FirstDivision State = lookupState(choosenOne.getDivisionID());
            ComboState.setValue(State);
            
           Countries CountryName3 = getStringCountry(lookupUpdateCountry(choosenOne.getDivisionID()));
           ComboCountry.setValue(CountryName3);
           System.out.println("DisplayUpdateInfo: "+CountryName3);
           
         
    }
    

     /**
      This method will change the title on the controller depending on the int. 
      * If the number if 1, it will display update and will act according or 2 for add a new customer.
      * @param num.
      */
public void changeScreenTitle(int num){

    //the if will display an update
    if(num == 1)
    {ScreenTitle.setText("Update Customer Records");
    UpdateButton1.setText(" Update ");
    
    }
//the if will display an add
   if(num == 2)
   {ScreenTitle.setText("Add Customer");
    UpdateButton1.setText(" Add ");
   }

}

/**
 This method will get the country depending on the selected object of the combo box.
 * @return returns the selected country in the combo box
 */
public int getCountry(){

return ComboCountry.getSelectionModel().getSelectedItem().getId();    

}

/**
 The method will search the country depending on the int. 
 * The method will search through all the countries a see if any of them match the id entered in the method. 
 * @param CountryId. the id of the country that will be checked.
 * @return seachId the country object matched to the id
 * @throws SQLException sql exception
 */
public static Countries lookupCountry(int CountryId) throws SQLException{

for (Countries seachId : getAllCountries()){
    
    //this if statement will return the id if they match the int entered.
    if(seachId.getId() == CountryId)
    {
        
        return seachId;
        
    
}
}
return null;
}

/**
 This method will lookup the state entered in the method. 
 * The int entered in the method will be compared with each state id and will returned the one that matched.
 * @param StateId. the id of state that need to be searched
 * @return seachId the FirstDivision object matching the id
 * @throws SQLException sql exception 
 *
 */
public static FirstDivision lookupState(int StateId) throws SQLException{

for (FirstDivision seachId : getUpdateStates()){
    
    //this if statement will return the id if they match the int entered.
    if(seachId.getId() == StateId)
    {
        
        return seachId;
        
    
}
}
return null;
}

/**
 This method will lookup the country according to the state entered in the method. 
 * The int entered in the method will be compared with each state id and will returned the country that matched.
 * @param StateId.
 * @return seachId.getCountry() the country 
 * @throws SQLException sql exception 
 */
public static String lookupUpdateCountry(int StateId) throws SQLException{

for (FirstDivision seachId : getUpdateStates()){
    
    //this if statement will return the id if they match the int entered.
    if(seachId.getId() == StateId)
    {
        
        return seachId.getCountry();
        
    
}
}
return null;
}

/**
 This method will lookup the country according to the state entered in the method. 
 * The int entered in the method will be compared with each state id and will returned the country that matched.
 * @param CountryName.
 * @return seachId.
 * @throws SQLException sql exception
 */
public static Countries getStringCountry(String CountryName) throws SQLException{

for (Countries seachId : getAllCountries()){
 
    if(seachId.getName().equals(CountryName))
    {
       
        return seachId;
    
}
}

return null;
}

/**
 This will populate the state combo box according to the country selected. 
 */
    @FXML
    private void PickCountry(ActionEvent event) throws SQLException {
        
            ObservableList<FirstDivision> FDList = DBFirstDivision.getAllStates( ComboCountry.getSelectionModel().getSelectedItem().getId());
           
             ComboState.setItems(FDList);
            ComboState.getSelectionModel().selectFirst();
           
    }

}//end of class