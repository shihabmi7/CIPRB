package com.alhikmah.ciprb;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alhikmah.ciprb.localdb.CiprbDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class InjuryMortalityActivity extends AppCompatActivity implements View.OnClickListener {

    Calendar myCalendar = null;
    DatePickerDialog.OnDateSetListener date, death_date;
    TimePickerDialog.OnTimeSetListener time;
    Activity activity = InjuryMortalityActivity.this;


    List<String> list;
    String person_id = "";
    ArrayAdapter<String> dataAdapter;
    CiprbDatabase ciprbDatabase;
    int alive_count = 0;

    ProgressDialog progressDialog = null;
    Button button_cancel, button_next;

    EditText edittext_date_of_injury, edittext_injured_parts, edittext_time_of_injury, edittext_death_date_of_person, edittext_time_take_health_facility, edittext_days_take_health_facility;
    Spinner spinner_how_injured, spinner_im_post_mortem_done, spinner_im_source_of_income_family, spinner_im_family_coping_loss,
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

    LinearLayout layout_type_admitted_health_facility, lay_type_of_disability,
            lay_type_of_anesthesia_given, lay_who_gave_first_aid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injury_mortality);

        init();

        myCalendar = Calendar.getInstance();

        try {
            ciprbDatabase = new CiprbDatabase(getApplicationContext());
            ciprbDatabase.open();


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


        } catch (NullPointerException e) {

            Log.e("" + getTitle(), "OnFailure" + e.toString());
            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();


        } catch (Exception e) {
            Log.e("" + getTitle(), "OnFailure" + e.toString());
            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();
        }


    }

    void init() {

        try {
            //layout_type_admitted_health_facility = (LinearLayout) findViewById(R.id.layout_type_admitted_health_facility);
            // lay_type_of_disability = (LinearLayout) findViewById(R.id.lay_type_of_disability);
            // lay_type_of_anesthesia_given = (LinearLayout) findViewById(R.id.lay_type_of_anesthesia_given);
            //lay_who_gave_first_aid = (LinearLayout) findViewById(R.id.lay_type_of_anesthesia_given);
            edittext_date_of_injury = (EditText) findViewById(R.id.edittext_date_of_injury);
            edittext_time_of_injury = (EditText) findViewById(R.id.edittext_time_of_injury);
            edittext_injured_parts = (EditText) findViewById(R.id.edittext_injured_parts);

            edittext_death_date_of_person = (EditText) findViewById(R.id.edittext_death_date_of_person);

            edittext_days_take_health_facility = (EditText) findViewById(R.id.edittext_injured_parts);
            edittext_time_take_health_facility = (EditText) findViewById(R.id.edittext_time_take_health_facility);

            spinner_im_family_coping_loss = (Spinner) findViewById(R.id.spinner_im_family_coping_loss);
            spinner_im_post_mortem_done = (Spinner) findViewById(R.id.spinner_im_post_mortem_done);


            spinner_person_name = (Spinner) findViewById(R.id.spinner_person_name);
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
            //   spinner_injured_person_become_disabled = (Spinner) findViewById(R.id.spinner_injured_person_become_disabled);
//            spinner_type_of_disability = (Spinner) findViewById(R.id.spinner_type_of_disability);
            // spinner_significant_source_of_income_for_family = (Spinner) findViewById(R.id.spinner_significant_source_of_income_for_family);
            //   spinner_family_being_coping_loss_income = (Spinner) findViewById(R.id.spinner_family_being_coping_loss_income);

            /*spinner_did_receive_first_aid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (position == 0) {

                        lay_who_gave_first_aid.setVisibility(View.VISIBLE);
                        // lay_how_injured.setVisibility(View.VISIBLE);

                    } else {

                        lay_who_gave_first_aid.setVisibility(View.GONE);
                        //lay_how_injured.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/
            /*spinner_admitted_health_facility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (position == 0) {

                        layout_type_admitted_health_facility.setVisibility(View.VISIBLE);
                        // lay_how_injured.setVisibility(View.VISIBLE);

                    } else {

                        layout_type_admitted_health_facility.setVisibility(View.GONE);
                        //lay_how_injured.setVisibility(View.GONE);

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
                        // lay_how_injured.setVisibility(View.VISIBLE);

                    } else {

                        lay_type_of_disability.setVisibility(View.GONE);
                        //lay_how_injured.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/

            button_cancel = (Button) findViewById(R.id.button_cancel);
            button_next = (Button) findViewById(R.id.button_next);

            button_cancel.setOnClickListener(this);
            button_next.setOnClickListener(this);

        } catch (NullPointerException e) {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();
        }


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

       /* editText_members_name.getText().clear();
        edittext_date_of_birth.getText().clear();
        edittext_current_age.getText().clear();
        editText_educatoin_level.getText().clear();

        if (edittext_how_many_injury_last_six != null)
            edittext_how_many_injury_last_six.getText().clear();*/

    }

    @Override
    public void onClick(View v) {


        if (v == button_next) {

            if (InternetConnection.checkNetworkConnection(this)) {

                if (!edittext_date_of_injury.getText().toString().isEmpty()
                        && !edittext_time_of_injury.getText().toString().isEmpty()) {


                    person_id = ApplicationData.spilitStringSecond(spinner_person_name.getSelectedItem().toString());
                    String url = ApplicationData.URL_INJURY_MORBIDITY + person_id;
                    //Put
                    Log.i("String are ", createJsonBody());
                    new PutAsync().execute(url, createJsonBody());


                } else {

                    Toast.makeText(activity, "Fill the form correctly", Toast.LENGTH_LONG).show();
                }


            } else

                showAlert(this);

        } else if (v == button_cancel) {

            cleartext();

        }
    }

    private class PutAsync extends AsyncTask<String, Void, String> {
        int value;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Please wait...");
            progressDialog.setTitle("Loading");
            progressDialog.setCancelable(true);
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

                int type = spinner_how_injured.getSelectedItemPosition() + 1;
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

    String createJsonBody() {

        String e17 = "";
        String e22 = "";
        String e26 = "";
        String e11 = "";

        if (layout_type_admitted_health_facility.getVisibility() == View.VISIBLE) {
            e17 = ApplicationData.spilitStringFirst(spinner_type_admitted_health_facility.getSelectedItem().toString());
        }
        /*if (lay_type_of_disability.getVisibility() == View.VISIBLE) {
            e26 = ApplicationData.spilitStringFirst(spinner_type_of_disability.getSelectedItem().toString());
        }*/
        if (lay_type_of_anesthesia_given.getVisibility() == View.VISIBLE) {
            e22 = ApplicationData.spilitStringFirst(spinner_type_of_anesthesia_given.getSelectedItem().toString());
        }
        if (lay_who_gave_first_aid.getVisibility() == View.VISIBLE) {
            e11 = ApplicationData.spilitStringFirst(spinner_who_gave_first_aid.getSelectedItem().toString());
        }

        //Log.i("Test String ", ApplicationData.spilitStringFirst(spinner_survey_suicide_where.getSelectedItem().toString()));
        String rep = "{" +
                "\"f01\":\"" +
                "" +
                "\",\"f02\":\"" +
                ApplicationData.spilitStringFirst((spinner_how_injured.getSelectedItem().toString()) +
                        "\",\"f03\":\"" +
                        edittext_date_of_injury.getText().toString() +
                        "\",\"f04\":\"" +
                        edittext_time_of_injury.getText().toString().toString() +
                        "\",\"f05\":\"" +
                        ApplicationData.spilitStringFirst(spinner_place_of_injury.getSelectedItem().toString())) +
                "\",\"f06\":\"" +
                ApplicationData.spilitStringFirst(spinner_injury_intent.getSelectedItem().toString()) +
                "\",\"f07\":\"" +
                "" +
                "\",\"f08\":\"" +
                ApplicationData.spilitStringFirst(spinner_condition_of_victims_after_injury.getSelectedItem().toString()) +
                "\",\"f09\":\"" +
                ApplicationData.spilitStringFirst(spinner_mobility_condition_after_injury.getSelectedItem().toString()) +
                "\",\"f10\":\"" +
                ApplicationData.spilitStringFirst(spinner_did_receive_first_aid.getSelectedItem().toString()) +
                "\",\"f11\":\"" +
                e11 +
                "\",\"f12\":\"" +
                ApplicationData.spilitStringFirst(spinner_Was_he_trained_in_first_aid.getSelectedItem().toString()) +
                "\",\"f13\":\"" +
                ApplicationData.spilitStringFirst(spinner_person_receive_treatment_for_injury.getSelectedItem().toString()) +
                "\",\"f14\":\"" +
                ApplicationData.spilitStringFirst(spinner_Who_provided_the_treatment.getSelectedItem().toString()) +
                "\",\"f15\":\"" +
                ApplicationData.spilitStringFirst(spinner_Where_receive_treatment.getSelectedItem().toString()) +
                "\",\"f16\":\"" +
                ApplicationData.spilitStringFirst(spinner_admitted_health_facility.getSelectedItem().toString()) +
                "\",\"f17\":\"" +
                e17 +
                "\",\"f18\":\"" +
                ApplicationData.spilitStringFirst(spinner_how_admitted_health_facility.getSelectedItem().toString()) +
                "\",\"f19\":\"" +
                edittext_time_take_health_facility.getText().toString() +
                "\",\"f20\":\"" +
                edittext_days_take_health_facility.getText().toString() +
                "\",\"f21\":\"" +
                ApplicationData.spilitStringFirst(spinner_any_surgery_operation_done.getSelectedItem().toString()) +
                "\",\"f22\":\"" +
                e22 +
                "\",\"f23\":\"" +
                ApplicationData.spilitStringFirst(spinner_outcome_of_treatment.getSelectedItem().toString()) +
                "\",\"f24\":\"" +
                "" +
                "\",\"f25\":\"" +
                ApplicationData.spilitStringFirst(spinner_injured_person_become_disabled.getSelectedItem().toString()) +
                "\",\"f26\":\"" +
                e26 +
                "\",\"f27\":\"" +
                "" +
                "\",\"f28\":\"" +
                ApplicationData.spilitStringFirst(spinner_im_post_mortem_done.getSelectedItem().toString()) +
                "\",\"f29\":\"" +
                ApplicationData.spilitStringFirst(spinner_im_source_of_income_family.getSelectedItem().toString()) +
                "\",\"f30\":\"" +
                ApplicationData.spilitStringFirst(spinner_im_family_coping_loss.getSelectedItem().toString()) +
                "\",\"f31\":\"" +
                "" +
                "\",\"f32\":\"" +
                "" +
                "\",\"f33\":\"" +
                "" +
                "\"}";
        //return "{\"g03\":\"10\"}";


        return rep;
        //return "";
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

    @Override
    public void onBackPressed() {

        Toast.makeText(activity, "You have to fill the form carefully", Toast.LENGTH_LONG).show();
        super.onBackPressed();
    }
}


