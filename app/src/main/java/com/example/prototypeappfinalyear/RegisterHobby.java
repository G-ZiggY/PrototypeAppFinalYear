package com.example.prototypeappfinalyear;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RegisterHobby extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextInputEditText aboutInput;
    Button submitBtn;
    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DocumentReference documentReference;
    String email, password, name, surname, nick, dob, zodiac,
            pathN, gender, interest, country, state, city, nationality,
            height, bodyType, hairColor, hairLength, eyeColor, skinColor, aboutUser;
    Integer dobDay, dobMonth, dobYear;


    AutoCompleteTextView activeInput, cerebralInput, creativeInput;
    boolean[] selectedActiveHobby, selectedCerebralHobby, selectedCreativeHobby;
    ArrayList<Integer> activeList = new ArrayList<>();
    ArrayList<Integer> cerebralList = new ArrayList<>();
    ArrayList<Integer> creativeList = new ArrayList<>();
    ArrayList<String> activeHobbyList = new ArrayList<>();
    ArrayList<String> cerebralHobbyList = new ArrayList<>();
    ArrayList<String> creativeHobbyList = new ArrayList<>();
    String[] activeArray = {"Aerobics", "Basketball", "Dancing", "Football", "Golf", "Gym",
    "Hiking", "Martial arts", "Rock climbing", "Running", "Skating", "Snooker", "Swimming", "Tennis", "Yoga", "Other"};
    String[] cerebralArray = {"Card games", "Mind games", "Reading", "Research", "Puzzles", "Sudoku", "Other"};
    String[] creativeArray = {"Cooking", "Designing", "Drawing", "Film making", "Painting", "Singing", "Social media", "Writing", "Other"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove action bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_hobby);

        submitBtn = findViewById(R.id.submitBtn);

        // extract extras from previous activity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        name = intent.getStringExtra("name");
        nick = intent.getStringExtra("nick");
        zodiac = intent.getStringExtra("zodiac");
        pathN = intent.getStringExtra("pathN");
        gender = intent.getStringExtra("gender");
        interest = intent.getStringExtra("interest");
        nationality = intent.getStringExtra("nationality");
        country = intent.getStringExtra("country");
        state = intent.getStringExtra("state");
        city = intent.getStringExtra("city");
        height = intent.getStringExtra("height");
        bodyType = intent.getStringExtra("body_type");
        hairColor = intent.getStringExtra("hair_color");
        hairLength = intent.getStringExtra("hair_length");
        eyeColor = intent.getStringExtra("eye_color");
        skinColor = intent.getStringExtra("skin_color");
        dobDay = intent.getIntExtra("dobDay", 0);
        dobMonth = intent.getIntExtra("dobMonth", 0);
        dobYear = intent.getIntExtra("dobYear", 0);

        aboutInput = findViewById(R.id.aboutInput);

        // assign variable
        activeInput = findViewById(R.id.activeInput);
        cerebralInput = findViewById(R.id.cerebralInput);
        creativeInput = findViewById(R.id.creativeInput);

        // initialize selected language array
        selectedActiveHobby = new boolean[activeArray.length];
        selectedCerebralHobby = new boolean[cerebralArray.length];
        selectedCreativeHobby = new boolean[creativeArray.length];

        // ACTIVE SELECTION SETUP
        activeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterHobby.this);

                // set title
                builder.setTitle("Select Active Hobbies");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(activeArray, selectedActiveHobby, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            activeList.add(i);
                            // Sort array list
                            Collections.sort(activeList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            activeList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < activeList.size(); j++) {
                            // concat array value
                            stringBuilder.append(activeArray[activeList.get(j)]);
                            activeHobbyList.add(activeArray[activeList.get(j)]);
                            // check condition
                            if (j != activeList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        activeInput.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedActiveHobby.length; j++) {
                            // remove all selection
                            selectedActiveHobby[j] = false;
                            // clear language list
                            activeList.clear();
                            // clear text view value
                            activeInput.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });



        // CEREBRAL SELECTION SETUP
        cerebralInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterHobby.this);

                // set title
                builder.setTitle("Select Cerebral Hobbies");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(cerebralArray, selectedCerebralHobby, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            cerebralList.add(i);
                            // Sort array list
                            Collections.sort(cerebralList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            cerebralList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < cerebralList.size(); j++) {
                            // concat array value
                            stringBuilder.append(cerebralArray[cerebralList.get(j)]);
                            cerebralHobbyList.add(cerebralArray[cerebralList.get(j)]);
                            // check condition
                            if (j != cerebralList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        cerebralInput.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedCerebralHobby.length; j++) {
                            // remove all selection
                            selectedCerebralHobby[j] = false;
                            // clear language list
                            cerebralList.clear();
                            // clear text view value
                            cerebralInput.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });



        // CREATIVE SELECTION SETUP
        creativeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterHobby.this);

                // set title
                builder.setTitle("Select Active Hobbies");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(creativeArray, selectedCreativeHobby, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            creativeList.add(i);
                            // Sort array list
                            Collections.sort(creativeList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            creativeList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < creativeList.size(); j++) {
                            // concat array value
                            stringBuilder.append(creativeArray[creativeList.get(j)]);
                            creativeHobbyList.add(creativeArray[creativeList.get(j)]);
                            // check condition
                            if (j != creativeList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        creativeInput.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedCreativeHobby.length; j++) {
                            // remove all selection
                            selectedCreativeHobby[j] = false;
                            // clear language list
                            creativeList.clear();
                            // clear text view value
                            creativeInput.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
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
                    aboutUser = aboutInput.getEditableText().toString().trim();
                    String userID = firebaseAuth.getCurrentUser().getUid();
                    documentReference = fireStore.collection("users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("email", email);
                    user.put("password", password);
                    user.put("name", name);
                    user.put("nick", nick);
                    user.put("zodiac", zodiac);
                    user.put("pathN", pathN);
                    user.put("gender", gender);
                    user.put("interest", interest);
                    user.put("country", country);
                    user.put("state", state);
                    user.put("city", city);
                    user.put("dobDay", dobDay);
                    user.put("dobMonth", dobMonth);
                    user.put("dobYear", dobYear);
                    user.put("active_hobbies", activeHobbyList);
                    user.put("cerebral_hobbies", cerebralHobbyList);
                    user.put("creative_hobbies", creativeHobbyList);
                    user.put("aboutUser", aboutUser);
                    user.put("height", height);
                    user.put("body_type", bodyType);
                    user.put("hair_color", hairColor);
                    user.put("hair_length", hairLength);
                    user.put("eye_color", eyeColor);
                    user.put("skin_color", skinColor);

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