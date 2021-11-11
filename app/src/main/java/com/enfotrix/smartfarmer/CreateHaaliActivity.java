package com.enfotrix.smartfarmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.enfotrix.smartfarmer.classes.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateHaaliActivity extends AppCompatActivity {
    private Spinner spinner_rateType;
    private Utils utils;
    private String str_selectedRate;
    private EditText edit_haaliName,edit_haaliAddress,edit_haaliPhone,edit_costPerHour,edit_costPerDay,edit_allocatedArea;
    private Button btn_createHaali;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_haali);

        utils = new Utils(this);

//        edit_costPerDay = findViewById(R.id.edit_costPerDay);
//        edit_costPerHour = findViewById(R.id.edit_costPerHour);
        edit_haaliAddress = findViewById(R.id.edit_haaliAddress);
        edit_haaliName = findViewById(R.id.edit_haaliName);
        edit_haaliPhone = findViewById(R.id.edit_haaliPhone);
        edit_allocatedArea = findViewById(R.id.edit_allocatedArea);
        btn_createHaali = findViewById(R.id.btn_createHaali);
        db = FirebaseFirestore.getInstance();

//        spinner_rateType = findViewById(R.id.spinner_rateType);


        //----------sim codes spinner
//        spinner_rateType= (Spinner) findViewById(R.id.spinner_rateType);//fetch the spinner from layout file
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, getResources()
//                .getStringArray(R.array.str_rateType));//setting the country_array to spinner
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_rateType.setAdapter(adapter);
//        //--------if you want to set any action you can do in this listener
//        spinner_rateType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int position, long id) {
//                str_selectedRate =arg0.getItemAtPosition(position).toString();
//                //Toast.makeText(MainActivity.this, ""+selected_code, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                Toast.makeText(CreateHaaliActivity.this, "Please Select Rate of Haali", Toast.LENGTH_SHORT).show();
//            }
//        });
        
        btn_createHaali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createHaaliInDB();
            }
        });
    }

    private void createHaaliInDB() {
        Map<String, Object> data = new HashMap<>();
        data.put("haali_name",edit_haaliName.getText().toString());
        data.put("haali_phoneNo",edit_haaliPhone.getText().toString());
        data.put("haali_allocatedArea",edit_allocatedArea.getText().toString());
        data.put("haali_address",edit_haaliAddress.getText().toString());
        db.collection("users").document(utils.getToken())
                .collection("Haali").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(CreateHaaliActivity.this, "Haali Added Successfully", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateHaaliActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
}