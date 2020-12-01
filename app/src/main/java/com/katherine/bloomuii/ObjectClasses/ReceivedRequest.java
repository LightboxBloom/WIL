package com.katherine.bloomuii.ObjectClasses;
//Child of Request
public class ReceivedRequest extends Request {
    //Fields
    private String Teacher_Id;
    private String Teacher_Name;
    private long Classroom_Id;
    //Constructor
    public ReceivedRequest(){}
    //Getters and Setters
    public String getTeacher_Id() {
        return Teacher_Id;
    }
    public void setTeacher_Id(String teacher_Id) {
        Teacher_Id = teacher_Id;
    }
    public String getTeacher_Name() {
        return Teacher_Name;
    }
    public void setTeacher_Name(String teacher_Name) {
        Teacher_Name = teacher_Name;
    }
    public long getClassroom_Id() {
        return Classroom_Id;
    }
    public void setClassroom_Id(long classroom_Id) {
        Classroom_Id = classroom_Id;
    }
}
