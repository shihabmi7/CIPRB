package com.alhikmah.ciprb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UnintentionalPoisoningActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_cancel, button_next;
    private Spinner sp_poisoning1, sp_poisoning2, sp_poisoning3, sp_poisoning4, sp_poisoning5, sp_poisoning6, sp_poisoning7, sp_poisoning8;
    private TextView poisoning1, poisoning2, poisoning3, poisoning4, poisoning5, poisoning6, poisoning7, textView2, textView4, textView6;

    ProgressDialog progressDialog;
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unintentional_poisoning);

        //String person_id = "101323210";
        String person_id = "101323210";
        TextView textView_person_id;

        try {
            textView_person_id = (TextView) findViewById(R.id.textView_person_id);
            person_id = getIntent().getExtras().getString(ApplicationData.KEY_PERSON);
            textView_person_id.setText("Person Id:" + person_id);

        } catch (NullPointerException e) {


        } catch (Exception e) {


        }

        poisoning1 = (TextView) findViewById(R.id.poisoning1);
        poisoning2 = (TextView) findViewById(R.id.poisoning2);
        poisoning3 = (TextView) findViewById(R.id.poisoning3);
        poisoning4 = (TextView) findViewById(R.id.poisoning4);
        poisoning5 = (TextView) findViewById(R.id.poisoning5);
        poisoning6 = (TextView) findViewById(R.id.poisoning6);
        poisoning7 = (TextView) findViewById(R.id.poisoning7);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView6 = (TextView) findViewById(R.id.textView6);

        sp_poisoning1 = (Spinner) findViewById(R.id.sp_poisoning1);
        sp_poisoning2 = (Spinner) findViewById(R.id.sp_poisoning2);
        sp_poisoning3 = (Spinner) findViewById(R.id.sp_poisoning3);
        sp_poisoning4 = (Spinner) findViewById(R.id.sp_poisoning4);
        sp_poisoning5 = (Spinner) findViewById(R.id.sp_poisoning5);
        sp_poisoning6 = (Spinner) findViewById(R.id.sp_poisoning6);
        sp_poisoning7 = (Spinner) findViewById(R.id.sp_poisoning7);
        sp_poisoning8 = (Spinner) findViewById(R.id.sp_poisoning8);

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


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (v == button_next) {

        } else if (v == button_cancel) {

        }

    }
}
