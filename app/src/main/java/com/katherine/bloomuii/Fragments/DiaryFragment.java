package com.katherine.bloomuii.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.katherine.bloomuii.Adapters.DiaryAdapter;
import com.katherine.bloomuii.ObjectClasses.DiaryEntry;
import com.katherine.bloomuii.R;

import java.util.ArrayList;

public class DiaryFragment extends Fragment {

    FloatingActionButton mAdd;
    ImageView mBack;
    ListView lvEntries;
    ArrayList<DiaryEntry> entries;
    DiaryAdapter diaryAdapter;
    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        mAdd = view.findViewById(R.id.btnAddEntry);
        mBack = view.findViewById(R.id.btnPuzzleBack);
        lvEntries = view.findViewById(R.id.lvEntries);
        entries = new ArrayList<>();

        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = database.getReference("Users/" + currentUser.getUid() + "/DiaryEntries");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                entries.clear();
                for(DataSnapshot child: children){
                    DiaryEntry pulledEntry = child.getValue(DiaryEntry.class);
                    if(pulledEntry != null){
                        entries.add(pulledEntry);
                    }
                }
                if(entries.size() >= 0){
                    diaryAdapter = new DiaryAdapter(getContext(),android.R.layout.simple_list_item_1,entries);
                    lvEntries.setAdapter(diaryAdapter);
                }
                else{
                    Toast.makeText(getActivity(), "Retrieving Entries failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //calling method
        fab();
        btnBackClicked();

        return view;
    }

    //method for clicking fab
    private void fab() {
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new AddEntryFragment()).commit();
            }
        });
    }//end fab method




    private void btnBackClicked()
    {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new HomeFragment()).commit();
            }
        });
    }
}
