//Developer: Rohini Naidu
package com.katherine.bloomuii.Adapters;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.katherine.bloomuii.Fragments.MyRequestFragment;
import com.katherine.bloomuii.ObjectClasses.ReceivedRequest;
import com.katherine.bloomuii.R;

import java.util.List;
public class ReceivedRequestAdapter extends ArrayAdapter<ReceivedRequest> {
    //Firebase Initializations
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference teacherRef;
    private DatabaseReference joinedClassroomsRef;
    private DatabaseReference contributorClassroomRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    //Global Variable
    Bundle typeOfRequest;
    //Constructor
    public ReceivedRequestAdapter(Context context, int simple_list_item_1, List<ReceivedRequest> sentRequestList){
        super(context,0, sentRequestList);
    }
    //Retrieve Recieved Request and bind the data to UI Components
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ReceivedRequest request = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.received_request_adapter,parent,false);
        }
        if(request != null){
            //Firebase Declarations
            database = FirebaseDatabase.getInstance();
            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser();
            myRef = database.getReference().child("Users/"+currentUser.getUid()+"/Requests_Received/"+request.getClassroom_Id());
            teacherRef = database.getReference("Users/"+request.getTeacher_Id()+"/MyClassrooms/"+request.getClassroom_Id()+"/Requests_Sent/"+currentUser.getUid());
            //Variable Declarations
            typeOfRequest = new Bundle();
            //UI Components
            TextView classroomName = convertView.findViewById(R.id.txtClassroomName);
            final TextView teacher = convertView.findViewById(R.id.txtTeacher);
            TextView sampleText = convertView.findViewById(R.id.txtSampleText);
            Button accept = convertView.findViewById(R.id.btnAccept);
            Button decline= convertView.findViewById(R.id.btnDecline);
            //Bind data with UI Components
            classroomName.setText(request.getClassroom_Name());
            sampleText.setText("Invited by:");
            if(request.getType_Of_Request().equals("Contributor")) {
                teacher.setText(request.getTeacher_Name()+"#"+request.getTeacher_Id().substring(0,4)+" as a Contributor");
            }
            else if(request.getType_Of_Request().equals("Student")){
                teacher.setText(request.getTeacher_Name()+"#"+request.getTeacher_Id().substring(0,4)+" as a Student");
            }
            //On button press Accept Request by changing status of request in Firebase (Different Database Paths based on Type of Request)
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean databaseUpdated = false;
                    myRef.child("Request_Status").setValue("Accepted");
                    teacherRef.child("Request_Status").setValue("Accepted");
                    if(request.getType_Of_Request().equals("Student")) {
                        joinedClassroomsRef = database.getReference("Users/"+currentUser.getUid()+"/JoinedClassrooms/"+request.getClassroom_Id());
                        joinedClassroomsRef.child("Classroom_Id").setValue(request.getClassroom_Id());
                        joinedClassroomsRef.child("Classroom_Name").setValue(request.getClassroom_Name());
                        joinedClassroomsRef.child("Teacher_Name").setValue(request.getTeacher_Name());
                        databaseUpdated = true;
                    }
                    if(!databaseUpdated) {
                        if (request.getType_Of_Request().equals("Contributor")) {
                            contributorClassroomRef = database.getReference("Users/" + currentUser.getUid() + "/MyClassrooms/" + request.getClassroom_Id());
                            contributorClassroomRef.child("Classroom_Id").setValue(request.getClassroom_Id());
                            contributorClassroomRef.child("Classroom_Name").setValue(request.getClassroom_Name());
                            contributorClassroomRef.child("Teacher_Name").setValue(request.getTeacher_Name());
                        }
                    }
                }
            });
            //On button press Decline Request by changing status of request in Firebase
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myRef.child("Request_Status").setValue("Declined");
                    teacherRef.child("Request_Status").setValue("Declined");
                }
            });
        }
        return convertView;
    }
}
