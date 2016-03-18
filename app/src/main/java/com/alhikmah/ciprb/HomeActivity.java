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

    /*"মডিউল-১  ফেস ফর্ম ",
    মডিউল-২ মৃত্যু নিশ্চিত করণ ফর্ম
    মডিউল- ৩ ইনজুরি জনিত অসুস্থতার ফর্ম
    মডিউল-৪  ইনজুরি জনিত মৃত্যু ফর্ম
    এম-১ আত্মহত্যা
    এম-২ সড়ক দুর্ঘটনা
    এম-৩ সহিংসতার তথ্য
    এম-৪ পড়ে যাওয়া
    এম-৫  ধারালো বস্তুর আঘাত/ কেটে যাওয়া
    এম-৬ পুড়ে যাওয়া
    এম-৭ পানিতে দুবে যাওয়া
    এম-৮ দুর্ঘটনা জনিত বিষক্রিয়া
    এম-৯  মেশিন/ যন্ত্রপাতির দারা আঘাত
    এম-১০ বিদ্যুৎ স্পৃষ্টতা
    এম-১১ প্রানি/ কিট পতঙ্গের দ্বারা জখম
    এম-১২ ভোতা বস্তু দ্বারা আঘাত
    এম-১৩ শ্বাস রোধ
    মডিউল-৬ দৈনন্দিন জীবনের মানদণ্ড
*/
    String form[] = {
            "Face Sheet",
            "Detection of injury morbidity",
            "Information on injury morbidity",
            "Death Confirmation",
            "Injury Mortality  Form",
            "Suicide",
            "RTI",
            "Violence",
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
                    InjuryMorbidityActivity.class,
                    DeathConfirmationActivity.class,
                    InjuryMortalityActivity.class,
                    SuicideAttemptActivity.class,
                    RoadTransportInjuryActivity.class,
                    ViolenceInjuryActivity.class,
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
        //form.length
        for (int i = 0; i < form.length; i++) {

            Form formObj = new Form();
            formObj.setTitle(form[i]);
            ApplicationData.formList.add(formObj);

        }
    }

}
