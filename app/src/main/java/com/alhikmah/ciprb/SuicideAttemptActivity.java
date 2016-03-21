package com.alhikmah.ciprb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
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

import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SuicideAttemptActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner_survey_suicide_where, spinner_survey_suicide_how, spinner_survey_suicide_type;
    private Button button_cancel, button_next;
    ProgressDialog progressDialog;
    SuicideAttemptActivity activity = this;

    final static int SUCCESS = 1;
    final static int FAILURE = 0;

    //String person_id = "101323210";
    String person_id = "";
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


    public String putRequestWithHeaderAndBody(String url, String jsonBody) throws IOException {


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        Response httpResponse = client.newCall(request).execute();
        httpResponse.code();
        Log.i("Response JsonBody are ", jsonBody);
        Log.i("Response data are ", response.body().string());
        Log.i("Response code are ", "" + httpResponse.code());
        //makeCall(client, request);

        return response.body().string();
    }

    public String postRequestWithHeaderAndBody(String url, String jsonBody) throws IOException {


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Response httpResponse = client.newCall(request).execute();
        httpResponse.code();

        Log.i("Response data are ", response.body().string());
        Log.i("Response code are ", "" + httpResponse.code());
        //makeCall(client, request);

        return response.body().string();
    }

    String createJsonBody() {
        Log.i("Test String ", ApplicationData.spilitStringFirst(spinner_survey_suicide_where.getSelectedItem().toString()));
        String rep = "{" +
                "\"g01\":\"" +
                ApplicationData.spilitStringFirst(spinner_survey_suicide_where.getSelectedItem().toString()) +
                "\",\"g02\":\"" +
                ApplicationData.spilitStringFirst(spinner_survey_suicide_how.getSelectedItem().toString()) +
                "\",\"g03\":\"" +
                ApplicationData.spilitStringFirst(spinner_survey_suicide_type.getSelectedItem().toString()) +
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
        String Result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                Log.i("URL are ", params[0]);
                // Result = putRequestWithHeaderAndBody(params[0], params[1]);

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

    private class PostAsync extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String Result = "";
                Log.i("URL are ", params[0]);
                Result = postRequestWithHeaderAndBody(params[0], params[1]);

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


        }

    }

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
            //saveDataToOnline();
            try {

                if (person_id.length() > 0) {

                    //putRequestWithHeaderAndBody(ApplicationData.URL_SUICIDE + person_id);
                    String url = ApplicationData.URL_SUICIDE + person_id;
                    //Put
                    Log.i("String are ", createJsonBody());
                    new PutAsync().execute(url, createJsonBody());
                    //Post
                    //new PostAsync().execute("http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity", PostcreateJsonBody());

                } else {
                }

            } catch (Exception e) {
//Log.i("Exception ",e.getMessage());

            }


            /// new PostThread();

//            prepare();
//
        } else if (v == button_cancel) {

        }

    }


}
