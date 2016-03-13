package com.alhikmah.ciprb;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HouseHoldMemberDetailsActivity extends AppCompatActivity {

    EditText date_of_birth, edittext_current_age;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household_member_details);

        date_of_birth = (EditText) findViewById(R.id.edittext_date_o_birth);
        edittext_current_age = (EditText) findViewById(R.id.edittext_current_age);

        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();  convertTwodates(myCalendar);
            }

        };

        date_of_birth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(HouseHoldMemberDetailsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });



    }

    private void updateLabel() {

        String myFormat = "dd/mm/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date_of_birth.setText(sdf.format(myCalendar.getTime()));
    }

    void convertTwodates(Calendar calendar) {


        try {

            //Calendar c = Calendar.getInstance();
            //SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
           // Datetime = dateformat.format(calendar.getTime());
            // String toyBornTime = "2014-06-18 12:56:50";

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd-MM-yyyy hh:mm:ss aa");

            String toyBornTime= dateFormat.format(calendar.getTime());
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

}