/*Created by: Katherine Chambers
* Edited by: Rohini Naidu 28/06/2020*/
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.katherine.bloomuii.R;
import com.katherine.bloomuii.ObjectClasses.User;
import com.katherine.bloomuii.Validation.Validator;

import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {

    //TAG description
    private static final String TAG = "SignupActivity";

    //UI Components
    private Button mRegister;
    private TextView mToLogin;
    private TextView fullName;
    private TextView email;
    private TextView password;
    private TextView confirmPassword;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //Authentication
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private boolean validationPass = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Declare UI Components
        mDisplayDate = (TextView) findViewById(R.id.txtProfileDob);
        email = findViewById(R.id.txtEmail);
        fullName = findViewById(R.id.txtFname);
        password = findViewById(R.id.txtPassword);
        confirmPassword = findViewById(R.id.txtConPassword);
        mRegister = (Button) findViewById(R.id.btnRegister);

        //Declare Authentication
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        //DatePicker Functionality
        datePicker();

        //If Register Button is clicked
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If any text fields are empty - prompt the user to fill them
                if(fullName.getText().toString().equals("")  || email.getText().toString().equals("") || password.getText().toString().equals("") || confirmPassword.getText().toString().equals("")){
                    Toast.makeText(SignupActivity.this, "All fields required",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    //Initialize the Validator
                    Validator v = new Validator();
                    //Validate password - According to Password Policy in Validator Class
                    if ((password.getText().toString().equals(confirmPassword.getText().toString()))) {
                        validationPass = true;
                        if (password.getText().toString().length() > 6 || v.CheckIfDigitExists(password.getText().toString()) || v.CheckIfCapsExists(password.getText().toString())) {
                            validationPass = true;
                        } else {
                            validationPass = false;
                        }
                    } else {
                        validationPass = false;
                    }
                    //If all validation tests are passed - Register the user and then navigate to the Login Activity
                    if(validationPass) {
                        btnRegister(mAuth);
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    }
                    else{
                        Toast.makeText(SignupActivity.this, "Sign Up failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //Text at the bottom of the activity to Navigate to Login
        goToLogin();
    }

    //Configure Date Picker
    private void datePicker() {
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
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);
                String date = day + "-" + (month+1) + "-" + year;
                mDisplayDate.setText(date);
            }
        };

    }

    //Register new User to Firebase and Write User information to Firebase
    private void btnRegister(final FirebaseAuth mAuth) {

        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(@NonNull AuthResult authResult) {

                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        //Save new Users Information
                        User newUser = new User(email.getText().toString(), mDisplayDate.getText().toString(), "English");
                        SaveUserToDatabase(newUser);
                        Toast.makeText(SignupActivity.this, "Authentication success.",
                                                      Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure");
                        Toast.makeText(SignupActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //Navigate to Login if Text clicked by User
    private void goToLogin() {
        //edit text to go to login page if already have an account
        mToLogin = (TextView) findViewById(R.id.txtLogin);
        mToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

    //Write new user that successfully registered to Firebase
    public void SaveUserToDatabase(User newUser)
    {
        //adding to RealTime Database
        DatabaseReference myRef = database.getReference("Users");

        myRef.child(mAuth.getUid()).child("Email_Address").setValue(newUser.getEmail_Address());
        myRef.child(mAuth.getUid()).child("Date_Of_Birth").setValue(newUser.getDate_Of_Birth());
        myRef.child(mAuth.getUid()).child("Full_Name").setValue(newUser.getFull_Name());
        myRef.child(mAuth.getUid()).child("Language_Preference").setValue(newUser.getLanguage_Preference());
    }
}//End of Activity

