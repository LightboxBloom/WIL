package com.katherine.bloomuii.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.katherine.bloomuii.R;

public class ViewDiaryEntry extends Fragment {
    private Bundle bundle;
    private String entry;
    private String date;
    private String emotion;

    ImageView imEmotion;
    TextView diaryDate;
    TextView diaryEntry;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_diary_entry, container, false);
        bundle = getArguments();

        imEmotion = view.findViewById(R.id.imEmotion);
        diaryDate = view.findViewById(R.id.txtDiaryDate);
        diaryEntry = view.findViewById(R.id.txtEntry);

        if(bundle != null) {
            entry = bundle.getString("DiaryEntry");
            date = bundle.getString("DiaryDate");
            emotion = bundle.getString("DiaryEmotion");

            //Set Each Emotion depending on field data
            if (emotion.equals("Happy")) {
                imEmotion.setImageResource(R.drawable.img_happy);
            } else if (emotion.equals("Sad")) {
                imEmotion.setImageResource(R.drawable.img_sad);
            } else if (emotion.equals("Embarassed")) {
                imEmotion.setImageResource(R.drawable.img_embarrased);
            } else if (emotion.equals("Excited")) {
                imEmotion.setImageResource(R.drawable.img_excited);
            } else if (emotion.equals("Angry")) {
                imEmotion.setImageResource(R.drawable.img_angry);
            } else if (emotion.equals("Scared")) {
                imEmotion.setImageResource(R.drawable.img_scared);
            }

            diaryDate.setText(date);
            diaryEntry.setText(entry);

        }
        return view;
    }
}