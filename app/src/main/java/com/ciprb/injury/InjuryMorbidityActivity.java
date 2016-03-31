package com.ciprb.injury;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ciprb.injury.localdb.CiprbDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class InjuryMorbidityActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edittext_date_of_injury, edittext_time_of_injury,
            edittext_how_much_cost_reatment, edittext_injured_parts, edittext_injured_type,
            edittext_number_of_days_work_loss,
            edittext_days_take_health_facility,
            edittext_time_take_health_facility,edittext_number_of_days_assistance_for_daily_leaving;


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
            spinner_significant_source_of_income_for_family, spinner_person_name, spinner_disability_affects_sides,
            spinner_family_being_coping_loss_income;
//    spinner_sex,



    ProgressDialog progressDialog;
    Button button_cancel, button_next;
    int alive_count = 0;

    LinearLayout layout_type_admitted_health_facility,
            layout_was_hetrained_in_first_aid, lay_type_of_disability,
            lay_type_of_anesthesia_given,
            lay_who_gave_first_aid, layout_disability_sides,
            layout_who_provided_the_treatment,
            layout_where_recieve_treatment,
            layout_how_admitted_health_facility,
            layout_time_take_health_facility,
            layout_days_take_health_facility;
    List<String> list;
    String person_id = "";
    ArrayAdapter<String> dataAdapter;
    CiprbDatabase ciprbDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);/**/
        setContentView(R.layout.activity_injury_morbidity);

        setTitle(getResources().getStringArray(R.array.survey_activity_title)[2]);

        ciprbDatabase = new CiprbDatabase(getApplicationContext());
        ciprbDatabase.open();

        initUI();

        try {

            list = new ArrayList<String>();
            //list = ApplicationData.alive_person_List;

            if (ciprbDatabase.getAlivePersonList().isEmpty()) {

                Toast.makeText(activity, "No Data to store", Toast.LENGTH_LONG).show();
                finish();

            } else {

                setMemberSpinner(list);

            }


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

            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage(getString(R.string.loading_message));
            progressDialog.setTitle(getString(R.string.waiting));
            progressDialog.setCancelable(true);


        } catch (NullPointerException e) {

            //Toast.makeText("Some Error Occurs");

        } catch (Exception e) {

        }
    }

    private void initUI() {
        layout_type_admitted_health_facility = (LinearLayout) findViewById(R.id.layout_type_admitted_health_facility);
        lay_type_of_disability = (LinearLayout) findViewById(R.id.lay_type_of_disability);
        lay_type_of_anesthesia_given = (LinearLayout) findViewById(R.id.lay_type_of_anesthesia_given);
        lay_who_gave_first_aid = (LinearLayout) findViewById(R.id.lay_who_gave_first_aid);
        layout_was_hetrained_in_first_aid = (LinearLayout) findViewById(R.id.layout_was_hetrained_in_first_aid);

        layout_disability_sides = (LinearLayout) findViewById(R.id.layout_disability_sides);
        layout_who_provided_the_treatment = (LinearLayout) findViewById(R.id.layout_who_provided_the_treatment);
        layout_where_recieve_treatment = (LinearLayout) findViewById(R.id.layout_where_recieve_treatment);
        layout_how_admitted_health_facility =(LinearLayout)findViewById(R.id.layout_how_admitted_health_facility);
        layout_time_take_health_facility =(LinearLayout)findViewById(R.id.layout_time_take_health_facility);
        layout_days_take_health_facility =(LinearLayout)findViewById(R.id.layout_days_take_health_facility);




        edittext_number_of_days_assistance_for_daily_leaving = (EditText) findViewById(R.id.edittext_number_of_days_assistance_for_daily_leaving);
        edittext_date_of_injury = (EditText) findViewById(R.id.edittext_date_of_injury);
        edittext_time_of_injury = (EditText) findViewById(R.id.edittext_time_of_injury);
        edittext_number_of_days_work_loss = (EditText) findViewById(R.id.edittext_number_of_days_work_loss);
        edittext_days_take_health_facility = (EditText) findViewById(R.id.edittext_days_take_health_facility);
        edittext_time_take_health_facility = (EditText) findViewById(R.id.edittext_time_take_health_facility);
        edittext_injured_parts = (EditText) findViewById(R.id.edittext_injured_parts);
        edittext_injured_type = (EditText) findViewById(R.id.edittext_injured_type);
        spinner_person_name = (Spinner) findViewById(R.id.spinner_person_name);


        spinner_disability_affects_sides = (Spinner) findViewById(R.id.spinner_disability_affects_sides);
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

        spinner_did_receive_first_aid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    lay_who_gave_first_aid.setVisibility(View.VISIBLE);
                    layout_was_hetrained_in_first_aid.setVisibility(View.VISIBLE);
                    // lay_how_injured.setVisibility(View.VISIBLE);

                } else {

                    lay_who_gave_first_aid.setVisibility(View.GONE);
                    layout_was_hetrained_in_first_aid.setVisibility(View.GONE);
                    //lay_how_injured.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_admitted_health_facility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    layout_type_admitted_health_facility.setVisibility(View.VISIBLE);
                    layout_how_admitted_health_facility .setVisibility(View.VISIBLE);
                    layout_time_take_health_facility .setVisibility(View.VISIBLE);
                    layout_days_take_health_facility.setVisibility(View.VISIBLE);



                } else {

                    layout_type_admitted_health_facility.setVisibility(View.GONE);
                    layout_how_admitted_health_facility .setVisibility(View.GONE);
                    layout_time_take_health_facility .setVisibility(View.GONE);
                    layout_days_take_health_facility.setVisibility(View.GONE);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_any_surgery_operation_done.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    lay_type_of_anesthesia_given.setVisibility(View.VISIBLE);
                    // lay_how_injured.setVisibility(View.VISIBLE);

                } else {

                    lay_type_of_anesthesia_given.setVisibility(View.GONE);
                    //lay_how_injured.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_injured_person_become_disabled.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    lay_type_of_disability.setVisibility(View.VISIBLE);

                    layout_disability_sides.setVisibility(View.VISIBLE);
                    // lay_how_injured.setVisibility(View.VISIBLE);

                } else {

                    lay_type_of_disability.setVisibility(View.GONE);
                    layout_disability_sides.setVisibility(View.GONE);

                    //lay_how_injured.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_person_receive_treatment_for_injury.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {


                    layout_who_provided_the_treatment.setVisibility(View.VISIBLE);
                    layout_where_recieve_treatment.setVisibility(View.VISIBLE);



                } else {

                    layout_who_provided_the_treatment.setVisibility(View.GONE);
                    layout_where_recieve_treatment.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_next = (Button) findViewById(R.id.button_next);

        button_cancel.setOnClickListener(this);
        button_next.setOnClickListener(this);
    }

    boolean checkSpinner() {


        if (spinner_how_injured.getSelectedItemPosition() != 0 &&
                spinner_place_of_injury.getSelectedItemPosition() != 0
                && spinner_injury_intent.getSelectedItemPosition() != 0
                && spinner_condition_of_victims_after_injury.getSelectedItemPosition() != 0 &&
                spinner_mobility_condition_after_injury.getSelectedItemPosition() != 0 &&
                spinner_outcome_of_treatment.getSelectedItemPosition() != 0 &&

                spinner_significant_source_of_income_for_family.getSelectedItemPosition() != 0 &&
                spinner_family_being_coping_loss_income.getSelectedItemPosition() != 0 &&
                edittext_date_of_injury.getText().length() > 0
                && edittext_time_of_injury.getText().length() > 0) {

            //Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_LONG).show();

            return true;


        } else {

            Toast.makeText(getApplicationContext(), getString(R.string.suggestion), Toast.LENGTH_LONG).show();
            return false;
        }

    }

    void setSpinnerDefaultState() {

        spinner_how_injured.setSelection(0);
        spinner_injury_intent.setSelection(0);
        spinner_condition_of_victims_after_injury.setSelection(0);
        spinner_mobility_condition_after_injury.setSelection(0);
        spinner_who_gave_first_aid.setSelection(0);
        spinner_outcome_of_treatment.setSelection(0);
        spinner_admitted_health_facility.setSelection(0);
        spinner_Where_receive_treatment.setSelection(0);
        spinner_type_of_disability.setSelection(0);
        spinner_significant_source_of_income_for_family.setSelection(0);
        spinner_family_being_coping_loss_income.setSelection(0);

    }

    @Override
    protected void onResume() {
        super.onResume();

        try {

            if (ciprbDatabase.getAlivePersonList().isEmpty()) {

                Toast.makeText(activity, "No Data to store", Toast.LENGTH_LONG).show();
                activity.finish();
                ciprbDatabase.close();

            }

            if (ApplicationData.INJURY_DATA_COLLECT == true) {

                Log.e("Resume Moribidity",">>> Adapter Updated....: " + ApplicationData.ALIVE_PERSON_NUMBER);
               // Toast.makeText(activity, "Adapter Updated....: " + ApplicationData.ALIVE_PERSON_NUMBER, Toast.LENGTH_LONG).show();

                list.remove(ApplicationData.ALIVE_PERSON_NUMBER);
                dataAdapter.notifyDataSetChanged();
                ciprbDatabase.deleteRowByID(person_id);

                if (list.size() <= 0) {

                    Toast.makeText(activity, "No Data to store", Toast.LENGTH_LONG).show();
                    activity.finish();
                    ciprbDatabase.close();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ApplicationData.INJURY_DATA_COLLECT = false;
        ApplicationData.alive_person_List.clear();
    }

    private void setMemberSpinner(List<String> list) {
        list.clear();
        alive_count = ciprbDatabase.getAlivePersonList().size();
        for (Person aPerson : ciprbDatabase.getAlivePersonList()) {
            list.add(aPerson.getMembers_name() + "." + aPerson.getPerson_id());
        }
        dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner_person_name.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext_date_of_injury.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateTime() {

        String myFormat = "hh:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext_time_of_injury.setText(sdf.format(myCalendar.getTime()));

    }

//    void saveDataToOnline(int pos) {
//
//        progressDialog.show();
//        final Person person = ApplicationData.alive_person_List.get(pos);
//        person.setInjury_type(spinner_how_injured.getSelectedItem().toString());
//        // post with no parameters
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//
//        //ApplicationData.spilitStringFirst(getSelectedItem().toString())
//        params.put("household_unique_code", person.getPerson_id());
////        params.put("e01", ApplicationData.spilitStringFirst(spinner_sex.getSelectedItem().toString()));
//        params.put("e02", ApplicationData.spilitStringFirst(spinner_how_injured.getSelectedItem().toString()));
//        params.put("e03", edittext_date_of_injury.getText().toString());
//        params.put("e04", edittext_time_of_injury.getText().toString());
//        params.put("e05", ApplicationData.spilitStringFirst(spinner_place_of_injury.getSelectedItem().toString()));
//        params.put("e06", ApplicationData.spilitStringFirst(spinner_injury_intent.getSelectedItem().toString()));
//        //params.put("e07",  );
//        params.put("e08", ApplicationData.spilitStringFirst(spinner_condition_of_victims_after_injury.getSelectedItem().toString()));
//        params.put("e09", ApplicationData.spilitStringFirst(spinner_mobility_condition_after_injury.getSelectedItem().toString()));
//        params.put("e10", ApplicationData.spilitStringFirst(spinner_did_receive_first_aid.getSelectedItem().toString()));
//        //params.put("e11", ApplicationData.spilitStringFirst(spinner_who_gave_first_aid.getSelectedItem().toString()));
//        params.put("e12", ApplicationData.spilitStringFirst(spinner_Was_he_trained_in_first_aid.getSelectedItem().toString()));
//        params.put("e13", ApplicationData.spilitStringFirst(spinner_person_receive_treatment_for_injury.getSelectedItem().toString()));
//        params.put("e14", ApplicationData.spilitStringFirst(spinner_Who_provided_the_treatment.getSelectedItem().toString()));
//        params.put("e15", ApplicationData.spilitStringFirst(spinner_Where_receive_treatment.getSelectedItem().toString()));
//        params.put("e16", ApplicationData.spilitStringFirst(spinner_admitted_health_facility.getSelectedItem().toString()));
//        // params.put("e17", ApplicationData.spilitStringFirst(spinner_type_admitted_health_facility.getSelectedItem().toString()));
//        params.put("e18", ApplicationData.spilitStringFirst(spinner_how_admitted_health_facility.getSelectedItem().toString()));
//        params.put("e19", edittext_time_take_health_facility.getText().toString());
//        params.put("e20", edittext_days_take_health_facility.getText().toString());
//        params.put("e21", ApplicationData.spilitStringFirst(spinner_any_surgery_operation_done.getSelectedItem().toString()));
//        //params.put("e22", ApplicationData.spilitStringFirst(spinner_type_of_anesthesia_given.getSelectedItem().toString()));
//        params.put("e23", ApplicationData.spilitStringFirst(spinner_outcome_of_treatment.getSelectedItem().toString()));
//        //params.put("e24", );
//        params.put("e25", ApplicationData.spilitStringFirst(spinner_injured_person_become_disabled.getSelectedItem().toString()));
//        //params.put("e26", ApplicationData.spilitStringFirst(spinner_type_of_disability.getSelectedItem().toString()));
//        //params.put("e27", ApplicationData.spilitStringFirst(spinner_outcome_of_treatment.getSelectedItem().toString()));
//        //params.put("e28", ApplicationData.spilitStringFirst(spinner_outcome_of_treatment.getSelectedItem().toString()));
//        params.put("e29", edittext_number_of_days_work_loss.getText().toString());
//        params.put("e30", ApplicationData.spilitStringFirst(spinner_significant_source_of_income_for_family.getSelectedItem().toString()));
//        params.put("e31", ApplicationData.spilitStringFirst(spinner_family_being_coping_loss_income.getSelectedItem().toString()));
//
//
//        if (layout_type_admitted_health_facility.getVisibility() == View.VISIBLE) {
//            params.put("e17", ApplicationData.spilitStringFirst(spinner_type_admitted_health_facility.getSelectedItem().toString()));
//            //params.put("e02", person.getInjury_type());
//        }
//
//        if (lay_type_of_disability.getVisibility() == View.VISIBLE) {
//            params.put("e26", ApplicationData.spilitStringFirst(spinner_type_of_disability.getSelectedItem().toString()));
//            //params.put("e02", person.getInjury_type());
//        }
//
//        if (lay_type_of_anesthesia_given.getVisibility() == View.VISIBLE) {
//            params.put("e22", ApplicationData.spilitStringFirst(spinner_type_of_anesthesia_given.getSelectedItem().toString()));
//            //params.put("e02", person.getInjury_type());
//        }
//        if (lay_who_gave_first_aid.getVisibility() == View.VISIBLE) {
//            params.put("e11", ApplicationData.spilitStringFirst(spinner_who_gave_first_aid.getSelectedItem().toString()));
//            //params.put("e02", person.getInjury_type());
//        }
//
//        client.put(ApplicationData.URL_INJURY_MORBIDITY, params,
//                new JsonHttpResponseHandler() {
//                    // Your implementation here
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        progressDialog.dismiss();
//                        int type = spinner_how_injured.getSelectedItemPosition();
//                        showTextLong("Success! Select Type:" + type);
//                        ApplicationData.alive_person_List.remove(spinner_person_name.getSelectedItemPosition());
//                        cleartext();
//
//                        moveToInjurySelectionActivity(type, person.getPerson_id());
//                        // go to next activity > module
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        showTextLong("Failed...");
//                        super.onFailure(statusCode, headers, responseString, throwable);
//                    }
//                }
//        );
//    }

    String createJsonBody() {

        String e17 = "";
        String e22 = "";
        String e26 = "";
        String e11 = "";
        String e12 = "";
        String e14 = "";
        String e15 = "";
        String e18 = "";
        String e19 = "";
        String e20 = "";
        String e27 = "";


        if(layout_who_provided_the_treatment.getVisibility() == View.VISIBLE){

            e14 = ApplicationData.spilitStringFirst(spinner_Who_provided_the_treatment.getSelectedItem().toString());
            e15 = ApplicationData.spilitStringFirst(spinner_Where_receive_treatment.getSelectedItem().toString());

        }

        if (layout_type_admitted_health_facility.getVisibility() == View.VISIBLE) {
            e17 = ApplicationData.spilitStringFirst(spinner_type_admitted_health_facility.getSelectedItem().toString());
            e18= ApplicationData.spilitStringFirst(spinner_how_admitted_health_facility.getSelectedItem().toString());
            e19 = edittext_time_take_health_facility.getText().toString();
            e20= edittext_days_take_health_facility.getText().toString() ;
        }
        if (lay_type_of_disability.getVisibility() == View.VISIBLE) {
            e26 = ApplicationData.spilitStringFirst(spinner_type_of_disability.getSelectedItem().toString());
            e27 = ApplicationData.spilitStringFirst(spinner_disability_affects_sides.getSelectedItem().toString());
        }

        if (lay_type_of_anesthesia_given.getVisibility() == View.VISIBLE) {
            e22 = ApplicationData.spilitStringFirst(spinner_type_of_anesthesia_given.getSelectedItem().toString());
        }

        if (lay_who_gave_first_aid.getVisibility() == View.VISIBLE) {
            e11 = ApplicationData.spilitStringFirst(spinner_who_gave_first_aid.getSelectedItem().toString());
            e12 = ApplicationData.spilitStringFirst(spinner_Was_he_trained_in_first_aid.getSelectedItem().toString());
        }

        //Log.i("Test String ", ApplicationData.spilitStringFirst(spinner_survey_suicide_where.getSelectedItem().toString()));
        String rep = "{" +
                "\"e01\":\"" +
                "" +
                "\",\"e02\":\"" +
                ApplicationData.spilitStringFirst(spinner_how_injured.getSelectedItem().toString()) +
                        "\",\"e03\":\"" +
                        edittext_date_of_injury.getText().toString() +
                        "\",\"e04\":\"" +
                        edittext_time_of_injury.getText().toString().toString() +
                        "\",\"e05\":\"" +
                        ApplicationData.spilitStringFirst(spinner_place_of_injury.getSelectedItem().toString()) +
                "\",\"e06\":\"" +
                ApplicationData.spilitStringFirst(spinner_injury_intent.getSelectedItem().toString()) +
                "\",\"e07\":\"" +
                "" +
                "\",\"e08\":\"" +
                ApplicationData.spilitStringFirst(spinner_condition_of_victims_after_injury.getSelectedItem().toString()) +
                "\",\"e09\":\"" +
                ApplicationData.spilitStringFirst(spinner_mobility_condition_after_injury.getSelectedItem().toString()) +
                "\",\"e10\":\"" +
                ApplicationData.spilitStringFirst(spinner_did_receive_first_aid.getSelectedItem().toString()) +
                "\",\"e11\":\"" +
                e11 +
                "\",\"e12\":\"" +
                e12 +
                "\",\"e13\":\"" +
                ApplicationData.spilitStringFirst(spinner_person_receive_treatment_for_injury.getSelectedItem().toString()) +
                "\",\"e14\":\"" +
                e14 +
                "\",\"e15\":\"" +
                e15 +
                "\",\"e16\":\"" +
                ApplicationData.spilitStringFirst(spinner_admitted_health_facility.getSelectedItem().toString()) +
                "\",\"e17\":\"" +
                e17 +
                "\",\"e18\":\"" +
                e18 +
                "\",\"e19\":\"" +
                e19 +
                "\",\"e20\":\"" +
                e20 +
                "\",\"e21\":\"" +
                ApplicationData.spilitStringFirst(spinner_any_surgery_operation_done.getSelectedItem().toString()) +
                "\",\"e22\":\"" +
                e22 +
                "\",\"e23\":\"" +
                ApplicationData.spilitStringFirst(spinner_outcome_of_treatment.getSelectedItem().toString()) +
                "\",\"e24\":\"" +
                edittext_how_much_cost_reatment.getText().toString() +
                "\",\"e25\":\"" +
                ApplicationData.spilitStringFirst(spinner_injured_person_become_disabled.getSelectedItem().toString()) +
                "\",\"e26\":\"" +
                e26 +
                "\",\"e27\":\"" +
                e27 +
                "\",\"e28\":\"" +
                edittext_number_of_days_assistance_for_daily_leaving.getText().toString() +
                "\",\"e29\":\"" +
                edittext_number_of_days_work_loss.getText().toString() +
                "\",\"e30\":\"" +
                ApplicationData.spilitStringFirst(spinner_significant_source_of_income_for_family.getSelectedItem().toString()) +
                "\",\"e31\":\"" +
                ApplicationData.spilitStringFirst(spinner_family_being_coping_loss_income.getSelectedItem().toString()) +
                "\",\"e32\":\"" +
                edittext_injured_parts.getText().toString() +
                "\",\"e33\":\"" +
                edittext_injured_type.getText().toString() +
                "\"}";
        //return "{\"g03\":\"10\"}";
        return rep;
    }

    String PostcreateJsonBody() {
        return "{\"household_unique_code\":\"101323210\"," +
                "\"f01\":\"05\"}";
    }

    private class PutAsync extends AsyncTask<String, Void, String> {
        int value;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String Result = "";
                Log.i("URL are ", params[0]);
                //Result = ApplicationData.putRequestWithHeaderAndBody(params[0], params[1]);
                value = ApplicationData.putRequestWithBody(params[0], params[1]);
                Log.i("Result Are ", Result);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            if (value == ApplicationData.STATUS_SUCCESS) {
                //// TODO: 3/22/2016

                int type = spinner_how_injured.getSelectedItemPosition();
                //showTextLong("Success! Select Type:" + type);
                Toast.makeText(activity, "Success" + type, Toast.LENGTH_LONG).show();

                ApplicationData.ALIVE_PERSON_NUMBER = spinner_person_name.getSelectedItemPosition();
                person_id = ApplicationData.spilitStringSecond(spinner_person_name.getSelectedItem().toString());
                cleartext();
                moveToInjurySelectionActivity(type, person_id);


            } else {
                Toast.makeText(activity, "Failed", Toast.LENGTH_LONG).show();
            }
        }
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

                                    // finish();

                                }

                            })
                    .setNegativeButton("Retry",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    // Stop the activity
                                    //showAlert(activity);

                                }

                            }).show();
        }

    }

    void cleartext() {

        try{

            setSpinnerDefaultState();

            edittext_date_of_injury.getText().clear();
            edittext_time_of_injury.getText().clear();
            edittext_injured_type.getText().clear();
            edittext_injured_parts.setText("");
            edittext_number_of_days_assistance_for_daily_leaving.setText("");
            edittext_how_much_cost_reatment.setText("");
            edittext_number_of_days_work_loss.setText("");
            edittext_time_take_health_facility.setText("");
            edittext_days_take_health_facility.setText("");


        }catch (Exception e){


        }



       /* editText_members_name.getText().clear();
        edittext_date_of_birth.getText().clear();

        .getText().clear();
        editText_educatoin_level.getText().clear();

        if (edittext_how_many_injury_last_six != null)
            edittext_how_many_injury_last_six.getText().clear();*/

    }

    @Override
    public void onClick(View v) {

        if (v == button_next && checkSpinner()) {

            if (InternetConnection.checkNetworkConnection(this)) {


                person_id = ApplicationData.spilitStringSecond(spinner_person_name.getSelectedItem().toString());
                String url = ApplicationData.URL_INJURY_MORBIDITY + person_id;
                //Put
                Log.e("String are ", createJsonBody());
                new PutAsync().execute(url, createJsonBody());

                //Post
                //new PostAsync().execute("http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity", PostcreateJsonBody());


            } else

                showAlert(this);

        } else if (v == button_cancel) {

            cleartext();

        }
    }

    void moveToInjurySelectionActivity(int type, String id) {
        // Integer.parseInt(ApplicationData.spilitStringFirst(person.getInjury_type()))
        switch (type) {
            case 1:
                ApplicationData.gotToNextActivity(this, SuicideAttemptActivity.class, id);
                break;
            case 2:
                ApplicationData.gotToNextActivity(this, RoadTransportInjuryActivity.class, id);
                break;
            case 3:
                ApplicationData.gotToNextActivity(this, ViolenceInjuryActivity.class, id);
                break;
            case 4:
                ApplicationData.gotToNextActivity(this, FallInjuryActivity.class, id);
                break;
            case 5:
                ApplicationData.gotToNextActivity(this, CutInjuryActivity.class, id);
                break;
            case 6:
                ApplicationData.gotToNextActivity(this, BurnInjuryActivity.class, id);
                break;
            case 7:
                ApplicationData.gotToNextActivity(this, NearDrowningActivity.class, id);
                break;
            case 8:
                ApplicationData.gotToNextActivity(this, UnintentionalPoisoningActivity.class, id);
                break;
            case 9:
                ApplicationData.gotToNextActivity(this, ToolInjuryActivity.class, id);
                break;
            case 10:
                ApplicationData.gotToNextActivity(this, ElectrocautionActivity.class, id);
                break;
            case 11:
                ApplicationData.gotToNextActivity(this, InsectInjuryActivity.class, id);
                break;
            case 12:
                ApplicationData.gotToNextActivity(this, InjuryBluntActivity.class, id);
                break;
            case 13:
                ApplicationData.gotToNextActivity(this, SuffocationActivity.class, id);
                break;
            case 14:
                ApplicationData.gotToNextActivity(this, QualityOfLifeActivity.class, id);
                break;
        }
    }

    void showTextLong(String value) {

        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {

        Toast.makeText(activity, "You have to fill the form carefully", Toast.LENGTH_LONG).show();
        super.onBackPressed();

    }
}


