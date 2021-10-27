package com.enfotrix.smartfarmer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.PhoneAuthProvider;

public class OtpVerificationActivity extends AppCompatActivity {
    private Button btn_verifyOTP;
    private EditText edit_OTP;
    private EditText phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        btn_verifyOTP = findViewById(R.id.btn_verifyOTP);
        edit_OTP = findViewById(R.id.edit_OTP);
        
        String str_phoneNo = "+92"+getIntent().getStringExtra("edit_mobileNo");
        sendVerificationCodeToUser(str_phoneNo);
        
    }

    private void sendVerificationCodeToUser(String str_phoneNo) {

    }
}