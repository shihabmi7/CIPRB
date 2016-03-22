package com.alhikmah.ciprb;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class HouseHoldCharacteristicsActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "HouseHoldCharacteristicsActivity";
    private Context mContext;
    private ProgressDialog progressDialog;
    private Button button_cancel, button_next;
    private Spinner spinner_c03, spinner_c04, spinner_c05, spinner_c07, spinner_c08, spinner_c10;
    EditText edit_c01, edit_c02, edittext_c06, edittext_c11;

    //String person_id = "101323211";
    String person_id = "";
    TextView textView_person_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_characteristics);
        mContext = this;
        try {

            textView_person_id = (TextView) findViewById(R.id.textView_person_id);
            //person_id = getIntent().getExtras().getString(ApplicationData.KEY_PERSON);
            textView_person_id.setText("Person Id:" + person_id);

        } catch (NullPointerException e) {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), getTitle() + "" + e.toString(), Toast.LENGTH_LONG).show();

        }
        initUI();

    }

    private void initUI() {
        edit_c01 = (EditText) findViewById(R.id.edit_c01);
        edit_c02 = (EditText) findViewById(R.id.edit_c02);
        edittext_c06 = (EditText) findViewById(R.id.edittext_c06);
        edittext_c11 = (EditText) findViewById(R.id.edittext_c11);

        spinner_c03 = (Spinner) findViewById(R.id.spinner_c03);
        spinner_c04 = (Spinner) findViewById(R.id.spinner_c04);
        spinner_c05 = (Spinner) findViewById(R.id.spinner_c05);
        spinner_c07 = (Spinner) findViewById(R.id.spinner_c07);
        spinner_c08 = (Spinner) findViewById(R.id.spinner_c08);
        spinner_c10 = (Spinner) findViewById(R.id.spinner_c10);


        button_next = (Button) findViewById(R.id.button_next);
        button_cancel = (Button) findViewById(R.id.button_cancel);

        button_cancel.setOnClickListener(this);
        button_next.setOnClickListener(this);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (v == button_cancel) {
            this.finish();

        } else if (v == button_next) {
            String url = ApplicationData.URL_CHARACTERISTIC + person_id;
            new PutAsync().execute(url, createJsonBody());

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
                "\"c01\":\"" +
                edit_c01.getText().toString() +
                "\",\"c02\":\"" +
                edit_c02.getText().toString() +
                "\",\"c03\":\"" +
                ApplicationData.spilitStringFirst(spinner_c03.getSelectedItem().toString()) +
                "\",\"c04\":\"" +
                ApplicationData.spilitStringFirst(spinner_c04.getSelectedItem().toString()) +
                "\",\"c05\":\"" +
                ApplicationData.spilitStringFirst(spinner_c05.getSelectedItem().toString()) +
                "\",\"c06\":\"" +
                edittext_c06.getText().toString() +
                "\",\"c07\":\"" +
                ApplicationData.spilitStringFirst(spinner_c07.getSelectedItem().toString()) +
                "\",\"c08\":\"" +
                ApplicationData.spilitStringFirst(spinner_c08.getSelectedItem().toString()) +
                "\",\"c09\":\"" +
                " " +
                "\",\"c10\":\"" +
                ApplicationData.spilitStringFirst(spinner_c10.getSelectedItem().toString()) +
                "\",\"c11\":\"" +
                edittext_c11.getText().toString() +
                "\"}";
        return jsonData;
    }

    private class PutAsync extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String Result = "";
                Log.i("URL are ", params[0]);
                Result = putRequestWithHeaderAndBody(params[0], params[1]);

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


        }

    }
}
