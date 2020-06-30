package com.katherine.bloomuii.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.katherine.bloomuii.ObjectClasses.DiaryEntry;
import com.katherine.bloomuii.R;

import java.util.List;

import static com.katherine.bloomuii.R.drawable.img_happy;

public class DiaryAdapter extends ArrayAdapter<DiaryEntry> {
    public DiaryAdapter(Context context, int simple_list_item_1, List<DiaryEntry> diaryEntries){
        super(context,0, diaryEntries);
    }
    @Override
    public View getView(int position, View convertView,ViewGroup parent){
        DiaryEntry entry = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.diary_card_adapter,parent,false);
        }
        if(entry != null){
            TextView date = convertView.findViewById(R.id.txtAdapterDiaryDate);
            ImageView emotion = convertView.findViewById(R.id.ivAdapterDiaryEmotion);

            date.setText(entry.getDiary_Date());
            //TODO: Katherine to change images to respective emotions

            if(entry.getDiary_Emotion().equals("Happy")){
                emotion.setImageResource(R.drawable.img_happy);
            }
            else if(entry.getDiary_Emotion().equals("Sad")){
                emotion.setImageResource(R.drawable.img_happy);
            }
            else if(entry.getDiary_Emotion().equals("Embarassed")){
                emotion.setImageResource(R.drawable.img_happy);
            }
            else if(entry.getDiary_Emotion().equals("Excited")){
                emotion.setImageResource(R.drawable.img_happy);
            }
            else if(entry.getDiary_Emotion().equals("Angry")){
                emotion.setImageResource(R.drawable.img_happy);
            }
            else if(entry.getDiary_Emotion().equals("Scared")){
                emotion.setImageResource(R.drawable.img_happy);
            }
        }
        return convertView;
    }

}
