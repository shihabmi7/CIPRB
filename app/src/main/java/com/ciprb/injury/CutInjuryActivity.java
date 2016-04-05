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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CutInjuryActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner_k01, spinner_k02, spinner_k03;
    private TextView cut1, cut2, cut3, textView2, textView4, textView6;
    private Button button_cancel, button_next;

    Activity activity = this;
    ProgressDialog progressDialog;
    String person_id = "";
    TextView textView_person_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut_injury);

        //String person_id = "101323210";

        setTitle(getResources().getStringArray(R.array.survey_activity_title)[11]);

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

        cut1 = (TextView) findViewById(R.id.cut1);
        cut2 = (TextView) findViewById(R.id.cut2);
        cut3 = (TextView) findViewById(R.id.cut3);

        spinner_k01 = (Spinner) findViewById(R.id.spinner_k01);
        spinner_k02 = (Spinner) findViewById(R.id.spinner_k02);
        spinner_k03 = (Spinner) findViewById(R.id.spinner_k03);

        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_next = (Button) findViewById(R.id.button_next);

        button_cancel.setOnClickListener(this);
        button_next.setOnClickListener(this);
    }

    boolean checkSpinner() {

        if (spinner_k01.getSelectedItemPosition() != 0 && spinner_k02.getSelectedItemPosition() != 0
                && spinner_k03.getSelectedItemPosition() != 0
                ) {

            //Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_LONG).show();

            return true;

        } else {

            Toast.makeText(getApplicationContext(), getString(R.string.suggestion), Toast.LENGTH_LONG).show();
            return false;

        }

    }


    void setSpinnerDefaultState() {

        spinner_k01.setSelection(0);
        spinner_k02.setSelection(0);
        spinner_k03.setSelection(0);


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

            if(InternetConnection.checkNetworkConnection(activity)){

                String url = ApplicationData.URL_CUTINJURY + person_id;
                new PutAsync().execute(url, createJsonBody());

            }else{

                Toast.makeText(getApplicationContext(),ApplicationData.OFFLINE_SAVED_SUCCESSFULLY,Toast.LENGTH_LONG).show();
                ApplicationData.writeToFile(this, ApplicationData.OFFLINE_DB_CUT_INJURY, createJsonBody());
                finishTask();
            }




        } else if (v == button_cancel) {


        }

    }

    String createJsonBody() {

        JSONObject jsonData = new JSONObject();
        try {

            jsonData.put("household_unique_code", person_id);
            jsonData.put("k01", ApplicationData.spilitStringFirst(spinner_k01.getSelectedItem().toString()));
            jsonData.put("k02", ApplicationData.spilitStringFirst(spinner_k02.getSelectedItem().toString()));
            jsonData.put("k03", ApplicationData.spilitStringFirst(spinner_k03.getSelectedItem().toString()) );

        } catch (JSONException e) {
            e.printStackTrace();
        }

/*        String jsonData = "{" +
                "\"k01\":\"" +
                ApplicationData.spilitStringFirst(spinner_k01.getSelectedItem().toString()) +
                "\",\"k02\":\"" +
                ApplicationData.spilitStringFirst(spinner_k02.getSelectedItem().toString()) +
                "\",\"k03\":\"" +
                ApplicationData.spilitStringFirst(spinner_k03.getSelectedItem().toString()) +
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
        setSpinnerDefaultState();
        activity.finish();
        // ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
        //activity.finish();
    }
}
