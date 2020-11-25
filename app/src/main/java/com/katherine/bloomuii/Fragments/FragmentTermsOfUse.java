//Developer: Rohini Naidu
package com.katherine.bloomuii.Fragments;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.katherine.bloomuii.ObjectClasses.RetrievePDFStream;
import com.katherine.bloomuii.R;
import com.squareup.picasso.Picasso;

import java.net.URL;
public class FragmentTermsOfUse extends Fragment {
    //PDF Viewer
    private String touId;
    private FirebaseDatabase database;
    private DatabaseReference touRef;
    private StorageReference storage;
    private StorageReference fileRef;
    //UI Components
    PDFView pdfView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terms_of_use, container, false);
        //UI Declarations
        pdfView = (PDFView) view.findViewById(R.id.pdfView);
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        touRef = database.getReference().child("TermsOfUse/MostRecentUpdate");
        storage = FirebaseStorage.getInstance().getReference();
        //Retrieve PDF from Firebase Storage
        touRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Retrieve latest Terms of Use Issue
                touId= dataSnapshot.getValue(String.class);
                if(touId != null) {
                    retrievePdf();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return view;
    }
    //Retrieve and Display PDF
    private void retrievePdf(){
        //Retrieve pdf from firebase
        fileRef = storage.child("TermsOfUse/" + touId +".pdf");
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
}
