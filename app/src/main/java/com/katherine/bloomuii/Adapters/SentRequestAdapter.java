//Developer: Rohini Naidu
package com.katherine.bloomuii.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.katherine.bloomuii.ObjectClasses.SentRequest;
import com.katherine.bloomuii.R;

import java.util.List;
public class SentRequestAdapter extends ArrayAdapter<SentRequest> {
    //Constructor
    public SentRequestAdapter(Context context, int simple_list_item_1, List<SentRequest> sentRequestList){
        super(context,0, sentRequestList);
    }
    ////Retrieve Sent Request and bind the data to UI Components
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Get Sent Request
        SentRequest request = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sent_request_adapter,parent,false);
        }
        //Bind Sent Request to UI Components
        if(request != null){
            TextView classroomName = convertView.findViewById(R.id.txtClassroomName);
            TextView user = convertView.findViewById(R.id.txtTeacher);
            TextView sampleText = convertView.findViewById(R.id.txtSampleText);

            classroomName.setText(request.getClassroom_Name());
            sampleText.setText("Sent to:");
            if(request.getType_Of_Request().equals("Contributor")) {
                user.setText(request.getRequested_Username()+"#"+request.getRequested_User_Id().substring(0,4)+" as a Contributor");
            }
            else if(request.getType_Of_Request().equals("Student")){
                user.setText(request.getRequested_Username()+"#"+request.getRequested_User_Id().substring(0,4)+" as a Student");
            }
        }
        return convertView;
    }
}
