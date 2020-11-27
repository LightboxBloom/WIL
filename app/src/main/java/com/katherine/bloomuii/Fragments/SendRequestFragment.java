//Developer: Rohini Naidu
package com.katherine.bloomuii.Fragments;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.katherine.bloomuii.Adapters.ClassroomAdapter;
import com.katherine.bloomuii.ObjectClasses.Classroom;
import com.katherine.bloomuii.ObjectClasses.SentRequest;
import com.katherine.bloomuii.ObjectClasses.User;
import com.katherine.bloomuii.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.content.ContentValues.TAG;
public class SendRequestFragment extends Fragment {
    //UI Components
    ImageView mBack;
    Spinner spMyClasses;
    //student request
    TextView studentUsername;
    Button btnstudentRequest;
    TextView studentFeedBack;
    //teacher request
    TextView teacherusername;
    Button btnteacherRequest;
    TextView teacherFeedback;
    //Firebase Initializations
    private FirebaseDatabase database;
    private DatabaseReference myclassRef;
    private DatabaseReference userRef;
    private DatabaseReference currentUserRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    //Global general variables
    private ArrayList<Classroom> myclasses;
    private ArrayList<String> strMyClasses;
    private ArrayAdapter<String> myClassesAdapter;
    private String selectedClassId;
    private String selectedClassName;
    private User requestedUser;
    private SimpleDateFormat df;
    private String[] splitUsername;
    private String feedback;
    private boolean userDoesNotExist;
    private User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_send_request, container, false);
        //Declare UI Components
        mBack = view.findViewById(R.id.btnPuzzleBack);
        spMyClasses = (Spinner) view.findViewById(R.id.spMyClasses);
        //student
        btnstudentRequest = view.findViewById(R.id.btnStudentSendRequest);
        studentUsername = view.findViewById(R.id.txtStudentUserName);
        studentFeedBack = view.findViewById(R.id.txtStudentUserFeedback);
        //teacher
        btnteacherRequest = view.findViewById(R.id.btnTeacherSendRequest);
        teacherusername = view.findViewById(R.id.txtTeacherUsername);
        teacherFeedback = view.findViewById(R.id.txtTeacherUserFeedback);
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myclassRef = database.getReference().child("Users/" + currentUser.getUid() + "/MyClassrooms");
        userRef = database.getReference("Users/");
        currentUserRef = database.getReference("Users/" + currentUser.getUid());
        //Variable Declarations
        user = new User();
        myclasses = new ArrayList<>();
        df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        userDoesNotExist = false;

        //retrieve all My Classes
        myclassRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                myclasses.clear();
                for (DataSnapshot child : children) {
                    Classroom classroom = child.getValue(Classroom.class);
                    if (classroom != null) {
                        myclasses.add(classroom);
                    }
                }
                createClassList();
                if (!myclasses.isEmpty()) {
                    myClassesAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, strMyClasses);
                    myClassesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spMyClasses.setAdapter(myClassesAdapter);
                } else {
                    Toast.makeText(getActivity(), "Retrieving Entries failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Combo box Listener for selected class
        spMyClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedClassName = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //Find Current User Details
        findUserName();
        //Validate and Send Request Made
        processRequest();
        //Navigate to previous Fragment
        btnBackClicked();
        return view;
    }

    //Identify type of Request
    private void processRequest() {
        btnstudentRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!myclasses.isEmpty()) {
                    if (!studentUsername.getText().toString().equals("")) {
                        retrieveClassId();
                        filterProcessedRequest("Student");
                    }
                }
                else{
                    setUserFeedback("You have no classes created","Student");
                }
            }
        });
        btnteacherRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!myclasses.isEmpty()) {
                    if (!teacherusername.getText().toString().equals("")) {
                        retrieveClassId();
                        filterProcessedRequest("Contributor");
                    }
                }
                else{
                    setUserFeedback("You have no classes created","Contributor");
                }
            }
        });
    }
    //Check if User is already in the class to prevent overwriting data
    private void isUserInClass(final String username, final String segmentedUserId, final String typeOfUser) {
        DatabaseReference userInClass = database.getReference("Users/" + currentUser.getUid() + "/MyClassrooms/" + selectedClassId + "/Requests_Sent");
        userInClass.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userDoesNotExist = true;
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    SentRequest existingRequest = child.getValue(SentRequest.class);
                    if (existingRequest != null) {
                        if (existingRequest.getRequested_Username().equals(username)) {
                            if (existingRequest.getRequested_User_Id().substring(0, 4).equals(segmentedUserId.trim())) {
                                if (existingRequest.getRequest_Status().equals("Accepted")) {
                                    feedback = "User is already in the class";
                                    userDoesNotExist = false;
                                } else if (existingRequest.getRequest_Status().equals("Pending")) {
                                    feedback = "Request already sent";
                                    userDoesNotExist = false;
                                }
                            }
                        }
                    }
                }
                if(userDoesNotExist){
                    findUser(typeOfUser);
                }
                setUserFeedback(feedback, typeOfUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //Validate Fields
    private void filterProcessedRequest(String typeOfRequest){
        if(typeOfRequest.equals("Student")) {
            if (!studentUsername.getText().toString().equals("")) {
                if (studentUsername.getText().toString().contains("#")) {
                    splitUsername = studentUsername.getText().toString().split("#");
                    isUserInClass(splitUsername[0], splitUsername[1], typeOfRequest);
                }
                else {
                    setUserFeedback("Student Username is in the Wrong Format (Required: Username#XXXX)", typeOfRequest);
                }
            }
            else{
                setUserFeedback("Student Username Field Required", typeOfRequest);
            }
        }
        else if(typeOfRequest.equals("Contributor")){
            if (!teacherusername.getText().toString().equals("")) {
                if (teacherusername.getText().toString().contains("#")) {
                    splitUsername = teacherusername.getText().toString().split("#");
                    isUserInClass(splitUsername[0],splitUsername[1],typeOfRequest);
                }else{
                    setUserFeedback("Student Username is in the Wrong Format (Required: Username#XXXX)",typeOfRequest);
                }
            }
            else{
                setUserFeedback("Student Username Field Required",typeOfRequest);
            }
        }
    }
    //Find the User that the Request will be sent too
    private void findUser(final String typeOfRequest){
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot child : children) {
                    requestedUser = child.getValue(User.class);
                    if(requestedUser != null) {

                        //Check Format of Username
                        if(splitUsername.length == 2) {
                            //Check if User ID and then Username exists
                            if (requestedUser.getUser_ID().substring(0, 4).equals(splitUsername[1].trim())) {
                                if (requestedUser.getFull_Name().equals(splitUsername[0])) {
                                    sendRequest(typeOfRequest);
                                    break;
                                }
                            } else {
                                feedback = "User not found.";
                            }
                        }
                        else{
                            feedback = "User Username is in the Wrong Format (Required: Username#XXXX)";

                        }
                    }
                }
                setUserFeedback(feedback,typeOfRequest);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    //Display User Feedback
    private void setUserFeedback(String feedback, String typeOfRequest){
            if(typeOfRequest.equals("Student")) {
                studentFeedBack.setText(feedback);
            }
            else if(typeOfRequest.equals("Contributor")){
                teacherFeedback.setText(feedback);
            }
        }
    //Send Request by saving to Firebase
    private void sendRequest(String typeOfRequest){
            String currentDate = df.format(Calendar.getInstance().getTime());
            //Students Pending Requests
                userRef.child(requestedUser.getUser_ID()).child("Requests_Received").child(String.valueOf(selectedClassId)).child("Classroom_Id").setValue(selectedClassId);
                userRef.child(requestedUser.getUser_ID()).child("Requests_Received").child(String.valueOf(selectedClassId)).child("Teacher_Id").setValue(currentUser.getUid());
            userRef.child(requestedUser.getUser_ID()).child("Requests_Received").child(String.valueOf(selectedClassId)).child("Teacher_Name").setValue(user.getFull_Name());
                userRef.child(requestedUser.getUser_ID()).child("Requests_Received").child(String.valueOf(selectedClassId)).child("Classroom_Name").setValue(selectedClassName);
                userRef.child(requestedUser.getUser_ID()).child("Requests_Received").child(String.valueOf(selectedClassId)).child("Date").setValue(currentDate);
                userRef.child(requestedUser.getUser_ID()).child("Requests_Received").child(String.valueOf(selectedClassId)).child("Type_Of_Request").setValue(typeOfRequest);
                userRef.child(requestedUser.getUser_ID()).child("Requests_Received").child(String.valueOf(selectedClassId)).child("Request_Status").setValue("Pending");
                //Teachers Sent out Requests
                myclassRef.child(String.valueOf(selectedClassId)).child("Requests_Sent").child(requestedUser.getUser_ID()).child("Requested_User_Id").setValue(requestedUser.getUser_ID());
                myclassRef.child(String.valueOf(selectedClassId)).child("Requests_Sent").child(requestedUser.getUser_ID()).child("Requested_Username").setValue(requestedUser.getFull_Name());
                myclassRef.child(String.valueOf(selectedClassId)).child("Requests_Sent").child(requestedUser.getUser_ID()).child("Date_Sent").setValue(currentDate);
                myclassRef.child(String.valueOf(selectedClassId)).child("Requests_Sent").child(requestedUser.getUser_ID()).child("Type_Of_Request").setValue(typeOfRequest);
                myclassRef.child(String.valueOf(selectedClassId)).child("Requests_Sent").child(requestedUser.getUser_ID()).child("Request_Status").setValue("Pending");

                setUserFeedback("Request sent.", typeOfRequest);
        }
    //Find Current User Details
    private void findUserName(){
        currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null) {
                    user = snapshot.getValue(User.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }
    //Create a List of Users Classes for ComboBox
    private void createClassList(){
        strMyClasses = new ArrayList<>();
        if(!myclasses.isEmpty()) {
            for (Classroom classroom : myclasses) {
                strMyClasses.add(classroom.getClassroom_Name());
            }
        }
    }
    //Retrieve all Class Ids that Current User Created for ComboBox
    private void retrieveClassId(){
        if(!myclasses.isEmpty()) {
            for (Classroom classroom : myclasses) {
                if (classroom.getClassroom_Name().equals(selectedClassName)) {
                    selectedClassId.equals(classroom.getClassroom_Id());
                }
            }
        }
    }
    //Back Button Functionality
    private void btnBackClicked() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ViewClassroomFragment viewClassFragment = new ViewClassroomFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, viewClassFragment);
                fragmentTransaction.commit();
            }
        });
    }

}