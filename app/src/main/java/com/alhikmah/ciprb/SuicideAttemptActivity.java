package com.alhikmah.ciprb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class SuicideAttemptActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner_survey_suicide_where, spinner_survey_suicide_how, spinner_survey_suicide_type;
    private Button button_cancel, button_next;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suicide_attempt);


        Person aPerson = (Person) getIntent().getSerializableExtra("aPerson");

        Toast.makeText(this, "" + aPerson.getPerson_id() + aPerson.getMembers_name(), Toast.LENGTH_LONG).show();
        spinner_survey_suicide_where = (Spinner) findViewById(R.id.spinner_survey_suicide_where);
        spinner_survey_suicide_how = (Spinner) findViewById(R.id.spinner_survey_suicide_how);
        spinner_survey_suicide_type = (Spinner) findViewById(R.id.spinner_survey_suicide_type);

        button_cancel = (Button) findViewById(R.id.button_cancel);
        button_next = (Button) findViewById(R.id.button_next);

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


        if (v == button_next) {


        } else if (v == button_cancel) {


        }

    }
}
