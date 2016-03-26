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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QualityOfLifeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_cancel, button_next;
    private TextView Quality1, Quality2, Quality3, Quality4, Quality5;
    private Spinner spinner_s1, spinner_s2, spinner_s3, spinner_s4, spinner_s5;

    EditText edt_s6,editText_person_id;

    ProgressDialog progressDialog;
    Activity activity = this;

    //String person_id = "101323210";
    String person_id = "";
    TextView textView_person_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_of_life);

        setTitle( getResources().getStringArray(R.array.survey_activity_title)[19]);

        try {

            initUI();

           // person_id = getIntent().getExtras().getString(ApplicationData.KEY_PERSON);
            //textView_person_id.setText("Person Id:" + person_id);

        } catch (NullPointerException e) {

        } catch (Exception e) {

        }

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(getString(R.string.loading_message));
        progressDialog.setTitle(getString(R.string.waiting));
        progressDialog.setCancelable(true);
    }

    private void initUI() {
        textView_person_id = (TextView) findViewById(R.id.textView_person_id);
        editText_person_id =(EditText)findViewById(R.id.editText_person_id);
        spinner_s1 = (Spinner) findViewById(R.id.spinner_s1);
        spinner_s2 = (Spinner) findViewById(R.id.spinner_s2);
        spinner_s3 = (Spinner) findViewById(R.id.spinner_s3);
        spinner_s4 = (Spinner) findViewById(R.id.spinner_s4);
        spinner_s5 = (Spinner) findViewById(R.id.spinner_s5);
        edt_s6 = (EditText) findViewById(R.id.edt_s6);
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

    /*void saveDataToOnline(Person person) {

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
    }*/


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

            if (InternetConnection.checkNetworkConnection(activity)) {

                person_id = editText_person_id.getText().toString();

                if (person_id.length()==11){

                    String url = ApplicationData.URL_QUALITY_OF_LIFE + person_id;
                    new PutAsync().execute(url, createJsonBody());

                }else
                    Toast.makeText(activity," Set Eleven (11) digit unique code",Toast.LENGTH_LONG).show();

            }

         else

        { showAlert(this);}



        } else if (v == button_cancel) {


            finish();
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
                "\"s1\":\"" +
                ApplicationData.spilitStringFirst(spinner_s1.getSelectedItem().toString()) +
                "\",\"s2\":\"" +
                ApplicationData.spilitStringFirst(spinner_s2.getSelectedItem().toString()) +
                "\",\"s3\":\"" +
                ApplicationData.spilitStringFirst(spinner_s3.getSelectedItem().toString()) +
                "\",\"s4\":\"" +
                ApplicationData.spilitStringFirst(spinner_s4.getSelectedItem().toString()) +
                "\",\"s5\":\"" +
                ApplicationData.spilitStringFirst(spinner_s5.getSelectedItem().toString()) +
                "\",\"s6\":\"" +
                edt_s6.getText().toString() +
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
        //ApplicationData.INJURY_DATA_COLLECT = true;
        cleartext();
        //onBackPressed();
        activity.finish();
        //ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);


    }


}
