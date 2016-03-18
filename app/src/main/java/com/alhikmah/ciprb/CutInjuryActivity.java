package com.alhikmah.ciprb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class CutInjuryActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner sp_cut1, sp_cut2, sp_cut3;
    private TextView cut1, cut2, cut3, textView2, textView4, textView6;
    private Button button_cancel, button_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut_injury);

        textView2 = (TextView) findViewById(R.id.textView2);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView6 = (TextView) findViewById(R.id.textView6);

        cut1 = (TextView) findViewById(R.id.cut1);
        cut2 = (TextView) findViewById(R.id.cut2);
        cut3 = (TextView) findViewById(R.id.cut3);

        sp_cut1 = (Spinner) findViewById(R.id.sp_cut1);
        sp_cut2 = (Spinner) findViewById(R.id.sp_cut2);
        sp_cut3 = (Spinner) findViewById(R.id.sp_cut3);

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
