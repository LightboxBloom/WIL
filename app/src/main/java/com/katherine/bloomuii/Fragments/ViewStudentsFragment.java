//Developer: Rohini Naidu
package com.katherine.bloomuii.Fragments;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.katherine.bloomuii.Adapters.DiaryAdapter;
import com.katherine.bloomuii.Adapters.StudentAdapter;
import com.katherine.bloomuii.ObjectClasses.User;
import com.katherine.bloomuii.R;

import java.util.ArrayList;
public class ViewStudentsFragment extends Fragment {
    //UI Components
    ListView lvStudents;
    Dialog leaveClass_popup;
    FloatingActionButton btnLeaveClass;
    Button leaveYes;
    Button leaveNo;
    TextView header;
    TextView message;
    TextView typeOfUsers;
    //Global Variables
    private ArrayList<User> users;
    private StudentAdapter studentAdapter;
    private Bundle bundle;
    private int classroom_id;
    private String typeOfUser;
    //Firebase Initializations
    private FirebaseDatabase database;
    private DatabaseReference studclassRef;
    private DatabaseReference conclassRef;
    private DatabaseReference studentRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    ImageView btnBack;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_students, container, false);
        lvStudents = view.findViewById(R.id.lvStudents);
        typeOfUsers = view.findViewById(R.id.txtTypeOfUser);
                //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        //UI
        leaveClass_popup = new Dialog(getContext());
        leaveClass_popup.setContentView(R.layout.leaveclass_dialog);
        header = leaveClass_popup.findViewById(R.id.txtDialogHeader);
        message = leaveClass_popup.findViewById(R.id.txtDialogMessage);

        bundle = new Bundle();
        users = new ArrayList<>();
        bundle = getArguments();
        if(bundle != null) {
            classroom_id = bundle.getInt("ClassroomId");
            typeOfUser = bundle.getString("TypeOfUser");
            studclassRef = database.getReference().child("Classrooms/"+classroom_id+"/Students");
            conclassRef = database.getReference().child("Classrooms/"+classroom_id+"/Contributors");
            if(typeOfUser.equals("Students")) {
                typeOfUsers.setText("My Students");
                retrieveStudents(classroom_id);
            }
            else if(typeOfUser.equals("Contributors")){
                typeOfUsers.setText("My Contributors");
                retrieveContributors(classroom_id);
            }
            else if(typeOfUser.equals("Teachers")){
                typeOfUsers.setText("My Teachers");
                retrieveContributors(classroom_id);
            }
        }
        removeUser();

        btnBack = (ImageView) view.findViewById(R.id.btnPuzzleBack);

        btnBackClicked();
        return view;
    }
    //click to get back to home fragment
    private void btnBackClicked()
    {
        final Fragment viewClassroomFragment = new ViewClassroomFragment();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, viewClassroomFragment)
                        .commit();
            }
        });
    }

    private void retrieveStudents(int class_id){
        studclassRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for(DataSnapshot child: children){
                    User student = child.getValue(User.class);
                    if(student != null){
                        findStudentDetails(student);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void findStudentDetails(User student){
        studentRef = database.getReference("Users/"+student.getUser_ID());
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User student = snapshot.getValue(User.class);
                if(student != null){
                    users.add(student);
                }
                if(users.size() >= 0){
                    studentAdapter = new StudentAdapter(getContext(),android.R.layout.simple_list_item_1,users);
                    lvStudents.setAdapter(studentAdapter);
                }
                else{
                    Toast.makeText(getActivity(), "Retrieving Entries failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //TODO:Not working on CLick and TODO Contributor Remove
    private void removeUser(){
        lvStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                leaveYes = leaveClass_popup.findViewById(R.id.btnLeaveYes);
                leaveNo = leaveClass_popup.findViewById(R.id.btnLeaveNo);
                header.setText("Remove Student");
                message.setText("Are you sure you want to remove" + users.get(i).getFull_Name() +"?");
                leaveClass_popup.show();

                leaveYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Delete Student from Class
                        studentRef.child("JoinedClassrooms/"+classroom_id).removeValue();
                        studclassRef.child(users.get(i).getUser_ID()).removeValue();
                    }
                });
                leaveNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        leaveClass_popup.dismiss();
                    }
                });
            }
        });
    }

    private void retrieveContributors(int classroom_id){
        conclassRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for(DataSnapshot child: children){
                    User contributor = child.getValue(User.class);
                    if(contributor != null){
                        findContributorsDetails(contributor);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void findContributorsDetails(User contributor){
        conclassRef = database.getReference("Users/"+contributor.getUser_ID());
        conclassRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User student = snapshot.getValue(User.class);
                if(student != null){
                    users.add(student);
                }
                if(users.size() >= 0){
                    studentAdapter = new StudentAdapter(getContext(),android.R.layout.simple_list_item_1,users);
                    lvStudents.setAdapter(studentAdapter);
                }
                else{
                    Toast.makeText(getActivity(), "Retrieving Entries failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}


