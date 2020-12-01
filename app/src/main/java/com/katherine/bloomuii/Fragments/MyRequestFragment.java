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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.katherine.bloomuii.Adapters.ReceivedRequestAdapter;
import com.katherine.bloomuii.Adapters.SentRequestAdapter;
import com.katherine.bloomuii.ObjectClasses.Classroom;
import com.katherine.bloomuii.ObjectClasses.ReceivedRequest;
import com.katherine.bloomuii.ObjectClasses.Request;
import com.katherine.bloomuii.ObjectClasses.SentRequest;
import com.katherine.bloomuii.ObjectClasses.User;
import com.katherine.bloomuii.R;

import java.util.ArrayList;
public class MyRequestFragment extends Fragment {
    //UI Components
    TextView receivedRequest;
    TextView sentRequest;
    ListView lvMyRequests;
    ImageView mBack;
    ProgressBar progressBar;
    //Global general Initializations
    private ArrayList<ReceivedRequest> receivedRequests;
    private ArrayList<SentRequest> sentRequests;
    private ReceivedRequestAdapter receivedRequestsAdapter;
    private SentRequestAdapter sentRequestsAdapter;
    //Firebase Initializations
    private FirebaseDatabase database;
    private DatabaseReference mySentRequestsRef;
    private DatabaseReference myReceivedRequestsRef;
    private DatabaseReference myUsersRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_request, container, false);
        //UI Components
        lvMyRequests = view.findViewById(R.id.lvMyRequests);
        receivedRequest = view.findViewById(R.id.txtReceivedRequests);
        sentRequest = view.findViewById(R.id.txtSentRequests);
        mBack = view.findViewById(R.id.btnPuzzleBack);
        progressBar = view.findViewById(R.id.pRequests);
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mySentRequestsRef = database.getReference().child("Users/" + currentUser.getUid() + "/MyClassrooms");
        myReceivedRequestsRef = database.getReference("Users/" + currentUser.getUid() + "/Requests_Received");
        myUsersRef = database.getReference("Users/");
        //Variable Declarations
        sentRequests = new ArrayList<>();
        receivedRequests = new ArrayList<>();

        //On button Press retrieve received requests
        receivedRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                retrieveReceivedRequests();
            }
        });
        //On button Press retrieve sent requests
        sentRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                retrieveSentRequests();
            }
        });
        //On button Navigate to Previous Fragment
        btnBackClicked();
        progressBar.setVisibility(View.INVISIBLE);
        return view;
    }
    //Retrieve users received requests from firebase
    private void retrieveReceivedRequests(){
        myReceivedRequestsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                receivedRequests.clear();
                for (DataSnapshot child : children) {
                    ReceivedRequest request = child.getValue(ReceivedRequest.class);
                    if(request != null){
                        //Requests processed must be requests that are unanswered (needs a response from users)
                        if(request.getRequest_Status().equals("Pending")) {
                            receivedRequests.add(request);
                        }
                    }
                }
                if(!receivedRequests.isEmpty()) {
                    processRequestDetailsandDisplay();
                }
                else {
                    receivedRequestsAdapter = new ReceivedRequestAdapter(getContext(), android.R.layout.simple_list_item_1, receivedRequests);
                    lvMyRequests.setAdapter(receivedRequestsAdapter);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //Retrieve users sent requests from firebase
    private void retrieveSentRequests(){
        mySentRequestsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                sentRequests.clear();
                for (DataSnapshot child : children) {
                    Classroom classroom = child.getValue(Classroom.class);
                    if(classroom != null && (classroom.getRequests_Sent()!= null)){
                        for(SentRequest request: classroom.getRequests_Sent().values()){
                            //Requests processed must be requests that are unanswered (needs a response from users)
                            if(request.getRequest_Status().equals("Pending")) {
                                request.setClassroom_Name(classroom.getClassroom_Name());
                                sentRequests.add(request);
                            }
                        }
                    }
                }
                if(getContext() != null){
                    sentRequestsAdapter = new SentRequestAdapter(getContext(),android.R.layout.simple_list_item_1,sentRequests);
                    lvMyRequests.setAdapter(sentRequestsAdapter);
                }
                else{
                   // Toast.makeText(getActivity(), "Retrieving Entries failed", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //Process and Display Received Requests
    private void processRequestDetailsandDisplay(){

            myUsersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Iterable<DataSnapshot> children = snapshot.getChildren();
                    for (DataSnapshot child : children) {
                        User teacher = child.getValue(User.class);
                        if (teacher != null) {
                            int count = 0;
                            for (ReceivedRequest request : receivedRequests) {
                                Log.d("TAG", "onDataChange: User: " + teacher.getUser_ID() + " Request ID: " + request.getTeacher_Id() + " count: " + count);
                                count++;
                                if (teacher.getUser_ID().equals(request.getTeacher_Id())) {
                                    request.setTeacher_Name(teacher.getFull_Name());
                                    break;
                                }
                            }
                        }
                        if (getContext() != null) {
                            receivedRequestsAdapter = new ReceivedRequestAdapter(getContext(), android.R.layout.simple_list_item_1, receivedRequests);
                            lvMyRequests.setAdapter(receivedRequestsAdapter);
                        } else {
                            //Toast.makeText(getActivity(), "Retrieving Entries failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
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