//Developer: Rohini Naidu
package com.katherine.bloomuii.Adapters;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.katherine.bloomuii.Fragments.ViewClassroomFragment;
import com.katherine.bloomuii.Fragments.ViewDiaryEntry;
import com.katherine.bloomuii.ObjectClasses.DiaryEntry;
import com.katherine.bloomuii.R;

import java.util.List;

import static com.katherine.bloomuii.R.drawable.img_happy;
public class DiaryAdapter extends ArrayAdapter<DiaryEntry> {
    //Constructor
    public DiaryAdapter(Context context, int simple_list_item_1, List<DiaryEntry> diaryEntries){
        super(context,0, diaryEntries);
    }
    //Retrieve an Diary Entry and Bind its fields to UI Components
    @Override
    public View getView(int position, View convertView,ViewGroup parent){
        //Get Diary Entry
        DiaryEntry entry = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.diary_card_adapter,parent,false);
        }
        //set Diary Entry Data to UI Components
        if(entry != null){
            TextView date = convertView.findViewById(R.id.txtAdapterDiaryDate);
            ImageView emotion = convertView.findViewById(R.id.ivAdapterDiaryEmotion);

            date.setText(entry.getDiary_Date());
            //Set Each Emotion depending on field data
            if(entry.getDiary_Emotion().equals("Happy")){
                emotion.setImageResource(R.drawable.img_happy);
            }
            else if(entry.getDiary_Emotion().equals("Sad")){
                emotion.setImageResource(R.drawable.img_sad);
            }
            else if(entry.getDiary_Emotion().equals("Embarassed")){
                emotion.setImageResource(R.drawable.img_embarrased);
            }
            else if(entry.getDiary_Emotion().equals("Excited")){
                emotion.setImageResource(R.drawable.img_excited);
            }
            else if(entry.getDiary_Emotion().equals("Angry")){
                emotion.setImageResource(R.drawable.img_angry);
            }
            else if(entry.getDiary_Emotion().equals("Scared")){
                emotion.setImageResource(R.drawable.img_scared);
            }
        }
        return convertView;
    }

}
