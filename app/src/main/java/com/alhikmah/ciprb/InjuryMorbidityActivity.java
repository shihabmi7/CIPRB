package com.alhikmah.ciprb;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class InjuryMorbidityActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edittext_date_of_injury, edittext_time_of_injury, edittext_how_much_cost_reatment,
            edittext_number_of_days_work_loss, edittext_days_take_health_facility, edittext_time_take_health_facility;
    Calendar myCalendar = null;
    DatePickerDialog.OnDateSetListener date;
    TimePickerDialog.OnTimeSetListener time;
    Activity activity = this;
    Spinner spinner_how_injured,
            spinner_place_of_injury,
            spinner_injury_intent,
            spinner_condition_of_victims_after_injury,
            spinner_mobility_condition_after_injury, spinner_did_receive_first_aid,
            spinner_who_gave_first_aid,
            spinner_Was_he_trained_in_first_aid,
            spinner_person_receive_treatment_for_injury, spinner_Who_provided_the_treatment,
            spinner_Where_receive_treatment, spinner_admitted_health_facility, spinner_type_admitted_health_facility,
            spinner_how_admitted_health_facility,
            spinner_any_surgery_operation_done, spinner_type_of_anesthesia_given, spinner_outcome_of_treatment,
            spinner_injured_person_become_disabled, spinner_type_of_disability,
            spinner_significant_source_of_income_for_family, spinner_person_name,
            spinner_family_being_coping_loss_income;
