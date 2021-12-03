package com.enfotrix.smartfarmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.Transformations.DepthTransformation;


public class MainActivity extends AppCompatActivity {
    private CardView crd_myProfile,crd_cropsInformation,crd_weather,crd_calculator,crd_chatWithExpert,crd_diseases,crd_fertilizers,crd_seeds;
    private  Button btn_logout,btn_createHaali;

    //slider
    SliderView sliderView;
    int[] images = {
            R.drawable.farmer1,
            R.drawable.farmer2,
            R.drawable.farmer3
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //slider view
        sliderView=findViewById(R.id.image_slider);


        crd_calculator = findViewById(R.id.crd_calculator);
        crd_cropsInformation = findViewById(R.id.crd_cropsInformation);
        crd_weather = findViewById(R.id.crd_weather);
        crd_myProfile = findViewById(R.id.crd_myProfile);
        btn_createHaali = findViewById(R.id.btn_createHaali);
        btn_logout = findViewById(R.id.btn_logout);
        crd_chatWithExpert = findViewById(R.id.crd_chatWithExpert);
        crd_diseases=findViewById(R.id.crd_diseases);
        crd_fertilizers=findViewById(R.id.crd_fertilizers);
        crd_seeds=findViewById(R.id.crd_seeds);


//        btn_createHaali.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,CreateHaaliActivity.class);
//                startActivity(intent);
//            }
//        });


        //Slider_Adapter_Object
        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();




        crd_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CalculatorActivity.class);
                startActivity(intent);
            }
        });

        crd_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WeatherActivity.class);
                startActivity(intent);
            }
        });

        crd_myProfile.setOnClickListener(new View.OnClickListener() {
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
        crd_chatWithExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                to be written
            }
        });

        crd_cropsInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CropsInformationActivity.class);
                startActivity(intent);
            }
        });
        crd_fertilizers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FertilizersActivity.class);
                startActivity(intent);
            }
        });

        crd_seeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SeedsActivity.class);
                startActivity(intent);
            }
        });

        crd_diseases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DiseasesActivity.class);
                startActivity(intent);
            }
        });
    }
}