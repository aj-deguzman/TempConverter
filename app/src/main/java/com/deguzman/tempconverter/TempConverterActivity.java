package com.deguzman.tempconverter;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class TempConverterActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    //Instance Variables
    private EditText fahrenheitInput;
    private TextView celsiusConverted;

    //Create String placeholder
    private String celValue = celsiusConverted.getText().toString();

    //Define shared preference object
    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);

        //Get reference to widgets
        fahrenheitInput = (EditText) findViewById(R.id.fahrenheitInput);
        celsiusConverted = (TextView) findViewById(R.id.celsiusConverted);

        //Set the listeners
        fahrenheitInput.setOnEditorActionListener(this);

        //Get SharedPreferences object
        savedValues = getSharedPreferences("savedValues", MODE_PRIVATE);
    }

    public void calculateAndDisplay(){
        //Get user input for fahrenheitInput
        String userInputString = fahrenheitInput.getText().toString();

        //Local variable to hold user input as float
        float userInputNum;

        //Calculate celsius value based on user input
        if(userInputString.equals("")){
            userInputNum = 0;
        } else {
            userInputNum = (Float.parseFloat(userInputString)-32)*5/9;
        }

        //Display and format
        NumberFormat temp = NumberFormat.getNumberInstance();
        celsiusConverted.setText(temp.format(userInputNum));
    }



    @Override
    protected void onPause() {
        //Save instance variables
        Editor editor = savedValues.edit();
        editor.putString("celValue", celValue);
        editor.commit();

        super.onPause();
    }

    @Override
    protected void onResume() {
        //Get instance variables
        celValue = savedValues.getString("celValue", "");

        //Set celsius to widget
        celsiusConverted.setText(celValue);

        super.onResume();
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            calculateAndDisplay();
        }
        return false;
    }

}
