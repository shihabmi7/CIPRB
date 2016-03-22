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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class NearDrowningActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner sp_Drowning1, sp_Drowning2, sp_Drowning4, sp_Drowning5, sp_Drowning6, sp_Drowning7, sp_Drowning8, sp_Drowning9, sp_Drowning11, sp_Drowning12, sp_Drowning13, sp_Drowning14, sp_Drowning15;
    private TextView Drowning1, Drowning2, Drowning3, Drowning4, Drowning5, Drowning6, Drowning7, Drowning8, Drowning9, Drowning10, Drowning11, Drowning12, Drowning13, Drowning14, Drowning15, textView2, textView4, textView6;
    private Button button_cancel, button_next;
    private EditText et_Drowning3, et_Drowning10;
    ProgressDialog progressDialog;
    Activity activity = this;

    //String person_id = "101323212";
    String person_id = "";
    TextView textView_person_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_drowning);

        try {
            textView_person_id = (TextView) findViewById(R.id.textView_person_id);
            person_id = getIntent().getExtras().getString(ApplicationData.KEY_PERSON);
            textView_person_id.setText("Person Id:" + person_id);
        } catch (NullPointerException e) {

        } catch (Exception e) {

        }

        sp_Drowning1 = (Spinner) findViewById(R.id.sp_Drowning1);
        sp_Drowning2 = (Spinner) findViewById(R.id.sp_Drowning2);
        sp_Drowning4 = (Spinner) findViewById(R.id.sp_Drowning4);
        sp_Drowning5 = (Spinner) findViewById(R.id.sp_Drowning5);
        sp_Drowning6 = (Spinner) findViewById(R.id.sp_Drowning6);
        sp_Drowning7 = (Spinner) findViewById(R.id.sp_Drowning7);
        sp_Drowning8 = (Spinner) findViewById(R.id.sp_Drowning8);
        sp_Drowning9 = (Spinner) findViewById(R.id.sp_Drowning9);
        sp_Drowning11 = (Spinner) findViewById(R.id.sp_Drowning11);
        sp_Drowning12 = (Spinner) findViewById(R.id.sp_Drowning12);
        sp_Drowning13 = (Spinner) findViewById(R.id.sp_Drowning13);
        sp_Drowning14 = (Spinner) findViewById(R.id.sp_Drowning14);
        sp_Drowning15 = (Spinner) findViewById(R.id.sp_Drowning15);


        et_Drowning3 = (EditText) findViewById(R.id.et_Drowning3);
        et_Drowning10 = (EditText) findViewById(R.id.et_Drowning10);

        button_next = (Button) findViewById(R.id.button_next);
        button_cancel = (Button) findViewById(R.id.button_cancel);

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

    private class PutAsync extends AsyncTask<String, Void, String> {

        int value = 0;
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
        ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
        //activity.finish();
    }


    @Override
    public void onClick(View v) {

        if (v == button_next) {

            if (person_id.length() > 0) {

                //putRequestWithHeaderAndBody(ApplicationData.URL_SUICIDE + person_id);
                String url = ApplicationData.URL_NEAR_DROWN + person_id;
                //Put
                Log.i("String are ", createJsonBody());
                new PutAsync().execute(url, createJsonBody());
                //Post
                //new PostAsync().execute("http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity", PostcreateJsonBody());

            } else {
            }

        } else if (v == button_cancel) {


        }

    }

    String createJsonBody() {
//        Log.i("Test String ", ApplicationData.spilitStringFirst(spinner_survey_suicide_where.getSelectedItem().toString()));

        String rep = "{" +
                "\"m01\":\"" +
                ApplicationData.spilitStringFirst(sp_Drowning1.getSelectedItem().toString()) +
                "\",\"m02\":\"" +
                ApplicationData.spilitStringFirst(sp_Drowning2.getSelectedItem().toString()) +
                "\",\"m03\":\"" +
                et_Drowning3.getText().toString() +
                "\",\"m04\":\"" +
                ApplicationData.spilitStringFirst(sp_Drowning4.getSelectedItem().toString()) +
                "\",\"m05\":\"" +
                ApplicationData.spilitStringFirst(sp_Drowning5.getSelectedItem().toString()) +
                "\",\"m06\":\"" +
                ApplicationData.spilitStringFirst(sp_Drowning6.getSelectedItem().toString()) +
                "\",\"m07\":\"" +
                ApplicationData.spilitStringFirst(sp_Drowning7.getSelectedItem().toString()) +
                "\",\"m08\":\"" +
                ApplicationData.spilitStringFirst(sp_Drowning8.getSelectedItem().toString()) +
                "\",\"m09\":\"" +
                ApplicationData.spilitStringFirst(sp_Drowning9.getSelectedItem().toString()) +
                "\",\"m10\":\"" +
                et_Drowning10.getText().toString() +
                "\",\"m11\":\"" +
                ApplicationData.spilitStringFirst(sp_Drowning11.getSelectedItem().toString()) +
                "\",\"m12\":\"" +
                ApplicationData.spilitStringFirst(sp_Drowning12.getSelectedItem().toString()) +
                "\",\"m13\":\"" +
                ApplicationData.spilitStringFirst(sp_Drowning13.getSelectedItem().toString()) +
                "\",\"m14\":\"" +
                ApplicationData.spilitStringFirst(sp_Drowning14.getSelectedItem().toString()) +
                "\",\"m15\":\"" +
                ApplicationData.spilitStringFirst(sp_Drowning15.getSelectedItem().toString()) +
                "\"}";

        //return "{\"g03\":\"10\"}";
        return rep;
    }
}
