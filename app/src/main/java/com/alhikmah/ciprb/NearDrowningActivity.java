package com.alhikmah.ciprb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class NearDrowningActivity extends AppCompatActivity {

    private Spinner sp_Drowning1,sp_Drowning2,sp_Drowning4,sp_Drowning5,sp_Drowning6,sp_Drowning7,sp_Drowning8,sp_Drowning9,sp_Drowning11,sp_Drowning12,sp_Drowning13,sp_Drowning14,sp_Drowning15;
    private TextView Drowning1,Drowning2,Drowning3,Drowning4,Drowning5,Drowning6,Drowning7,Drowning8,Drowning9,Drowning10,Drowning11,Drowning12,Drowning13,Drowning14,Drowning15,textView2,textView4,textView6;
    private Button button_cancel,button_next;
    private EditText et_Drowning3,et_Drowning10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_drowning);

        textView2=(TextView)findViewById(R.id.textView2);
        textView4=(TextView)findViewById(R.id.textView4);
        textView6=(TextView)findViewById(R.id.textView6);
        Drowning1=(TextView)findViewById(R.id.Drowning1);
        Drowning2=(TextView)findViewById(R.id.Drowning2);
        Drowning3=(TextView)findViewById(R.id.Drowning3);
        Drowning4=(TextView)findViewById(R.id.Drowning4);
        Drowning5=(TextView)findViewById(R.id.Drowning5);
        Drowning6=(TextView)findViewById(R.id.Drowning6);
        Drowning7=(TextView)findViewById(R.id.Drowning7);
        Drowning8=(TextView)findViewById(R.id.Drowning8);
        Drowning9=(TextView)findViewById(R.id.Drowning9);
        Drowning10=(TextView)findViewById(R.id.Drowning10);
        Drowning11=(TextView)findViewById(R.id.Drowning11);
        Drowning12=(TextView)findViewById(R.id.Drowning12);
        Drowning13=(TextView)findViewById(R.id.Drowning13);
        Drowning14=(TextView)findViewById(R.id.Drowning14);
        Drowning15=(TextView)findViewById(R.id.Drowning15);

        sp_Drowning1=(Spinner)findViewById(R.id.sp_Drowning1);
        sp_Drowning2=(Spinner)findViewById(R.id.sp_Drowning2);

        sp_Drowning4=(Spinner)findViewById(R.id.sp_Drowning4);
        sp_Drowning5=(Spinner)findViewById(R.id.sp_Drowning5);
        sp_Drowning6=(Spinner)findViewById(R.id.sp_Drowning6);
        sp_Drowning7=(Spinner)findViewById(R.id.sp_Drowning7);
        sp_Drowning8=(Spinner)findViewById(R.id.sp_Drowning8);
        sp_Drowning9=(Spinner)findViewById(R.id.sp_Drowning9);

        sp_Drowning11=(Spinner)findViewById(R.id.sp_Drowning11);
        sp_Drowning12=(Spinner)findViewById(R.id.sp_Drowning12);
        sp_Drowning13=(Spinner)findViewById(R.id.sp_Drowning13);
        sp_Drowning14=(Spinner)findViewById(R.id.sp_Drowning14);
        sp_Drowning15=(Spinner)findViewById(R.id.sp_Drowning15);


        et_Drowning3=(EditText)findViewById(R.id.et_Drowning3);
        et_Drowning10=(EditText)findViewById(R.id.et_Drowning10);

        button_next=(Button)findViewById(R.id.button_next);
        button_cancel=(Button)findViewById(R.id.button_cancel);

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
