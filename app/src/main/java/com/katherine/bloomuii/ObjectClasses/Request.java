package com.katherine.bloomuii.ObjectClasses;
//Parent Class
public abstract class Request {
    //Fields
    private String Date_Sent;
    private String Request_Status;
    private String Type_Of_Request;
    private String Classroom_Name;
    //Constructor
    public Request(){}
    //Getters and Setters
    public String getDate_Sent() {
        return Date_Sent;
    }
    public void setDate_Sent(String date_Sent) {
        Date_Sent = date_Sent;
    }
    public String getRequest_Status() {
        return Request_Status;
    }
    public void setRequest_Status(String request_Status) {
        Request_Status = request_Status;
    }
    public String getType_Of_Request() {
        return Type_Of_Request;
    }
    public void setType_Of_Request(String type_Of_Request) {
        Type_Of_Request = type_Of_Request;
    }
    public String getClassroom_Name() {
        return Classroom_Name;
    }
    public void setClassroom_Name(String classroom_Name) {
        Classroom_Name = classroom_Name;
    }
}
