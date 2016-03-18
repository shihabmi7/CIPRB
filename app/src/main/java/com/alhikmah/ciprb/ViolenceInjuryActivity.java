package com.alhikmah.ciprb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ViolenceInjuryActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner_violence_why, spinner_violence_relation, spinner_sex, spinner_violence_object;

    private TextView fall1, fall2, fall3, fall4, fall5, textView2, textView4, textView6;
    private Button button_cancel, button_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voilence_injury);


        spinner_violence_why = (Spinner) findViewById(R.id.spinner_violence_why);
        spinner_violence_relation = (Spinner) findViewById(R.id.spinner_violence_relation);
        spinner_sex = (Spinner) findViewById(R.id.spinner_sex);
        spinner_violence_object = (Spinner) findViewById(R.id.spinner_violence_object);

        button_next = (Button) findViewById(R.id.button_next);
        button_cancel = (Button) findViewById(R.id.button_cancel);


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
