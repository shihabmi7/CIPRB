package com.alhikmah.ciprb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class FallInjuryActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner sp_fall1, sp_fall2, sp_fall3, sp_fall4, sp_fall5, sp_fall06;

    private TextView fall1, fall2, fall3, fall4, fall5, textView2, textView4, textView6;
    private Button button_cancel, button_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall_injury);

        textView2 = (TextView) findViewById(R.id.textView2);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView6 = (TextView) findViewById(R.id.textView6);
        fall1 = (TextView) findViewById(R.id.fall1);
        fall2 = (TextView) findViewById(R.id.fall2);
        fall3 = (TextView) findViewById(R.id.fall3);
        fall4 = (TextView) findViewById(R.id.fall4);
        fall5 = (TextView) findViewById(R.id.fall5);

        sp_fall1 = (Spinner) findViewById(R.id.sp_fall1);
        sp_fall2 = (Spinner) findViewById(R.id.sp_fall2);
        sp_fall3 = (Spinner) findViewById(R.id.sp_fall3);
        sp_fall4 = (Spinner) findViewById(R.id.sp_fall4);
        sp_fall5 = (Spinner) findViewById(R.id.sp_fall5);
        sp_fall06 = (Spinner) findViewById(R.id.sp_fall5);


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
