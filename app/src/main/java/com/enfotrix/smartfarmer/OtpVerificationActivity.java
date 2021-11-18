package com.enfotrix.smartfarmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enfotrix.smartfarmer.classes.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OtpVerificationActivity extends AppCompatActivity {
    private Button btn_verifyOTP;
    private EditText edit_OTP;
    private EditText phoneNo;
    private String otpId;
    private String str_mobileNo;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Utils utils;
    private int counter_userPhoneNo=0;
    boolean isUniqueUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        btn_verifyOTP = findViewById(R.id.btn_verifyOTP);
        edit_OTP = findViewById(R.id.edit_OTP);
        str_mobileNo = getIntent().getStringExtra("mobile").toString();
        Toast.makeText(OtpVerificationActivity.this, ""+str_mobileNo, Toast.LENGTH_SHORT).show();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        utils = new Utils(this);

        //------- auto check otp if sim is in same device
        initiateOTP();

        //------ manual check otp
        btn_verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_OTP.getText().toString().isEmpty()){
                    Toast.makeText(OtpVerificationActivity.this, "Please enter otp", Toast.LENGTH_SHORT).show();
                }
                else if(edit_OTP.getText().length()!=6){
                    Toast.makeText(OtpVerificationActivity.this, "Please enter 6 digit otp", Toast.LENGTH_SHORT).show();
                }
//                else if(edit_OTP.getText().toString().trim()!=otpId){
//                    Toast.makeText(OtpVerificationActivity.this, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show();
//                }
                else {
                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(otpId,edit_OTP.getText().toString().trim());
                    signInWithPhoneAuthCredential(credential);

                }
            }
        });

        

        
    }
    private void initiateOTP(){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(str_mobileNo)       // Phone number to verify
                        .setTimeout(10L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(OtpVerificationActivity.this, "OTP: "+otpId, Toast.LENGTH_SHORT).show();
                                Toast.makeText(OtpVerificationActivity.this, "Verification failed "+e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otpId = s;
                            }

                            @Override
                            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                                super.onCodeAutoRetrievalTimeOut(s);
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (checkUserExistence()) {
                                // Toast.makeText(OtpVerificationActivity.this, "OTP is: "+credential, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(OtpVerificationActivity.this, MainActivity.class));
                                finish();
                            }

                        } else {
                            Toast.makeText(OtpVerificationActivity.this, "OTP verification failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean checkUserExistence() {
        db.collectionGroup("users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull  Task<QuerySnapshot> task) {

                        for(QueryDocumentSnapshot document : task.getResult()){

                            if(document.getString("user_phoneNo").equals(str_mobileNo)){
                                counter_userPhoneNo++;
                                utils.putToken(document.getString("user_phoneNo"));
//                                Toast.makeText(ActivitySignup.this, ""+document.getString("username"), Toast.LENGTH_SHORT).show();
                            }
                        }
                        if(counter_userPhoneNo>0) Toast.makeText(OtpVerificationActivity.this, "User Already Exists!", Toast.LENGTH_SHORT).show();
                        else createNewUser();
                        counter_userPhoneNo=0;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull  Exception e) {
                        Toast.makeText(OtpVerificationActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                    }
                });

        return  isUniqueUser;
    }

    private void createNewUser() {
        Map<String, Object> data = new HashMap<>();
        data.put("user_phoneNo",str_mobileNo);

        db.collection("users").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
               utils.putToken(str_mobileNo);
                Toast.makeText(OtpVerificationActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OtpVerificationActivity.this, "Failed to Register New User", Toast.LENGTH_SHORT).show();
            }
        });
    }


}