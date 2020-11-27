//Developer: Rohini Naidu
package com.katherine.bloomuii.Fragments;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.katherine.bloomuii.R;

import java.util.ArrayList;
public class FileRepoFragment extends Fragment {
    //UI Components
    TextView txtClassroomName;
    ListView lvFiles;
    //Global Variables
    private Bundle bundle;
    private String classroomId;
    private String filePath;
    private String classroomName;
    private ArrayList<String> filenames;
    //Firebase Initializations
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private StorageReference storage;
    private StorageReference fileRef;
    //TODO: Complete Pulling list of files in file storage @Cameron and @Rohini
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_file_repo, container, false);

        //UI Components
        txtClassroomName = view.findViewById(R.id.txtClassroomName);
        lvFiles = view.findViewById(R.id.grFiles);
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance().getReference();
        //Variable Declarations
        filenames = new ArrayList<>();
        bundle = new Bundle();
        bundle = getArguments();
        if(bundle != null){
            filePath =  bundle.getString("FilePath");
            classroomId = bundle.getString("ClassroomId");
            classroomName = bundle.getString("ClassroomName");
            txtClassroomName.setText(classroomName);
            //To remove User from Class
            myRef = database.getReference("Users/" + currentUser.getUid() + "/" +filePath+"/"+classroomId);
           displayAllFiles();
        }
        getFileName();
        return view;
    }
    //TODO: Methods not complete waiting on @Cameron
    private void displayAllFiles(){
        fileRef = storage.child("FileRepo/" + classroomId);
        //filenames = fileRef.li;
    }
    private void getFileName(){
        lvFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                DisplayPDFFragment pdfFragment = new DisplayPDFFragment();
                bundle.putString("FileName", filenames.get(i));
                bundle.putString("TypeOfClass", filePath);
                bundle.putString("ClassroomId", classroomId);
                pdfFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragmentContainer, pdfFragment);
                fragmentTransaction.commit();
            }
        });
    }
}