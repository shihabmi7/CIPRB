package com.ciprb.injury;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ciprb.injury.localdb.CiprbDatabase;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class HouseHoldMemberDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    EditText editText_educatoin_level, edittext_date_of_birth, edittext_current_age, editText_members_name, edittext_how_many_injury_last_six;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    Spinner spinner_occupasion, spinner_marital_status,
            spinner_sex, spinner_realation_with_hh, spinner_smoking,
            spinner_buttle_nut, spinner_swiming, spinner_injury_last_six, spinner_how_injured;

    PrefsValues prefsValues;
    RelativeLayout linerar_how_injury;
    int count = 00;
    int injury_count=01;
    int calculate_member = 0;
    Button button_cancel, button_next;
    int member_no;
    TextView house_hold_id;

    String value[] = {"খানা প্রধান", "", ""};
    LinearLayout linear_responder;
    DecimalFormat formatter;
    ProgressDialog progressDialog = null;
    Activity activity = this;

    String mCURRENT_MEMBER_ID = "";
    CheckBox isResonder;
    LinearLayout lay_how_injured;
    private Person aPerson;

    CiprbDatabase ciprbDatabase;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household_member_details);
        ciprbDatabase = new CiprbDatabase(getApplicationContext());
        // i close it now
        //ciprbDatabase.open();

        setTitle(getResources().getStringArray(R.array.survey_activity_title)[1]);
        // no need now
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {


            prefsValues = new PrefsValues(this);
            // shihab added code

            if (prefsValues.getMembersNo() == 0) {
                Toast.makeText(getApplicationContext(), "No Member to input", Toast.LENGTH_LONG).show();
                finish();
            }


            count = prefsValues.getSerial();

            formatter = new DecimalFormat("00");
            scrollView = (ScrollView) findViewById(R.id.scrollView);
            house_hold_id = (TextView) findViewById(R.id.house_hold_id);
            isResonder = (CheckBox) findViewById(R.id.chkb_responder);

            edittext_date_of_birth = (EditText) findViewById(R.id.edittext_date_of_birth);
            edittext_current_age = (EditText) findViewById(R.id.edittext_current_age);
            editText_members_name = (EditText) findViewById(R.id.editText_members_name);
            editText_educatoin_level = (EditText) findViewById(R.id.editText_educatoin_level);

            linear_responder = (LinearLayout) findViewById(R.id.linear_responder);
            spinner_occupasion = (Spinner) findViewById(R.id.spinner_occupasion);
            spinner_marital_status = (Spinner) findViewById(R.id.spinner_marital_status);
            spinner_sex = (Spinner) findViewById(R.id.spinner_sex);
            spinner_realation_with_hh = (Spinner) findViewById(R.id.spinner_realation_with_hh);

            spinner_smoking = (Spinner) findViewById(R.id.spinner_smoking);
            spinner_buttle_nut = (Spinner) findViewById(R.id.spinner_buttle_nut);
            spinner_swiming = (Spinner) findViewById(R.id.spinner_swiming);

            spinner_injury_last_six = (Spinner) findViewById(R.id.spinner_injury_last_six);
            edittext_how_many_injury_last_six = (EditText) findViewById(R.id.edittext_how_many_injury_last_six);
            spinner_how_injured = (Spinner) findViewById(R.id.spinner_how_injured);


            button_cancel = (Button) findViewById(R.id.button_can);
            button_next = (Button) findViewById(R.id.button_go);


            button_cancel.setOnClickListener(this);
            button_next.setOnClickListener(this);

            linerar_how_injury = (RelativeLayout) findViewById(R.id.linerar_how_injury);
            linerar_how_injury.setVisibility(View.GONE);

       /* lay_how_injured = (LinearLayout) findViewById(R.id.lay_how_injured);
        lay_how_injured.setVisibility(View.GONE);*/

            spinner_injury_last_six.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (position == 1) {

                        linerar_how_injury.setVisibility(View.VISIBLE);
                        // lay_how_injured.setVisibility(View.VISIBLE);

                    } else {

                        linerar_how_injury.setVisibility(View.GONE);
                        //lay_how_injured.setVisibility(View.GONE);

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


            member_no = prefsValues.getMembersNo();
            editText_members_name.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);


            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage(getString(R.string.loading_message));
            progressDialog.setTitle(getString(R.string.waiting));
            progressDialog.setCancelable(true);


            // showTextLong(""+spinner_injury_last_six.getSelectedItem().toString());
            setheader();

        } catch (NullPointerException e) {

            Toast.makeText(getApplicationContext(), "" + e.toString(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "" + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    boolean checkSpinner() {


        if (spinner_sex.getSelectedItemPosition() != 0
                && spinner_marital_status.getSelectedItemPosition() != 0
                && spinner_occupasion.getSelectedItemPosition() != 0
                && spinner_smoking.getSelectedItemPosition() != 0 &&
                spinner_buttle_nut.getSelectedItemPosition() != 0 &&
                spinner_swiming.getSelectedItemPosition() != 0 &&
                spinner_injury_last_six.getSelectedItemPosition()!=0) {

            //Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_LONG).show();

            return true;


        } else {

            Toast.makeText(getApplicationContext(), getString(R.string.suggestion), Toast.LENGTH_LONG).show();
            return false;
        }

    }

    void setSpinnerDefaultState() {

        spinner_realation_with_hh.setSelection(0);
        spinner_sex.setSelection(0);
        spinner_marital_status.setSelection(0);
        spinner_occupasion.setSelection(0);
        spinner_smoking.setSelection(0);
        spinner_buttle_nut.setSelection(0);
        spinner_swiming.setSelection(0);
        spinner_injury_last_six.setSelection(0);
        edittext_date_of_birth.getText().clear();;
        edittext_current_age.setText("");
        edittext_how_many_injury_last_six.setText("");

    }

    void setheader() {

        Log.e("setheader","called:" +"Total Members: " + member_no + "   Remain Member: " + (member_no - calculate_member));
        house_hold_id.setText("Total Members: " + member_no + "   Remain Member: " + (member_no - calculate_member));
        prefsValues.setMembers_no(member_no - calculate_member);
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext_date_of_birth.setText(sdf.format(myCalendar.getTime()));

    }

    @Override
    protected void onResume() {
        super.onResume();
        //count = 1;
    }

    String responderStatus() {

        if (isResonder.isChecked()) {
            return "yes";

        } else {

            return "no";
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        try {
            // set check must
//
//            && checkFieldStatus() && checkSpinner()
            if (v == button_next ) {

                // Toast.makeText(activity, responderStatus(), Toast.LENGTH_LONG).show();

//                if (InternetConnection.checkNetworkConnection(this)) {


                    // if a injury happen

                    if (linerar_how_injury.getVisibility() == View.VISIBLE
                            && !edittext_how_many_injury_last_six.getText().toString().isEmpty()) {

                        int num = Integer.parseInt(edittext_how_many_injury_last_six.getText().toString());
                        ciprbDatabase.open();
                        calculate_member++;

                        Log.e("count", "" + count);
                        for (int i = 1; i <= num; i++) {

                            mCURRENT_MEMBER_ID = prefsValues.getHouseUniqueId() + "" + formatter.format(count)+""+ formatter.format(injury_count);
                            //count++; //
                            //prefsValues.setSerial(count);
                            aPerson = new Person();
                            aPerson.setMembers_name(editText_members_name.getText().toString());
                            aPerson.setSex(spinner_sex.getSelectedItem().toString());
                            aPerson.setInjury_number(num);
                            aPerson.setPerson_id(mCURRENT_MEMBER_ID);

                            prefsValues.setPerson_id_for_house_hold_characteristics(mCURRENT_MEMBER_ID);

                            ciprbDatabase.insertIntoDB(mCURRENT_MEMBER_ID, editText_members_name.getText().toString());
                            //ApplicationData.alive_person_List.add(aPerson);
                            saveDataForInjuredPerson(aPerson);
                            injury_count++; // added newly
                        }
                        cleartext();
                        count++;
                        injury_count=01;
                         // added newly
                        Log.d("AMLOG:: Mem++", "calculate_member: " + calculate_member);
                        // ciprbDatabase.close();
                    }
                    // if no injury happen
                    else {

                        aPerson = new Person();
                        aPerson.setMembers_name(editText_members_name.getText().toString());
                        aPerson.setSex(spinner_sex.getSelectedItem().toString());

                        mCURRENT_MEMBER_ID = prefsValues.getHouseUniqueId() + "" + formatter.format(count);
                        count++;
                        //prefsValues.setSerial(count);
                        aPerson.setPerson_id(mCURRENT_MEMBER_ID);
                        prefsValues.setPerson_id_for_house_hold_characteristics(mCURRENT_MEMBER_ID);

                        saveDataForNormalPerson(aPerson);

                    }
                    // have to find a solution if only one man is there or no injury

                }
           /* else

                    showAlert(aPerson);*/

          /*  } else if (v == button_cancel) {
                //onBackPressed();
            }*/

        } catch (NullPointerException e) {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();

        } catch (Exception e)

        {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    void saveDataForInjuredPerson(Person person) {

        try {


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
            params.put("smoking", ApplicationData.spilitStringFirst(spinner_smoking.getSelectedItem().toString()));
            params.put("bettle_nut_chew", ApplicationData.spilitStringFirst(spinner_buttle_nut.getSelectedItem().toString()));
            params.put("swimming", ApplicationData.spilitStringFirst(spinner_swiming.getSelectedItem().toString()));
            params.put("responder", responderStatus());
            params.put("interviewer_code", prefsValues.getInterviewer_code());
            params.put("injury_last_six", ApplicationData.spilitStringFirst(spinner_injury_last_six.getSelectedItem().toString()));
            params.put("interview_time", ApplicationData.getCurrentDate());
            params.put("household_no", prefsValues.gethouse_hold_no());
            if (linerar_how_injury.getVisibility() == View.VISIBLE) {

                params.put("how_many_injury_last_six", person.getInjury_number());
                //params.put("e02", person.getInjury_type());

            }

            if (InternetConnection.checkNetworkConnection(this)) {

                progressDialog.show();

                client.post(ApplicationData.URL_HOUSE_HOLD_MEMBERS, params,
                        new JsonHttpResponseHandler() {
                            // save to data base ,
                            // check if count > member then go to next activity n save to
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                                if (statusCode == ApplicationData.STATUS_SUCCESS) {
                                    progressDialog.dismiss();
                                    showTextLong(" : Data saved Successfully...: " + mCURRENT_MEMBER_ID);
                                    prefsValues.setSerial(count);
                                    scrollView.setY(0);
                                    if (calculate_member >= member_no) {

                                        if (ciprbDatabase.getAlivePersonList().size() != 0) {

                                            ciprbDatabase.close();
                                            ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
                                            finish();
                                        } else {
                                            //  go to home activity n fill up home characteristics
                                            ApplicationData.gotToNextActivity(activity, HomeActivity.class);
                                            finish();
                                        }
                                    }

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
            } else {  // No internet connection

                String json = getJsonObject(person).toString();
                ApplicationData.writeToFile(this, ApplicationData.OFFLINE_DB_HOUSE_HOLD_MEMBERS, json);

                //calculate_member++;
                prefsValues.setSerial(count);
                Toast.makeText(getApplicationContext(),"Offline Data Saved...",Toast.LENGTH_LONG).show();
                Log.e("saveDataInjuredPerson","calculate member: "+calculate_member+"   member_no:  "+member_no);
                if (calculate_member >= member_no) {

                    if (ciprbDatabase.getAlivePersonList().size() != 0) {
//                        ciprbDatabase.close();
                        ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
                        finish();
                    } else {
                        //  go to home activity n fill up home characteristics
                        ApplicationData.gotToNextActivity(activity, HomeActivity.class);
                        finish();
                    }
                }
              //  cleartext();


            }
        } catch (Exception e) {

            e.printStackTrace();

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    void saveDataForNormalPerson(Person person) {

        try {

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
            params.put("smoking", ApplicationData.spilitStringFirst(spinner_smoking.getSelectedItem().toString()));
            params.put("bettle_nut_chew", ApplicationData.spilitStringFirst(spinner_buttle_nut.getSelectedItem().toString()));
            params.put("swimming", ApplicationData.spilitStringFirst(spinner_swiming.getSelectedItem().toString()));
            params.put("responder", responderStatus());
            params.put("interviewer_code", prefsValues.getInterviewer_code());
            params.put("injury_last_six", ApplicationData.spilitStringFirst(spinner_injury_last_six.getSelectedItem().toString()));
            params.put("interview_time", ApplicationData.getCurrentDate());
            params.put("household_no", prefsValues.gethouse_hold_no());
            if (linerar_how_injury.getVisibility() == View.VISIBLE) {

                params.put("how_many_injury_last_six", person.getInjury_number());
                //params.put("e02", person.getInjury_type());

            }

            if (InternetConnection.checkNetworkConnection(this)) {

                progressDialog.show();
                client.post(ApplicationData.URL_HOUSE_HOLD_MEMBERS, params,
                        new JsonHttpResponseHandler() {
                            // save to data base ,
                            // check if count > member then go to next activity n save to
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                calculate_member++;
                                if (statusCode == ApplicationData.STATUS_SUCCESS) {
                                    progressDialog.dismiss();
                                    showTextLong(" : Data saved Successfully...: " + mCURRENT_MEMBER_ID);
                                    prefsValues.setSerial(count);
                                    scrollView.setY(0);
                                    Log.e("saveDataNormalPerson", "calculate member: " + calculate_member + "   member_no:  " + member_no);
                                    if (calculate_member >= member_no) {

                                        if (ciprbDatabase.getAlivePersonList().size() != 0) {
                                            ciprbDatabase.close();
                                            ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
                                            finish();
                                        } else {
                                            //  go to home activity n fill up home characteristics
                                            ApplicationData.gotToNextActivity(activity, HomeActivity.class);
                                            finish();
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
            }  else {
             // No internet connection
                Log.e("AMLOG:saveINjury", "No Internet connection");
                Log.e("AMLOG:: ", "Save Offline");

                String json = getJsonObject(person).toString();
                ApplicationData.writeToFile(this, ApplicationData.OFFLINE_DB_HOUSE_HOLD_MEMBERS, json);
                StringBuilder stringBuilder = ApplicationData.readFile(this, ApplicationData.OFFLINE_DB_HOUSE_HOLD_MEMBERS);
                Log.e("AMLOG::filedata: ", stringBuilder.toString());

                calculate_member++;
                prefsValues.setSerial(count);

                Toast.makeText(getApplicationContext()," Save Data Offline, To get update to server Press Sync when internet available",Toast.LENGTH_LONG).show();
                scrollView.setY(0);
                if (calculate_member >= member_no) {

                    if (ciprbDatabase.getAlivePersonList().size() != 0) {
//                        ciprbDatabase.close();
                        ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
                        finish();
                    } else {
                        //  go to home activity n fill up home characteristics
                        ApplicationData.gotToNextActivity(activity, HomeActivity.class);
                        finish();
                    }
                }

                cleartext();
            }


        } catch (NullPointerException e) {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public JSONObject getJsonObject(Person person) {

        JSONObject houseHoldPostJson = new JSONObject();
        try {

            houseHoldPostJson.put("household_unique_code", person.getPerson_id());
            houseHoldPostJson.put("name", person.getMembers_name());
            houseHoldPostJson.put("sex", ApplicationData.spilitStringFirst(person.getSex()));
            houseHoldPostJson.put("date_of_birth", edittext_date_of_birth.getText().toString());
            houseHoldPostJson.put("maritial_status", ApplicationData.spilitStringFirst(spinner_marital_status.getSelectedItem().toString()));
            houseHoldPostJson.put("education", editText_educatoin_level.getText().toString());
            houseHoldPostJson.put("relation_with_hh", ApplicationData.spilitStringFirst(spinner_realation_with_hh.getSelectedItem().toString()));
            houseHoldPostJson.put("age", edittext_current_age.getText().toString());
            houseHoldPostJson.put("occupasion", ApplicationData.spilitStringFirst(spinner_occupasion.getSelectedItem().toString()));
            houseHoldPostJson.put("smoking", ApplicationData.spilitStringFirst(spinner_smoking.getSelectedItem().toString()));
            houseHoldPostJson.put("bettle_nut_chew", ApplicationData.spilitStringFirst(spinner_buttle_nut.getSelectedItem().toString()));
            houseHoldPostJson.put("swimming", ApplicationData.spilitStringFirst(spinner_swiming.getSelectedItem().toString()));
            houseHoldPostJson.put("responder", responderStatus());
            houseHoldPostJson.put("interviewer_code", prefsValues.getInterviewer_code());
            houseHoldPostJson.put("injury_last_six", ApplicationData.spilitStringFirst(spinner_injury_last_six.getSelectedItem().toString()));
            houseHoldPostJson.put("interview_time", ApplicationData.getCurrentDate());
            houseHoldPostJson.put("household_no", prefsValues.gethouse_hold_no());
            if (linerar_how_injury.getVisibility() == View.VISIBLE) {

                houseHoldPostJson.put("how_many_injury_last_six", person.getInjury_number());
                //params.put("e02", person.getInjury_type());

            }
        }catch (Exception e) {

        }
        return houseHoldPostJson;
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

    private boolean checkFieldStatus() {

        if (!editText_members_name.getText().toString().isEmpty()
                && !edittext_current_age.getText().toString().isEmpty()) {

            return true;

        } else {
            Toast.makeText(getApplicationContext(), "Enter All Data Correctly ", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        // correctionDialog();

    }

    public void correctionDialog() {

        new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Any Correction")
                .setMessage("")
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                activity.finish();

                            }

                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                // no works
                            }

                        }).show();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
    void cleartext() {
        setheader();
        setSpinnerDefaultState();

        editText_members_name.setText("");
        edittext_date_of_birth.setText("");
        edittext_current_age.setText("");
        editText_educatoin_level.setText("");
        isResonder.setChecked(false);

        if (edittext_how_many_injury_last_six != null)
            edittext_how_many_injury_last_six.getText().clear();

    }

}