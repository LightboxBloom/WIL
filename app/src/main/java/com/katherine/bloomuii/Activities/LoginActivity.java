/*Created by: Katherine Chambers
* Edited by Rohini Naidu 28/06/2020*/
package com.katherine.bloomuii.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
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
    private TextView txtSendEmailResetPassword;
    private TextView errorFeedback;
    private ProgressBar progress;

    //Authentication
    private FirebaseAuth  mAuth;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        database.getInstance().setPersistenceEnabled(false);

        //Declaring UI Components
        mLogin = findViewById(R.id.btnLogin);
        email = findViewById(R.id.txtHomeUsername);
        password = findViewById(R.id.txtPassword);
        txtSendEmailResetPassword = findViewById(R.id.txtForgotPassword);
        errorFeedback = findViewById(R.id.txtErrorFeedback);
        progress = findViewById(R.id.progressBar);

        progress.setVisibility(View.INVISIBLE);
        //Reset Password by emailing
        sendEmailAndRestPassword();
        //Authenticating user details
        btn_login(mAuth);
        //Navigate to Sign Up if User has no Account
        go_to_regi();
    }

    private void sendEmailAndRestPassword(){
        if(!email.getText().toString().trim().equals("")) {
            txtSendEmailResetPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!errorFeedback.getText().toString().equals("Email Sent")) {
                        FirebaseAuth.getInstance().sendPasswordResetEmail(email.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Email sent.");
                                            errorFeedback.setText("Email Sent.");

                                        }
                                    }
                                });
                    }
                }
            });
        }
         else{
            errorFeedback.setText("Email required.");
        }
    }
    //Login button clicked
    private void btn_login(final FirebaseAuth mAuth) {
        errorFeedback.setText("");
        //Authentication
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(email.getText().toString().equals("")) || !(password.getText().toString().equals(""))){
                    progress.setVisibility(View.VISIBLE);
                    Login();

                }
               else{
                    errorFeedback.setText("All fields required.");
                    progress.setVisibility(View.INVISIBLE);
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
        //If verified login
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //If Login Successful - Allow Access
                        if(checkIfEmailisVerified()) {
                            Log.d(TAG, "signInWithEmail:success");

                            final FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            i.putExtra("CurrentUser", user.getUid());
                            startActivity(i);
                        }
                        else{
                            sendVerification();
                            errorFeedback.setText("Email is not verified. Check your Email.");
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        errorFeedback.setText("Incorrect credentials.");
                        progress.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    private boolean checkIfEmailisVerified(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user.isEmailVerified()){
            return true;
        }
        else{
            return false;
        }
    }
    //Send Email Verification if account is not verified
    private void sendVerification(){
        FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // email sent
                    // after email is sent just logout the user and finish this activity
                    FirebaseAuth.getInstance().signOut();
                    progress.setVisibility(View.INVISIBLE);
                    finish();
                }
            }
        });
    }
    //Check if user is signed in and update UI accordingly
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.putExtra("CurrentUser", currentUser.getUid());
            startActivity(i);
        }
    }
}//End of Activity
