package com.alhikmah.ciprb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class QualityOfLifeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_cancel,button_next;
    private TextView Quality1,Quality2,Quality3,Quality4,Quality5;
    private CheckBox Quality11,Quality12,Quality13,Quality21,Quality22,Quality23,Quality31,Quality32,Quality33,
            Quality41,Quality42,Quality43,Quality51,Quality52,Quality53;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_of_life);
        Quality1=(TextView)findViewById(R.id.Quality1);
        Quality2=(TextView)findViewById(R.id.Quality2);
        Quality3=(TextView)findViewById(R.id.Quality3);
        Quality4=(TextView)findViewById(R.id.Quality4);
        Quality5=(TextView)findViewById(R.id.Quality5);

        Quality11=(CheckBox)findViewById(R.id.Quality11);
        Quality12=(CheckBox)findViewById(R.id.Quality12);
        Quality13=(CheckBox)findViewById(R.id.Quality13);

        Quality21=(CheckBox)findViewById(R.id.Quality21);
        Quality22=(CheckBox)findViewById(R.id.Quality22);
        Quality23=(CheckBox)findViewById(R.id.Quality23);

        Quality31=(CheckBox)findViewById(R.id.Quality31);
        Quality32=(CheckBox)findViewById(R.id.Quality32);
        Quality33=(CheckBox)findViewById(R.id.Quality33);

        Quality41=(CheckBox)findViewById(R.id.Quality41);
        Quality42=(CheckBox)findViewById(R.id.Quality42);
        Quality43=(CheckBox)findViewById(R.id.Quality43);

        Quality51=(CheckBox)findViewById(R.id.Quality51);
        Quality52=(CheckBox)findViewById(R.id.Quality52);
        Quality53=(CheckBox)findViewById(R.id.Quality53);



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
