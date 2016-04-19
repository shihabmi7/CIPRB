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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UnintentionalPoisoningActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_cancel, button_next;
    private Spinner spinner_n01, spinner_n02, spinner_n03, spinner_n04, spinner_n05, spinner_n06, spinner_n07, spinner_n08, spinner_n09;
    private TextView poisoning1, poisoning2, poisoning3, poisoning4, poisoning5, poisoning6, poisoning7, textView2, textView4, textView6;

    ProgressDialog progressDialog;
    Activity activity = this;
    //String person_id = "101323210";
    String person_id = "";
    TextView textView_person_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unintentional_poisoning);

        try {

            setTitle( getResources().getStringArray(R.array.survey_activity_title)[14]);

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
        poisoning1 = (TextView) findViewById(R.id.poisoning1);
        poisoning2 = (TextView) findViewById(R.id.poisoning2);
        poisoning3 = (TextView) findViewById(R.id.poisoning3);
        poisoning4 = (TextView) findViewById(R.id.poisoning4);
        poisoning5 = (TextView) findViewById(R.id.poisoning5);
        poisoning6 = (TextView) findViewById(R.id.poisoning6);
        poisoning7 = (TextView) findViewById(R.id.poisoning7);

        spinner_n01 = (Spinner) findViewById(R.id.spinner_n01);
        spinner_n02 = (Spinner) findViewById(R.id.spinner_n02);
        spinner_n03 = (Spinner) findViewById(R.id.spinner_n03);
        spinner_n04 = (Spinner) findViewById(R.id.spinner_n04);
        spinner_n05 = (Spinner) findViewById(R.id.spinner_n05);
        spinner_n06 = (Spinner) findViewById(R.id.spinner_n06);
        spinner_n07 = (Spinner) findViewById(R.id.spinner_n07);
        spinner_n08 = (Spinner) findViewById(R.id.spinner_n08);
        spinner_n09 = (Spinner) findViewById(R.id.spinner_n09);

        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_next = (Button) findViewById(R.id.button_next);

        button_cancel.setOnClickListener(this);
        button_next.setOnClickListener(this);
    }


    boolean checkSpinner() {

        if (spinner_n01.getSelectedItemPosition() != 0 &&
                spinner_n02.getSelectedItemPosition() != 0
                && spinner_n03.getSelectedItemPosition() != 0
                && spinner_n04.getSelectedItemPosition() != 0
                && spinner_n05.getSelectedItemPosition() != 0
                && spinner_n06.getSelectedItemPosition() != 0
                && spinner_n07.getSelectedItemPosition() != 0
                && spinner_n08.getSelectedItemPosition() != 0
                && spinner_n09.getSelectedItemPosition() != 0

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

//    void saveDataToOnline(Person person) {
//
//        progressDialog.show();
//        // post with no parameters
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//
//        params.put("house_member_id", person.getPerson_id());
//        params.put("i01", person.getMembers_name());
//        params.put("i02", person.getPerson_id());
//        params.put("i03", person.getMembers_name());
//        params.put("i04", person.getMembers_name());
//
//
//        client.post(ApplicationData.URL_HOUSE_HOLD_MEMBERS, params,
//                new JsonHttpResponseHandler() {
//                    // Your implementation here
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//
//                        cleartext();
//                        progressDialog.dismiss();
//
//                        showTextLong("finish this input");
//                        //ApplicationData.memberListHashMap.clear();
//                        ApplicationData.gotToNextActivity(activity, InjuryMorbidityActivity.class);
//
//                    }
//                }
//        );
//    }


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

            if (InternetConnection.checkNetworkConnection(activity)){

                String url = ApplicationData.URL_UNINTENTIONAL_INJURY + person_id;
                new PutAsync().execute(url, createJsonBody());

            }else{

                Toast.makeText(getApplicationContext(),ApplicationData.OFFLINE_SAVED_SUCCESSFULLY,Toast.LENGTH_LONG).show();
                ApplicationData.writeToFile(this, ApplicationData.OFFLINE_DB_UNINTENTIONAL_POISIONING, createJsonBody());
                finishTask();
            }



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

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("household_unique_code", person_id);
            jsonObject.put("n01", ApplicationData.spilitStringFirst(spinner_n01.getSelectedItem().toString()));
            jsonObject.put("n02", ApplicationData.spilitStringFirst(spinner_n02.getSelectedItem().toString()));
            jsonObject.put("n03", ApplicationData.spilitStringFirst(spinner_n03.getSelectedItem().toString()));
            jsonObject.put("n04", ApplicationData.spilitStringFirst(spinner_n04.getSelectedItem().toString()));
            jsonObject.put("n05", ApplicationData.spilitStringFirst(spinner_n05.getSelectedItem().toString()));
            jsonObject.put("n06", ApplicationData.spilitStringFirst(spinner_n06.getSelectedItem().toString()));
            jsonObject.put("n07", ApplicationData.spilitStringFirst(spinner_n07.getSelectedItem().toString()));
            jsonObject.put("n08", ApplicationData.spilitStringFirst(spinner_n08.getSelectedItem().toString()));
            jsonObject.put("n09", ApplicationData.spilitStringFirst(spinner_n09.getSelectedItem().toString()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
 /*       String jsonData = "{" +
                "\"n01\":\"" +
                ApplicationData.spilitStringFirst(spinner_n01.getSelectedItem().toString()) +
                "\",\"n02\":\"" +
                ApplicationData.spilitStringFirst(spinner_n02.getSelectedItem().toString()) +
                "\",\"n03\":\"" +
                ApplicationData.spilitStringFirst(spinner_n03.getSelectedItem().toString()) +
                "\",\"n04\":\"" +
                ApplicationData.spilitStringFirst(spinner_n04.getSelectedItem().toString()) +
                "\",\"n05\":\"" +
                ApplicationData.spilitStringFirst(spinner_n05.getSelectedItem().toString()) +
                "\",\"n06\":\"" +
                ApplicationData.spilitStringFirst(spinner_n06.getSelectedItem().toString()) +
                "\",\"n07\":\"" +
                ApplicationData.spilitStringFirst(spinner_n07.getSelectedItem().toString()) +
                "\",\"n08\":\"" +
                ApplicationData.spilitStringFirst(spinner_n08.getSelectedItem().toString()) +
                "\",\"n09\":\"" +
                ApplicationData.spilitStringFirst(spinner_n09.getSelectedItem().toString()) +
                "\"}";*/
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
