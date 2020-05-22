package com.katherine.bloomuii.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.katherine.bloomuii.R;

import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    //Text view txt_dob
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //button btn_regi
    private Button mRegister;

    //text view txt_login
    private TextView mToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //calling methods
        date_picker();
        btn_regi();
        go_to_login();


    }



    //method for date picker
    private void date_picker() {
        //date picker
        mDisplayDate = (TextView) findViewById(R.id.txt_dob);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SignupActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };

    }//end of date picker selector

    //method to go from registration to home screen
    private void btn_regi() {
        //register button to take user to home
        mRegister = (Button) findViewById(R.id.btn_regi);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
            }
        });

    }//end of register button click

    //method for going from registration to login screen
    private void go_to_login() {
        //edit text to go to login page if already have an account
        mToLogin = (TextView) findViewById(R.id.txt_login);
        mToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

    }//end of text view click

}

