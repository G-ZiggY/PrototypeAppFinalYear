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

import java.util.Calendar;

public class RegisterDetails extends AppCompatActivity {

    TextInputLayout nameEdit, surnameEdit, nickEdit, dateFormat;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    Button submitBtn;
    String name, surname, nick, userDOB;


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

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        nameEdit = findViewById(R.id.nameEdit);
        surnameEdit = findViewById(R.id.surnameEdit);
        nickEdit = findViewById(R.id.nickEdit);
        dateFormat = findViewById(R.id.dateFormat);
        submitBtn = findViewById(R.id.submitBtn);


        dateFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 DatePickerDialog datePickerDialog = new DatePickerDialog
                         (getApplicationContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);
                 datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                 datePickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                userDOB = date;
            }
        };

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEdit == null || surnameEdit == null || nickEdit == null || dateFormat == null){
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
                    i.putExtra("dob", userDOB);
                    startActivity(i);
                }
            }
        });
    }


//    private String getTodaysDate() {
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        month += 1;
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        return makeDateString(day, month, year);
//    }
//
//    private String makeDateString(int day, int month, int year) {
//        return getMonthFormat(month) + " " + day + " " + year;
//    }
//
//    private String getMonthFormat(int month) {
//        if (month == 1)
//            return "JAN";
//        if (month == 2)
//            return "FEB";
//        if (month == 3)
//            return "MAR";
//        if (month == 4)
//            return "ARP";
//        if (month == 5)
//            return "MAY";
//        if (month == 6)
//            return "JUN";
//        if (month == 7)
//            return "JUL";
//        if (month == 8)
//            return "AUG";
//        if (month == 9)
//            return "SEP";
//        if (month == 10)
//            return "OCT";
//        if (month == 11)
//            return "NOV";
//        if (month == 12)
//            return "DEC";
//
//        return "JAN";
//    }
//
//    private void initDatePicker() {
//        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                    month += 1;
//                    String date = makeDateString(dayOfMonth, month, year);
//                    dateBtn.setText(date);
//                }
//            };
//
//            Calendar calendar = Calendar.getInstance();
//            int year = calendar.get(Calendar.YEAR);
//            int month = calendar.get(Calendar.MONTH);
//            int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//            int style = AlertDialog.THEME_HOLO_DARK;
//
//            datePickerDialog = new DatePickerDialog(getApplicationContext(), style, dateSetListener, year, month, day);
//    }
//
//    public void openDatePicker(View view) {
//        datePickerDialog.show();
//    }
}