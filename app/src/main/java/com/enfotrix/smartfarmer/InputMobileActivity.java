package com.enfotrix.smartfarmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class InputMobileActivity extends AppCompatActivity {
    private EditText edit_mobileNo;
    private Button btn_sendOTP;
    private CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_mobile);
        edit_mobileNo = findViewById(R.id.editText_mobileNo);
        btn_sendOTP = findViewById(R.id.btn_sendOTP);
        ccp = findViewById(R.id.ccp);

        ccp.registerCarrierNumberEditText(edit_mobileNo);

        btn_sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMobileActivity.this,OtpVerificationActivity.class);
                intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
                startActivity(intent);
            }
        });


    }
}