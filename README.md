# :date: Scheduling-Desktop-Application :calendar:



Title:
 Database Application (Currently connected to a live database)  :exclamation: 

Purpose:
The main goal of the scheduling application is to let the user store appointments in the most efficient way possible while making it easy to understand for the user. This program connects to an Oracle database that will verify the password on the login screen; also store any changes made in the program. Implementing localization and date/time APIs in the program will automatically update the time to the user's localization. This application was created with Scene Builder for the graphical user interface and coded in NetBeans IDE. The database was modified using MySQL workbench.

![scheProjGif](https://github.com/GKARLOZ/Scheduling-Desktop-Application-WGU-SoftwareDevTwon/assets/20764455/5bfb493d-0bd2-40ef-b9d4-6724f85f0828)

<!---![scheduling-custrec-im2](https://user-images.githubusercontent.com/20764455/188293878-aef4bdc2-75ab-4233-8154-41de6de0ce06.png) --->



Additional Report:

 The additional report is in the customer records interface at the bottom of the table view. 
 When the user press the country report button, it will show a report of the total customers for each country. 

Author:
 Giancarlo Bustos

Contact Information: 
 
 GianBustos04@gmail.com
 <br>
 <br>

## :warning:	 New Version :warning:	
* Added Maven now for easier execution or feel free to execute the jar file inside the target folder:  <br>
("\target\scheduling-1.0-SNAPSHOT-shaded.jar") :exclamation:
  
IDE Version: Visual Studio code <br>
Version: 1.71.0 (user setup) <br>
Node.js: 16.14.2 <br>
V8: 10.2.154.15-electron.0 <br>
OS: Windows_NT x64 10.0.22000 <br>
MySQL Connector Driver: mysql-connector-java-8.0.22 <br>
Javafx: javafx-sdk-18.0.2 <br>
JDK: JDK 11 openjdk-11.0.16.1 <br>

<br>
<br>

 ## :radioactive:	 Important Dependencies! :radioactive:	
 In order to run the jar file, installed JRE 11 or later.  <br>
  Install Java for vs code if you don't already have it. 
 Use this link: https://code.visualstudio.com/docs/languages/java
 <br>
 <br>
 
 ## 1. Clone this project 
 git clone git@github.com:GKARLOZ/Scheduling-Desktop-Application-WGU-SoftwareDevTwon.git 
 
 <br>
 <br>
 
 ## 2. Lets GO! :flying_saucer:		:rocket: 
 Open this project with VSCode
 Now open the src\main\java\com\giancodes\Main.java file 
 Click run on top of the main method 
    
     Run | Debug
     public static void main(String[] args) {
   
Or go to the root directory for this project and run: 

     mvn clean install
     java -jar target/scheduling-1.0-SNAPSHOT-shaded.jar

 <br>
 <br>
     
## 3. Login info :ninja:
 
  * User: test
  * Password: test
  
 ![scheduling-login-img](https://user-images.githubusercontent.com/20764455/188293766-6e4ac59f-f2f3-4578-a2d1-14bbedb0ccd5.png)

