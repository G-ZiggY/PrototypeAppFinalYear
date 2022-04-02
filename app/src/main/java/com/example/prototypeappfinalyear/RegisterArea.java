package com.example.prototypeappfinalyear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.vikktorn.picker.City;
import com.vikktorn.picker.CityPicker;
import com.vikktorn.picker.Country;
import com.vikktorn.picker.CountryPicker;
import com.vikktorn.picker.State;
import com.vikktorn.picker.StatePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RegisterArea extends AppCompatActivity {

    public static int countryID, stateID;
    // pickers for location
    private CountryPicker countryPicker;
    private StatePicker statePicker;
    private CityPicker cityPicker;
    // arrays of state object
    public static List<State> stateObject;
    // arrays of city object
    public static List<City> cityObject;

    TextInputLayout countryInputL, stateInputL, cityInputL;
    AutoCompleteTextView countryInput, nationalityInput, stateInput, cityInput;
    Button submitBtn;
    String countryName = "", stateName = "", cityName = "", nationality, height, occupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove action bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_area);

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
        Integer dobDay = intent.getIntExtra("dobDay", 2);
        Integer dobMonth = intent.getIntExtra("dobMonth", 1);
        Integer dobYear = intent.getIntExtra("dobYear", 1977);

        // nationality stuff
        nationalityInput = findViewById(R.id.nationalityTV);
        ArrayAdapter<String> nationalityAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, getResources().getStringArray(R.array.nationality));
        nationalityInput.setAdapter(nationalityAdapter);
        nationalityInput.setOnItemClickListener((parent, view, position, id) -> {
            nationality = (String) parent.getItemAtPosition(position);
            nationalityInput.setHint(nationality);
        });

        // initialize view
        initView();
        // get state from assets JSON
        try {
            getStateJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // get City from assets JSON
        try {
            getCityJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // initialize country picker
        countryPicker = new CountryPicker.Builder().with(this).listener(this::onSelectCountry).build();

        // initialize listeners
        setListener();
        setCountryListener();
        setCityListener();



        // submit button listener
        submitBtn.setOnClickListener(v -> {
            // fetch all user selected details and bring into another activity to select hobbies


            if ( countryName.isEmpty() || stateName.isEmpty() || cityName.isEmpty() || nationality.isEmpty()){
                Toast.makeText(getApplicationContext(), "You need to select all inputs", Toast.LENGTH_LONG).show();
            } else {
                // bring user into RegisterHobby activity
                Intent i = new Intent(getApplicationContext(), RegisterAppearance.class);
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
                i.putExtra("country", countryName);
                i.putExtra("state", stateName);
                i.putExtra("city", cityName);
                i.putExtra("nationality", nationality);
                startActivity(i);
            }

        });
    }
    // INIT VIEWS

    public void initView(){
        //Buttons
        stateInput = findViewById(R.id.stateInput);
        stateInputL = findViewById(R.id.stateInputL);
        //set state picker invisible
        stateInput.setVisibility(View.INVISIBLE);
        stateInputL.setVisibility(View.INVISIBLE);
        countryInput = findViewById(R.id.countryInput);
        cityInput = findViewById(R.id.cityInput);
        countryInputL = findViewById(R.id.countryInputL);
        cityInputL = findViewById(R.id.cityInputL);
        // set city picker invisible
        cityInput.setVisibility(View.INVISIBLE);
        cityInputL.setVisibility(View.INVISIBLE);
        // submit button
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setVisibility(View.INVISIBLE);
        // Text Views
//        countryName = (TextView) findViewById(R.id.countryNameTextView);
//        countryCode = (TextView) findViewById(R.id.countryCodeTextView);
//        countryPhoneCode = (TextView) findViewById(R.id.countryDialCodeTextView);
//        countryCurrency = (TextView) findViewById(R.id.countryCurrencyTextView);
//        stateNameTextView = (TextView) findViewById(R.id.state_name);
//        //set state name text view invisible
//        stateNameTextView.setVisibility(View.INVISIBLE);
//        cityName = (TextView) findViewById(R.id.city_name);
//        //set state name text view invisible
//        cityName.setVisibility(View.INVISIBLE);
//
//        // ImageView
//        flagImage = (ImageView) findViewById(R.id.flag_image);

        // initiate state object, parser, and arrays
        stateObject = new ArrayList<>();
        cityObject = new ArrayList<>();
    }

    // SET STATE LISTENER
    private void setListener() {
        stateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statePicker.showDialog(getSupportFragmentManager());
            }
        });
    }
    //SET COUNTRY LISTENER
    private void setCountryListener() {
        countryInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryPicker.showDialog(getSupportFragmentManager());
            }
        });
    }
    //SET CITY LISTENER
    private void setCityListener() {
        cityInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityPicker.showDialog(getSupportFragmentManager());
            }
        });
    }
    // ON SELECTED COUNTRY ADD STATES TO PICKER
    public void onSelectCountry(Country country) {
        // get country name and country ID
        countryName = country.getName();
        countryID = country.getCountryId();
        StatePicker.equalStateObject.clear();
        CityPicker.equalCityObject.clear();

        //set state name text and state pick button visible
        stateInput.setVisibility(View.VISIBLE);
        stateInputL.setVisibility(View.VISIBLE);
        stateInputL.getEditText().setText("Region");
        cityInputL.getEditText().setText("City");
        countryInput.setText(countryName);
        countryInputL.getEditText().setText(countryName);
        // set text on main view
//        countryCode.setText("Country code: " + country.getCode());
//        countryPhoneCode.setText("Country dial code: " + country.getDialCode());
//        countryCurrency.setText("Country currency: " + country.getCurrency());
//        flagImage.setBackgroundResource(country.getFlag());


        // GET STATES OF SELECTED COUNTRY
        for(int i = 0; i < stateObject.size(); i++) {
            // init state picker
            statePicker = new StatePicker.Builder().with(this).listener(this::onSelectState).build();
            State stateData = new State();
            if (stateObject.get(i).getCountryId() == countryID) {

                stateData.setStateId(stateObject.get(i).getStateId());
                stateData.setStateName(stateObject.get(i).getStateName());
                stateData.setCountryId(stateObject.get(i).getCountryId());
                stateData.setFlag(country.getFlag());
                StatePicker.equalStateObject.add(stateData);
            }
        }
    }
    // ON SELECTED STATE ADD CITY TO PICKER
    public void onSelectState(State state) {
        cityInput.setVisibility(View.VISIBLE);
        cityInputL.setVisibility(View.VISIBLE);
        cityInput.setText("City");
        CityPicker.equalCityObject.clear();

        stateName = state.getStateName();
        stateInput.setText(stateName);
        stateInputL.getEditText().setText(stateName);
        stateID = state.getStateId();



        for(int i = 0; i < cityObject.size(); i++) {
            cityPicker = new CityPicker.Builder().with(this).listener(this::onSelectCity).build();
            City cityData = new City();
            if (cityObject.get(i).getStateId() == stateID) {
                cityData.setCityId(cityObject.get(i).getCityId());
                cityData.setCityName(cityObject.get(i).getCityName());
                cityData.setStateId(cityObject.get(i).getStateId());

                CityPicker.equalCityObject.add(cityData);
            }
        }
    }
    // ON SELECTED CITY
    public void onSelectCity(City city) {

        cityName = city.getCityName();
        cityInput.setText(cityName);
//        cityInputL.getEditText().setText(cityName);
        submitBtn.setVisibility(View.VISIBLE);
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    // GET STATE FROM ASSETS JSON
    public void getStateJson() throws JSONException {
        String json = null;
        try {
            InputStream inputStream = getAssets().open("states.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }


        JSONObject jsonObject = new JSONObject(json);
        JSONArray events = jsonObject.getJSONArray("states");
        for (int j = 0; j < events.length(); j++) {
            JSONObject cit = events.getJSONObject(j);
            State stateData = new State();

            stateData.setStateId(Integer.parseInt(cit.getString("id")));
            stateData.setStateName(cit.getString("name"));
            stateData.setCountryId(Integer.parseInt(cit.getString("country_id")));
            stateObject.add(stateData);
        }
    }
    // GET CITY FROM ASSETS JSON
    public void getCityJson() throws JSONException {
        String json = null;
        try {
            InputStream inputStream = getAssets().open("cities.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }


        JSONObject jsonObject = new JSONObject(json);
        JSONArray events = jsonObject.getJSONArray("cities");
        for (int j = 0; j < events.length(); j++) {
            JSONObject cit = events.getJSONObject(j);
            City cityData = new City();

            cityData.setCityId(Integer.parseInt(cit.getString("id")));
            cityData.setCityName(cit.getString("name"));
            cityData.setStateId(Integer.parseInt(cit.getString("state_id")));
            cityObject.add(cityData);
        }
    }
}