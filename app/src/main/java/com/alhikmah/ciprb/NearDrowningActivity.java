package com.alhikmah.ciprb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class NearDrowningActivity extends AppCompatActivity {

    private Spinner sp_Drowning1, sp_Drowning2, sp_Drowning4, sp_Drowning5, sp_Drowning6, sp_Drowning7, sp_Drowning8, sp_Drowning9, sp_Drowning11, sp_Drowning12, sp_Drowning13, sp_Drowning14, sp_Drowning15;
    private TextView Drowning1, Drowning2, Drowning3, Drowning4, Drowning5, Drowning6, Drowning7, Drowning8, Drowning9, Drowning10, Drowning11, Drowning12, Drowning13, Drowning14, Drowning15, textView2, textView4, textView6;
    private Button button_cancel, button_next;
    private EditText et_Drowning3, et_Drowning10;
    ProgressDialog progressDialog;
    Activity activity = this;

    //String person_id = "101323210";
    String person_id = "101323210";
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
        textView2 = (TextView) findViewById(R.id.textView2);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView6 = (TextView) findViewById(R.id.textView6);
        Drowning1 = (TextView) findViewById(R.id.Drowning1);
        Drowning2 = (TextView) findViewById(R.id.Drowning2);
        Drowning3 = (TextView) findViewById(R.id.Drowning3);
        Drowning4 = (TextView) findViewById(R.id.Drowning4);
        Drowning5 = (TextView) findViewById(R.id.Drowning5);
        Drowning6 = (TextView) findViewById(R.id.Drowning6);
        Drowning7 = (TextView) findViewById(R.id.Drowning7);
        Drowning8 = (TextView) findViewById(R.id.Drowning8);
        Drowning9 = (TextView) findViewById(R.id.Drowning9);
        Drowning10 = (TextView) findViewById(R.id.Drowning10);
        Drowning11 = (TextView) findViewById(R.id.Drowning11);
        Drowning12 = (TextView) findViewById(R.id.Drowning12);
        Drowning13 = (TextView) findViewById(R.id.Drowning13);
        Drowning14 = (TextView) findViewById(R.id.Drowning14);
        Drowning15 = (TextView) findViewById(R.id.Drowning15);

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

}
