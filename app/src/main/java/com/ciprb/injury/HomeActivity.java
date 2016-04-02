package com.ciprb.injury;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    PrefsValues prefsValues;
    String form[] = {

            // "DATABASE",
            "মডিউল-১  ফেস ফর্ম",
            "খানা সদস্যদের ইনজুরি তথ্য",
            "মডিউল- ৩ ইনজুরি জনিত অসুস্থতার ফর্ম",
            "মডিউল-২ মৃত্যু নিশ্চিত করণ ফর্ম",
            "মডিউল-৪  ইনজুরি জনিত মৃত্যু ফর্ম",
            "বাড়ির তথ্য",
            "মডিউল-৬ দৈনন্দিন জীবনের মানদণ্ড",
            "এম-১ আত্মহত্যা",
            "এম-২ সড়ক দুর্ঘটনা",
            "এম-৩ সহিংসতার তথ্য",
            "এম-৪ পড়ে যাওয়া",
            "এম-৫  ধারালো বস্তুর আঘাত/ কেটে যাওয়া",
            "এম-৬ পুড়ে যাওয়া",
            "এম-৭ পানিতে দুবে যাওয়া",
            "এম-৮ দুর্ঘটনা জনিত বিষক্রিয়া",
            "এম-৯  মেশিন/ যন্ত্রপাতির দারা আঘাত",
            "এম-১০ বিদ্যুৎ স্পৃষ্টতা",
            "এম-১১ প্রানি/ কিট পতঙ্গের দ্বারা জখম",
            "এম-১২ ভোতা বস্তু দ্বারা আঘাত",
            "এম-১৩ শ্বাস রোধ"

    };

    //" Assult",
    Class[] samples =
            { //HouseHoldInformationActivity

                    // AndroidDatabaseManager.class,
                    HouseHoldInformationActivity.class,
                    HouseHoldMemberDetailsActivity.class,
                    InjuryMorbidityActivity.class,
                    DeathConfirmationActivity.class,
                    InjuryMortalityActivity.class,
                    HouseHoldCharacteristicsActivity.class,
                    QualityOfLifeActivity.class,
                    SuicideAttemptActivity.class,
                    RoadTransportInjuryActivity.class,
                    ViolenceInjuryActivity.class,
                    FallInjuryActivity.class, CutInjuryActivity.class,
                    BurnInjuryActivity.class, NearDrowningActivity.class,
                    UnintentionalPoisoningActivity.class, ToolInjuryActivity.class,
                    ElectrocautionActivity.class, InsectInjuryActivity.class,
                    InjuryBluntActivity.class, SuffocationActivity.class,
            };
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // wake lock
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        prefsValues = new PrefsValues(this);
        listView = (ListView) findViewById(R.id.listView_form_list);
        getFormList();

        FormAdapter formAdapter = new FormAdapter(HomeActivity.this);
        listView.setAdapter(formAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

//                    if (prefsValues.getMembersNo() > 0) {
//
//                        Toast.makeText(getApplicationContext(), getString(R.string.finish_current_house_hold_alive), Toast.LENGTH_LONG).show();
//
//                    } else if (prefsValues.getMembers_died_no() > 0) {
//
//                        Toast.makeText(getApplicationContext(), getString(R.string.finish_current_house_hold_died), Toast.LENGTH_LONG).show();
//
//
//                    }

//                    if (!prefsValues.getHouse_characteristics()){
//
//                        Toast.makeText(getApplicationContext(),
//                                getString(R.string.finish_current_house_hold_house_characteristics),
//                                Toast.LENGTH_LONG).show();
//
//                    }else
                    //  {

                    if (prefsValues.getMembers_died_no() > 0) {

                        Toast.makeText(getApplicationContext(), getString(R.string.finish_current_house_hold_died), Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(getApplicationContext(),
                                samples[position]);
                        startActivity(intent);

                    } else {

                        Intent intent = new Intent(getApplicationContext(),
                                samples[position]);
                        startActivity(intent);

                    }

                    // }

                    //
                } else if (position == 1) {
                    if (prefsValues.getMembersNo() == 0) {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_live_member), Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(),
                                samples[position]);
                        startActivity(intent);
                    }
                } else if (position == 3) {

                    if (prefsValues.getMembers_died_no() == 0) {

                        Toast.makeText(getApplicationContext(), getString(R.string.no_died_member), Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(),
                                samples[position]);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(getApplicationContext(),
                            samples[position]);
                    startActivity(intent);
                }

               /* Intent intent = new Intent(getApplicationContext(),
                        samples[position]);
                startActivity(intent);*/

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApplicationData.formList.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefsValues.getMembers_died_no() > 0) {

            Toast.makeText(getApplicationContext(), getString(R.string.finish_current_house_hold_died), Toast.LENGTH_LONG).show();



        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ApplicationData.formList.clear();
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

        int live = 7;
        int test = form.length;

        for (int i = 0; i < live; i++) {

            Form formObj = new Form();
            formObj.setTitle(form[i]);
            ApplicationData.formList.add(formObj);

        }
    }


    protected void goForRateUs() {

//        com.ciprb.injury

        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Could't lunch the market",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.update:
                //Toast.makeText(getBaseContext(), "You selected Phone", Toast.LENGTH_SHORT).show();

                goForRateUs();

                break;


        }
        return true;

    }
}
