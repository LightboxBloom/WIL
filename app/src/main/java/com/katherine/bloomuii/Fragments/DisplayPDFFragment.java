//Developer: Rohini Naidu
package com.katherine.bloomuii.Fragments;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.katherine.bloomuii.ObjectClasses.RetrievePDFStream;
import com.katherine.bloomuii.R;
public class DisplayPDFFragment extends Fragment {
    //UI Components
    PDFView pdfView;
    TextView txtFileName;
    //Global Variables
    private Bundle bundle;
    private String fileName;
    private String classroomId;
    private String typeOfClass;
    //Firebase Initializations
    private FirebaseDatabase database;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private StorageReference storage;
    private StorageReference fileRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_display_p_d_f, container, false);
        //UI Components
        pdfView = view.findViewById(R.id.pdfView);
        txtFileName = view.findViewById(R.id.txtFileName);
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance().getReference();
        //Variable Declarations
        bundle = new Bundle();
        bundle = getArguments();
        if(bundle != null){
            fileName = bundle.getString("FileName");
            classroomId = bundle.getString("ClassroomId");
            typeOfClass = bundle.getString("TypeOfClass");
            txtFileName.setText(fileName);
            //Display PDF
            fileRef = storage.child("FileRepo/" + classroomId + "/" + fileName + ".pdf");
            //https://console.firebase.google.com/u/2/project/bloom-database/storage/bloom-database.appspot.com/files~2FTermsOfUse/" + touId+".pdf
            if(fileRef !=null) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        new RetrievePDFStream(pdfView).execute((uri.toString()));
                    }
                });
            }
        }
        return view;
    }
}