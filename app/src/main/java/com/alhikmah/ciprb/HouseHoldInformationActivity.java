package com.alhikmah.ciprb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HouseHoldInformationActivity extends AppCompatActivity implements View.OnClickListener {


    Spinner spinner_location, spinner_city_corp, spinner_survey_location, spinner_upozila,
            spinner_adjust;
    TextView textView_interview_starting_time, textView_adjustable;
    String Datetime;
    //NumberPicker death_number_picker, member_number_picker;
    Button button_cancel, button_next;
    EditText editText_ward, editText_household_no, editText_moholla, editText_interviewer_code, edittext_death_number, editText_member_number;
    PrefsValues prefsValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_hold_information);

        try {

            prefsValues = new PrefsValues(this);

            spinner_survey_location = (Spinner) findViewById(R.id.spinner_survey_location);
            spinner_adjust = (Spinner) findViewById(R.id.spinner_adjust);
            spinner_upozila = (Spinner) findViewById(R.id.spinner_upozila);

            textView_interview_starting_time = (TextView) findViewById(R.id.textView_interview_starting_time);
            textView_adjustable = (TextView) findViewById(R.id.textView_adjustable);
            editText_ward = (EditText) findViewById(R.id.editText_ward);
            editText_moholla = (EditText) findViewById(R.id.editText_moholla);

            edittext_death_number = (EditText) findViewById(R.id.edittext_death_number);
            editText_member_number = (EditText) findViewById(R.id.editText_member_number);
            editText_interviewer_code = (EditText) findViewById(R.id.editText_interviewer_unique);
            editText_household_no = (EditText) findViewById(R.id.editText_household_no);

            button_cancel = (Button) findViewById(R.id.button_cancel);
            button_next = (Button) findViewById(R.id.button_next);
            button_cancel.setOnClickListener(this);
            button_next.setOnClickListener(this);

            //editText_household_unique_code = (EditText) findViewById(R.id.editText_household_unique_code);
            spinner_survey_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    int array_id;
                    switch (position) {
                        case 0:
                            array_id = R.array.survey_city_corp;
                            textView_adjustable.setText(" সিটি কর্পোরেশন");
                            spinner_upozila.setVisibility(View.GONE);
                            editText_ward.setVisibility(View.VISIBLE);
                            setSpinner(array_id);
                            break;
                        case 1:
                            array_id = R.array.survey_district_town;
                            textView_adjustable.setText("জেলা শহর");
                            spinner_upozila.setVisibility(View.GONE);
                            editText_ward.setVisibility(View.VISIBLE);
                            setSpinner(array_id);
                            break;
                        case 2:
                            array_id = R.array.survey_district_rural;
                            textView_adjustable.setText("জেলা (গ্রাম)");
                            spinner_upozila.setVisibility(View.VISIBLE);
                            editText_ward.setVisibility(View.GONE);
                            setSpinner(array_id);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        /*death_number_picker = (NumberPicker) findViewById(R.id.numberpicker_death_number);
        death_number_picker.setMaxValue(ApplicationData.HOUSE_HOLD_MWMBERS_MAX);
        death_number_picker.setMinValue(ApplicationData.HOUSE_HOLD_MWMBERS_MIN);*/
        /*member_number_picker = (NumberPicker) findViewById(R.id.numberpicker_member_number);
        member_number_picker.setMaxValue(ApplicationData.HOUSE_HOLD_MWMBERS_MAX);
        member_number_picker.setMinValue(1);*/

        /*death_number_picker.setOnValueChangedListener(new NumberPicker.
                OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {

                Toast.makeText(getApplicationContext(), "" + death_number_picker.getValue(), Toast.LENGTH_LONG).show();

            }
        });*/

            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm"); //
            Datetime = dateformat.format(c.getTime());
            textView_interview_starting_time.setText("Date: " + ApplicationData.getCurrentDate());


        } catch (NullPointerException e) {

            Toast.makeText(getApplicationContext(), "" + e.toString(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "" + e.toString(), Toast.LENGTH_LONG).show();
        }


    }


    void setSpinner(int value) {

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(value));
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_adjust.setAdapter(adp);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        try {

            if (v == button_cancel) {

                finish();

            } else if (v == button_next && checkFieldStatus()) {

                switch (spinner_survey_location.getSelectedItemPosition()) {

                    case 0:
                    case 1:

                        ApplicationData.HOUSE_HOLD_UNIQE_ID = "" + ApplicationData.spilitStringFirst(spinner_survey_location.getSelectedItem().toString()) +
                                "" + ApplicationData.spilitStringFirst(spinner_adjust.getSelectedItem().toString()) + "" +
                                editText_ward.getText().toString() + "" + editText_moholla.getText().toString() + "" + editText_household_no.getText().toString();

                        saveToPreference(ApplicationData.HOUSE_HOLD_UNIQE_ID);
                        break;

                    case 2:

                        ApplicationData.HOUSE_HOLD_UNIQE_ID = "" + ApplicationData.spilitStringFirst(spinner_survey_location.getSelectedItem().toString()) +
                                "" + ApplicationData.spilitStringFirst(spinner_adjust.getSelectedItem().toString()) + "" +
                                ApplicationData.spilitStringFirst(spinner_upozila.getSelectedItem().toString()) + ""
                                + editText_moholla.getText().toString() + "" + editText_household_no.getText().toString();

                        saveToPreference(ApplicationData.HOUSE_HOLD_UNIQE_ID);
                        break;
                }

                ApplicationData.gotToNextActivity(this, HouseHoldMemberDetailsActivity.class);
            }

        } catch (NumberFormatException e) {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();

        } catch (NullPointerException e) {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkFieldStatus() {

        if (editText_member_number.getText().length() > 0 && edittext_death_number.getText().length() > 0
                && editText_interviewer_code.getText().length() > 0 &&
                editText_household_no.getText().length() == 2 &&
                editText_ward.getText().length() == 2 && editText_moholla.getText().length() == 2) {

            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Enter All Data Correctly ", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    private void saveToPreference(String value) {
        Toast.makeText(getApplicationContext(), ApplicationData.HOUSE_HOLD_UNIQE_ID, Toast.LENGTH_LONG).show();
        prefsValues.setMembers_no(Integer.parseInt(editText_member_number.getText().toString()));
        prefsValues.setMembers_died_no(Integer.parseInt(edittext_death_number.getText().toString()));
        prefsValues.setinterviewer_code(editText_interviewer_code.getText().toString());
        prefsValues.sethouse_hold_no(editText_household_no.getText().toString());
        prefsValues.setHouseUnique_id(value);
        prefsValues.setSerial(ApplicationData.SERIAL);
        prefsValues.setDeathSerial(ApplicationData.SERIAL_DEATH);


    }




}
