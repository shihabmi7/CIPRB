package com.alhikmah.ciprb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HouseHoldInformationActivity extends AppCompatActivity implements View.OnClickListener {


    Spinner spinner_location, spinner_city_corp;
    TextView textView_interview_starting_time;
    String Datetime;
    NumberPicker numberPicker;
    Button button_cancel, button_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_hold_information);

        spinner_location = (Spinner) findViewById(R.id.spinner_location);
        spinner_city_corp = (Spinner) findViewById(R.id.spinner_city_corp);

        textView_interview_starting_time = (TextView) findViewById(R.id.textView_interview_starting_time);

        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_next = (Button) findViewById(R.id.button_next);

        numberPicker = (NumberPicker) findViewById(R.id.numberpicker_death_number);
        numberPicker.setMaxValue(15);
        numberPicker.setMinValue(0);

        numberPicker.setOnValueChangedListener(new NumberPicker.
                OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {

                Toast.makeText(getApplicationContext(), "" + numberPicker.getValue(), Toast.LENGTH_LONG).show();


            }
        });

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa"); //
        Datetime = dateformat.format(c.getTime());
        textView_interview_starting_time.setText("Date: " + Datetime);
        String text = spinner_location.getSelectedItem().toString();


    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (v == button_cancel) {

        } else if (v == button_next) {


        }

    }


}