//    spinner_sex,

    ProgressDialog progressDialog;

    Button button_cancel, button_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);/**/
        setContentView(R.layout.activity_injury_morbidity);

        /*edittext*/
        edittext_date_of_injury = (EditText) findViewById(R.id.edittext_date_of_injury);
        edittext_time_of_injury = (EditText) findViewById(R.id.edittext_time_of_injury);
        edittext_number_of_days_work_loss = (EditText) findViewById(R.id.edittext_number_of_days_work_loss);
        edittext_days_take_health_facility = (EditText) findViewById(R.id.edittext_days_take_health_facility);
        edittext_time_take_health_facility = (EditText) findViewById(R.id.edittext_time_take_health_facility);

        //spinner_sex = (Spinner) findViewById(R.id.spinner_sex);

        spinner_person_name = (Spinner) findViewById(R.id.spinner_person_name);

        List<String> list = new ArrayList<String>();
        /*list.add("Android");
        list.add("Java");
        list.add("Spinner Data");
        list.add("Spinner Adapter");
        list.add("Spinner Example");*/

        //list = ApplicationData.alive_person_List;

        for (Person dataAdapter : ApplicationData.alive_person_List) {

            list.add(dataAdapter.getMembers_name() + "." + dataAdapter.getPerson_id());

           /* for (int i = 1; i <= dataAdapter.getInjury_number(); i++) {

                list.add(dataAdapter.getMembers_name() + "." + dataAdapter.getPerson_id());

            }*/
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner_person_name.setAdapter(dataAdapter);


        spinner_how_injured = (Spinner) findViewById(R.id.spinner_how_injured);
        spinner_place_of_injury = (Spinner) findViewById(R.id.spinner_place_of_injury);
        spinner_injury_intent = (Spinner) findViewById(R.id.spinner_injury_intent);
        spinner_condition_of_victims_after_injury = (Spinner) findViewById(R.id.spinner_condition_of_victims_after_injury);
        spinner_mobility_condition_after_injury = (Spinner) findViewById(R.id.spinner_mobility_condition_after_injury);
        spinner_did_receive_first_aid = (Spinner) findViewById(R.id.spinner_did_receive_first_aid);
        spinner_who_gave_first_aid = (Spinner) findViewById(R.id.spinner_who_gave_first_aid);
        spinner_Was_he_trained_in_first_aid = (Spinner) findViewById(R.id.spinner_Was_he_trained_in_first_aid);
        spinner_person_receive_treatment_for_injury = (Spinner) findViewById(R.id.spinner_person_receive_treatment_for_injury);
        spinner_Who_provided_the_treatment = (Spinner) findViewById(R.id.spinner_Who_provided_the_treatment);
        spinner_Where_receive_treatment = (Spinner) findViewById(R.id.spinner_Where_receive_treatment);
        spinner_admitted_health_facility = (Spinner) findViewById(R.id.spinner_admitted_health_facility);
        spinner_type_admitted_health_facility = (Spinner) findViewById(R.id.spinner_type_admitted_health_facility);
        spinner_how_admitted_health_facility = (Spinner) findViewById(R.id.spinner_how_admitted_health_facility);
        spinner_any_surgery_operation_done = (Spinner) findViewById(R.id.spinner_any_surgery_operation_done);
        spinner_type_of_anesthesia_given = (Spinner) findViewById(R.id.spinner_type_of_anesthesia_given);
        spinner_outcome_of_treatment = (Spinner) findViewById(R.id.spinner_outcome_of_treatment);


        edittext_how_much_cost_reatment = (EditText) findViewById(R.id.edittext_how_much_cost_reatment);
        spinner_injured_person_become_disabled = (Spinner) findViewById(R.id.spinner_injured_person_become_disabled);
        spinner_type_of_disability = (Spinner) findViewById(R.id.spinner_type_of_disability);
        spinner_significant_source_of_income_for_family = (Spinner) findViewById(R.id.spinner_significant_source_of_income_for_family);
        spinner_family_being_coping_loss_income = (Spinner) findViewById(R.id.spinner_family_being_coping_loss_income);


        myCalendar = Calendar.getInstance();

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

        edittext_date_of_injury.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(activity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        /*time = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                myCalendar.set(Calendar.HOUR, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);

                updateTime();
            }
        };*/

      /*  edittext_time_of_injury.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new TimePickerDialog(activity, time, myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.HOUR), false).show();
            }

        });*/

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(true);

        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_next = (Button) findViewById(R.id.button_next);

        button_cancel.setOnClickListener(this);
        button_next.setOnClickListener(this);

    }

    private void updateLabel() {

        String myFormat = "dd/mm/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext_date_of_injury.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateTime() {

        String myFormat = "hh:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext_time_of_injury.setText(sdf.format(myCalendar.getTime()));

    }

    void saveDataToOnline(String id) {

        progressDialog.show();
        // post with no parameters
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();


        //ApplicationData.spilitStringFirst(getSelectedItem().toString())
        params.put("household_unique_code", id);
        //params.put("e01", ApplicationData.spilitStringFirst(spinner_sex.getSelectedItem().toString()));
        params.put("e02", ApplicationData.spilitStringFirst(spinner_how_injured.getSelectedItem().toString()));
        params.put("e03", edittext_date_of_injury.getText().toString());
        params.put("e04", edittext_time_of_injury.getText().toString());
        params.put("e05", ApplicationData.spilitStringFirst(spinner_place_of_injury.getSelectedItem().toString()));
        params.put("e06", ApplicationData.spilitStringFirst(spinner_injury_intent.getSelectedItem().toString()));
        //params.put("e07",  );
        params.put("e08", ApplicationData.spilitStringFirst(spinner_condition_of_victims_after_injury.getSelectedItem().toString()));
        params.put("e09", ApplicationData.spilitStringFirst(spinner_mobility_condition_after_injury.getSelectedItem().toString()));
        params.put("e10", ApplicationData.spilitStringFirst(spinner_did_receive_first_aid.getSelectedItem().toString()));
        params.put("e11", ApplicationData.spilitStringFirst(spinner_who_gave_first_aid.getSelectedItem().toString()));
        params.put("e12", ApplicationData.spilitStringFirst(spinner_Was_he_trained_in_first_aid.getSelectedItem().toString()));
        params.put("e13", ApplicationData.spilitStringFirst(spinner_person_receive_treatment_for_injury.getSelectedItem().toString()));
        params.put("e14", ApplicationData.spilitStringFirst(spinner_Who_provided_the_treatment.getSelectedItem().toString()));
        params.put("e15", ApplicationData.spilitStringFirst(spinner_Where_receive_treatment.getSelectedItem().toString()));
        params.put("e16", ApplicationData.spilitStringFirst(spinner_admitted_health_facility.getSelectedItem().toString()));
        params.put("e17", ApplicationData.spilitStringFirst(spinner_type_admitted_health_facility.getSelectedItem().toString()));
        params.put("e18", ApplicationData.spilitStringFirst(spinner_how_admitted_health_facility.getSelectedItem().toString()));
        params.put("e19", edittext_time_take_health_facility.getText().toString());
        params.put("e20", edittext_days_take_health_facility.getText().toString());
        params.put("e21", ApplicationData.spilitStringFirst(spinner_any_surgery_operation_done.getSelectedItem().toString()));
        params.put("e22", ApplicationData.spilitStringFirst(spinner_type_of_anesthesia_given.getSelectedItem().toString()));
        params.put("e23", ApplicationData.spilitStringFirst(spinner_outcome_of_treatment.getSelectedItem().toString()));
        //params.put("e24", );
        params.put("e25", ApplicationData.spilitStringFirst(spinner_injured_person_become_disabled.getSelectedItem().toString()));
        params.put("e26", ApplicationData.spilitStringFirst(spinner_type_of_disability.getSelectedItem().toString()));
        //params.put("e27", ApplicationData.spilitStringFirst(spinner_outcome_of_treatment.getSelectedItem().toString()));
        //params.put("e28", ApplicationData.spilitStringFirst(spinner_outcome_of_treatment.getSelectedItem().toString()));
        params.put("e29", edittext_number_of_days_work_loss.getText().toString());
        params.put("e30", ApplicationData.spilitStringFirst(spinner_significant_source_of_income_for_family.getSelectedItem().toString()));
        params.put("e31", ApplicationData.spilitStringFirst(spinner_family_being_coping_loss_income.getSelectedItem().toString()));


        client.post(ApplicationData.URL_HOUSE_HOLD_MEMBERS, params,
                new JsonHttpResponseHandler() {
                    // Your implementation here
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        cleartext();
                        progressDialog.dismiss();
                        /*if (count >= member_no) {

                            showTextLong("finish this input");
                            ApplicationData.memberListHashMap.clear();
                            ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);

                        }
*/
                    }
                }
        );
    }

    void cleartext() {

       /* editText_members_name.getText().clear();
        edittext_date_of_birth.getText().clear();
        edittext_current_age.getText().clear();
        editText_educatoin_level.getText().clear();

        if (edittext_how_many_injury_last_six != null)
            edittext_how_many_injury_last_six.getText().clear();*/

    }

    public void showAlert(final Activity activity) {

        if (InternetConnection.isAvailable(activity)) {


        } else {

            new AlertDialog.Builder(activity)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("No Internet Connection")
                    .setMessage("Please check your connectivity.")
                    .setPositiveButton("Exit",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    finish();

                                }

                            })
                    .setNegativeButton("Retry",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    // Stop the activity
                                    showAlert(activity);

                                }

                            }).show();
        }

    }

    @Override
    public void onClick(View v) {

        if (v == button_next) {

            if (InternetConnection.checkNetworkConnection(this)) {

                if (!edittext_date_of_injury.getText().toString().isEmpty()
                        && !edittext_time_of_injury.getText().toString().isEmpty()) {

                    int pos = spinner_person_name.getSelectedItemPosition();
                    Person person = ApplicationData.alive_person_List.get(pos);
                    person.setInjury_type(spinner_how_injured.getSelectedItem().toString());
                    //String id = ApplicationData.alive_person_List.get(pos).getPerson_id();
                    //String type_of_injury = ApplicationData.alive_person_List.get(pos).getInjury_type();

                    //int val = Integer.parseInt(ApplicationData.spilitStringFirst(type_of_injury));

                    moveToInjurySelectionActivity(person);
                    // go to next activity > module
                    showTextLong("" + person.getInjury_type());
                    //ApplicationData.memberListHashMap.clear();
                    // ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);

                    //Toast.makeText(getApplicationContext(), "Saved" + id, Toast.LENGTH_LONG).show();
                    // saveDataToOnline(person);

                } else {
                    // showTextLong(" empty field not allowed");
                }

            } else
                showAlert(this);

        } else if (v == button_cancel) {

            cleartext();

        }
    }

    void moveToInjurySelectionActivity(Person person) {

        switch (Integer.parseInt(ApplicationData.spilitStringFirst(person.getInjury_type()))) {
            case 1:
                ApplicationData.gotToNextActivity(this, SuicideAttemptActivity.class, person);
                break;
            case 2:
                ApplicationData.gotToNextActivity(this, RoadTransportInjuryActivity.class, person);
                break;
            case 3:
                ApplicationData.gotToNextActivity(this, ViolenceInjuryActivity.class, person);
                break;
            case 4:
                ApplicationData.gotToNextActivity(this, FallInjuryActivity.class, person);
                break;
            case 5:
                ApplicationData.gotToNextActivity(this, CutInjuryActivity.class, person);
                break;
            case 6:
                ApplicationData.gotToNextActivity(this, BurnInjuryActivity.class, person);
                break;
            case 7:
                ApplicationData.gotToNextActivity(this, NearDrowningActivity.class, person);
                break;
            case 8:
                ApplicationData.gotToNextActivity(this, UnintentionalPoisoningActivity.class, person);
                break;
            case 9:
                ApplicationData.gotToNextActivity(this, ToolInjuryActivity.class, person);
                break;
            case 10:
                ApplicationData.gotToNextActivity(this, ElectrocutionActivity.class, person);
                break;
            case 11:
                ApplicationData.gotToNextActivity(this, InsectInjuryActivity.class, person);
                break;
            case 12:
                ApplicationData.gotToNextActivity(this, InjuryBluntActivity.class, person);
                break;
            case 13:
                ApplicationData.gotToNextActivity(this, SuffocationActivity.class, person);
                break;
            case 14:
                ApplicationData.gotToNextActivity(this, QualityOfLifeActivity.class, person);
                break;
        }

    }

    void showTextLong(String value) {

        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();
    }
}


