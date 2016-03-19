package com.alhikmah.ciprb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class QualityOfLifeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_cancel,button_next;
    private TextView Quality1,Quality2,Quality3,Quality4,Quality5;
    private CheckBox Quality11,Quality12,Quality13,Quality21,Quality22,Quality23,Quality31,Quality32,Quality33,
            Quality41,Quality42,Quality43,Quality51,Quality52,Quality53;

    ProgressDialog  progressDialog;
    Activity activity=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_of_life);
        Quality1=(TextView)findViewById(R.id.Quality1);
        Quality2=(TextView)findViewById(R.id.Quality2);
        Quality3=(TextView)findViewById(R.id.Quality3);
        Quality4=(TextView)findViewById(R.id.Quality4);
        Quality5=(TextView)findViewById(R.id.Quality5);

        Quality11=(CheckBox)findViewById(R.id.Quality11);
        Quality12=(CheckBox)findViewById(R.id.Quality12);
        Quality13=(CheckBox)findViewById(R.id.Quality13);

        Quality21=(CheckBox)findViewById(R.id.Quality21);
        Quality22=(CheckBox)findViewById(R.id.Quality22);
        Quality23=(CheckBox)findViewById(R.id.Quality23);

        Quality31=(CheckBox)findViewById(R.id.Quality31);
        Quality32=(CheckBox)findViewById(R.id.Quality32);
        Quality33=(CheckBox)findViewById(R.id.Quality33);

        Quality41=(CheckBox)findViewById(R.id.Quality41);
        Quality42=(CheckBox)findViewById(R.id.Quality42);
        Quality43=(CheckBox)findViewById(R.id.Quality43);

        Quality51=(CheckBox)findViewById(R.id.Quality51);
        Quality52=(CheckBox)findViewById(R.id.Quality52);
        Quality53=(CheckBox)findViewById(R.id.Quality53);



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
