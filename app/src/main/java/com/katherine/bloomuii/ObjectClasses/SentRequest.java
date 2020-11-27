//Developer: Rohini Naidu
package com.katherine.bloomuii.ObjectClasses;
//Child of Request
public class SentRequest extends Request {
    //Fields
    private String Requested_User_Id;
    private String Requested_Username;
    //Constructor
    public SentRequest(){}
    //Getters and Setters
    public String getRequested_User_Id() {
        return Requested_User_Id;
    }
    public void setRequested_User_Id(String requested_User_Id) {
        Requested_User_Id = requested_User_Id;
    }
    public String getRequested_Username() {
        return Requested_Username;
    }
    public void setRequested_Username(String requested_Username) {
        Requested_Username = requested_Username;
    }
}
