package co3102.cw2.eSurveyapp;

import static android.widget.Button.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private IntentIntegrator qrScan;

    EditText mEmail, mFullName, mHomeAddress, mPassword, mConfirmPassword, mSNI;
    Button mRegisterBtn, buttonScan;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar progressBar;
    String userID;
    TextView loginLinkBtn, mDOB, regSNI;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    /*
    Calendar today = Calendar.getInstance();
    Date dob;
    int age = 0;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonScan = findViewById(R.id.regScanQR);
        regSNI = findViewById(R.id.regSNI);

        fStore = FirebaseFirestore.getInstance();
        qrScan = new IntentIntegrator(this);
        buttonScan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });


        mEmail = findViewById(R.id.email);
        mFullName = findViewById(R.id.regName);
        mDOB = (TextView) findViewById(R.id.regDOB);
        mHomeAddress = findViewById(R.id.regAddress);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirmPassword);
        mSNI = findViewById(R.id.regSNI);

        mRegisterBtn = findViewById(R.id.registerButton);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        loginLinkBtn = findViewById(R.id.loginLink);

        loginLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        mDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Register.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        mDateSetListener,
                        year,
                        month,
                        day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d("Register", "onDateSet: date: " + year + "/" + month + "/" + day);

                String date = day + "/" + month + "/" + year;
                mDOB.setText(date);
            }
        };


        if(fAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String _email = mEmail.getText().toString().trim();
                final String _fullName = mFullName.getText().toString().trim();
                final String _dob = mDOB.getText().toString();
                final String _address = mHomeAddress.getText().toString();
                final String _password = mPassword.getText().toString().trim();
                final String _confirm_password = mConfirmPassword.getText().toString().trim();
                final String _sni = mSNI.getText().toString().trim();

                //String userID = fAuth.getCurrentUser().getUid();
                //Log.d("Message", userID);
/*
                Map<String, String> email = (Map<String, String>) new HashMap<>().put("Email",_email);
                documentReference.set(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Message", "Email saved.");
                    }
                });

                Map<String, String> fullName = (Map<String, String>) new HashMap<>().put("Fullname",_fullName);
                documentReference.set(fullName).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Message", "Fullname saved.");
                    }
                });

                Map<String, String> dob = (Map<String, String>) new HashMap<>().put("DOB",_dob);
                documentReference.set(dob).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Message", "Date of Birth saved.");
                    }
                });

                Map<String, String> address = (Map<String, String>) new HashMap<>().put("Address",_address);
                documentReference.set(address).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Message", "Address saved.");
                    }
                });
*/
                if(TextUtils.isEmpty(_email)) {
                    mEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(_fullName)) {
                    mFullName.setError("Name is required");
                    return;
                }

                if(TextUtils.isEmpty(_dob)) {
                    mDOB.setError("Date of Birth is required");
                    return;
                }

                if(TextUtils.isEmpty(_address)) {
                    mHomeAddress.setError("Address is required");
                    return;
                }

                if(TextUtils.isEmpty(_password)) {
                    mPassword.setError("Password is required");
                    return;
                }
                if(TextUtils.isEmpty(_confirm_password)) {
                    mPassword.setError("Password is required");
                    return;
                }

                if(TextUtils.isEmpty(_sni)) {
                    mSNI.setError("8-Digit SNI is required");
                    return;
                }

                if(!_confirm_password.equals(_password)) {
                    mPassword.setError("Passwords do not match.");
                    return;
                }

                if(_sni.length() < 8 || _sni.length()  > 8) {
                    mSNI.setError("Shangri-La National Insurance Number is 8 digits long");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(_email,_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast toast = Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_SHORT);
                            toast.show();

                            userID = fAuth.getCurrentUser().getUid();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {
                            Toast toast = Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });

                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null) {
            if(result.getContents()==null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    regSNI.setText(result.getContents());

                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                    String userID = firebaseAuth.getCurrentUser().getUid();
                    Log.d("Message", userID);
                    DocumentReference documentReference = fStore.collection("users").document(userID);


                    Map<String, Object> user = new HashMap<>();

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("Message", "QR Code Saved");
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();

                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}