package com.alhikmah.ciprb;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alhikmah.ciprb.localdb.CiprbDatabase;
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
    int count = 01;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household_member_details);
        ciprbDatabase=new CiprbDatabase(getApplicationContext());
        ciprbDatabase.open();
        try {

            prefsValues = new PrefsValues(this);
            count = prefsValues.getSerial();

            formatter = new DecimalFormat("00");
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

                    if (position == 0) {

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
            progressDialog.setMessage("Please wait...");
            progressDialog.setTitle("Loading");
            progressDialog.setCancelable(true);


            // showTextLong(""+spinner_injury_last_six.getSelectedItem().toString());
            setheader();

        } catch (NullPointerException e) {

            Toast.makeText(getApplicationContext(), "" + e.toString(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "" + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    void setheader() {

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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        try {

            if (v == button_next && checkFieldStatus()) {
                if (InternetConnection.checkNetworkConnection(this)) {

                    // aPerson.setInjury_type(spinner_how_injured.getSelectedItem().toString());
                    if (linerar_how_injury.getVisibility() == View.VISIBLE
                            && !edittext_how_many_injury_last_six.getText().toString().isEmpty()) {

                        int num = Integer.parseInt(edittext_how_many_injury_last_six.getText().toString());

                        for (int i = 1; i <= num; i++) {

                            mCURRENT_MEMBER_ID = prefsValues.getHouseUniqueId() + "" + formatter.format(count);
                            count++;
                            //prefsValues.setSerial(count);
                            aPerson = new Person();
                            aPerson.setMembers_name(editText_members_name.getText().toString());
                            aPerson.setSex(spinner_sex.getSelectedItem().toString());
                            aPerson.setInjury_number(num);
                            aPerson.setPerson_id(mCURRENT_MEMBER_ID);
                            // if a injury happen
                            ciprbDatabase.insertIntoDB(mCURRENT_MEMBER_ID, editText_members_name.getText().toString());
                            //ApplicationData.alive_person_List.add(aPerson);

                        }

                    } else {

                        aPerson = new Person();
                        aPerson.setMembers_name(editText_members_name.getText().toString());
                        aPerson.setSex(spinner_sex.getSelectedItem().toString());

                        mCURRENT_MEMBER_ID = prefsValues.getHouseUniqueId() + "" + formatter.format(count);
                        count++;
                        //prefsValues.setSerial(count);
                        aPerson.setPerson_id(mCURRENT_MEMBER_ID);
                    }
                    // have to find a solution if only one man is there or no injury
                    saveDataToOnline(aPerson);
                } else
                    showAlert(aPerson);

            } else if (v == button_cancel) {
                //onBackPressed();
            }

        } catch (NullPointerException e) {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();

        } catch (Exception e)

        {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    void cleartext() {
        setheader();

        editText_members_name.getText().clear();
        edittext_date_of_birth.getText().clear();
        edittext_current_age.getText().clear();
        editText_educatoin_level.getText().clear();
        isResonder.setChecked(false);

        if (edittext_how_many_injury_last_six != null)
            edittext_how_many_injury_last_six.getText().clear();

    }

    void saveDataToOnline(Person person) {

        try {

            progressDialog.show();

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
            params.put("responder", isResonder.isPressed());
            params.put("interviewer_code", prefsValues.getInterviewer_code());
            params.put("injury_last_six", ApplicationData.spilitStringFirst(spinner_injury_last_six.getSelectedItem().toString()));
            params.put("interview_time", ApplicationData.getCurrentDate());
            params.put("household_no", prefsValues.gethouse_hold_no());
            if (linerar_how_injury.getVisibility() == View.VISIBLE) {

                params.put("how_many_injury_last_six", person.getInjury_number());
                //params.put("e02", person.getInjury_type());

            }

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
                                if (calculate_member >= member_no) {
                                    finish();
                                    if (ApplicationData.alive_person_List.size() != 0) {
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

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void showAlert(final Person person) {

        if (InternetConnection.isAvailable(activity)) {

            if (person != null) {
                saveDataToOnline(person);
            }

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

        //super.onBackPressed();
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

}