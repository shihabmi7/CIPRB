package com.alhikmah.ciprb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class BurnInjuryActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner sp_Burn1, sp_Burn2, sp_Burn3, sp_Burn4, sp_Burn5, sp_Burn6, sp_Burn7;
    private TextView Burn1, Burn2, Burn3, Burn4, Burn5, Burn6, Burn7, textView2, textView4, textView6;
    private Button button_cancel, button_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burn_injury);

        textView2 = (TextView) findViewById(R.id.textView2);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView6 = (TextView) findViewById(R.id.textView6);

        Burn1 = (TextView) findViewById(R.id.Burn1);
        Burn2 = (TextView) findViewById(R.id.Burn2);
        Burn3 = (TextView) findViewById(R.id.Burn3);
        Burn4 = (TextView) findViewById(R.id.Burn4);
        Burn5 = (TextView) findViewById(R.id.Burn5);
        Burn6 = (TextView) findViewById(R.id.Burn6);
        Burn7 = (TextView) findViewById(R.id.Burn7);
        sp_Burn1 = (Spinner) findViewById(R.id.sp_Burn1);
        sp_Burn2 = (Spinner) findViewById(R.id.sp_Burn2);
        sp_Burn3 = (Spinner) findViewById(R.id.sp_Burn3);
        sp_Burn4 = (Spinner) findViewById(R.id.sp_Burn4);
        sp_Burn5 = (Spinner) findViewById(R.id.sp_Burn5);
        sp_Burn6 = (Spinner) findViewById(R.id.sp_Burn6);
        sp_Burn7 = (Spinner) findViewById(R.id.sp_Burn7);


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
