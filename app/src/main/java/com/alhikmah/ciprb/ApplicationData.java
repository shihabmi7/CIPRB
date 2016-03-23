package com.alhikmah.ciprb;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.DatePicker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Shihab on 3/11/2016.
 */
public class ApplicationData {

    public static String URL_HOUSE_HOLD_MEMBERS = "http://saeradesign.com/LumenApi/public/index.php/api/household";
    public static String URL_DEATH_CONFIRMATION = "http://saeradesign.com/LumenApi/public/index.php/api/household";

    // change just

    public static String URL_INJURY_MORBIDITY = "http://saeradesign.com/LumenApi/public/index.php/api/household/injurymorbidity/";
    public static String URL_INJURY_MORTALITY = "http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity/";

    public static String URL_SUICIDE = "http://saeradesign.com/LumenApi/public/index.php/api/household/suicideattemptactivity/";
    public static String URL_ROADTRANSPORTINJURY = "http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity/roadtransportinjury/";
    public static String URL_CUTINJURY = "http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity/cutinjury/";
    public static String URL_VIOLENCEINJURY = "http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity/violenceinjury/";
    public static String URL_FALL = "http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity/fallinjury/";
    public static String URL_NEAR_DROWN = "http://saeradesign.com/LumenApi/public/index.php/api/activity/neardrowing/";
    public static String URL_BURNINJURY = "http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity/burninjury/";
    public static String URL_UNINTENTIONAL_INJURY = "http://saeradesign.com/LumenApi/public/index.php/api/activity/unintentionpoisoning/";
    public static String URL_TOOl_INJURY = "http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity/toolinjury/";
    public static String URL_ELECTROCAUTION = "http://saeradesign.com/LumenApi/public/index.php/api/activity/electrocaution/";
    public static String URL_INSECT_INJURY = "http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity/insectinjury/";
    public static String URL_BLUNT_INJURY = "http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity/injuryblunt/";
    public static String URL_SUFFOGATION = "http://saeradesign.com/LumenApi/public/index.php/api/activity/suffocation/";
    public static String URL_QUALITY_OF_LIFE = "http://saeradesign.com/LumenApi/public/index.php/api/activity/qualityoflife/";


    // public static String URL_INJURY_MORBIDITY = "http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity";
    // public static String URL_INJURY_MORBIDITY = "http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity";
    // public static String URL_INJURY_MORBIDITY = "http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity";
    // public static String URL_INJURY_MORBIDITY = "http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity";
    public static String URL_CHARACTERISTIC = "http://saeradesign.com/LumenApi/public/index.php/api/household/characteristics/";


    public static int STATUS_SUCCESS = 200;
    public static String STATUS_FAILED = "400";

    public static ArrayList<Form> formList = new ArrayList<Form>();
    public static ArrayList<Person> alive_person_List = new ArrayList<Person>();
    public static ArrayList<Person> died_person_List = new ArrayList<Person>();

    public static String TAG_HOUSE_HOLD_UNIQE_ID = "house_unique_id";
    public static String HOUSE_HOLD_UNIQE_ID = "";
    public static String HOUSE_HOLD_MWMBERS_NO = "";

    public static int HOUSE_HOLD_MWMBERS_MAX = 15;
    public static int HOUSE_HOLD_MWMBERS_MIN = 0;

    public static int HOUSE_HOLD_MAIN = 01;
    public static int HOUSE_HOLD_RESPONDER = 02;
    public static String KEY_PERSON = "personid";

    public static int SERIAL = 01;
    public static int SERIAL_DEATH = 90;
    public static int ALIVE_PERSON_NUMBER = 0;
    public static boolean INJURY_DATA_COLLECT = false;

    public static String putRequestWithHeaderAndBody(String url, String jsonBody) throws IOException {


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        Response httpResponse = client.newCall(request).execute();
        httpResponse.code();
        Log.i("Response JsonBody are ", jsonBody);
        Log.i("Response data are ", response.body().string());
        Log.i("Response code are ", "" + httpResponse.code());
        //makeCall(client, request);

        return response.body().string();
    }

    public static int postRequestWithHeaderAndBody(String url, String jsonBody) throws IOException {


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonBody);
        Log.e("Response data are ", jsonBody);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        //Response httpResponse = client.newCall(request).execute();
        //  httpResponse.code();

        Log.e("Response data are ", response.body().string());
        Log.e("Response code are ", "" + response.code());
        //makeCall(client, request);

        return response.code();
    }


    public static int putRequestWithBody(String url, String jsonBody) throws IOException {


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        Log.i("Response JsonBody are ", jsonBody);
        Log.i("Response data are ", response.body().string());
        Log.i("Response code are ", "" + response.code());

        return response.code();
    }

    public static void gotToNextActivity(Activity activity, Class actiivty) {

        activity.startActivity(new Intent(activity, actiivty));

    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy hh:mm"); //
        String Datetime = dateformat.format(c.getTime()).toString();
        return Datetime;
    }

    public static void convertTwodates(Calendar calendar) {


        try {

            //Calendar c = Calendar.getInstance();
            //SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
            // Datetime = dateformat.format(calendar.getTime());
            // String toyBornTime = "2014-06-18 12:56:50";

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd-MM-yyyy hh:mm:ss aa");

            String toyBornTime = dateFormat.format(calendar.getTime());
            Date oldDate = dateFormat.parse(toyBornTime);
            System.out.println(oldDate);

            Date currentDate = new Date();

            long diff = currentDate.getTime() - oldDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            long month = days / 30;
            long year = month / 12;

            if (oldDate.before(currentDate)) {

                Log.e("oldDate", "is previous date");
                Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
                        + " hours: " + hours + " days: " + days + "month:" + month + "year:" + year);

            }

            // Log.e("toyBornTime", "" + toyBornTime);

        } catch (java.text.ParseException e) {

            e.printStackTrace();
        }
    }

    public static void gotToNextActivity(Activity activity, Class actiivty, Person aPerson) {

        Intent intent = new Intent(activity, actiivty);
        intent.putExtra("aPerson", aPerson);
        activity.startActivity(intent);

    }

    public static void gotToNextActivity(Activity activity, Class actiivty, String aPerson) {

        Intent intent = new Intent(activity, actiivty);
        intent.putExtra("personid", aPerson);
        activity.startActivity(intent);

    }

    public static String spilitStringFirst(String string) {

        String[] namesList = string.split("\\.");
        return namesList[0];

    }

    public static String spilitStringSecond(String string) {

        String[] namesList = string.split("\\.");
        return namesList[1];

    }

    public static String getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(calendar);

        return dateString.toString();
    }

    public static HashMap<String, String> memberListHashMap = new HashMap<String, String>();

    public static HashMap getmemberhashMap() {

        String variableKey = "";
        String variableValue = "";

        for (Object variableName : memberListHashMap.keySet()) {
            variableKey += variableName.toString() + "\n";
            variableValue += memberListHashMap.get(variableName) + "\n";
            System.out.println(variableKey + variableValue);
        }


        return memberListHashMap;
    }
}
