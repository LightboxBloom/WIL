package com.katherine.bloomuii.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.katherine.bloomuii.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    //button btn_regi
    private Button mLogin;

    //text view txt_login
    private TextView mToRegi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //calling methods
        btn_login();
        go_to_regi();

    }

    //login button clicked
    private void btn_login() {
        //register button to take user to home
        mLogin = (Button) findViewById(R.id.btn_login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }//end of login btn

    //edit text to go to login page if already have an account
    private void go_to_regi() {
        mToRegi = (TextView) findViewById(R.id.txt_regi);
        mToRegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }//end of text view click
}
