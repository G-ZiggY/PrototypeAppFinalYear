package com.example.prototypeappfinalyear;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity {

    ImageView profilePic, profilePic_1, profilePic_2, profilePic_3, profilePic_4, profilePic_5, profilePic_6;
    public Uri imageUri;
    private StorageReference storageReference;
    EditText aboutYou;


//Todo: create all user profile data attributes to be editable in user profile edit settings like:
//    private Context mContext = EditProfileActivity.this;
//    private ImageView mProfileImage;
//    private String userId, profileImageUri;
//    private Uri resultUri
//    boolean values?


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        profilePic_1 = findViewById(R.id.profilePic);
        profilePic_2 = findViewById(R.id.profilePic_2);
        profilePic_3 = findViewById(R.id.profilePic_3);
        profilePic_4 = findViewById(R.id.profilePic_4);
        profilePic_5 = findViewById(R.id.profilePic_5);
        profilePic_6 = findViewById(R.id.profilePic_6);
        aboutYou = findViewById(R.id.aboutYou);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference(); 


        profilePic_1.setOnClickListener(v -> {
            profilePic = profilePic_1;
            choosePic();
        });

        profilePic_2.setOnClickListener(v -> {
            profilePic = profilePic_2;
            choosePic();
        });

        profilePic_3.setOnClickListener(v -> {
            profilePic = profilePic_3;
            choosePic();
        });

        profilePic_4.setOnClickListener(v -> {
            profilePic = profilePic_4;
            choosePic();
        });

        profilePic_5.setOnClickListener(v -> {
            profilePic = profilePic_5;
            choosePic();
        });

        profilePic_6.setOnClickListener(v -> {
            profilePic = profilePic_6;
            choosePic();
        });
    }


    private void choosePic() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
            uploadPic();
        }
    }

    private void uploadPic() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading your image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + randomKey);
        riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                Snackbar.make(findViewById(android.R.id.content), "Image has been uploaded!", Snackbar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Upload failed!", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Progress: " + (int)progressPercent + "%");
                if(progressPercent == 100) {
                    pd.setMessage("Complete!");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            pd.dismiss();
                        }
                    }, 1000);
                }
            }
        });
    }


}