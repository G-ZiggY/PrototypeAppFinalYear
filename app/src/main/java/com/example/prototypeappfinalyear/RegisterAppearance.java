package com.example.prototypeappfinalyear;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterAppearance extends AppCompatActivity {

AutoCompleteTextView heightInput, bodyTypeInput, hairColorInput, hairLengthInput, eyeInput, skinInput;
String height, bodyType, hairColor, hairLength, eyeColor, skinColor;
Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove action bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_appearance);

        submitBtn = findViewById(R.id.submitBtn);

        // getting extras from previous activity
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        String name = intent.getStringExtra("name");
        String nick = intent.getStringExtra("nick");
        String gender = intent.getStringExtra("gender");
        String interest = intent.getStringExtra("interest");
        String occupation = intent.getStringExtra("occupation");
        String zodiac = intent.getStringExtra("zodiac");
        String pathN = intent.getStringExtra("pathN");
        String dobDay = intent.getStringExtra("dobDay");
        String dobMonth = intent.getStringExtra("dobMonth");
        String dobYear = intent.getStringExtra("dobYear");
        String nationality = intent.getStringExtra("nationality");
        String country = intent.getStringExtra("country");
        String state = intent.getStringExtra("state");
        String city = intent.getStringExtra("city");

        // height adapter
        heightInput = findViewById(R.id.heightInput);
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.height));
        heightInput.setAdapter(heightAdapter);
        heightInput.setOnItemClickListener((parent, view, position, id) -> {
            height = (String) parent.getItemAtPosition(position);
            heightInput.setHint(height);
        });

        // body type adapter
        bodyTypeInput = findViewById(R.id.bodyInput);
        ArrayAdapter<String> bodyAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.body_type));
        bodyTypeInput.setAdapter(bodyAdapter);
        bodyTypeInput.setOnItemClickListener((parent, view, position, id) -> {
            bodyType = (String) parent.getItemAtPosition(position);
            bodyTypeInput.setHint(bodyType);
        });

        // hair color adapter
        hairColorInput = findViewById(R.id.hairColorInput);
        ArrayAdapter<String> hairColorAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.hair_color));
        hairColorInput.setAdapter(hairColorAdapter);
        hairColorInput.setOnItemClickListener((parent, view, position, id) -> {
            hairColor = (String) parent.getItemAtPosition(position);
            hairColorInput.setHint(hairColor);
        });

        // hair length adapter
        hairLengthInput = findViewById(R.id.hairLengthInput);
        ArrayAdapter<String> hairLengthAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.hair_length));
        hairLengthInput.setAdapter(hairLengthAdapter);
        hairLengthInput.setOnItemClickListener((parent, view, position, id) -> {
            hairLength = (String) parent.getItemAtPosition(position);
            hairLengthInput.setHint(hairLength);
        });

        // eye color adapter
        eyeInput = findViewById(R.id.eyeInput);
        ArrayAdapter<String> eyeAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.eye_color));
        eyeInput.setAdapter(eyeAdapter);
        eyeInput.setOnItemClickListener((parent, view, position, id) -> {
            eyeColor = (String) parent.getItemAtPosition(position);
            eyeInput.setHint(eyeColor);
        });

        // skin color adapter
        skinInput = findViewById(R.id.skinInput);
        ArrayAdapter<String> skinAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.skin_color));
        skinInput.setAdapter(skinAdapter);
        skinInput.setOnItemClickListener((parent, view, position, id) -> {
            skinColor = (String) parent.getItemAtPosition(position);
            skinInput.setHint(skinColor);
        });

        submitBtn.setOnClickListener(v -> {

            if ( height.isEmpty() || bodyType.isEmpty() || hairColor.isEmpty() || hairLength.isEmpty()
            || eyeColor.isEmpty() || skinColor.isEmpty()){
                Toast.makeText(getApplicationContext(), "You need to select all inputs", Toast.LENGTH_LONG).show();
            } else {
                // bring user into RegisterHobby activity
                Intent i = new Intent(getApplicationContext(), RegisterHobby.class);
                i.putExtra("email", email);
                i.putExtra("password", password);
                i.putExtra("name", name);
                i.putExtra("nick", nick);
                i.putExtra("zodiac", zodiac);
                i.putExtra("pathN", pathN);
                i.putExtra("dobDay", dobDay);
                i.putExtra("dobMonth", dobMonth);
                i.putExtra("dobYear", dobYear);
                i.putExtra("gender", gender);
                i.putExtra("interest", interest);
                i.putExtra("occupation", occupation);
                i.putExtra("dobDay", dobDay);
                i.putExtra("dobMonth", dobMonth);
                i.putExtra("dobYear", dobYear);
                i.putExtra("country", country);
                i.putExtra("state", state);
                i.putExtra("city", city);
                i.putExtra("nationality", nationality);
                i.putExtra("height", height);
                i.putExtra("body_type", bodyType);
                i.putExtra("hair_color", hairColor);
                i.putExtra("hair_length", hairLength);
                i.putExtra("eye_color", eyeColor);
                i.putExtra("skin_color", skinColor);
                startActivity(i);
            }
        });

    }
}