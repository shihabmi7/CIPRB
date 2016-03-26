package com.ciprb.injury;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import fr.ganfra.materialspinner.MaterialSpinner;

public class SpinnerTest extends AppCompatActivity {


    BetterSpinner textView;
    Spinner spinner,spinner_two;
    MaterialSpinner spinner_material;
    BetterSpinner better_spinner;

    String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};

    private static final String[] COUNTRIES = new String[] {

            "Belgium", "France", "Italy", "Germany", "Spain"

    };

    Button test ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_test);

        spinner =(Spinner)findViewById(R.id.spinner);
        spinner_two =(Spinner)findViewById(R.id.spinner_two);


        test =(Button)findViewById(R.id.testButton);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkSpinner();


            }
        });

//        spinner_material = (MaterialSpinner) findViewById(R.id.spinner_material);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ITEMS);
//        spinner_material.setAdapter(adapter);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
//
//        textView = (BetterSpinner)
//                findViewById(R.id.better_spinner);
//        textView.setAdapter(adapter);


    }

    boolean checkSpinner(){


        if (spinner.getSelectedItemPosition() == 0){

            Toast.makeText(getApplicationContext(),"Please Select every option",Toast.LENGTH_LONG).show();
            return  false;


        }else {

            Toast.makeText(getApplicationContext(),"ok ..good",Toast.LENGTH_LONG).show();
            return true;
        }

    }




}
