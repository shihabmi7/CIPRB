package com.alhikmah.ciprb;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class HouseHoldMemberDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    EditText editText_educatoin_level, editText_interviewer_unique, edittext_date_of_birth, edittext_current_age, editText_members_name, edittext_how_many_injury_last_six;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    Spinner spinner_occupasion, spinner_marital_status,
            spinner_sex, spinner_realation_with_hh, spinner_smoking,
            spinner_buttle_nut, spinner_swiming, spinner_injury_last_six, spinner_how_injured;

    PrefsValues prefsValues;
    RelativeLayout linerar_how_injury;
    int count = 1;
    Button button_cancel, button_next;
    int member_no;
    TextView house_hold_id;

    String value[] = {"খানা প্রধান", "", ""};
    LinearLayout linear_responder;
    DecimalFormat formatter;
    ProgressDialog progressDialog;
    Activity activity = this;

    String mCURRENT_MEMBER_ID = "";

    CheckBox isResonder;

    LinearLayout lay_how_injured;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household_member_details);

        prefsValues = new PrefsValues(this);
        formatter = new DecimalFormat("00");
        house_hold_id = (TextView) findViewById(R.id.house_hold_id);
        isResonder = (CheckBox) findViewById(R.id.chkb_responder);

        edittext_date_of_birth = (EditText) findViewById(R.id.edittext_date_of_birth);
        edittext_current_age = (EditText) findViewById(R.id.edittext_current_age);
        editText_members_name = (EditText) findViewById(R.id.editText_members_name);
        editText_educatoin_level = (EditText) findViewById(R.id.editText_members_name);
        editText_interviewer_unique = (EditText) findViewById(R.id.editText_interviewer_unique);
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

        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_next = (Button) findViewById(R.id.button_next);
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

        /*if (count == 1) {
            house_hold_id.setText(value[0]);
        }*/

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(true);

        // showTextLong(""+spinner_injury_last_six.getSelectedItem().toString());

    }

    void cleartext() {

        editText_members_name.getText().clear();
        edittext_date_of_birth.getText().clear();
        edittext_current_age.getText().clear();
        editText_educatoin_level.getText().clear();

        if (edittext_how_many_injury_last_six != null)
            edittext_how_many_injury_last_six.getText().clear();

    }

    private void updateLabel() {

        String myFormat = "dd/mm/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext_date_of_birth.setText(sdf.format(myCalendar.getTime()));
    }


    void saveDataToOnline(Person person) {

        progressDialog.show();
        // post with no parameters
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("house_member_id", person.getPerson_id());
        params.put("name", person.getMembers_name());
        params.put("sex", ApplicationData.spilitStringFirst(person.getSex()));
        params.put("date_of_birth", edittext_current_age.getText().toString());
        params.put("maritial_status", ApplicationData.spilitStringFirst(spinner_marital_status.getSelectedItem().toString()));
        params.put("education", edittext_current_age.getText().toString());
        params.put("relation_with_hh", ApplicationData.spilitStringFirst(spinner_realation_with_hh.getSelectedItem().toString()));
        params.put("age", edittext_current_age.getText().toString());
        params.put("occupasion", ApplicationData.spilitStringFirst(spinner_occupasion.getSelectedItem().toString()));
        params.put("smoking", ApplicationData.spilitStringFirst(spinner_smoking.getSelectedItem().toString()));
        params.put("bettle_nut_chew", ApplicationData.spilitStringFirst(spinner_buttle_nut.getSelectedItem().toString()));
        params.put("swimming", ApplicationData.spilitStringFirst(spinner_swiming.getSelectedItem().toString()));
        params.put("responder", isResonder.isPressed());
        params.put("interviewer_code", editText_interviewer_unique.getText().toString());
        params.put("injury_last_six", ApplicationData.spilitStringFirst(spinner_injury_last_six.getSelectedItem().toString()));

        if (linerar_how_injury.getVisibility() == View.VISIBLE) {

            params.put("how_many_injury_last_six", person.getInjury_number());
            //params.put("e02", person.getInjury_type());
        }

        client.post(ApplicationData.URL_HOUSE_HOLD_MEMBERS, params,
                new JsonHttpResponseHandler() {
                    // Your implementation here
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        cleartext();
                        progressDialog.dismiss();
                        if (count >= member_no) {
                            showTextLong("finish this input");
                            //ApplicationData.memberListHashMap.clear();
                            ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
                        }
                    }
                }
        );
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

    @Override
    protected void onResume() {
        super.onResume();
        count = 1;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (v == button_next) {
            if (InternetConnection.checkNetworkConnection(this)) {

                if (!editText_members_name.getText().toString().isEmpty()
                        && !edittext_current_age.getText().toString().isEmpty() && !edittext_date_of_birth.getText().toString().isEmpty()) {


                    mCURRENT_MEMBER_ID = ApplicationData.HOUSE_HOLD_UNIQE_ID + formatter.format(count);

                    /*ApplicationData.memberListHashMap.put(mCURRENT_MEMBER_ID,
                            editText_members_name.getText().toString());
                    */

                    Person aPerson = new Person();
                    aPerson.setMembers_name(editText_members_name.getText().toString());
                    aPerson.setSex(spinner_sex.getSelectedItem().toString());
                    // aPerson.setInjury_type(spinner_how_injured.getSelectedItem().toString());

                    if (linerar_how_injury.getVisibility() == View.VISIBLE
                            && !edittext_how_many_injury_last_six.getText().toString().isEmpty()) {

                        aPerson.setInjury_number(Integer.parseInt(edittext_how_many_injury_last_six.getText().toString()));

                        for (int i = 0; i < aPerson.getInjury_number(); i++) {
                            aPerson.setPerson_id(ApplicationData.HOUSE_HOLD_UNIQE_ID + formatter.format(count));
                            count++;


                            // if a injury happen
                            ApplicationData.alive_person_List.add(aPerson);
                        }

                    } else {
                        aPerson.setPerson_id(ApplicationData.HOUSE_HOLD_UNIQE_ID + formatter.format(count));
                        count++;
                    }

                    // have to find a solution if only one man is there or no injury
                    if (count >= member_no) {

                        if (ApplicationData.alive_person_List.size() != 0) {
                            ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
                        } else {
                            //  go to home activity n fill up home characteristics
                            ApplicationData.gotToNextActivity(activity, HomeActivity.class);
                        }
                    }
                    showTextLong("" + aPerson.getMembers_name() + " : Data saved Successfully...: " + mCURRENT_MEMBER_ID);
                    //ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
                    // open this >>>>
                    //saveDataToOnline(aPerson);


                    // save to data base , check if count > member then go to next activity n save to

                } else {
                    showTextLong(" empty field not allowed");
                }

            } else
                showAlert(this);
        } else if (v == button_cancel) {

            cleartext();

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


    void showTextLong(String value) {

        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();
    }

}