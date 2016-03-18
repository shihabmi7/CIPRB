package com.alhikmah.ciprb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class UnintentionalPoisoningActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_cancel, button_next;
    private Spinner sp_poisoning1, sp_poisoning2, sp_poisoning3, sp_poisoning4, sp_poisoning5, sp_poisoning6, sp_poisoning7, sp_poisoning8;
    private TextView poisoning1, poisoning2, poisoning3, poisoning4, poisoning5, poisoning6, poisoning7, textView2, textView4, textView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unintentional_poisoning);

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
