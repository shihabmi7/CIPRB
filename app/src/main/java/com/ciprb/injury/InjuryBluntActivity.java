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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class InjuryBluntActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_cancel, button_next;
    private TextView blunt1, blunt2, blunt3, textView2, textView4, textView6;
    private Spinner sp_blunt1, sp_blunt2;


    ProgressDialog progressDialog;
    Activity activity = this;
    String person_id = "";
    TextView textView_person_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injury_blunt);

        setTitle( getResources().getStringArray(R.array.survey_activity_title)[18]);

        try {
            textView_person_id = (TextView) findViewById(R.id.textView_person_id);
            person_id = getIntent().getExtras().getString(ApplicationData.KEY_PERSON);
            textView_person_id.setText("Person Id:" + person_id);

        } catch (NullPointerException e) {

        } catch (Exception e) {

        }
        initUI();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(getString(R.string.loading_message));
        progressDialog.setTitle(getString(R.string.waiting));
        progressDialog.setCancelable(true);
    }

    private void initUI() {
        blunt1 = (TextView) findViewById(R.id.blunt1);
        blunt2 = (TextView) findViewById(R.id.blunt2);
        blunt3 = (TextView) findViewById(R.id.blunt3);

        sp_blunt1 = (Spinner) findViewById(R.id.sp_blunt1);
        sp_blunt2 = (Spinner) findViewById(R.id.sp_blunt2);

        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_next = (Button) findViewById(R.id.button_next);

        button_cancel.setOnClickListener(this);
        button_next.setOnClickListener(this);
    }

    boolean checkSpinner() {


        if (sp_blunt1.getSelectedItemPosition() != 0 && sp_blunt2.getSelectedItemPosition() != 0) {

            //Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_LONG).show();

            return true;


        } else {

            Toast.makeText(getApplicationContext(), getString(R.string.suggestion), Toast.LENGTH_LONG).show();
            return false;
        }

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


        if (v == button_next && checkSpinner()) {

            if(InternetConnection.checkNetworkConnection(activity)){
                String url = ApplicationData.URL_BLUNT_INJURY + person_id;
                new PutAsync().execute(url, createJsonBody());
            } else{

                Toast.makeText(getApplicationContext(),ApplicationData.OFFLINE_SAVED_SUCCESSFULLY,Toast.LENGTH_LONG).show();
                ApplicationData.writeToFile(this, ApplicationData.OFFLINE_DB_INJURY_BLUNT, createJsonBody());
                finishTask();
            }


        } else if (v == button_cancel) {


        }

    }


    String createJsonBody() {

        JSONObject jsonData = new JSONObject();
        try {

            jsonData.put("household_unique_code", person_id);
            jsonData.put("q01", ApplicationData.spilitStringFirst(sp_blunt1.getSelectedItem().toString()));
            jsonData.put("q02", ApplicationData.spilitStringFirst(sp_blunt2.getSelectedItem().toString()));
        } catch (JSONException e) {
            e.printStackTrace();

        }
       /*
        String jsonData = "{" +
                "\"q01\":\"" +
                ApplicationData.spilitStringFirst(sp_blunt1.getSelectedItem().toString()) +
                "\",\"q02\":\"" +
                ApplicationData.spilitStringFirst(sp_blunt2.getSelectedItem().toString()) +
                "\"}";*/
        return jsonData.toString();
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
        // ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
        //activity.finish();
    }

}

