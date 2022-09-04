# Scheduling-Desktop-Application-WGU-SoftwareDevTwon


Title:
 Database Application 

Purpose:
 Users can add Customers and add appointments for the customers.

Additional Report:

 The additional report is in the customer records interface at the bottom of the table view. 
 When the user press the country report button, it will show a report of the total customers for each country. 

Author:
 Giancarlo Bustos

Contact Information: 
 
 GianBustos04@gmail.com

## New Version 

* The old version used the database that was provided from the university.
* I created my own relational database for this application

Database: 

 FreeSQLDatabase.com

IDE Version: 
 Visual Studio code
 Version: 1.71.0 (user setup)
 Node.js: 16.14.2
 V8: 10.2.154.15-electron.0
 OS: Windows_NT x64 10.0.22000


MySQL Connector Driver:

 mysql-connector-java-8.0.22

Javafx:

 javafx-sdk-18.0.2

JDK:

 JDK 11 openjdk-11.0.16.1

 ## Clone this project 

 git clone (copied linked)


 ## Java in VSCODE 

 Install Java for vs code if you don't already have it. 
 Use this link: https://code.visualstudio.com/docs/languages/java

 ## Important Dependencies!

 In order to run this program in VS Code, you need to have JavaFX installed.
 
 You could find your JAVAFX version in this link: https://gluonhq.com/products/javafx/
 
 JavaFX jars need to be added in the Java Projects/Referenced Libraries folder to run this program. 

 Once you installed JAVAFX, copy the path of the lib folder (from the JAVAFX that was installed). It should look something like this -->  C:/JavaOne/javafx-sdk-18.0.2/lib

 open the .vscode/lauch.json file and replace the current path with your own path.  It should look something like this -->  "vmArgs": "--module-path C:/JavaOne/javafx-sdk-18.0.2/lib --add-modules javafx.controls,javafx.fxml",


 ## Lets GO!

 Now open the src/Model/Inventory.java file  
 Click run on top of the main method 
    
     Run | Debug
     public static void main(String[] args) {
     
## Login info
 
  User:test
  Password:test

## VS CODE GUIDELINES 

Here is a guideline to help you get started to write Java code in Visual Studio Code.

### Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

### Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).



 
 
 
