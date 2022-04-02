package com.example.prototypeappfinalyear;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterDetails extends AppCompatActivity {

//    TextInputLayout nameIL, surnameIL, nickIL, genderIL, interestIL;
    TextInputEditText nameInput, nickInput, birthInput;
    AutoCompleteTextView genderInput, interestInput, occupationInput;
//    EditText birthInput;
    DatePickerDialog datePickerDialog;
    Button submitBtn;
    String name, nick, zodiac, gender, interest, occupation, dob;
    Integer pathN = 0, dobDay = 0, dobMonth = 0, dobYear = 0;

    /*
    TODO: current time - date of birth = age
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove action bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_details);

        // take in email and password from previous activity
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        nameInput = findViewById(R.id.nameInput);
        nickInput = findViewById(R.id.nickInput);
        occupationInput = findViewById(R.id.occupationInput);
        genderInput = findViewById(R.id.genderInput);
        interestInput = findViewById(R.id.interestInput);
        birthInput = findViewById(R.id.birthInput);

        submitBtn = findViewById(R.id.submitBtn);

        // occupation adapter
        ArrayAdapter<String> occupationAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.occupation));
        occupationInput.setAdapter(occupationAdapter);
        occupationInput.setOnItemClickListener((parent, view, position, id) -> {
            occupation = (String) parent.getItemAtPosition(position);
            occupationInput.setHint(occupation);
        });

        // gender adapter
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.gender));
        genderInput.setAdapter(genderAdapter);
        genderInput.setOnItemClickListener((parent, view, position, id) -> {
            gender = (String) parent.getItemAtPosition(position);
            genderInput.setHint(gender);
        });

        // occupation stuff
        ArrayAdapter<String> interestAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.interest));
        interestInput.setAdapter(interestAdapter);
        interestInput.setOnItemClickListener((parent, view, position, id) -> {
            interest = (String) parent.getItemAtPosition(position);
            interestInput.setHint(interest);
        });

//        datePickerDialog = new DatePickerDialog(RegisterDetails.this, (view, year1, month1, dayOfMonth) -> {
//            birthInput.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
//            dobDay = dayOfMonth;
//            dobMonth = month1 +1;
//            dobYear = year1;
//            zodiac_sign(dayOfMonth, month1 + 1);
//            find_path(dayOfMonth, month1 + 1, year1);
//        }, year, month, day);
//        datePickerDialog.show();

        birthInput.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            datePickerDialog = new DatePickerDialog(RegisterDetails.this, (view, year1, month1, dayOfMonth) -> {
                birthInput.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
                dobDay = dayOfMonth;
                dobMonth = month1 +1;
                dobYear = year1;
                zodiac_sign(dayOfMonth, month1 + 1);
                find_path(dayOfMonth, month1 + 1, year1);
                dob = birthInput.getEditableText().toString().trim();
            }, year, month, day);
            datePickerDialog.show();
        });

        submitBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(nameInput.getText()) || TextUtils.isEmpty(occupation) ||
                    TextUtils.isEmpty(nickInput.getText()) || TextUtils.isEmpty(birthInput.getText()) ||
                    TextUtils.isEmpty(gender) || TextUtils.isEmpty(interest)){
                Toast.makeText(getApplicationContext(), "You need to complete all form fields!", Toast.LENGTH_LONG).show();
            } else {
                name = nameInput.getEditableText().toString().trim();
                nick = nickInput.getEditableText().toString().trim();
                occupation = occupationInput.getText().toString().trim();
                gender = genderInput.getText().toString().trim();
                interest = interestInput.getText().toString().trim();
                Intent i = new Intent(getApplicationContext(), RegisterArea.class);
                i.putExtra("email", email);
                i.putExtra("password", password);
                i.putExtra("name", name);
                i.putExtra("nick", nick);
                i.putExtra("gender", gender);
                i.putExtra("interest", interest);
                i.putExtra("occupation", occupation);
                i.putExtra("zodiac", zodiac);
                i.putExtra("pathN", pathN.toString());
                i.putExtra("dobDay", dobDay);
                i.putExtra("dobMonth", dobMonth);
                i.putExtra("dobYear", dobYear);
                startActivity(i);
            }
        });
    }

    void find_path(int day, int month, int year){
        int total = day + month + year;
        List<Integer> arrTotal = new ArrayList<>();
        while (total > 0){
            arrTotal.add(total % 10);
            total /= 10;
        }
        int finalN = 0;
        for (int i = 0; i < arrTotal.size(); i++){
            finalN += arrTotal.get(i);
        }
        List<Integer> arrFinal = new ArrayList<>();
        while (finalN > 0){
            arrFinal.add(finalN % 10);
            finalN /= 10;
        }
        for (int i = 0; i < arrFinal.size(); i++){
            pathN += arrFinal.get(i);
        }
    }

    void zodiac_sign(int day, int month){
        if (month == 12){
            if (day < 22){
                zodiac = "Sagittarius";
            } else
                zodiac = "Capricorn";
        } else if (month == 1){
            if (day < 20){
                zodiac = "Capricorn";
            } else
                zodiac = "Aquarius";
        } else if (month == 2){
            if (day < 19){
                zodiac = "Aquarius";
            } else
                zodiac = "Pisces";
        } else if (month == 3){
            if (day < 21){
                zodiac = "Pisces";
            } else
                zodiac = "Aries";
        } else if (month == 4){
            if (day < 20){
                zodiac = "Aries";
            } else
                zodiac = "Taurus";
        } else if (month == 5){
            if (day < 21){
                zodiac = "Taurus";
            } else
                zodiac = "Gemini";
        } else if (month == 6){
            if (day < 21){
                zodiac = "Gemini";
            } else
                zodiac = "Cancer";
        } else if (month == 7){
            if (day < 23){
                zodiac = "Cancer";
            } else
                zodiac = "Leo";
        } else if (month == 8){
            if (day < 23){
                zodiac = "Leo";
            } else
                zodiac = "Virgo";
        } else if (month == 9){
            if (day < 23){
                zodiac = "Virgo";
            } else
                zodiac = "Libra";
        } else if (month == 10){
            if (day < 23){
                zodiac = "Libra";
            } else
                zodiac = "Scorpio";
        } else if (month == 11){
            if (day < 22){
                zodiac = "Scorpio";
            } else
                zodiac = "Sagittarius";
        }
    }

}