package com.enfotrix.smartfarmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button btn_createHaali,btn_myProfile,btn_cropsInformation,btn_weather,btn_calculator,btn_logout,btn_chatWithExpert;
    private TextView txt_weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_calculator = findViewById(R.id.btn_calculator);
        btn_cropsInformation = findViewById(R.id.btn_cropsInformation);
        btn_weather = findViewById(R.id.btn_weather);
        btn_myProfile = findViewById(R.id.btn_myProfile);
        btn_createHaali = findViewById(R.id.btn_createHaali);
        btn_logout = findViewById(R.id.btn_logout);
        btn_chatWithExpert = findViewById(R.id.btn_chatWithExpert);
        txt_weather = findViewById(R.id.txt_weather);

        btn_createHaali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CreateHaaliActivity.class);
                startActivity(intent);
            }
        });

        btn_createHaali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CreateHaaliActivity.class);
                startActivity(intent);
            }
        });

        btn_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CalculatorActivity.class);
                startActivity(intent);
            }
        });

        btn_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WeatherActivity.class);
                startActivity(intent);
            }
        });

        btn_myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyProfileActivity.class);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this,InputMobileActivity.class);
                startActivity(intent);
            }
        });
        btn_chatWithExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                to be written
            }
        });
    }
}