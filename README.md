# :date: Scheduling-Desktop-Application-WGU-SoftwareDevTwon :calendar:


Title:
 Database Application 

Purpose:
The main goal of the scheduling application is to let the user store appointments in the most efficient way possible while making it easy to understand for the user. This program connects to an Oracle database that will verify the password on the login screen; also store any changes made in the program. Implementing localization and date/time APIs in the program will automatically update the time to the user's localization. This application was created with Scene Builder for the graphical user interface and coded in NetBeans IDE. The database was modified using MySQL workbench.

![scheduling-custrec-im2](https://user-images.githubusercontent.com/20764455/188293878-aef4bdc2-75ab-4233-8154-41de6de0ce06.png)



Additional Report:

 The additional report is in the customer records interface at the bottom of the table view. 
 When the user press the country report button, it will show a report of the total customers for each country. 

Author:
 Giancarlo Bustos

Contact Information: 
 
 GianBustos04@gmail.com

## :warning:	 New Version :warning:	

* The university no longer granted me access to the original database :exclamation:
* I created my own relational database for this application :exclamation:

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

 git clone git@github.com:GKARLOZ/Scheduling-Desktop-Application-WGU-SoftwareDevTwon.git


 ## Java in VSCODE 

 Install Java for vs code if you don't already have it. 
 Use this link: https://code.visualstudio.com/docs/languages/java

 ## :radioactive:	 Important Dependencies! :radioactive:	

 In order to run this program in VS Code, you need to have JavaFX installed.
 
 You could find your JAVAFX version in this link: https://gluonhq.com/products/javafx/
 
 JavaFX jars need to be added in the Java Projects/Referenced Libraries folder to run this program. 

 Once you installed JAVAFX, copy the path of the lib folder (from the JAVAFX that was installed). It should look something like this -->  C:/JavaOne/javafx-sdk-18.0.2/lib

 open the .vscode/lauch.json file and replace the current path with your own path.  It should look something like this -->  "vmArgs": "--module-path C:/JavaOne/javafx-sdk-18.0.2/lib --add-modules javafx.controls,javafx.fxml",


 ## 	:rocket: Lets GO! :flying_saucer:	

 Now open the src/Model/DatabaseApp.java file  
 Click run on top of the main method 
    
     Run | Debug
     public static void main(String[] args) {
     
## :ninja: Login info
 
  * User: test
  * Password: test
  
 ![scheduling-login-img](https://user-images.githubusercontent.com/20764455/188293766-6e4ac59f-f2f3-4578-a2d1-14bbedb0ccd5.png)




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



 
 
 
