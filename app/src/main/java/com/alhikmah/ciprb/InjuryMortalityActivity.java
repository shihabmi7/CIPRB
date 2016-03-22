package com.alhikmah.ciprb;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alhikmah.ciprb.localdb.CiprbDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class InjuryMortalityActivity extends AppCompatActivity {

    EditText edittext_date_of_injury, edittext_time_of_injury, edittext_death_date_of_person;
    Calendar myCalendar = null;
    DatePickerDialog.OnDateSetListener date, death_date;
    TimePickerDialog.OnTimeSetListener time;
    Activity activity = this;
    private Spinner spinner_person_name;
    List<String> list;

    String person_id = "";
    ArrayAdapter<String> dataAdapter;

    CiprbDatabase ciprbDatabase;
    int alive_count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injury_mortality);
        ciprbDatabase=new CiprbDatabase(getApplicationContext());
        ciprbDatabase.open();
        edittext_date_of_injury = (EditText) findViewById(R.id.edittext_date_of_injury);
        edittext_time_of_injury = (EditText) findViewById(R.id.edittext_time_of_injury);
        spinner_person_name=(Spinner)findViewById(R.id.spinner_person_name);
        edittext_death_date_of_person = (EditText) findViewById(R.id.edittext_death_date_of_person);

        myCalendar = Calendar.getInstance();

        list = new ArrayList<String>();
        //list = ApplicationData.alive_person_List;

        if (ciprbDatabase.getDeathPersonList().isEmpty()) {

            Toast.makeText(activity, "No Data to store", Toast.LENGTH_LONG).show();
            finish();

        } else {

            setMemberSpinner(list);

        }


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        death_date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDeathDateLabel();
            }

        };


        edittext_date_of_injury.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(activity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        time = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                myCalendar.set(Calendar.HOUR, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);

                updateTime();
            }
        };

        edittext_time_of_injury.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new TimePickerDialog(activity, time, myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.HOUR), false).show();
            }

        });

        edittext_death_date_of_person.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(activity, death_date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });


    }

    private void updateLabel() {

        String myFormat = "dd/mm/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext_date_of_injury.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateDeathDateLabel() {

        String myFormat = "dd/mm/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext_death_date_of_person.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateTime() {

        String myFormat = "hh:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext_time_of_injury.setText(sdf.format(myCalendar.getTime()));

    }
    private void setMemberSpinner(List<String> list) {
        list.clear();
        alive_count = ciprbDatabase.getDeathPersonList().size();
        for (Person aPerson : ciprbDatabase.getDeathPersonList()) {
            list.add(aPerson.getMembers_name() + "." + aPerson.getPerson_id());
        }
        dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner_person_name.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onResume() {
        super.onResume();

        try {

            if (ciprbDatabase.getDeathPersonList().isEmpty()) {

                Toast.makeText(activity, "No Data to store", Toast.LENGTH_LONG).show();
                activity.finish();

            }

            if (ApplicationData.INJURY_DATA_COLLECT == true) {

                Toast.makeText(activity, "Adapter Updated....: " + ApplicationData.ALIVE_PERSON_NUMBER, Toast.LENGTH_LONG).show();
                /*ApplicationData.alive_person_List.remove(ApplicationData.ALIVE_PERSON_NUMBER);


                Toast.makeText(activity, "Adapter Updated....: " +
                        ApplicationData.ALIVE_PERSON_NUMBER + "List size:" +
                        ApplicationData.alive_person_List.size(), Toast.LENGTH_LONG).show();

                list.remove(ApplicationData.ALIVE_PERSON_NUMBER);*/
                list.remove(ApplicationData.ALIVE_PERSON_NUMBER);
                ciprbDatabase.deleteRowByIDFromDeath(person_id);





                if (list.size() <= 0) {

                    Toast.makeText(activity, "No Data to store", Toast.LENGTH_LONG).show();
                    activity.finish();

                }

            }

        } catch (NullPointerException e) {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();

        } catch (ArrayIndexOutOfBoundsException e) {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();
        }


    }
}


