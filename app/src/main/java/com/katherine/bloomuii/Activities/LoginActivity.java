/*Created by: Katherine Chambers
* Edited by Rohini Naidu 28/06/2020*/
package com.katherine.bloomuii.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.katherine.bloomuii.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    //UI Components
    private TextView email;
    private TextView password;
    private Button mLogin;
    private TextView mToRegi;

    //Authentication
    private FirebaseAuth  mAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        //Authenticating user details
        btn_login(mAuth);
        //Navigate to Sign Up if User has no Account
        go_to_regi();
    }

    //Login button clicked
    private void btn_login(final FirebaseAuth mAuth) {
        //Declaring UI Components
        mLogin = (Button) findViewById(R.id.btnLogin);
        email = findViewById(R.id.txtHomeUsername);
        password = findViewById(R.id.txtPassword);

        //Authentication
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(email.getText().toString().equals("")) || !(password.getText().toString().equals(""))){
                    Login();

                }
               else{
                    Toast.makeText(LoginActivity.this, "All fields required",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Navigate to Sign Up
    private void go_to_regi() {

        mToRegi = (TextView) findViewById(R.id.txtRegistration);
        mToRegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    //Authenticate User details to allow Access
    public void Login(){
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //If Login Successful - Allow Access
                            Log.d(TAG, "signInWithEmail:success");

                            final FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            i.putExtra("CurrentUser", user.getUid());
                            startActivity(i);
                        }
                         else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }});
    }

    //TODO Test this functionality
    //Check if user is signed in and update UI accordingly
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}//End of Activity
