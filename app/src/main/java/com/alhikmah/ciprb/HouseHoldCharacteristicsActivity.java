package com.alhikmah.ciprb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class HouseHoldCharacteristicsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button_cancel, button_next;
    private Spinner sp_socio1;
    EditText edittext_c01, edit_c02, edittext_c06, edittext_c11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_characteristics);

        edittext_c01 = (EditText) findViewById(R.id.edit_c01);
        edit_c02 = (EditText) findViewById(R.id.edit_c02);
        edittext_c06 = (EditText) findViewById(R.id.edittext_c06);
        edittext_c11 = (EditText) findViewById(R.id.edittext_c11);


        button_next = (Button) findViewById(R.id.button_next);
        button_cancel = (Button) findViewById(R.id.button_cancel);

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

        if (v == button_cancel) {


        } else if (v == button_next) {


        }
    }
}
