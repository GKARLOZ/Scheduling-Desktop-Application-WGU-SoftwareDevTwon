/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *This class creates user objects. 
 * @author gianm
 */
public class Users {
    
    
    
    private int id;
    private String name;
    private String password;
    
    public Users(int id, String name, String password){
        
        this.id = id;
        this.name = name;
        this.password = password;
    
    
    
    }
    
  
    /**returns the id
     @return id*/
    public int getId(){return id;}
    /**returns name
     @return name*/
    public String getName(){return name;}
    /**returns password
     @return password*/
    public String password(){return password;}
    
 
    /**
 This method will let you see the name in the add user combo box.
 */
@Override
public String toString(){
return("["+id+"]  "+name);
}




}//end of class
