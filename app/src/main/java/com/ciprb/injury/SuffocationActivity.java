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

public class SuffocationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_cancel, button_next;
    private TextView suffocation1, suffocation2, textView2, textView4, textView6;
    private Spinner spinner_r01;
    private EditText edt_r02;


    ProgressDialog progressDialog;
    Activity activity = this;
    //String person_id = "101323210";
    String person_id = "101323210";
    TextView textView_person_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suffocation);

        try {


            setTitle( getResources().getStringArray(R.array.survey_activity_title)[19]);

            textView_person_id = (TextView) findViewById(R.id.textView_person_id);
            person_id = getIntent().getExtras().getString(ApplicationData.KEY_PERSON);
            textView_person_id.setText("Person Id:" + person_id);
        } catch (NullPointerException e) {


        } catch (Exception e) {


        }

        button_next = (Button) findViewById(R.id.button_next);
        button_cancel = (Button) findViewById(R.id.button_cancel);

        edt_r02 = (EditText) findViewById(R.id.edt_r02);

        suffocation1 = (TextView) findViewById(R.id.suffocation1);
        suffocation2 = (TextView) findViewById(R.id.suffocation2);
        spinner_r01 = (Spinner) findViewById(R.id.spinner_r01);


        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_next = (Button) findViewById(R.id.button_next);

        button_cancel.setOnClickListener(this);
        button_next.setOnClickListener(this);

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(getString(R.string.loading_message));
        progressDialog.setTitle(getString(R.string.waiting));
        progressDialog.setCancelable(true);
    }


    boolean checkSpinner() {

        if (spinner_r01.getSelectedItemPosition() != 0 && edt_r02.getText().length() > 0

                ) {

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

            String url = ApplicationData.URL_SUFFOGATION + person_id;
            new PutAsync().execute(url, createJsonBody());

        } else if (v == button_cancel) {

            finish();

        }

    }


    String createJsonBody() {
        String jsonData = "{" +
                "\"r01\":\"" +
                ApplicationData.spilitStringFirst(spinner_r01.getSelectedItem().toString()) +
                "\",\"r02\":\"" +
                edt_r02.getText().toString() +
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

            if (value == ApplicationData.STATUS_SUCCESS)

            {
                //// TODO: 3/22/2016

                finishTask();

            } else

            {
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
//        ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
        //activity.finish();
    }


}
