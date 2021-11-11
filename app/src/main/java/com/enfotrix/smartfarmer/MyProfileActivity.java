package com.enfotrix.smartfarmer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enfotrix.smartfarmer.classes.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MyProfileActivity extends AppCompatActivity {

    private ImageView image_profilePicture;
    private EditText edit_cast,edit_fullName,edit_phoneNo,edit_address,edit_landOwned;
    private FirebaseFirestore db;

    private Utils utils;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        image_profilePicture = findViewById(R.id.image_profilePicture);
        edit_cast = findViewById(R.id.edit_cast);
        edit_fullName = findViewById(R.id.edit_fullName);
        edit_phoneNo = findViewById(R.id.edit_phoneNo);
        edit_address = findViewById(R.id.edit_address);
        edit_landOwned = findViewById(R.id.edit_landOwned);

        utils = new Utils(this);

        db=FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();


        image_profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadProfilePicture();
            }
        });

        fetchProfile();
    }

    private void uploadProfilePicture() {
        Dexter.withActivity(MyProfileActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent,"Select Image"),1);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 && resultCode==RESULT_OK){
            filePath=data.getData();
            try {
                InputStream inputStream =getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                image_profilePicture.setImageBitmap(bitmap );
                uploadToFirebase();

            }catch (Exception e){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadToFirebase() {
        storageReference = storage.getReference().child("profilePhotos/"+ utils.getToken()/*UUID.randomUUID().toString()*/);

        storageReference.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Map<String, Object> m = new HashMap<>();
                                        m.put("user_photo", uri.toString());

                                        db.collection("users").document(utils.getToken()).update(m)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(MyProfileActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    }
                                });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        });

    }


    private void fetchProfile() {
        utils.putToken("Lh1u3z15LbnChhX5c2eX");

        db.collection("users").document(utils.getToken()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    edit_fullName.setText(documentSnapshot.getString("user_name"));
                    edit_address.setText(documentSnapshot.getString("user_address"));
                    edit_landOwned.setText(documentSnapshot.getString("user_landOwned"));
                    edit_cast.setText(documentSnapshot.getString("user_cast"));
                    edit_phoneNo.setText(documentSnapshot.getString("user_phoneNo"));
                    Glide.with(MyProfileActivity.this).load(documentSnapshot.getString("user_photo")).into(image_profilePicture);

                }

            }
        });

    }
}