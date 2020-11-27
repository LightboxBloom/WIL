//Developer: Rohini Naidu
package com.katherine.bloomuii.ObjectClasses;
import java.util.HashMap;
public class Classroom {
    //Fields
    private String Classroom_Id;
    private String Classroom_Name;
    private String Teacher_Name;
    private HashMap<String,SentRequest> Requests_Sent;
    //Costructor
    public Classroom(){}
    //Getter and Setters
    public String getClassroom_Name() {
        return Classroom_Name;
    }
    public void setClassroom_Name(String classroom_Name) {
        Classroom_Name = classroom_Name;
    }
    public String getClassroom_Id() {
        return Classroom_Id;
    }
    public void setClassroom_Id(String classroom_Id) {
        Classroom_Id = classroom_Id;
    }
    public HashMap<String,SentRequest> getRequests_Sent() {
        return Requests_Sent;
    }
    public void setRequests_Sent(HashMap<String,SentRequest> requests_Sent) {
        Requests_Sent = requests_Sent;
    }
    public String getTeacher_Name() {
        return Teacher_Name;
    }
    public void setTeacher_Name(String teacher_Name) {
        Teacher_Name = teacher_Name;
    }
}
