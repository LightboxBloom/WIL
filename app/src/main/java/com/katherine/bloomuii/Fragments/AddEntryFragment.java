package com.katherine.bloomuii.Fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.katherine.bloomuii.ObjectClasses.DiaryEntry;
import com.katherine.bloomuii.R;

import java.util.Calendar;

public class AddEntryFragment extends Fragment {

    private static final String TAG = "AddEntryFragment";
    private TextView mDisplayDate, diaryEntry;
    private ImageView diaryHappyEmotion, diarySadEmotion, diaryExcitedEmotion, diaryEmbarassedEmotion, diaryAngryEmotion, diaryScaredEmotion;
    private LinearLayout happy, sad, excited, embarassed, angry, scared;
    private String emotionChosen = "";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ImageView mBack;
    private FloatingActionButton addEntry;

    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    boolean addSuccessful=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_entry, container, false);

        mDisplayDate = view.findViewById(R.id.txtDiaryDatePicker);
        mBack = view.findViewById(R.id.btnPuzzleBack);
        addEntry = view.findViewById(R.id.btnAddEntry);

        diaryHappyEmotion = view.findViewById(R.id.imgHappy);
        diarySadEmotion = view.findViewById(R.id.imgSad);
        diaryExcitedEmotion = view.findViewById(R.id.imgExcited);
        diaryEmbarassedEmotion = view.findViewById(R.id.imgEmbarassed);
        diaryAngryEmotion = view.findViewById(R.id.imgAngry);
        diaryScaredEmotion = view.findViewById(R.id.imgScared);

        happy = view.findViewById(R.id.itemHappy);
        sad = view.findViewById(R.id.itemSad);
        excited = view.findViewById(R.id.itemExcited);
        embarassed = view.findViewById(R.id.itemEmbarassed);
        angry = view.findViewById(R.id.itemAngry);
        scared = view.findViewById(R.id.itemScared);

        diaryEntry = view.findViewById(R.id.txtDiaryEntry);

        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //calling methods
        datePicker();
        btnBackClicked();
        emotionSelected();

        btnAddEntry();
        return view;
    }

    //back arrow
    private void btnBackClicked()
    {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DiaryFragment diaryFragment = new DiaryFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, diaryFragment);
                fragmentTransaction.commit();
            }
        });
    }

    //date picker
    private void datePicker()
    {

        mDisplayDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }
});
        mDateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day)
            {
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "-" + (month + 1) + "-" + year;
                mDisplayDate.setText(date);
            }
        };

    }//end of date picker selector

    private String emotionSelected(){
        diaryHappyEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionChosen = "Happy";
                happy.setBackgroundColor(Color.parseColor("#5588a3"));
                sad.setBackgroundColor(Color.parseColor("#FFFFFF"));
                excited.setBackgroundColor(Color.parseColor("#FFFFFF"));
                embarassed.setBackgroundColor(Color.parseColor("#FFFFFF"));
                scared.setBackgroundColor(Color.parseColor("#FFFFFF"));
                angry.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        diarySadEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionChosen = "Sad";
                sad.setBackgroundColor(Color.parseColor("#5588a3"));
                happy.setBackgroundColor(Color.parseColor("#FFFFFF"));
                excited.setBackgroundColor(Color.parseColor("#FFFFFF"));
                embarassed.setBackgroundColor(Color.parseColor("#FFFFFF"));
                scared.setBackgroundColor(Color.parseColor("#FFFFFF"));
                angry.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        diaryExcitedEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionChosen = "Excited";
                excited.setBackgroundColor(Color.parseColor("#5588a3"));
                sad.setBackgroundColor(Color.parseColor("#FFFFFF"));
                happy.setBackgroundColor(Color.parseColor("#FFFFFF"));
                embarassed.setBackgroundColor(Color.parseColor("#FFFFFF"));
                scared.setBackgroundColor(Color.parseColor("#FFFFFF"));
                angry.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        diaryEmbarassedEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionChosen = "Embarassed";
                embarassed.setBackgroundColor(Color.parseColor("#5588a3"));
                sad.setBackgroundColor(Color.parseColor("#FFFFFF"));
                excited.setBackgroundColor(Color.parseColor("#FFFFFF"));
                happy.setBackgroundColor(Color.parseColor("#FFFFFF"));
                scared.setBackgroundColor(Color.parseColor("#FFFFFF"));
                angry.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        diaryAngryEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionChosen = "Angry";
                angry.setBackgroundColor(Color.parseColor("#5588a3"));
                sad.setBackgroundColor(Color.parseColor("#FFFFFF"));
                excited.setBackgroundColor(Color.parseColor("#FFFFFF"));
                happy.setBackgroundColor(Color.parseColor("#FFFFFF"));
                embarassed.setBackgroundColor(Color.parseColor("#FFFFFF"));
                scared.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        diaryScaredEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionChosen = "Scared";
                scared.setBackgroundColor(Color.parseColor("#5588a3"));
                sad.setBackgroundColor(Color.parseColor("#FFFFFF"));
                excited.setBackgroundColor(Color.parseColor("#FFFFFF"));
                happy.setBackgroundColor(Color.parseColor("#FFFFFF"));
                embarassed.setBackgroundColor(Color.parseColor("#FFFFFF"));
                angry.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });
        return emotionChosen;
    }
    private void btnAddEntry(){

            addEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(mDisplayDate.getText().toString().equals("")) && !(emotionChosen.equals("")) && !(diaryEntry.getText().toString().equals(""))) {
                        myRef = database.getReference("Users/" + currentUser.getUid() + "/DiaryEntries/" + mDisplayDate.getText().toString());
                        DiaryEntry entry = new DiaryEntry(mDisplayDate.getText().toString(), emotionChosen, diaryEntry.getText().toString());

                        myRef.child("Diary_Date").setValue(entry.getDiary_Date());
                        myRef.child("Diary_Emotion").setValue(entry.getDiary_Emotion());
                        myRef.child("Diary_Entry").setValue(entry.getDiary_Entry());
//TODO: Katherine check this out - dosnt want to navigate back to diary fragment but goes back to Login Activity
//**************************************************************************************************************************************
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        DiaryFragment diaryFragment = new DiaryFragment();
                        fragmentTransaction.replace(R.id.fragmentContainer, diaryFragment);
                        fragmentTransaction.commit();
//*************************************************************************************************************************************
                        Toast.makeText(getActivity(), "Diary Entry Added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "All fields are required to add this Diary Entry.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

