//Developer: Rohini Naidu
package com.katherine.bloomuii.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.katherine.bloomuii.ObjectClasses.Classroom;
import com.katherine.bloomuii.ObjectClasses.DiaryEntry;
import com.katherine.bloomuii.R;

import java.util.List;
public class ClassroomAdapter extends ArrayAdapter<Classroom> {
    //Constructor
    public ClassroomAdapter(Context context, int simple_list_item_1, List<Classroom> classes){
        super(context,0, classes);
    }
    //Retrieve an Classroom and Bind its fields to UI Components
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Get Classroom
        Classroom classroom = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.classroom_adapter,parent,false);
        }
        //set Classroom Data to UI Components
        if(classroom != null){
            TextView classroomName = convertView.findViewById(R.id.txtClassroomName);
            classroomName.setText(classroom.getClassroom_Name());

            TextView teacher = convertView.findViewById(R.id.txtHeadTeacher);
            teacher.setText(classroom.getTeacher_Name());
        }
        return convertView;
    }
}
