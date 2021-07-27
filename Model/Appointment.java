/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;

/**
 *Creates Appointment objects.
 * @author gianm
 */
public class Appointment {
    
    
    private int appointmentid;
    private String Title;
    private String Description;
    private String Location;
    private int contactid;
    private String type;
    private int userId;
   private LocalDateTime StartDate;
   private LocalDateTime EndDate;
    private int CustomerId;
    
    public Appointment (int appointmentId, String Title ,String Description,String Location,int contactId,String Type, int CustomerId,int userId,LocalDateTime StartDate, LocalDateTime EndDate)
    {
    
    this.appointmentid = appointmentId;
    this.Title = Title;
    this.Description = Description;
    this.Location= Location;
    this.contactid = contactId;
    this.type = Type;
    this.userId = userId;
    this.StartDate = StartDate;
    this.EndDate = EndDate;
    this.CustomerId = CustomerId;
 
    
    
    }
    /**
     returns appointments id 
     * @return appointmentid
     */
    public int           getAppointmentId(){return appointmentid;}
    /**
     returns Title.
     * @return Title
     */
    public String        getTitle(){return Title;}
    /**
     returns Description
     * @return Description
     */
    public String        getDescription(){return Description;}
    /**
     returns Location
     * @return Location
     */
    public String        getLocation(){return Location;}
    /**
     returns type
     * @return type
     */
    public String        getType(){return type;}
    /**
     returns contactid
     * @return contactid
     */
    public int           getContactId(){return contactid;}
    /**
     returns userId
     * @return userId
     */
    public int           getUserId(){return userId;}
    /**
     * returns StartDate
     * @return StartDate
     */
    public LocalDateTime getStartDate(){return StartDate;}
    /**
     returns EndDate
     * @return EndDate
     */
    public LocalDateTime getEndDate(){return EndDate;}
    /**
     returns CustomerId
     * @return CustomerId
     */
    public int           getCustomerId(){return CustomerId;}
    
    
    
    
}
