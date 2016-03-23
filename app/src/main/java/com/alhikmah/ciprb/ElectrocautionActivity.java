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

public class ElectrocautionActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinner_p01, spinner_p02, spinner_p03, spinner_p04;
    private TextView electrocution1, electrocution2, electrocution3, electrocution4, textView2, textView4, textView6;
    private Button button_cancel, button_next;


    ProgressDialog progressDialog;
    Activity activity = this;

    //String person_id = "101323212";
    String person_id = "";
    TextView textView_person_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrocution);


        try {

            textView_person_id = (TextView) findViewById(R.id.textView_person_id);
            person_id = getIntent().getExtras().getString(ApplicationData.KEY_PERSON);
            textView_person_id.setText("Person Id:" + person_id);

        } catch (NullPointerException e) {


        } catch (Exception e) {


        }
        initUI();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(true);
    }

    private void initUI() {

        electrocution1 = (TextView) findViewById(R.id.electrocution1);
        electrocution2 = (TextView) findViewById(R.id.electrocution2);
        electrocution3 = (TextView) findViewById(R.id.electrocution3);
        electrocution4 = (TextView) findViewById(R.id.electrocution4);

        spinner_p01 = (Spinner) findViewById(R.id.spinner_p01);
        spinner_p02 = (Spinner) findViewById(R.id.spinner_p02);
        spinner_p03 = (Spinner) findViewById(R.id.spinner_p03);
        spinner_p04 = (Spinner) findViewById(R.id.spinner_p04);


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

        progressDialog.show();
        // post with no parameters
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("house_member_id", person.getPerson_id());
        params.put("i01", person.getMembers_name());
        params.put("i02", person.getPerson_id());
        params.put("i03", person.getMembers_name());
        params.put("i04", person.getMembers_name());


        client.post(ApplicationData.URL_HOUSE_HOLD_MEMBERS, params,
                new JsonHttpResponseHandler() {
                    // Your implementation here
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        cleartext();
                        progressDialog.dismiss();

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
            String url = ApplicationData.URL_ELECTROCAUTION + person_id;
            new PutAsync().execute(url, createJsonBody());

        } else if (v == button_cancel) {


        }

    }

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
                "\"p01\":\"" +
                ApplicationData.spilitStringFirst(spinner_p01.getSelectedItem().toString()) +
                "\",\"p02\":\"" +
                ApplicationData.spilitStringFirst(spinner_p02.getSelectedItem().toString()) +
                "\",\"p03\":\"" +
                ApplicationData.spilitStringFirst(spinner_p03.getSelectedItem().toString()) +
                "\",\"p04\":\"" +
                ApplicationData.spilitStringFirst(spinner_p04.getSelectedItem().toString()) +
                "\"}";
        return jsonData;
    }

    private class PutAsync extends AsyncTask<String, Void, String> {

        int value = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
        //ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
        //activity.finish();
    }
}
