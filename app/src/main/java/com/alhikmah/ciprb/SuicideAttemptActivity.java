package com.alhikmah.ciprb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;


public class SuicideAttemptActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner_survey_suicide_where, spinner_survey_suicide_how, spinner_survey_suicide_type;
    private Button button_cancel, button_next;
    ProgressDialog progressDialog;
    SuicideAttemptActivity activity = this;

    final static int SUCCESS = 1;
    final static int FAILURE = 0;

    //String person_id = "101323210";
    String person_id = "101323214";
    TextView textView_person_id;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

        //params.put("household_unique_code", person_id);
        params.put("g01", ApplicationData.spilitStringFirst(spinner_survey_suicide_where.getSelectedItem().toString()));
        params.put("g02", ApplicationData.spilitStringFirst(spinner_survey_suicide_how.getSelectedItem().toString()));
        params.put("g03", ApplicationData.spilitStringFirst(spinner_survey_suicide_type.getSelectedItem().toString()));

        // String generate = ApplicationData.URL_SUICIDE + "" = g01 =;
        //http://saeradesign.com/LumenApi/public/index.php/api/household/suicideattemptactivity/
        // 101323210?household_unique_code=101323210&g01=11111111111111111&g02=11111111111&g03=111111111111111111111

//        String generate = ApplicationData.URL_SUICIDE + person_id +
//                "?g01=" + g01 + "&g02=" + g02 + "&g03=" + g03;
        String generate = ApplicationData.URL_SUICIDE + person_id;

        Log.e("URL", generate);
        client.post(generate, params,
                new JsonHttpResponseHandler() {
                    // Your implementation here
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        if (statusCode == 200) {
                            cleartext();
                            progressDialog.dismiss();
                            showTextLong("Saved");
                            //ApplicationData.memberListHashMap.clear();
                            ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
                        } else {
                            Log.e("onFailure: suicide", response.toString());
                            progressDialog.dismiss();
                            showTextLong("Not Saved");
                            // super.onFailure(statusCode, headers, responseString, throwable);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {


                        progressDialog.dismiss();
                        showTextLong("Not Saved");
                        //ApplicationData.memberListHashMap.clear();
                        ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);


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


        String g01 = ApplicationData.spilitStringFirst(spinner_survey_suicide_where.getSelectedItem().toString());
        String g02 = ApplicationData.spilitStringFirst(spinner_survey_suicide_how.getSelectedItem().toString());
        String g03 = ApplicationData.spilitStringFirst(spinner_survey_suicide_type.getSelectedItem().toString());
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
//            saveDataToOnline();

            new PostThread();

//            prepare();
//
        } else if (v == button_cancel) {

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SuicideAttempt Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.alhikmah.ciprb/http/host/path")
        );
        AppIndex.AppIndexApi.start(client2, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SuicideAttempt Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.alhikmah.ciprb/http/host/path")
        );
        AppIndex.AppIndexApi.end(client2, viewAction);
        client2.disconnect();
    }


    public class PostThread extends Thread {


        public void run() {

            URL url = null;
            try {
                //        String generate = ApplicationData.URL_SUICIDE + person_id +
//                "?g01=" + g01 + "&g02=" + g02 + "&g03=" + g03;
                String generate = ApplicationData.URL_SUICIDE + person_id;

                String g01 = ApplicationData.spilitStringFirst(spinner_survey_suicide_where.getSelectedItem().toString());
                String g02 = ApplicationData.spilitStringFirst(spinner_survey_suicide_how.getSelectedItem().toString());
                String g03 = ApplicationData.spilitStringFirst(spinner_survey_suicide_type.getSelectedItem().toString());

                String REQUEST_URL = ApplicationData.URL_SUICIDE + person_id +
                        "?g01=" + g01 + "&g02=" + g02 + "&g03=" + g03;

                url = new URL(REQUEST_URL);
                HttpURLConnection client = (HttpURLConnection) url.openConnection();
                int statusCode = client.getResponseCode();
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String line;
                    while ((line = r.readLine()) != null) {

                        JSONObject respObject = new JSONObject(line);
                        int success = respObject.getInt("success");
                        if (success == 1) {
                            Log.i("REQUEST URL:", "postReq" + success);
                            postHandler.sendEmptyMessage(SUCCESS);
                        } else {
                            postHandler.sendEmptyMessage(FAILURE);
                        }
                    }
                } else {
                    postHandler.sendEmptyMessage(FAILURE);
                }

            } catch (MalformedURLException e) {
                postHandler.sendEmptyMessage(FAILURE);
                e.printStackTrace();
            } catch (IOException e) {
                postHandler.sendEmptyMessage(FAILURE);
                e.printStackTrace();
            } catch (JSONException e) {
                postHandler.sendEmptyMessage(FAILURE);
                e.printStackTrace();
            }


        }

        @SuppressLint("HandlerLeak")
        Handler postHandler = new Handler() {
            public void handleMessage(Message msg) {

                if (msg.what == SUCCESS) {

                    Toast.makeText(activity,"shohag success",Toast.LENGTH_LONG).show();

                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                    alert.setMessage("You must fill  brand name or generic name");
                    alert.setNegativeButton("Please fill up",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    alert.show();
                }

            }

        };

    }

}
