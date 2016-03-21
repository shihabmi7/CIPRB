package com.alhikmah.ciprb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class SuicideAttemptActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner_survey_suicide_where, spinner_survey_suicide_how, spinner_survey_suicide_type;
    private Button button_cancel, button_next;
    ProgressDialog progressDialog;
    SuicideAttemptActivity activity = this;

    //String person_id = "101323210";
    String person_id = "101323210";
    TextView textView_person_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suicide_attempt);

        try {

            textView_person_id = (TextView) findViewById(R.id.textView_person_id);
            person_id = getIntent().getExtras().getString(ApplicationData.KEY_PERSON);
            textView_person_id.setText("Person Id:" + person_id);

        } catch (NullPointerException e) {


        } catch (Exception e) {


        }

        Toast.makeText(activity, "" + person_id, Toast.LENGTH_LONG).show();
     /*   Person aPerson = (Person) getIntent().getSerializableExtra("aPerson");
        Toast.makeText(this, "" + aPerson.getPerson_id() + aPerson.getMembers_name(), Toast.LENGTH_LONG).show();*/

        spinner_survey_suicide_where = (Spinner) findViewById(R.id.spinner_survey_suicide_where);
        spinner_survey_suicide_how = (Spinner) findViewById(R.id.spinner_survey_suicide_how);
        spinner_survey_suicide_type = (Spinner) findViewById(R.id.spinner_survey_suicide_type);

        button_cancel = (Button) findViewById(R.id.button_sui_cancel);
        button_next = (Button) findViewById(R.id.button_sui_next);

        button_cancel.setOnClickListener(this);
        button_next.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(true);
    }


    void cleartext() {

      /*  editText_members_name.getText().clear();
        edittext_date_of_birth.getText().clear();
        edittext_current_age.getText().clear();
        editText_educatoin_level.getText().clear();

        if (edittext_how_many_injury_last_six != null)
            edittext_how_many_injury_last_six.getText().clear();*/

    }

//    params.put("household_unique_code", person_id);
//    params.put("g01", ApplicationData.spilitStringFirst(spinner_survey_suicide_where.getSelectedItem().toString()));
//    params.put("g02", ApplicationData.spilitStringFirst(spinner_survey_suicide_how.getSelectedItem().toString()));
//    params.put("g03", ApplicationData.spilitStringFirst(spinner_survey_suicide_type.getSelectedItem().toString()));
//

    void saveDataToOnline() {

        progressDialog.show();
        // post with no parameters
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        String g01 = ApplicationData.spilitStringFirst(spinner_survey_suicide_where.getSelectedItem().toString());
        String g02 = ApplicationData.spilitStringFirst(spinner_survey_suicide_how.getSelectedItem().toString());
        String g03 = ApplicationData.spilitStringFirst(spinner_survey_suicide_type.getSelectedItem().toString());
        // String generate = ApplicationData.URL_SUICIDE + "" = g01 =;

        //http://saeradesign.com/LumenApi/public/index.php/api/household/suicideattemptactivity/
        // 101323210?household_unique_code=101323210&g01=11111111111111111&g02=11111111111&g03=111111111111111111111

        String generate = ApplicationData.URL_SUICIDE + person_id + "?household_unique_code=" + person_id +
                "&g01=" + g01 + "&g02=" + g02 + "&g03=" + g03;

        Log.e("URL", generate);
        client.put(generate,
                new JsonHttpResponseHandler() {
                    // Your implementation here
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        cleartext();
                        progressDialog.dismiss();
                        showTextLong("Saved");
                        //ApplicationData.memberListHashMap.clear();
                        ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                        Log.e("onFailure: suicide", responseString);
                        progressDialog.dismiss();
                        showTextLong("Not Saved");
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                }
        );
    }

    void prepare() {
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient(); // not null

        RequestParams params = new RequestParams();
        //params.put("household_unique_code", person_id);
        params.put("g01", ApplicationData.spilitStringFirst(spinner_survey_suicide_where.getSelectedItem().toString()));
        params.put("g02", ApplicationData.spilitStringFirst(spinner_survey_suicide_how.getSelectedItem().toString()));
        params.put("g03", ApplicationData.spilitStringFirst(spinner_survey_suicide_type.getSelectedItem().toString()));


       /* String g01 = ApplicationData.spilitStringFirst(spinner_survey_suicide_where.getSelectedItem().toString());
        String g02 = ApplicationData.spilitStringFirst(spinner_survey_suicide_how.getSelectedItem().toString());
        String g03 = ApplicationData.spilitStringFirst(spinner_survey_suicide_type.getSelectedItem().toString());*/
        // String generate = ApplicationData.URL_SUICIDE + "" = g01 =;

        // http://saeradesign.com/LumenApi/public/index.php/api/household/suicideattemptactivity/
        // 101323210?household_unique_code=101323210&g01=hello&g02=mama&g03=ami to sesh
       /* String generate = ApplicationData.URL_SUICIDE + person_id + "?household_unique_code=" + person_id +
                "&g01=" + g01 + "&g02=" + g02 + "&g03=" + g03;
*/

        String URL = ApplicationData.URL_SUICIDE + person_id + "?";
        client.put(URL, params, new JsonHttpResponseHandler() {
            // Your implementation here
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                cleartext();
                progressDialog.dismiss();
                showTextLong("finish this input");
                //ApplicationData.memberListHashMap.clear();
                ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                Log.e("onFailure: suicide", responseString);
                progressDialog.dismiss();
                showTextLong("Not Saved");
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }


    public void showAlert(final Activity activity) {

        if (InternetConnection.isAvailable(activity)) {


        } else {

            new AlertDialog.Builder(activity)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("No Internet Connection")
                    .setMessage(getString(R.string.internet_check_bn))
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {


        if (v == button_next) {
            // finish();
            saveDataToOnline();

//            prepare();
//
        } else if (v == button_cancel) {

        }

    }
}
