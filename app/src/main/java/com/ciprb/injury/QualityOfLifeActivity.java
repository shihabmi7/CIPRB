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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ciprb.injury.localdb.CiprbDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QualityOfLifeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_cancel, button_next;
    private TextView Quality1, Quality2, Quality3, Quality4, Quality5;
    private Spinner spinner_person_name, spinner_s1, spinner_s2, spinner_s3, spinner_s4, spinner_s5;

    EditText edt_s6;

    ProgressDialog progressDialog;
    Activity activity = this;

    //String person_id = "101323210";
    String person_id = "";
    TextView textView_person_id;

    CiprbDatabase ciprbDatabase;
    ArrayAdapter<String> dataAdapter;
    int alive_count = 0;
    List<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_of_life);

        ciprbDatabase = new CiprbDatabase(getApplicationContext());
        ciprbDatabase.open();


        try {
            setTitle(getResources().getStringArray(R.array.survey_activity_title)[6]);
            initUI();

            list = new ArrayList<String>();
            if (ciprbDatabase.getAllPersonNeverSkipped().isEmpty()) {

                Toast.makeText(activity, getString(R.string.no_data), Toast.LENGTH_LONG).show();
                finish();

            } else {

                setMemberSpinner(list);

            }

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

    private void setMemberSpinner(List<String> list) {
        list.clear();
        alive_count = ciprbDatabase.getAllPersonNeverSkipped().size();
        for (Person aPerson : ciprbDatabase.getAllPersonNeverSkipped()) {
            list.add(aPerson.getMembers_name() + "." + aPerson.getPerson_id());
        }
        dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        spinner_person_name.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
    }

    private void initUI() {
        spinner_person_name = (Spinner) findViewById(R.id.spinner_person_name);

        textView_person_id = (TextView) findViewById(R.id.textView_person_id);

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

    boolean checkSpinner() {

        if (spinner_s1.getSelectedItemPosition() != 0
                && spinner_s2.getSelectedItemPosition() != 0
                && spinner_s3.getSelectedItemPosition() != 0
                && spinner_s4.getSelectedItemPosition() != 0 && spinner_s5.getSelectedItemPosition() != 0
                ) {

            //Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_LONG).show();

            return true;

        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.suggestion), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    void setSpinnerDefaultState() {

        spinner_s1.setSelection(0);
        spinner_s2.setSelection(0);
        spinner_s3.setSelection(0);
        spinner_s4.setSelection(0);
        spinner_s5.setSelection(0);


    }

    void cleartext() {

        setSpinnerDefaultState();

        edt_s6.getText().clear();

      /*  editText_members_name.getText().clear();
        edittext_date_of_birth.getText().clear();
        edittext_current_age.getText().clear();
        editText_educatoin_level.getText().clear();

        if (edittext_how_many_injury_last_six != null)
            edittext_how_many_injury_last_six.getText().clear();*/

    }

    @Override
    protected void onResume() {
        super.onResume();

        try {

            if (ciprbDatabase.getAllPersonNeverSkipped().isEmpty()) {

                Toast.makeText(activity, "No Data to store", Toast.LENGTH_LONG).show();
                activity.finish();
                ciprbDatabase.close();

            }

            /*if (ApplicationData.INJURY_DATA_COLLECT == true) {

                Log.e("Resume Moribidity", ">>> Adapter Updated....: " + ApplicationData.ALIVE_PERSON_NUMBER);
                // Toast.makeText(activity, "Adapter Updated....: " + ApplicationData.ALIVE_PERSON_NUMBER, Toast.LENGTH_LONG).show();

                list.remove(ApplicationData.ALIVE_PERSON_NUMBER);
                dataAdapter.notifyDataSetChanged();
                ciprbDatabase.deleteRowByID(person_id);

                if (list.size() <= 0) {

                    Toast.makeText(activity, "No Data to store", Toast.LENGTH_LONG).show();
                    activity.finish();
                    ciprbDatabase.close();
                }
            }*/

        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

        try {
            person_id = ApplicationData.spilitStringSecond(spinner_person_name.getSelectedItem().toString());
            int number = spinner_person_name.getSelectedItemPosition();

            if (v == button_next && checkSpinner()) {

                if (InternetConnection.checkNetworkConnection(activity)) {

                    // person_id = editText_person_id.getText().toString();
                    // shihab changed

                    String url = ApplicationData.URL_QUALITY_OF_LIFE + person_id;
                    new PutAsync().execute(url, createJsonBody());
                    //Toast.makeText(activity, " Set Eleven (11) digit unique code", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getApplicationContext(), ApplicationData.OFFLINE_SAVED_SUCCESSFULLY, Toast.LENGTH_LONG).show();
                    ApplicationData.writeToFile(this, ApplicationData.OFFLINE_DB_QUALITY_OF_LIFE, createJsonBody());
                    ciprbDatabase.updatePersonQuality(person_id);
                    ciprbDatabase.deleteRowForNonInjuredPerson(person_id);
                    list.remove(number);
                    dataAdapter.notifyDataSetChanged();

                    if (ciprbDatabase.getAllPersonNeverSkipped().isEmpty()) {
                        Toast.makeText(activity, getString(R.string.no_data), Toast.LENGTH_LONG).show();
                        ciprbDatabase.close();
                        activity.finish();
                    }
                    Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG).show();
                }

            } else if (v == button_cancel) {

                ciprbDatabase.updatePersonQuality(person_id);
                ciprbDatabase.deleteRowForNonInjuredPerson(person_id);
                list.remove(number);
                dataAdapter.notifyDataSetChanged();

                if (ciprbDatabase.getAllPersonNeverSkipped().isEmpty()) {

                    Toast.makeText(activity, getString(R.string.no_data), Toast.LENGTH_LONG).show();
                    ciprbDatabase.close();
                    activity.finish();
                }

                Toast.makeText(getApplicationContext(), getString(R.string.skipped), Toast.LENGTH_LONG).show();
                //finish();
            }
        } catch (Exception e) {

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

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("household_unique_code", person_id);
            jsonObject.put("s01", ApplicationData.spilitStringFirst(spinner_s1.getSelectedItem().toString()));
            jsonObject.put("s02", ApplicationData.spilitStringFirst(spinner_s2.getSelectedItem().toString()));
            jsonObject.put("s03", ApplicationData.spilitStringFirst(spinner_s3.getSelectedItem().toString()));
            jsonObject.put("s04", ApplicationData.spilitStringFirst(spinner_s4.getSelectedItem().toString()));
            jsonObject.put("s05", ApplicationData.spilitStringFirst(spinner_s5.getSelectedItem().toString()));
            jsonObject.put("s06", edt_s6.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
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


        Log.e("Resume Moribidity", ">>> Adapter Updated....: " + ApplicationData.ALIVE_PERSON_NUMBER);
        // Toast.makeText(activity, "Adapter Updated....: " + ApplicationData.ALIVE_PERSON_NUMBER, Toast.LENGTH_LONG).show();

        ApplicationData.ALIVE_PERSON_NUMBER = spinner_person_name.getSelectedItemPosition();
        list.remove(ApplicationData.ALIVE_PERSON_NUMBER);
        dataAdapter.notifyDataSetChanged();
        // ciprbDatabase.deleteRowByID(person_id);

        if (list.size() <= 0) {

            //Toast.makeText(activity, "No Data to store", Toast.LENGTH_LONG).show();
            activity.finish();
            ciprbDatabase.close();
        }

        Toast.makeText(activity, "Successfully Data Saved", Toast.LENGTH_LONG).show();
        cleartext();
        //onBackPressed();
//        activity.finish();
//        ApplicationData.gotToNextActivity(activity, HomeActivity.class);

    }


}
