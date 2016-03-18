package com.alhikmah.ciprb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class InjuryBluntActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_cancel, button_next;
    private TextView blunt1, blunt2, blunt3, textView2, textView4, textView6;
    private Spinner sp_blunt1, sp_blunt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injury_blunt);

        blunt1 = (TextView) findViewById(R.id.blunt1);
        blunt2 = (TextView) findViewById(R.id.blunt2);
        blunt3 = (TextView) findViewById(R.id.blunt3);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView6 = (TextView) findViewById(R.id.textView6);

        sp_blunt1 = (Spinner) findViewById(R.id.sp_blunt1);
        sp_blunt2 = (Spinner) findViewById(R.id.sp_blunt2);

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

