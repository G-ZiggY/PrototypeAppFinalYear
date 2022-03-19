package com.example.prototypeappfinalyear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterHobby extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextView gaming, reading, gym, cycling, netflix, programing, hiking, swimming, party, travel, diy;
    TextView gamingOn, readingOn, gymOn, cyclingOn, netflixOn, programingOn, hikingOn, swimmingOn, partyOn, travelOn, diyOn;
    ArrayList<String> hobbies;
    Button submitBtn;
    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DocumentReference documentReference;
    String email, password, name, surname, nick, dob, zodiac, pathN, gender, interest, country, nationality, height, occupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove action bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_hobby);

        // extract extras from previous activity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        name = intent.getStringExtra("name");
        surname = intent.getStringExtra("surname");
        nick = intent.getStringExtra("nick");
        dob = intent.getStringExtra("dob");
        zodiac = intent.getStringExtra("zodiac");
        pathN = intent.getStringExtra("pathN");
        gender = intent.getStringExtra("gender");
        interest = intent.getStringExtra("interest");
        country = intent.getStringExtra("country");
        nationality = intent.getStringExtra("nationality");
        height = intent.getStringExtra("height");
        occupation = intent.getStringExtra("occupation");

        gaming = findViewById(R.id.gaming);
        reading = findViewById(R.id.reading);
        gym = findViewById(R.id.gym);
        cycling = findViewById(R.id.cycling);
        netflix = findViewById(R.id.netflix);
        programing = findViewById(R.id.programming);
        hiking = findViewById(R.id.hiking);
        swimming = findViewById(R.id.swimming);
        party = findViewById(R.id.party);
        travel = findViewById(R.id.travel);
        diy = findViewById(R.id.diy);

        hobbies = new ArrayList<>();

        submitBtn = findViewById(R.id.submitBtn);

        // selected text views
        gamingOn = findViewById(R.id.gamingOn);
        readingOn = findViewById(R.id.readingOn);
        gymOn = findViewById(R.id.gymOn);
        cyclingOn = findViewById(R.id.cyclingOn);
        netflixOn = findViewById(R.id.netflixOn);
        programingOn = findViewById(R.id.programmingOn);
        hikingOn = findViewById(R.id.hikingOn);
        swimmingOn = findViewById(R.id.swimmingOn);
        partyOn = findViewById(R.id.partyOn);
        travelOn = findViewById(R.id.travelOn);
        diyOn = findViewById(R.id.diyOn);

        gaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamingOn.setText("Gaming");
                hobbies.add("Gaming");
            }
        });

        reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readingOn.setText("Reading");
                hobbies.add("Reading");
            }
        });

        gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gymOn.setText("Gym");
                hobbies.add("Gym");
            }
        });

        cycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cyclingOn.setText("Cycling");
                hobbies.add("Cycling");
            }
        });

        netflix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netflixOn.setText("Netflix");
                hobbies.add("Netflix");
            }
        });

        programing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                programingOn.setText("Programming");
                hobbies.add("Programming");
            }
        });

        hiking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hikingOn.setText("Hiking");
                hobbies.add("Hiking");
            }
        });

        swimming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swimmingOn.setText("Swimming");
                hobbies.add("Swimming");
            }
        });

        party.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partyOn.setText("Party");
                hobbies.add("Party");
            }
        });

        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                travelOn.setText("Travelling");
                hobbies.add("Travelling");
            }
        });

        diy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diyOn.setText("DIY");
                hobbies.add("DIY");
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });



    }

    private void createUser() {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_SHORT).show();
                    String userID = firebaseAuth.getCurrentUser().getUid();
                    documentReference = fireStore.collection("users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("email", email);
                    user.put("password", password);
                    user.put("name", name);
                    user.put("surname", surname);
                    user.put("nick", nick);
                    user.put("dob", dob);
                    user.put("zodiac", zodiac);
                    user.put("pathN", pathN);
                    user.put("gender", gender);
                    user.put("interest", interest);
                    user.put("country", country);
                    user.put("nationality", nationality);
                    user.put("height", height);
                    user.put("occupation", occupation);
                    user.put("hobbies", hobbies);

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: user Profile is created for "
                                    + name + "\nID = " + userID);
                        }
                    });
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Registration Error: " +
                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}