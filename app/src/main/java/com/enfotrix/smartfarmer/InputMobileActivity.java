package com.enfotrix.smartfarmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputMobileActivity extends AppCompatActivity {
    private EditText edit_mobileNo;
    private Button btn_sendOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_mobile);
        edit_mobileNo = findViewById(R.id.editText_mobileNo);
        btn_sendOTP = findViewById(R.id.btn_sendOTP);

        btn_sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMobileActivity.this,OtpVerificationActivity.class);
                intent.putExtra(edit_mobileNo.getText().toString().trim(),"edit_mobileNo");
                startActivity(intent);
            }
        });


    }
}