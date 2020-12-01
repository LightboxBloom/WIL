//Developer: Rohini Naidu
package com.katherine.bloomuii.Fragments;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.katherine.bloomuii.ObjectClasses.RetrievePDFStream;
import com.katherine.bloomuii.R;

import java.io.File;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class DisplayPDFFragment extends Fragment {
    //UI Components
    PDFView pdfView;
    TextView txtFileName;
    ProgressBar pPDF;
    ImageView mDownload;
    //Global Variables
    private Bundle bundle;
    private String fileName;
    private int classroomId;
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
        mDownload = view.findViewById(R.id.imDownload);
        pPDF = view.findViewById(R.id.pPDF);
        pPDF.setVisibility(View.VISIBLE);
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
            classroomId = bundle.getInt("ClassroomId");
            typeOfClass = bundle.getString("TypeOfClass");
            txtFileName.setText(fileName);
            //Display PDF
            fileRef = storage.child("FileRepo/Uploads/" + classroomId + "/" + fileName + ".pdf");
             if(fileRef !=null) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        new RetrievePDFStream(pdfView).execute((uri.toString()));
                    }
                });
                pPDF.setVisibility(View.INVISIBLE);
            }
             download();
        }
        return view;
    }
    //On button press, download file displayed
    private void download(){
        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       String url = uri.toString();
                       downloadFiles(getContext(),fileName,".pdf",DIRECTORY_DOWNLOADS,url);
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {

                   }
               });
            }
        });
    }
    //Process Download
    //Source: https://www.youtube.com/watch?v=SmXGlv7QEO0
    private void downloadFiles(Context context, String fileName, String fileExtension, String destinationDirectory, String url){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,fileName+fileExtension);
        downloadManager.enqueue(request);
    }
}