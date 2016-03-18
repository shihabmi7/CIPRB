package com.alhikmah.ciprb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SuffocationActivity extends AppCompatActivity  implements View.OnClickListener{

    private Button button_cancel,button_next;
    private TextView suffocation1,suffocation2,textView2,textView4,textView6;
    private Spinner sp_suffocation1;
    private EditText etSuffocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suffocation);

        button_next=(Button)findViewById(R.id.button_next);
        button_cancel=(Button)findViewById(R.id.button_cancel);

        etSuffocation=(EditText)findViewById(R.id.etSuffocation);

        textView2=(TextView)findViewById(R.id.textView2);
        textView4=(TextView)findViewById(R.id.textView4);
        textView6=(TextView)findViewById(R.id.textView6);
        suffocation1=(TextView)findViewById(R.id.suffocation1);
        suffocation2=(TextView)findViewById(R.id.suffocation2);
        sp_suffocation1=(Spinner)findViewById(R.id.sp_suffocation1);


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
