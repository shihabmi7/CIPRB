package com.alhikmah.ciprb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UnintentionalPoisoningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unintentional_poisoning);
    }
}
