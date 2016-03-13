package com.alhikmah.ciprb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class HomeActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 1600;
    private static final String AUDIO_FILE_NAME = "labbaiyk_allah_first";

    String form[] = {

            "Face Sheet",
            "Detection of injury morbidity",
            "Death Confirmation",
            "Information on injury morbidity",
            "Injury Mortality  Form",
            " Suicide",
            "  RTI",
            " Fall  injury",
            " Cut injury",
            " Burn injury",
            " Drowning",
            " Unintentional Poisoning",
            " Machinetool injury",
            " Electrocution",
            " Animalinsect injury",
            " Injury by blunt object",
            " Suffocation",
            " Quality of life assessment"

    }; //" Assult",


    Class[] samples =
            {
                    HouseHoldInformationActivity.class,
                    HouseHoldMemberDetailsActivity.class,
                    DeathConfirmationActivity.class,
                    InjuryMorbidityActivity.class,
                    InjuryMortalityActivity.class,
                    SuicideAttemptActivity.class,
                    RoadTransportInjuryActivity.class,
                    FallInjuryActivity.class, CutInjuryActivity.class,
                    BurnInjuryActivity.class, NearDrowningActivity.class,
                    UnintentionalPoisoningActivity.class, ToolInjuryActivity.class, ElectrocutionActivity.class, InsectInjuryActivity.class,
                    InjuryBluntActivity.class, SuffocationActivity.class, QualityOfLifeActivity.class

            };

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = (ListView) findViewById(R.id.listView_form_list);

        getFormList();
        FormAdapter formAdapter = new FormAdapter(HomeActivity.this);
        listView.setAdapter(formAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getApplicationContext(),
                        samples[position]);
                startActivity(intent);

            }
        });

    }

    private static class Sample {
        private String mTitle;
        private Class<? extends Activity> mActivityClass;

        private Sample(String title, Class<? extends Activity> activityClass) {
            mTitle = title;
            mActivityClass = activityClass;
        }

        @Override
        public String toString() {
            return mTitle;
        }

        public Class<? extends Activity> getActivityClass() {
            return mActivityClass;
        }
    }


    void getFormList() {

        for (int i = 0; i < form.length; i++) {

            Form formObj = new Form();
            formObj.setTitle(form[i]);
            ApplicationData.formList.add(formObj);

        }
    }

}
