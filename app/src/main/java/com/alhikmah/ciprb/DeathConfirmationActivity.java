package com.alhikmah.ciprb;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class DeathConfirmationActivity extends AppCompatActivity implements View.OnClickListener {

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date, deathdate;

    RelativeLayout linerar_how_injury;
    int count = 01;
    int calculate_member = 0;
    Button button_cancel, button_next;
    int member_no;
    TextView house_hold_id;
    PrefsValues prefsValues;
    ProgressDialog progressDialog = null;
    EditText editText_death_date;
    Activity activity = this;

    EditText editText_sicness_time, editText_educatoin_level, edittext_date_of_birth, edittext_current_age,
            editText_members_name, edittext_how_many_injury_last_six;

    Spinner spinner_occupasion, spinner_marital_status, spinner_death_sex, spinner_realation_with_hh, spinner_cause_death, spinner_death_place;
    private String mCURRENT_MEMBER_ID = "125481214";
    private Person aPerson;
    DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death_confirmation);

        // get value from radio button
        // RadioGroup rg = (RadioGroup)findViewById(R.id.youradio);
        //  String radiovalue = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();

        try {
            prefsValues = new PrefsValues(this);
            formatter = new DecimalFormat("00");
            member_no = prefsValues.getMembers_died_no();
            count = ApplicationData.SERIAL_DEATH;

            //mCURRENT_MEMBER_ID = prefsValues.getHouseUniqueId();

            showTextLong(" : Current HouseHold ID  " + mCURRENT_MEMBER_ID);

            /*if (member_no == 0 || mCURRENT_MEMBER_ID.length() < 7) {
                Toast.makeText(this, "No Death person here", Toast.LENGTH_LONG).show();
                finish();
            }*/

            edittext_date_of_birth = (EditText) findViewById(R.id.edittext_date_of_birth);
            editText_death_date = (EditText) findViewById(R.id.editText_death_date);
            edittext_current_age = (EditText) findViewById(R.id.edittext_current_age);
            editText_members_name = (EditText) findViewById(R.id.editText_members_name);
            editText_members_name.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            editText_educatoin_level = (EditText) findViewById(R.id.editText_educatoin_level);
            editText_sicness_time = (EditText) findViewById(R.id.editText_sicness_time);

            myCalendar = Calendar.getInstance();
            date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateBirthLabel();
                }
            };

            edittext_date_of_birth.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    new DatePickerDialog(activity, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }

            });

            deathdate = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDeathLabel();
                }
            };

            editText_death_date.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    new DatePickerDialog(activity, deathdate, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }

            });


            spinner_occupasion = (Spinner) findViewById(R.id.spinner_occupasion);
            spinner_marital_status = (Spinner) findViewById(R.id.spinner_marital_status);
            spinner_death_sex = (Spinner) findViewById(R.id.spinner_death_sex);
            spinner_realation_with_hh = (Spinner) findViewById(R.id.spinner_realation_with_hh);
            spinner_death_place = (Spinner) findViewById(R.id.spinner_death_place);
            spinner_cause_death = (Spinner) findViewById(R.id.spinner_death_place);
            //spinner_how_injured = (Spinner) findViewById(R.id.spinner_how_injured);

            button_cancel = (Button) findViewById(R.id.button_cancel);
            button_next = (Button) findViewById(R.id.button_next);


            button_cancel.setOnClickListener(this);
            button_next.setOnClickListener(this);

            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Please wait...");
            progressDialog.setTitle("Loading");
            progressDialog.setCancelable(true);


        } catch (Exception e) {


        }
    }

    private void updateBirthLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext_date_of_birth.setText(sdf.format(myCalendar.getTime()));

    }

    private void updateDeathLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText_death_date.setText(sdf.format(myCalendar.getTime()));

    }

    void setheader() {

        house_hold_id.setText("Total Members: " + member_no + "   Remain Member: " + (member_no - calculate_member));

    }

    @Override
    public void onClick(View v) {

        if (v == button_next) {
            if (InternetConnection.checkNetworkConnection(this)) {
                if (!editText_members_name.getText().toString().isEmpty()
                        && !edittext_current_age.getText().toString().isEmpty()) {

                    aPerson = new Person();
                    aPerson.setMembers_name(editText_members_name.getText().toString());
                    aPerson.setSex(spinner_death_sex.getSelectedItem().toString());

                    //  mCURRENT_MEMBER_ID = ApplicationData.HOUSE_HOLD_UNIQE_ID + "" + formatter.format(count);

                    //  mCURRENT_MEMBER_ID = "" + 1013232 + "" + formatter.format(count);
                    //prefsValues.setSerial(count);

                    Log.e("Death Person ID:", mCURRENT_MEMBER_ID);
                    aPerson.setPerson_id(mCURRENT_MEMBER_ID);
                    count++;
                    // have to find a solution if only one man is there or no injury
                    //showTextLong(mCURRENT_MEMBER_ID);
                    ///ApplicationData.died_person_List.add(aPerson)
                    //  saveDataToOnline(aPerson);

                    new PostAsync().execute(ApplicationData.URL_DEATH_CONFIRMATION, createJsonBody());

                } else
                    showTextLong("Fill data correctly");
            } else {

                showAlert(aPerson);

            }
        } else if (v == button_cancel) {

            cleartext();


        }
    }

    void saveDataToOnline(Person person) {

        try {

            progressDialog.show();

            int death_type = Integer.parseInt(ApplicationData.spilitStringFirst(spinner_cause_death.getSelectedItem().toString()));

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("household_unique_code", person.getPerson_id());
            params.put("name", person.getMembers_name());
            params.put("sex", ApplicationData.spilitStringFirst(person.getSex()));
            params.put("date_of_birth", edittext_date_of_birth.getText().toString());
            params.put("maritial_status", ApplicationData.spilitStringFirst(spinner_marital_status.getSelectedItem().toString()));
            params.put("education", editText_educatoin_level.getText().toString());
            params.put("relation_with_hh", ApplicationData.spilitStringFirst(spinner_realation_with_hh.getSelectedItem().toString()));
            params.put("age", edittext_current_age.getText().toString());
            params.put("occupasion", ApplicationData.spilitStringFirst(spinner_occupasion.getSelectedItem().toString()));
            params.put("interview_time", ApplicationData.getCurrentDate());
            params.put("d01", editText_death_date.getText().toString());
            // done sex params.put("d02", ApplicationData.getCurrentDate());
            params.put("d03", ApplicationData.spilitStringFirst(spinner_death_place.getSelectedItem().toString()));
            params.put("d04", ApplicationData.getCurrentDate());
            params.put("d05", ApplicationData.getCurrentDate());
            params.put("d06", editText_sicness_time.getText().toString());


            params.put("d07", "" + death_type);


            client.post(ApplicationData.URL_DEATH_CONFIRMATION, params,
                    new JsonHttpResponseHandler() {
                        // save to data base ,
                        // check if count > member then go to next activity n save to
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                            Log.e("Death confirmation", "" + response.toString());

                            calculate_member++;
                            progressDialog.dismiss();

                            if (statusCode == ApplicationData.STATUS_SUCCESS) {

                                showTextLong(" : Data saved Successfully...: " + mCURRENT_MEMBER_ID);

                                if (calculate_member >= member_no) {
                                    finish();
                                    if (ApplicationData.died_person_List.size() != 0) {
                                        ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
                                    } else {
                                        //  go to home activity n fill up home characteristics
                                        ApplicationData.gotToNextActivity(activity, HomeActivity.class);
                                    }
                                }
                                cleartext();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                            progressDialog.dismiss();
                            showTextLong(" : Data not Saved Successfully...: " + statusCode);
                            super.onFailure(statusCode, headers, responseString, throwable);
                            Log.e("" + getTitle(), "OnFailure" + statusCode);

                        }
                    }
            );
        } catch (NullPointerException e) {
        }

    }

    void cleartext() {
        setheader();

        editText_members_name.getText().clear();
        edittext_date_of_birth.getText().clear();
        edittext_current_age.getText().clear();
        editText_educatoin_level.getText().clear();

    }

    public void showAlert(final Person person) {

        if (InternetConnection.isAvailable(activity)) {

            if (person != null)
                saveDataToOnline(person);

        } else {

            new AlertDialog.Builder(activity)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("No Internet Connection")
                    .setMessage(getString(R.string.internet_check_bn))
                    .setPositiveButton("Ok",
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
                                    if (person != null)
                                        showAlert(person);

                                }

                            }).show();
        }

    }

    void showTextLong(String value) {
        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();
    }

    private class PostAsync extends AsyncTask<String, Void, String> {
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
                value = ApplicationData.postRequestWithHeaderAndBody(params[0], params[1]);
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
                Toast.makeText(activity, "Success", Toast.LENGTH_LONG).show();
                //ApplicationData.alive_person_List.remove(spinner_person_name.getSelectedItemPosition());
                cleartext();

            } else {
                Toast.makeText(activity, "Failed", Toast.LENGTH_LONG).show();
            }


        }

    }

    String createJsonBody() {
//        Log.i("Test String ", ApplicationData.spilitStringFirst(spinner_survey_suicide_where.getSelectedItem().toString()));

        String rep = "";
        try {

            rep = "{" +
                    "\"household_unique_code\":\"" +
                    mCURRENT_MEMBER_ID +
                    "\",\"name\":\"" +
                    editText_members_name.getText().toString() +
                    "\",\"sex\":\"" +
                    ApplicationData.spilitStringFirst(spinner_death_sex.getSelectedItem().toString()) +
                    "\"date_of_birth\":\"" +
                    edittext_date_of_birth.getText().toString() +
                    "\"maritial_status\":\"" +
                    ApplicationData.spilitStringFirst(spinner_marital_status.getSelectedItem().toString()) +
                    "\"education\":\"" +
                    editText_educatoin_level.getText().toString() +
                    "\"relation_with_hh\":\"" +
                    ApplicationData.spilitStringFirst(spinner_realation_with_hh.getSelectedItem().toString()) +
                    "\"age\":\"" +
                    edittext_current_age.getText().toString() +
                    "\"occupasion\":\"" +
                    ApplicationData.spilitStringFirst(spinner_occupasion.getSelectedItem().toString()) +
                    "\"interview_time\":\"" +
                    "" +
                    "\"d01\":\"" +
                    editText_death_date.getText().toString() +
                    "\"d02\":\"" +
                    "" +
                    "\"d03\":\"" +
                    ApplicationData.spilitStringFirst(spinner_death_place.getSelectedItem().toString()) +
                    "\"d04\":\"" +
                    "" +
                    "\"d05\":\"" +
                    "" +
                    "\"d06\":\"" +
                    editText_sicness_time.getText().toString() +
                    "\"d07\":\"" +
                    ApplicationData.spilitStringFirst(spinner_cause_death.getSelectedItem().toString()) +
                    "\"}";


        } catch (NullPointerException e) {

            Log.e("Error: DeathConf", "" + e.toString());
        }

        //return "{\"g03\":\"10\"}";
        return rep;
    }
}


