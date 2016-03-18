package com.alhikmah.ciprb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class ToolInjuryActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner sp_machine1, sp_machine2, sp_machine3, sp_machine4;
    private TextView machine1, machine2, machine3, machine4, textView2, textView4, textView6;
    private Button button_cancel, button_next;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_injury);

        button_next = (Button) findViewById(R.id.button_next);
        button_cancel = (Button) findViewById(R.id.button_cancel);

        sp_machine1 = (Spinner) findViewById(R.id.sp_machine1);
        sp_machine2 = (Spinner) findViewById(R.id.sp_machine2);
        sp_machine3 = (Spinner) findViewById(R.id.sp_machine3);
        sp_machine4 = (Spinner) findViewById(R.id.sp_machine4);

        machine1 = (TextView) findViewById(R.id.machine1);
        machine2 = (TextView) findViewById(R.id.machine2);
        machine3 = (TextView) findViewById(R.id.machine3);
        machine4 = (TextView) findViewById(R.id.machine4);

        textView2 = (TextView) findViewById(R.id.textView2);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView6 = (TextView) findViewById(R.id.textView6);

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
