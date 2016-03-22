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

public class FallInjuryActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner sp_fall1, sp_fall2, sp_fall3, sp_fall4, sp_fall5, sp_fall06;

    private TextView fall1, fall2, fall3, fall4, fall5, textView2, textView4, textView6;
    private Button button_cancel, button_next;
    ProgressDialog progressDialog;
    Activity activity = this;

    //String person_id = "101323210";
    String person_id = "101323210";
    TextView textView_person_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall_injury);

        try {

            textView_person_id = (TextView) findViewById(R.id.textView_person_id);
            person_id = getIntent().getExtras().getString(ApplicationData.KEY_PERSON);
            textView_person_id.setText("Person Id:" + person_id);

        } catch (NullPointerException e) {

        } catch (Exception e) {

        }

        sp_fall1 = (Spinner) findViewById(R.id.sp_fall1);
        sp_fall2 = (Spinner) findViewById(R.id.sp_fall2);
        sp_fall3 = (Spinner) findViewById(R.id.sp_fall3);
        sp_fall4 = (Spinner) findViewById(R.id.sp_fall4);
        sp_fall5 = (Spinner) findViewById(R.id.sp_fall5);
        sp_fall06 = (Spinner) findViewById(R.id.sp_fall6);


        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_next = (Button) findViewById(R.id.button_next);

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

                String Result = "";
                Log.i("URL are ", params[0]);
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

            progressDialog.dismiss();
            if (value == ApplicationData.STATUS_SUCCESS) {

                Toast.makeText(activity, "Success", Toast.LENGTH_LONG).show();
                //ApplicationData.alive_person_List.remove(spinner_person_name.getSelectedItemPosition());
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


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */

    @Override
    public void onClick(View v) {


        if (v == button_next) {

            if (person_id.length() > 0) {

                //putRequestWithHeaderAndBody(ApplicationData.URL_SUICIDE + person_id);
                String url = ApplicationData.URL_FALL + person_id;
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
                "\"j01\":\"" +
                ApplicationData.spilitStringFirst(sp_fall1.getSelectedItem().toString()) +
                "\",\"j02\":\"" +
                ApplicationData.spilitStringFirst(sp_fall2.getSelectedItem().toString()) +
                "\",\"j03\":\"" +
                ApplicationData.spilitStringFirst(sp_fall3.getSelectedItem().toString()) +
                "\",\"j04\":\"" +
                ApplicationData.spilitStringFirst(sp_fall4.getSelectedItem().toString()) +
                "\",\"j05\":\"" +
                ApplicationData.spilitStringFirst(sp_fall5.getSelectedItem().toString()) +
                "\",\"j06\":\"" +
                ApplicationData.spilitStringFirst(sp_fall06.getSelectedItem().toString()) +
                "\"}";

        //return "{\"g03\":\"10\"}";
        return rep;
    }
}
