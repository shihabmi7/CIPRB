package com.alhikmah.ciprb;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.util.Calendar;

public class DeathConfirmationActivity extends AppCompatActivity {

    EditText date_of_birth, edittext_current_age;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death_confirmation);

    }

}


