package com.alhikmah.ciprb;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class HouseHoldMemberDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    EditText edittext_date_of_birth, edittext_current_age, editText_members_name;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    Spinner spinner_occupasion, spinner_marital_status,
            spinner_sex, spinner_realation_with_hh, spinner_smoking,
            spinner_buttle_nut, spinner_swiming, spinner_how_many_injury_last_six, spinner_injury_last_six;
    PrefsValues prefsValues;

    LinearLayout linerar_how_injury;
    int count = 0;
    Button button_cancel, button_next;
    int member_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household_member_details);

        prefsValues = new PrefsValues(this);

        edittext_date_of_birth = (EditText) findViewById(R.id.edittext_date_of_birth);
        edittext_current_age = (EditText) findViewById(R.id.edittext_current_age);
        editText_members_name = (EditText) findViewById(R.id.editText_members_name);

        spinner_occupasion = (Spinner) findViewById(R.id.spinner_occupasion);
        spinner_marital_status = (Spinner) findViewById(R.id.spinner_marital_status);
        spinner_sex = (Spinner) findViewById(R.id.spinner_sex);
        spinner_realation_with_hh = (Spinner) findViewById(R.id.spinner_realation_with_hh);

        spinner_smoking = (Spinner) findViewById(R.id.spinner_smoking);
        spinner_buttle_nut = (Spinner) findViewById(R.id.spinner_buttle_nut);
        spinner_swiming = (Spinner) findViewById(R.id.spinner_swiming);

        spinner_injury_last_six = (Spinner) findViewById(R.id.spinner_injury_last_six);
        spinner_how_many_injury_last_six = (Spinner) findViewById(R.id.spinner_how_many_injury_last_six);

        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_next = (Button) findViewById(R.id.button_next);
        button_cancel.setOnClickListener(this);
        button_next.setOnClickListener(this);


        linerar_how_injury = (LinearLayout) findViewById(R.id.linerar_how_injury);
        spinner_injury_last_six.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    linerar_how_injury.setVisibility(View.VISIBLE);

                } else {

                    linerar_how_injury.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                convertTwodates(myCalendar);
            }
        };

        edittext_date_of_birth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(HouseHoldMemberDetailsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });


        member_no = prefsValues.getMembersNo();


    }

    private void updateLabel() {

        String myFormat = "dd/mm/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext_date_of_birth.setText(sdf.format(myCalendar.getTime()));
    }

    private void halalResponse() {

        // here I use asyncronous http client library
        // post with no parameters

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("", new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }
        });

    }

    void convertTwodates(Calendar calendar) {


        try {

            //Calendar c = Calendar.getInstance();
            //SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
            // Datetime = dateformat.format(calendar.getTime());
            // String toyBornTime = "2014-06-18 12:56:50";

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd-MM-yyyy hh:mm:ss aa");

            String toyBornTime = dateFormat.format(calendar.getTime());
            Date oldDate = dateFormat.parse(toyBornTime);
            System.out.println(oldDate);

            Date currentDate = new Date();

            long diff = currentDate.getTime() - oldDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            long month = days / 30;
            long year = month / 12;

            if (oldDate.before(currentDate)) {

                Log.e("oldDate", "is previous date");
                Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
                        + " hours: " + hours + " days: " + days + "month:" + month + "year:" + year);

            }

            // Log.e("toyBornTime", "" + toyBornTime);

        } catch (java.text.ParseException e) {

            e.printStackTrace();
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (v == button_next) {

            if (count <= member_no) {


            }

        } else {


        }
    }
}