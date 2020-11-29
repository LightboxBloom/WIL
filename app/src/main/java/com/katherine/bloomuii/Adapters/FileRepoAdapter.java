//Developer: Rohini Naidu
package com.katherine.bloomuii.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.katherine.bloomuii.ObjectClasses.Classroom;
import com.katherine.bloomuii.ObjectClasses.FirebaseFile;
import com.katherine.bloomuii.ObjectClasses.FirebaseFile;
import com.katherine.bloomuii.R;

import java.util.List;

public class FileRepoAdapter extends ArrayAdapter<FirebaseFile> {
    //Constructor
    public FileRepoAdapter(Context context, int simple_list_item_1, List<FirebaseFile> files){
        super(context,0, files);
    }
    //Retrieve a File Name and Bind its fields to UI Components
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Get File Name
        FirebaseFile file = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.filerepo_adapter,parent,false);
        }
        //set File Name Data to UI Components
        if(file != null){
            TextView txtFileName = convertView.findViewById(R.id.txtFileName);
            txtFileName.setText(file.getFile_Name());
        }
        return convertView;
    }
}
