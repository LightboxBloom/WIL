//Developer: Rohini Naidu
package com.katherine.bloomuii.Fragments;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.katherine.bloomuii.Adapters.FileRepoAdapter;
import com.katherine.bloomuii.ObjectClasses.FirebaseFile;
import com.katherine.bloomuii.R;

import java.util.ArrayList;
public class FileRepoFragment extends Fragment {
    //UI Components
    TextView txtClassroomName;
    ListView lvFiles;
    ProgressBar pLoad;
    //Global Variables
    private Bundle bundle;
    private int classroomId;
    private ArrayList<FirebaseFile> files;
    private FileRepoAdapter fileRepoAdapter;
    //Firebase Initializations
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myClass;
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
        lvFiles = view.findViewById(R.id.lvFiles);

        pLoad = view.findViewById(R.id.pLoadFiles);
        pLoad.setVisibility(View.VISIBLE);
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance().getReference();
        //Variable Declarations
        files = new ArrayList<>();
        bundle = new Bundle();
        bundle = getArguments();
        if(bundle != null){
            classroomId = bundle.getInt("ClassroomId");
            //To remove User from Class
            myRef = database.getReference("Files/Uploads");
           displayAllFiles();
        }
        getFileName();

        return view;
    }
    private void displayAllFiles(){
        fileRef = storage.child("FileRepo/Uploads/" + classroomId);
        //Retrieve all Files
        retrieveAllFiles();
    }
    //Get a list of all PDFs related to this class
    private void retrieveAllFiles(){
        //Retrieve allFile names
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                for(DataSnapshot child:children){
                    FirebaseFile file = child.getValue(FirebaseFile.class);
                    if(file!=null){
                        if(file.getClassroom_Id() == classroomId) {
                            files.add(file);
                        }
                    }
                }
                if (getContext() != null) {
                    fileRepoAdapter = new FileRepoAdapter(getContext(), android.R.layout.simple_list_item_1, files);
                    lvFiles.setAdapter(fileRepoAdapter);
                } else {
                    //Toast.makeText(getActivity(), "Retrieving Entries failed", Toast.LENGTH_SHORT).show();
                }
                pLoad.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //Get File Name to display as PDF
    private void getFileName(){
        lvFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                DisplayPDFFragment pdfFragment = new DisplayPDFFragment();
                bundle.putString("FileName", files.get(i).getFile_Name());
                bundle.putInt("ClassroomId", classroomId);
                pdfFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragmentContainer, pdfFragment);
                fragmentTransaction.commit();
            }
        });
    }
}