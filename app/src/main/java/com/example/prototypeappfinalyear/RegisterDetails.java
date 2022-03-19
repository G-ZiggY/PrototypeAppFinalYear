package com.example.prototypeappfinalyear;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterDetails extends AppCompatActivity {

    TextInputLayout nameEdit, surnameEdit, nickEdit;
    EditText userDoB;
    DatePickerDialog datePickerDialog;
    Button submitBtn;
    String name, surname, nick, zodiac;
    Integer pathN;

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



        nameEdit = findViewById(R.id.nameEdit);
        surnameEdit = findViewById(R.id.surnameEdit);
        nickEdit = findViewById(R.id.nickEdit);
        userDoB = findViewById(R.id.userDoB);
        submitBtn = findViewById(R.id.submitBtn);

        userDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(RegisterDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        userDoB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        zodiac_sign(dayOfMonth, month + 1);
                        find_path(dayOfMonth, month + 1, year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEdit == null || surnameEdit == null || nickEdit == null || userDoB == null){
                    Toast.makeText(getApplicationContext(), "You need to complete all form fields!", Toast.LENGTH_LONG).show();
                } else {
                    name = nameEdit.getEditText().getText().toString().trim();
                    surname = surnameEdit.getEditText().getText().toString().trim();
                    nick = nickEdit.getEditText().getText().toString().trim();
                    Intent i = new Intent(getApplicationContext(), RegisterDetailsContinued.class);
                    i.putExtra("email", email);
                    i.putExtra("password", password);
                    i.putExtra("name", name);
                    i.putExtra("surname", surname);
                    i.putExtra("nick", nick);
                    i.putExtra("dob", userDoB.getText().toString());
                    i.putExtra("zodiac", zodiac);
                    i.putExtra("pathN", pathN.toString());
                    startActivity(i);
                }
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