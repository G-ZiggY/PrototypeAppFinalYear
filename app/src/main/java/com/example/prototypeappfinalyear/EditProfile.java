package com.example.prototypeappfinalyear;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    private CircleImageView profileIV;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private Uri imageUri;
    private String myUri = "";
    private StorageReference storageReference;

    Button saveBtn, closeBtn;
    TextView changeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // initialization
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileIV);
            }
        });

        profileIV = findViewById(R.id.profileIV);
        changeBtn = findViewById(R.id.changeBtn);
        saveBtn = findViewById(R.id.saveBtn);
        closeBtn = findViewById(R.id.closeBtn);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

        closeBtn.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), HomeActivity.class)));

        saveBtn.setOnClickListener(v -> uploadImage());
        
        getUserInfo();

    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void requestStoragePermission() {
//        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
//
//    }
//
//    private boolean checkStoragePermission() {
//        boolean res2 = ContextCompat.checkSelfPermission
//                (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
//        return res2;
//    }
//
//    private void pickImage() {
//        CropImage.activity().start(this);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void requestCameraPermission() {
//        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
//    }
//
//    private boolean checkCameraPermission() {
//        boolean res1 = ContextCompat.checkSelfPermission
//                (this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
//        boolean res2 = ContextCompat.checkSelfPermission
//                (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
//        return res1 && res2;
//    }

    private void getUserInfo() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount() > 0){
                    if(snapshot.hasChild("image")){
                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profileIV);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                imageUri = data.getData();
                profileIV.setImageURI(imageUri);
                uploadImage();
            }
        }
    }

    private void uploadImage() {
        // upload profile image to firebase
        StorageReference fileReference = storageReference.child("users/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileIV);
                    }
                });
                Toast.makeText(EditProfile.this, "Image Uploaded!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfile.this, "Upload failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void cropImage(Uri imageUri) {
//        UCrop.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(this);
//    }

//    private void uploadProfilePic(){
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Set your profile picture");
//        progressDialog.setMessage("Please wait until your data is set");
//        progressDialog.show();
//
//        if(imageUri != null){
//            final StorageReference fileReference = storageProfilePic.child(mAuth.getCurrentUser().getUid() + ".jpg");
//            StorageTask<UploadTask.TaskSnapshot> uploadTask = fileReference.putFile(imageUri);
//
//            uploadTask.continueWithTask(new Continuation() {
//                @Override
//                public Object then(@NonNull Task task) throws Exception {
//                    if(!task.isSuccessful()){
//                        throw task.getException();
//                    }
//                    return fileReference.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if(task.isSuccessful()){
//                        Uri downloadUrl = task.getResult();
//                        myUri = downloadUrl.toString();
//                        HashMap<String, Object> userMap = new HashMap<>();
//                        userMap.put("image", myUri);
//
//                        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
//                        progressDialog.show();
//                    }
//                }
//            });
//        } else {
//            progressDialog.dismiss();
//            Toast.makeText(this, "Image has not been selected", Toast.LENGTH_SHORT).show();
//        }
//    }


}