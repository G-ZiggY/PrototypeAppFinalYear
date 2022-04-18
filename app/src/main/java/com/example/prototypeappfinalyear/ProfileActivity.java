package com.example.prototypeappfinalyear;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    // creating firebase authentication + DB ref instance
    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;
    private FirebaseUser user;

    TextView nameTV,nickTV, ageTV, activeHobbiesTV, creativeHobbiesTV,
            cerebralHobbiesTV, heightTV, eyeColorTV, interestTV,
            hairColorTV, hairLengthTV, profileTV, aboutTV, locationTV,
            zodiacTV, welcomeTV;

    String userID;

    ImageView profileIV, editProfileIV, appSettingsIV, chatIV, homeIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove action bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        if(user != null){
            userID = user.getUid();
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/" + userID + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileIV);
            }
        });


        nameTV = findViewById(R.id.nameTV);
        profileTV = findViewById(R.id.profileTV);
        nickTV = findViewById(R.id.nickTV);
        ageTV = findViewById(R.id.ageTV);
        aboutTV = findViewById(R.id.aboutTV);
        locationTV = findViewById(R.id.locationTV);
        activeHobbiesTV = findViewById(R.id.activeHobbiesTV);
        cerebralHobbiesTV = findViewById(R.id.cerebralHobbiesTV);
        creativeHobbiesTV = findViewById(R.id.creativeHobbiesTV);
        heightTV = findViewById(R.id.heightTV);
        eyeColorTV = findViewById(R.id.eyeColorTV);
        interestTV = findViewById(R.id.interestTV);
        hairColorTV = findViewById(R.id.hairColorTV);
        hairLengthTV = findViewById(R.id.hairLengthTV);
        profileIV = findViewById(R.id.profileIV);
        editProfileIV = findViewById(R.id.userIV);
        appSettingsIV = findViewById(R.id.app_settingsIV);
        chatIV = findViewById(R.id.chatIV);
        homeIV = findViewById(R.id.homeIV);
        zodiacTV = findViewById(R.id.zodiacTV);
        welcomeTV = findViewById(R.id.welcomeTV);



//        age = getAge(Integer.valueOf(dobYear), Integer.valueOf(dobMonth), Integer.valueOf(dobDay));



        homeIV.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
        });

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


        getUserInfo();



    }

    private void getUserInfo() {
        DocumentReference documentReference = fireStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                nameTV.setText("Name: " + value.getString("name"));
                nickTV.setText("Alias: " + value.getString("nick"));
                ArrayList<String> activeHobbies = (ArrayList<String>) value.get("active_hobbies");
                ArrayList<String> cerebralHobbies = (ArrayList<String>) value.get("creative_hobbies");
                ArrayList<String> creativeHobbies = (ArrayList<String>) value.get("cerebral_hobbies");
                setTextViewValues(activeHobbies, activeHobbiesTV);
                setTextViewValues(cerebralHobbies, cerebralHobbiesTV);
                setTextViewValues(creativeHobbies, creativeHobbiesTV);
                heightTV.setText("Height: " + value.getString("height"));
                eyeColorTV.setText("Eye color: " + value.getString("eye_color"));
                interestTV.setText("Interest: " + value.getString("interest"));
                hairColorTV.setText("Hair color: " + value.getString("hair_color"));
                hairLengthTV.setText("Hair length: " + value.getString("hair_length"));
                zodiacTV.setText("Zodiac: " + value.getString("zodiac"));
                aboutTV.setText("About me:\n" + value.getString("aboutUser"));
                String city = value.getString("city");
                String country = value.getString("country");
                locationTV.setText(city + ", " + country);
                welcomeTV.setText("Welcome back, " + value.getString("name") + "!");
                String dobDay = value.getString("dobDay");
                String dobMonth = value.getString("dobMonth");
                String dobYear = value.getString("dobYear");
                ageTV.setText("Age: " +   getAge(Integer.valueOf(dobYear), Integer.valueOf(dobMonth), Integer.valueOf(dobDay)));

            }
        });

    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public void setTextViewValues(ArrayList<String> value, TextView text){
        // variable that will store all array list values
        String output = "";

        for (int i = 0; i < value.size(); i++){
            // append values to output string
            if(i == 0){
                output = value.get(i);
            } else
                output = output + ", " + value.get(i);
        }
        text.setText(output);
    }


}