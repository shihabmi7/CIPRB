package com.alhikmah.ciprb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class InsectInjuryActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button_cancel,button_next;
    private TextView insect1,insect2,insect3,textView2,textView4,textView6;
    private Spinner sp_insect1,sp_insect2,sp_insect3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insect_injury);

        textView2=(TextView)findViewById(R.id.textView2);
        textView4=(TextView)findViewById(R.id.textView4);
        textView6=(TextView)findViewById(R.id.textView6);
        insect1=(TextView)findViewById(R.id.insect1);
        insect2=(TextView)findViewById(R.id.insect2);
        insect3=(TextView)findViewById(R.id.insect3);
        sp_insect1=(Spinner)findViewById(R.id.sp_insect1);
        sp_insect2=(Spinner)findViewById(R.id.sp_insect2);
        sp_insect3=(Spinner)findViewById(R.id.sp_insect3);

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
