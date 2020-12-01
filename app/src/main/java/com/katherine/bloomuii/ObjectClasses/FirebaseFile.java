//Developer: Rohini Naidu
package com.katherine.bloomuii.ObjectClasses;
public class FirebaseFile {
    private int Classroom_Id;
    private String Teacher_Id;
    private String Storage_Uri;
    private String File_Name;

    public FirebaseFile(){}
    //Getters and Setters
    public String getFile_Name() {
        return File_Name;
    }
    public void setFile_Name(String file_Name) {
        File_Name = file_Name;
    }
    public int getClassroom_Id() {
        return Classroom_Id;
    }
    public void setClassroom_Id(int classroom_Id) {
        Classroom_Id = classroom_Id;
    }
    public String getTeacher_Id() {
        return Teacher_Id;
    }
    public void setTeacher_Id(String teacher_Id) {
        Teacher_Id = teacher_Id;
    }
    public String getStorage_Uri() {
        return Storage_Uri;
    }
    public void setStorage_Uri(String storage_Uri) {
        Storage_Uri = storage_Uri;
    }
}
