package com.ciprb.injury;

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

/**
 * Created by Shihab on 3/12/2016.
 */
public class RoadTransportInjuryActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinner_talking_over_mobile;
    Button button_next, button_cancel;
    private ProgressDialog progressDialog;
    Activity activity = this;

    private Spinner spinner_h01, spinner_h02, spinner_h03,
            spinner_h04, spinner_h05, spinner_h06, spinner_h07,
            spinner_h08, spinner_h09;


    //String person_id = "101323210";
    String person_id = "";
    TextView textView_person_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_injury);
        //String person_id = "101323210";
        try {

            setTitle( getResources().getStringArray(R.array.survey_activity_title)[6]);

            textView_person_id = (TextView) findViewById(R.id.textView_person_id);
            person_id = getIntent().getExtras().getString(ApplicationData.KEY_PERSON);
            textView_person_id.setText("Person Id:" + person_id);

        } catch (NullPointerException e) {


        } catch (Exception e) {


        }
        initUI();
    }

    private void initUI() {
        spinner_h01 = (Spinner) findViewById(R.id.spinner_h01);
        spinner_h02 = (Spinner) findViewById(R.id.spinner_h02);
        spinner_h03 = (Spinner) findViewById(R.id.spinner_h03);
        spinner_h04 = (Spinner) findViewById(R.id.spinner_h04);
        spinner_h05 = (Spinner) findViewById(R.id.spinner_h05);
        spinner_h06 = (Spinner) findViewById(R.id.spinner_h06);
        spinner_h07 = (Spinner) findViewById(R.id.spinner_h07);
        spinner_h08 = (Spinner) findViewById(R.id.spinner_h08);
        spinner_h09 = (Spinner) findViewById(R.id.spinner_h09);

        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_next = (Button) findViewById(R.id.button_next);

        button_cancel.setOnClickListener(this);
        button_next.setOnClickListener(this);
    }

    void cleartext() {

      /*  editText_members_name.getText().clear();
        edittext_date_of_birth.getText().clear();
        edittext_current_age.getText().clear();
        editText_educatoin_level.getText().clear();

        if (edittext_how_many_injury_last_six != null)
            edittext_how_many_injury_last_six.getText().clear();*/

    }

    void saveDataToOnline(Person person) {
        // Activity activity = this;
        // progressDialog.show();
        // post with no parameters
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("house_member_id", person.getPerson_id());
        params.put("h01", person.getMembers_name());
        params.put("h02", person.getPerson_id());
        params.put("h03", person.getMembers_name());
        params.put("h04", person.getMembers_name());
        params.put("h05", person.getPerson_id());
        params.put("h06", person.getMembers_name());
        params.put("h07", person.getMembers_name());
        params.put("h09", person.getPerson_id());


        client.post(ApplicationData.URL_HOUSE_HOLD_MEMBERS, params,
                new JsonHttpResponseHandler() {
                    // Your implementation here
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        cleartext();

                        showTextLong("finish this input");
                        //ApplicationData.memberListHashMap.clear();
                        ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);

                    }
                }
        );
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
            String url = ApplicationData.URL_ROADTRANSPORTINJURY + person_id;
            new PutAsync().execute(url, createJsonBody());

        } else if (v == button_cancel) {
            this.finish();

        }

    }


    //ali Code
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

        Log.i("Response data are ", response.body().string());
        Log.i("Response code are ", "" + response.code());
        //makeCall(client, request);

        return response.body().string();
    }

    String createJsonBody() {
        String jsonData = "{" +
                "\"h01\":\"" +
                ApplicationData.spilitStringFirst(spinner_h01.getSelectedItem().toString()) +
                "\",\"h02\":\"" +
                ApplicationData.spilitStringFirst(spinner_h02.getSelectedItem().toString()) +
                "\",\"h03\":\"" +
                ApplicationData.spilitStringFirst(spinner_h03.getSelectedItem().toString()) +
                "\",\"h04\":\"" +
                ApplicationData.spilitStringFirst(spinner_h04.getSelectedItem().toString()) +
                "\",\"h05\":\"" +
                ApplicationData.spilitStringFirst(spinner_h05.getSelectedItem().toString()) +
                "\",\"h06\":\"" +
                ApplicationData.spilitStringFirst(spinner_h06.getSelectedItem().toString()) +
                "\",\"h07\":\"" +
                ApplicationData.spilitStringFirst(spinner_h07.getSelectedItem().toString()) +
                "\",\"h08\":\"" +
                ApplicationData.spilitStringFirst(spinner_h08.getSelectedItem().toString()) +
                "\",\"h09\":\"" +
                ApplicationData.spilitStringFirst(spinner_h09.getSelectedItem().toString()) +
                "\"}";
        return jsonData;
    }

    private class PutAsync extends AsyncTask<String, Void, String> {

        int value = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage(getString(R.string.loading_message));
            progressDialog.setTitle(getString(R.string.waiting));
            progressDialog.setCancelable(true);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                Log.i("URL are ", params[0]);
                value = ApplicationData.putRequestWithBody(params[0], params[1]);

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

                finishTask();

            } else {
                Toast.makeText(activity, "Failed", Toast.LENGTH_LONG).show();
            }

        }

    }

    void finishTask() {

        Toast.makeText(activity, "Successfully Data Saved", Toast.LENGTH_LONG).show();
        ApplicationData.INJURY_DATA_COLLECT = true;
        cleartext();
        //onBackPressed();
        activity.finish();
        // ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
        //activity.finish();
    }


}

