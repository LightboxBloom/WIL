//Developer: Rohini Naidu
package com.katherine.bloomuii.ObjectClasses;
import java.util.HashMap;
public class Classroom {
    //Fields
    private int Classroom_Id;
    private String Classroom_Name;
    private String Teacher_Name;
    private String Teacher_Id;
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
    public int getClassroom_Id() {
        return Classroom_Id;
    }
    public void setClassroom_Id(int classroom_Id) {
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

    public String getTeacher_Id() {
        return Teacher_Id;
    }

    public void setTeacher_Id(String teacher_Id) {
        Teacher_Id = teacher_Id;
    }
}
