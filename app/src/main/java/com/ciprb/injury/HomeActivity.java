package com.ciprb.injury;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONObject;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {

    PrefsValues prefsValues;
    ProgressDialog progressDialog = null;

    boolean hasData=false;

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

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_message));
        progressDialog.setTitle(getString(R.string.waiting));
        progressDialog.setCancelable(true);
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
        hasData =false;
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

            case R.id.sync:

                try {


                    postDataOnServer(ApplicationData.URL_HOUSE_HOLD_MEMBERS, ApplicationData.OFFLINE_DB_HOUSE_HOLD_MEMBERS);
                    putDataOnServer(ApplicationData.URL_INJURY_MORBIDITY, ApplicationData.OFFLINE_DB_MORBIDITY);
                    putDataOnServer(ApplicationData.URL_BURNINJURY, ApplicationData.OFFLINE_DB_BURN_INJURY);
                    putDataOnServer(ApplicationData.URL_CUTINJURY, ApplicationData.OFFLINE_DB_CUT_INJURY);
                    postDataOnServer(ApplicationData.URL_DEATH_CONFIRMATION, ApplicationData.OFFLINE_DB_DEATH_CONFIRMATION);
                    putDataOnServer(ApplicationData.URL_ELECTROCAUTION, ApplicationData.OFFLINE_DB_ELECTROCATION);
                    putDataOnServer(ApplicationData.URL_CHARACTERISTIC, ApplicationData.OFFLINE_DB_HOUSE_HOLD_CHARACTERISTICS);
                    putDataOnServer(ApplicationData.URL_INJURY_MORTALITY, ApplicationData.OFFLINE_DB_INJURY_MORTALITY);
                    putDataOnServer(ApplicationData.URL_INSECT_INJURY, ApplicationData.OFFLINE_DB_INSECT_INJURY);
                    putDataOnServer(ApplicationData.URL_NEAR_DROWN, ApplicationData.OFFLINE_DB_NEAR_DROWNING);
                    putDataOnServer(ApplicationData.URL_QUALITY_OF_LIFE, ApplicationData.OFFLINE_DB_QUALITY_OF_LIFE);
                    putDataOnServer(ApplicationData.URL_SUICIDE, ApplicationData.OFFLINE_DB_SUICIDE_ATTEMPT);
                    putDataOnServer(ApplicationData.URL_TOOl_INJURY, ApplicationData.OFFLINE_DB_TOOL_INJURY);
                    putDataOnServer(ApplicationData.URL_UNINTENTIONAL_INJURY, ApplicationData.OFFLINE_DB_UNINTENTIONAL_POISIONING);
                    putDataOnServer(ApplicationData.URL_VIOLENCEINJURY, ApplicationData.OFFLINE_DB_VIOLENCE_INJURY);
                    putDataOnServer(ApplicationData.URL_SUFFOGATION, ApplicationData.OFFLINE_DB_SUFFOCATION);
                    putDataOnServer(ApplicationData.URL_BLUNT_INJURY, ApplicationData.OFFLINE_DB_INJURY_BLUNT);
                    putDataOnServer(ApplicationData.URL_FALL, ApplicationData.OFFLINE_DB_FALL_INJURY);
                    putDataOnServer(ApplicationData.URL_ROADTRANSPORTINJURY, ApplicationData.OFFLINE_DB_ROAD_TRANSPORT);

                    if (hasData) {

                        Toast.makeText(getApplicationContext(), getString(R.string.sync_doing), Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(getApplicationContext(), getString(R.string.no_data), Toast.LENGTH_LONG).show();
                    }



                }catch (Exception e) {

                    Log.e("AMLOG::: ", e.getMessage());
                    e.printStackTrace();
                }

                break;

        }
        return true;

    }

    private String[] getJsonArray(String fileName) {
        StringBuilder stringBuilder = ApplicationData.readFile(getApplicationContext(), fileName);
        ApplicationData.doFileEmpty(getApplicationContext(), fileName);
        if(stringBuilder.length() > 0)
            return ApplicationData.getDataArray(stringBuilder.toString());
        else
            return null;
    }

    private void postDataOnServer(String apiUri, String fileName) {

        String[] dataArray = getJsonArray(fileName);
        if(dataArray != null && dataArray.length > 0) {

            ApplicationData.doFileEmpty(getApplicationContext(), fileName);
            hasData = true;
            Log.e("postData:: ", "dataArray length: " + dataArray.length);
            for (String aDataArray : dataArray) {

                try {

                    Log.d("AMLOG::upload: ", aDataArray);
                    if (InternetConnection.checkNetworkConnection(this)) {

                        Log.e("AMLOG:::" , "Internet connection found!");
                        new PostAsync(fileName).execute(apiUri, aDataArray);

                    } else  {

                        Log.e("AMLOG:::" , "No internet");
                        ApplicationData.writeToFile(this, fileName, aDataArray);
                        Toast.makeText(getApplicationContext(),"Internet connection not available",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e("AMLOG::: ", e.getMessage());
                    e.printStackTrace();
                }
            }

        }
    }

    private void putDataOnServer(String apiUri, String fileName) {

        String[] dataArray = getJsonArray(fileName);
        if( dataArray != null && dataArray.length > 0) {

            ApplicationData.doFileEmpty(getApplicationContext(), fileName);
            hasData = true;
            for (String aDataArray : dataArray) {
                try {

                    JSONObject obj = new JSONObject(aDataArray);
                    String personId = obj.getString("household_unique_code");
                    Log.d("AMLOG::upload: ", aDataArray);
                    Log.d("AMLOG::personId: ", personId);

                    if (InternetConnection.checkNetworkConnection(this)) {

                        new PutAsync(fileName).execute(apiUri+personId, aDataArray);
                    }else  {

                        Toast.makeText(getApplicationContext(),"Internet connection not available",Toast.LENGTH_LONG).show();
                        ApplicationData.writeToFile(this, fileName, aDataArray);
                    }
                } catch (Exception e) {
                    Log.e("AMLOG::: ", e.getMessage());
                    e.printStackTrace();
                }
            }

//
        }

    }



    private class PostAsync extends AsyncTask<String, Void, String> {

        int value = 0;

        String file;
        String data = null;
        PostAsync() {

        }
        PostAsync(String fileName) {
            file = fileName;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                Log.e("URL are ", params[0]);
                Log.e("Data ", params[1]);
                data = params[1];
                value = ApplicationData.postRequestWithHeaderAndBody(params[0], params[1]);

            } catch (Exception e) {
                Log.e("doInBackground: ", e.getMessage());
                ApplicationData.writeToFile(getApplicationContext(), file, params[1]);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            if (value == ApplicationData.STATUS_SUCCESS) {

                Log.d("", "Data uploaded successfully");
//                finishTask();

            } else {
                Log.d("", "Data uploaded failed.");
            }

        }

    }

    private class PutAsync extends AsyncTask<String, Void, String> {

        int value = 0;
        String data = null;
        String file;

        PutAsync() {

        }

        PutAsync(String file) {
            this.file = file;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                Log.e("URL are ", params[0]);
                data = params[1];
                value = ApplicationData.putRequestWithBody(params[0], params[1]);

            } catch (Exception e) {
                Log.e("doInBackground: ", e.getMessage());
                ApplicationData.writeToFile(getApplicationContext(), file, params[1]);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            if (value == ApplicationData.STATUS_SUCCESS) {

                Log.d("", "Data uploaded successfully");
//                finishTask();

            } else {
                Log.d("", "Data uploaded failed.");
            }


        }

    }



    void finishTask() {

        Toast.makeText(this, "Successfully Data Saved", Toast.LENGTH_LONG).show();
    }

}
