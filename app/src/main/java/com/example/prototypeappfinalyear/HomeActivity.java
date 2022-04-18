package com.example.prototypeappfinalyear;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    // creating firebase authentication + DB ref instance
    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;
    private FirebaseUser user;

    Button logoutBtn;
    TextView nameTV,profileTV;

    String userID;

    ImageView profileIV, editProfileIV, appSettingsIV, chatIV;


    // on app start checks if user is logged in, if not, send to login activity
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove action bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        if(user != null){
            userID = user.getUid();
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/" + userID + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileIV);
            }
        });


        logoutBtn = findViewById(R.id.logoutBtn);
        nameTV = findViewById(R.id.nameTV);
        profileTV = findViewById(R.id.profileTV);
        profileIV = findViewById(R.id.profileIV);
        editProfileIV = findViewById(R.id.user_settings);
        appSettingsIV = findViewById(R.id.app_settings);
        chatIV = findViewById(R.id.chat_button);

        chatIV.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ChatActivity.class);
            startActivity(i);
        });

        appSettingsIV.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
        });

        editProfileIV.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), EditProfile.class);
            startActivity(i);
        });

        profileTV.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(i);
        });

        profileIV.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(i);
        });

        logoutBtn.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        });

        if(userID != null){
            getUserInfo();

        }
    }

    private void getUserInfo() {
        DocumentReference documentReference = fireStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                nameTV.setText(value.getString("name"));
            }
        });
    }
}