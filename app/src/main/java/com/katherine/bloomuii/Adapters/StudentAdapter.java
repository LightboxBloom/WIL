//Developer: Rohini Naidu
package com.katherine.bloomuii.Adapters;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.katherine.bloomuii.ObjectClasses.DiaryEntry;
import com.katherine.bloomuii.ObjectClasses.User;
import com.katherine.bloomuii.R;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<User> {

    //Constructor
    public StudentAdapter(Context context, int simple_list_item_1, List<User> students){
        super(context,0, students);

    }
    //Retrieve an User and Bind its fields to UI Components
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Get Diary Entry
        final User user = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_adapter,parent,false);
        }
        //set Diary Entry Data to UI Components
        if(user != null){
            TextView studentName = convertView.findViewById(R.id.txtStudentName);
            studentName.setText(user.getFull_Name());
        }
        return convertView;
    }
}
