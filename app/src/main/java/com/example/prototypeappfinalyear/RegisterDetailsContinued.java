package com.example.prototypeappfinalyear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterDetailsContinued extends AppCompatActivity {

    AutoCompleteTextView genderInput, interestInput, countryInput, nationalityInput, heightInput, occupationInput;
    Button submitBtn;
    String gender, interest, country, nationality, height, occupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove action bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_details_continued);

        // getting extras from previous activity
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        String name = intent.getStringExtra("name");
        String surname = intent.getStringExtra("surname");
        String nick = intent.getStringExtra("nick");
        String dob = intent.getStringExtra("dob");
        String zodiac = intent.getStringExtra("zodiac");
        String pathN = intent.getStringExtra("pathN");

        // gender stuff
        genderInput = findViewById(R.id.genderInput);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.gender));
        genderInput.setAdapter(genderAdapter);
        genderInput.setOnItemClickListener((parent, view, position, id) -> {
            /*
            do something when clicked,
            e.g. save that variable and then
            attach that to users profile attribute once finished registration
             */
            gender = (String) parent.getItemAtPosition(position);
        });

        // interest stuff
        interestInput = findViewById(R.id.interestInput);
        ArrayAdapter<String> interestAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.interest));
        interestInput.setAdapter(interestAdapter);
        interestInput.setOnItemClickListener((parent, view, position, id) -> {
            /*
            do something when clicked,
            e.g. save that variable and then
            attach that to users profile attribute once finished registration
             */
            interest = (String) parent.getItemAtPosition(position);
        });

        // country stuff
        countryInput = findViewById(R.id.countryInput);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.country));
        countryInput.setAdapter(countryAdapter);
        countryInput.setOnItemClickListener((parent, view, position, id) -> {
            /*
            do something when clicked,
            e.g. save that variable and then
            attach that to users profile attribute once finished registration
             */
            country = (String) parent.getItemAtPosition(position);
        });

        // nationality stuff
        nationalityInput = findViewById(R.id.nationalityInput);
        ArrayAdapter<String> nationalityAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.nationality));
        nationalityInput.setAdapter(nationalityAdapter);
        nationalityInput.setOnItemClickListener((parent, view, position, id) -> {
            /*
            do something when clicked,
            e.g. save that variable and then
            attach that to users profile attribute once finished registration
             */
            nationality = (String) parent.getItemAtPosition(position);
        });

        // height stuff
        heightInput = findViewById(R.id.heightInput);
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.height));
        heightInput.setAdapter(heightAdapter);
        heightInput.setOnItemClickListener((parent, view, position, id) -> {
            /*
            do something when clicked,
            e.g. save that variable and then
            attach that to users profile attribute once finished registration
             */
            height = (String) parent.getItemAtPosition(position);
        });

        // occupation stuff
        occupationInput = findViewById(R.id.occupationInput);
        ArrayAdapter<String> occupationAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.occupation));
        occupationInput.setAdapter(occupationAdapter);
        occupationInput.setOnItemClickListener((parent, view, position, id) -> {
            /*
            do something when clicked,
            e.g. save that variable and then
            attach that to users profile attribute once finished registration
             */
            occupation = (String) parent.getItemAtPosition(position);
        });

        // submit button listener
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(v -> {
            // fetch all user selected details and bring into another activity to select hobbies


            // check if user has selected inputs
            if (gender.isEmpty() ||
                    interest.isEmpty() || country.isEmpty() ||
                    nationality.isEmpty() || height.isEmpty() || occupation.isEmpty()){
                Toast.makeText(getApplicationContext(), "You need to select all inputs", Toast.LENGTH_LONG).show();
            } else {
                // bring user into RegisterHobby activity
                Intent i = new Intent(getApplicationContext(), RegisterHobby.class);
                i.putExtra("email", email);
                i.putExtra("password", password);
                i.putExtra("name", name);
                i.putExtra("surname", surname);
                i.putExtra("nick", nick);
                i.putExtra("dob", dob);
                i.putExtra("zodiac", zodiac);
                i.putExtra("pathN", pathN);
                i.putExtra("gender", gender);
                i.putExtra("interest", interest);
                i.putExtra("country", country);
                i.putExtra("nationality", nationality);
                i.putExtra("height", height);
                i.putExtra("occupation", occupation);
                startActivity(i);
            }

        });
    }
}