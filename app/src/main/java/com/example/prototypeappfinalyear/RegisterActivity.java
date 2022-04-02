package com.example.prototypeappfinalyear;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etRegEmail, etRegPassword, passConfirm;
    TextView tvLoginHere;
    Button btnRegister;
    FirebaseFirestore fireStore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove action bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPass);
        passConfirm = findViewById(R.id.etRegPassConfirm);
        tvLoginHere = findViewById(R.id.tvLoginHere);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(view -> continueReg());

        tvLoginHere.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void continueReg(){
        String email = etRegEmail.getText().toString();
        String password = etRegPassword.getText().toString();
        String confirmPass = passConfirm.getText().toString();

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etRegEmail.setError("Email is not valid");
            etRegEmail.requestFocus();
        } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPass)){
            etRegPassword.setError("Password fields cannot be empty");
            etRegPassword.requestFocus();
        } else if (!password.equals(confirmPass)){
            Toast.makeText(getApplicationContext(), "Passwords do not match! Please Try Again!", Toast.LENGTH_SHORT).show();
        } else if (password.length() <= 7){
            Toast.makeText(getApplicationContext(), "Password has to have 8 characters at least",  Toast.LENGTH_SHORT).show();
            etRegPassword.requestFocus();
        } else {
            Intent i = new Intent(getApplicationContext(), RegisterDetails.class);
            i.putExtra("email", email);
            i.putExtra("password", password);
            startActivity(i);
        }
    }


}
