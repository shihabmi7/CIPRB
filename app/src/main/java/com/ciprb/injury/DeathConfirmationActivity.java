package com.ciprb.injury;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ciprb.injury.localdb.CiprbDatabase;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DeathConfirmationActivity extends AppCompatActivity implements View.OnClickListener {

    Calendar myCalendar, myDeathCalender;
    DatePickerDialog.OnDateSetListener date, deathdate;

    RelativeLayout linerar_how_injury;
    int count = 01;
    int calculate_member = 0;
    Button button_cancel, button_next;
    int death_member_no;
    TextView house_hold_id;
    PrefsValues prefsValues;
    ProgressDialog progressDialog = null;

    EditText editText_death_date;
    Activity activity = this;

    EditText editText_sicness_time, editText_educatoin_level, edittext_date_of_birth, edittext_current_age,
            editText_members_name, edittext_how_many_injury_last_six;

    Spinner spinner_occupasion, spinner_marital_status, spinner_death_sex, spinner_realation_with_hh, spinner_cause_death, spinner_death_place;
    //private String mCURRENT_MEMBER_ID = "35457899923232";

    private String mCURRENT_MEMBER_ID = "";
    private Person aPerson;
    DecimalFormat formatter;
    CiprbDatabase ciprbDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death_confirmation);

        ciprbDatabase = new CiprbDatabase(getApplicationContext());
        ciprbDatabase.open();

        setTitle(getResources().getStringArray(R.array.survey_activity_title)[3]);


        try {
            prefsValues = new PrefsValues(this);
            formatter = new DecimalFormat("00");
            death_member_no = prefsValues.getMembers_died_no();
            count = prefsValues.getDeathSerial();

            //mCURRENT_MEMBER_ID = prefsValues.getHouseUniqueId();
            //showTextLong(" : Current HouseHold ID  " + mCURRENT_MEMBER_ID);

          /*  if (death_member_no == 0 || mCURRENT_MEMBER_ID.length() < 9) {
                Toast.makeText(this, "No Death person here", Toast.LENGTH_LONG).show();
                finish();
            }*/

            house_hold_id = (TextView) findViewById(R.id.house_hold_id);
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

            myDeathCalender = Calendar.getInstance();
            deathdate = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                    myDeathCalender.set(Calendar.YEAR, year);
                    myDeathCalender.set(Calendar.MONTH, monthOfYear);
                    myDeathCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDeathLabel();
                }
            };

            editText_death_date.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    new DatePickerDialog(activity, deathdate, myDeathCalender
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }

            });


            spinner_occupasion = (Spinner) findViewById(R.id.spinner_occupasion);
            spinner_marital_status = (Spinner) findViewById(R.id.spinner_marital_status);
            spinner_death_sex = (Spinner) findViewById(R.id.spinner_death_sex);
            spinner_realation_with_hh = (Spinner) findViewById(R.id.spinner_realation_with_hh);
            spinner_death_place = (Spinner) findViewById(R.id.spinner_death_place);
            spinner_cause_death = (Spinner) findViewById(R.id.spinner_cause_death);
            //spinner_how_injured = (Spinner) findViewById(R.id.spinner_how_injured);

            button_cancel = (Button) findViewById(R.id.button_cancel);
            button_next = (Button) findViewById(R.id.button_next);

            button_cancel.setOnClickListener(this);
            button_next.setOnClickListener(this);

            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage(getString(R.string.loading_message));
            progressDialog.setTitle(getString(R.string.waiting));
            progressDialog.setCancelable(true);


            setheader();

           // getRadioButtonGroupData();


        } catch (Exception e) {


        }
    }

    String getRadioButtonGroupData() {


        String result = "";
        try {

            // get value from radio button
            RadioGroup rg_fever = (RadioGroup) findViewById(R.id.rg_fever);
            RadioGroup rg_rash = (RadioGroup) findViewById(R.id.rg_rash);
            RadioGroup rg_growth = (RadioGroup) findViewById(R.id.rg_growth);
            RadioGroup rg_jaundice = (RadioGroup) findViewById(R.id.rg_jaundice);
            RadioGroup rg_cough = (RadioGroup) findViewById(R.id.rg_cough);
            RadioGroup rg_respiratory = (RadioGroup) findViewById(R.id.rg_respiratory);
            RadioGroup rg_diarea = (RadioGroup) findViewById(R.id.rg_diarea);
            RadioGroup rg_vomitting = (RadioGroup) findViewById(R.id.rg_vomitting);
            RadioGroup rg_abdominal_pain = (RadioGroup) findViewById(R.id.rg_abdominal_pain);
            RadioGroup rg_abdominal_distension = (RadioGroup) findViewById(R.id.rg_abdominal_distension);
            RadioGroup rg_weight_loss = (RadioGroup) findViewById(R.id.rg_weight_loss);
            RadioGroup rg_chest_pain = (RadioGroup) findViewById(R.id.rg_chest_pain);
            RadioGroup rg_mass = (RadioGroup) findViewById(R.id.rg_mass);

            RadioGroup rg_stiff_neck = (RadioGroup) findViewById(R.id.rg_stiff_neck);
            RadioGroup rg_headache = (RadioGroup) findViewById(R.id.rg_headache);
            RadioGroup rg_convulsion = (RadioGroup) findViewById(R.id.rg_convulsion);
            RadioGroup rg_fits_unconsciousness = (RadioGroup) findViewById(R.id.rg_fits_unconsciousness);
            RadioGroup rg_paralysis = (RadioGroup) findViewById(R.id.rg_paralysis);
            RadioGroup rg_urine_colour = (RadioGroup) findViewById(R.id.rg_urine_colour);
            RadioGroup rg_surgery_operation = (RadioGroup) findViewById(R.id.rg_surgery_operation);
            RadioGroup rg_Others_specify = (RadioGroup) findViewById(R.id.rg_Others_specify);


            if (rg_fever.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += "fever:" + ((RadioButton) findViewById(rg_fever.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_rash.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",rash:" + ((RadioButton) findViewById(rg_rash.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_growth.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",growth:" + ((RadioButton) findViewById(rg_growth.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_jaundice.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",jaundice:" + ((RadioButton) findViewById(rg_jaundice.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_cough.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",cough:" + ((RadioButton) findViewById(rg_cough.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_respiratory.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",respiratory:" + ((RadioButton) findViewById(rg_respiratory.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_diarea.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",diarrohea:" + ((RadioButton) findViewById(rg_diarea.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_vomitting.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",vomitting:" + ((RadioButton) findViewById(rg_vomitting.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_abdominal_pain.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",abdominal pain:" + ((RadioButton) findViewById(rg_abdominal_pain.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_abdominal_distension.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",abdominal distension:" + ((RadioButton) findViewById(rg_abdominal_distension.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_weight_loss.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",weight loss:" + ((RadioButton) findViewById(rg_weight_loss.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_chest_pain.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",chest pain:" + ((RadioButton) findViewById(rg_chest_pain.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_mass.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",mass:" + ((RadioButton) findViewById(rg_mass.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_stiff_neck.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",stiff neck:" + ((RadioButton) findViewById(rg_stiff_neck.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_headache.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",headache:" + ((RadioButton) findViewById(rg_headache.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_convulsion.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",convulsion:" + ((RadioButton) findViewById(rg_convulsion.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_fits_unconsciousness.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",fits unconsciousness:" + ((RadioButton) findViewById(rg_fits_unconsciousness.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_paralysis.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",paralysis:" + ((RadioButton) findViewById(rg_paralysis.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_urine_colour.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",urine colour:" + ((RadioButton) findViewById(rg_urine_colour.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_surgery_operation.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",surgery operation:" + ((RadioButton) findViewById(rg_surgery_operation.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_Others_specify.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",Others:" + ((RadioButton) findViewById(rg_Others_specify.getCheckedRadioButtonId())).getText().toString();
            }

          //  Toast.makeText(activity, result, Toast.LENGTH_LONG).show();

        } catch (NullPointerException e) {

            Toast.makeText(activity, "" + e.toString(), Toast.LENGTH_LONG).show();

        }


        return result.toString();
    }

    String getRadioButtonGroupDataForChronicDeasease() {


        String result = "";
        try {

            // get value from radio button
            RadioGroup rg_deaseases_heart = (RadioGroup) findViewById(R.id.rg_deaseases_heart);
            RadioGroup rg_deaseases_hypertension = (RadioGroup) findViewById(R.id.rg_deaseases_hypertension);
            RadioGroup rg_deaseases_cancer = (RadioGroup) findViewById(R.id.rg_deaseases_cancer);
            RadioGroup rg_deaseases_diabatic = (RadioGroup) findViewById(R.id.rg_deaseases_diabatic);



            if (rg_deaseases_heart.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += "heart:" + ((RadioButton) findViewById(rg_deaseases_heart.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_deaseases_hypertension.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",hypertension:" + ((RadioButton) findViewById(rg_deaseases_hypertension.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_deaseases_cancer.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",cancer:" + ((RadioButton) findViewById(rg_deaseases_cancer.getCheckedRadioButtonId())).getText().toString();
            }
            if (rg_deaseases_diabatic.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
            } else {
                result += ",diabatics:" + ((RadioButton) findViewById(rg_deaseases_diabatic.getCheckedRadioButtonId())).getText().toString();
            }

            //Toast.makeText(activity, result, Toast.LENGTH_LONG).show();

        } catch (NullPointerException e) {

            Toast.makeText(activity, "" + e.toString(), Toast.LENGTH_LONG).show();

        }


        return result.toString();
    }

    boolean checkSpinner() {

        if (spinner_occupasion.getSelectedItemPosition() != 0
                && spinner_occupasion.getSelectedItemPosition() != 0
                && spinner_marital_status.getSelectedItemPosition() != 0 &&

                spinner_death_place.getSelectedItemPosition() != 0 && !editText_members_name.getText().toString().isEmpty()
                && !edittext_current_age.getText().toString().isEmpty()) {

            //Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_LONG).show();

            return true;


        } else {

            Toast.makeText(getApplicationContext(), getString(R.string.suggestion), Toast.LENGTH_LONG).show();
            return false;
        }

    }

    void setSpinnerDefaultState() {

        spinner_occupasion.setSelection(0);
        spinner_occupasion.setSelection(0);
        spinner_marital_status.setSelection(0);
        spinner_death_place.setSelection(0);

    }


    private String updateBirthLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext_date_of_birth.setText(sdf.format(myCalendar.getTime()));

        return sdf.format(myCalendar.getTime());
    }

    private String updateDeathLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText_death_date.setText(sdf.format(myDeathCalender.getTime()));

        return sdf.format(myDeathCalender.getTime());
    }

    void setheader() {

        house_hold_id.setText("Total Members: " + death_member_no + "   Remain Member: " + (death_member_no - calculate_member));
        prefsValues.setMembers_died_no(death_member_no - calculate_member);
    }

    @Override
    public void onClick(View v) {

        if (v == button_next && checkSpinner()) {

            // getRadioButtonGroupData();
            if (InternetConnection.checkNetworkConnection(this)) {

                mCURRENT_MEMBER_ID = prefsValues.getHouseUniqueId() + formatter.format(count);
                Log.e("Death Person ID:", mCURRENT_MEMBER_ID);

                //  mCURRENT_MEMBER_ID = "" + 1013232 + "" + formatter.format(count);
                //prefsValues.setSerial(count);
                count++;
                Log.e("Death Person ID:", "" + count);
                // have to find a solution if only one man is there or no injury
                //showTextLong(mCURRENT_MEMBER_ID);
                //  saveDataToOnline(aPerson);
                new PostAsync().execute(ApplicationData.URL_DEATH_CONFIRMATION, createJsonBody());

            } else {

                showAlert(aPerson);

            }
        } else if (v == button_cancel) {

            cleartext();


        }
    }

   /* void saveDataToOnline(Person person) {

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
            params.put("d01", editText_injury_date.getText().toString());
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

                                if (calculate_member >= death_member_no) {
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

    }*/

    void cleartext() {
        setheader();
        setSpinnerDefaultState();

        editText_members_name.getText().clear();
        edittext_date_of_birth.getText().clear();
        editText_death_date.getText().clear();
        edittext_current_age.getText().clear();
        editText_educatoin_level.getText().clear();

    }

    public void showAlert(final Person person) {


        if (InternetConnection.isAvailable(activity)) {


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
            try {
                if (value == ApplicationData.STATUS_SUCCESS) {

                    //// TODO: 3/22/2016

//                    aPerson = new Person();
//                    aPerson.setMembers_name(editText_members_name.getText().toString());
//                    aPerson.setSex(spinner_death_sex.getSelectedItem().toString());
//                    aPerson.setPerson_id(mCURRENT_MEMBER_ID);

                    int death_type = Integer.parseInt(ApplicationData.spilitStringFirst(spinner_cause_death.getSelectedItem().toString()));

                    // if death is caused by injury 1 0r 2
                    if (death_type == 1) {

                        ciprbDatabase.insertIntoDeathDB(mCURRENT_MEMBER_ID, editText_members_name.getText().toString());

                        Toast.makeText(activity, "Success: Saved In Device Database " + death_type, Toast.LENGTH_LONG).show();

                    }

                    // starts from zero & increment here
                    calculate_member++;
                    // Toast.makeText(activity, "Success: Death Type" + death_type, Toast.LENGTH_LONG).show();
                    prefsValues.setDeathSerial(count);
                    cleartext();


                    if (calculate_member >= death_member_no) {

                        if (!ciprbDatabase.getDeathPersonList().isEmpty()) {


                            ApplicationData.gotToNextActivity(activity, InjuryMortalityActivity.class);
                            finish();

                        } else {
                            //  go to home activity n fill up home characteristics

                            ApplicationData.gotToNextActivity(activity, HomeActivity.class);
                            finish();

                        }


                    } else {

                        Toast.makeText(activity, "Success: Enter Another Died Person Data" + death_type, Toast.LENGTH_LONG).show();

                    }


                    progressDialog.dismiss();

                } else {
                    Toast.makeText(activity, "Failed", Toast.LENGTH_LONG).show();
                }

            } catch (NullPointerException e) {

            } catch (Exception e) {

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
                    "\",\"date_of_birth\":\"" +
                    edittext_date_of_birth.getText().toString() +
                    "\",\"maritial_status\":\"" +
                    ApplicationData.spilitStringFirst(spinner_marital_status.getSelectedItem().toString()) +
                    "\",\"education\":\"" +
                    editText_educatoin_level.getText().toString() +
                    "\",\"relation_with_hh\":\"" +
                    ApplicationData.spilitStringFirst(spinner_realation_with_hh.getSelectedItem().toString()) +
                    "\",\"age\":\"" +
                    edittext_current_age.getText().toString() +
                    "\",\"occupasion\":\"" +
                    ApplicationData.spilitStringFirst(spinner_occupasion.getSelectedItem().toString()) +
                    "\",\"interview_time\":\"" +
                    ApplicationData.getCurrentDate() +
                    "\",\"interviewer_code\":\"" +
                    prefsValues.getInterviewer_code() +
                    "\",\"d01\":\"" +
                    editText_death_date.getText().toString() +
                    "\",\"d02\":\"" +
                    "" +
                    "\",\"d03\":\"" +
                    ApplicationData.spilitStringFirst(spinner_death_place.getSelectedItem().toString()) +
                    "\",\"d04\":\"" +
                    getRadioButtonGroupData() +
                    "\",\"d05\":\"" +
                    getRadioButtonGroupDataForChronicDeasease() +
                    "\",\"d06\":\"" +
                    editText_sicness_time.getText().toString() +
                    "\",\"d07\":\"" +
                    ApplicationData.spilitStringFirst(spinner_cause_death.getSelectedItem().toString()) +
                    "\"}";


        } catch (NullPointerException e) {

            Log.e("Error: DeathConf", "" + e.toString());
        }

        //return "{\"g03\":\"10\"}";
        return rep;
    }

}


