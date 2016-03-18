package com.alhikmah.ciprb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ElectrocutionActivity extends AppCompatActivity implements View.OnClickListener {
     private Spinner sp_electrocution1,sp_electrocution2,sp_electrocution3,sp_electrocution4;
    private TextView electrocution1,electrocution2,electrocution3,electrocution4,textView2,textView4,textView6;
    private Button button_cancel,button_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrocution);

        textView2=(TextView)findViewById(R.id.textView2);
        textView4=(TextView)findViewById(R.id.textView4);
        textView6=(TextView)findViewById(R.id.textView6);

        electrocution1=(TextView)findViewById(R.id.electrocution1);
        electrocution2=(TextView)findViewById(R.id.electrocution2);
        electrocution3=(TextView)findViewById(R.id.electrocution3);
        electrocution4=(TextView)findViewById(R.id.electrocution4);

        sp_electrocution1=(Spinner)findViewById(R.id.sp_electrocution1);
        sp_electrocution2=(Spinner)findViewById(R.id.sp_electrocution2);
        sp_electrocution3=(Spinner)findViewById(R.id.sp_electrocution3);
        sp_electrocution4=(Spinner)findViewById(R.id.sp_electrocution4);



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
