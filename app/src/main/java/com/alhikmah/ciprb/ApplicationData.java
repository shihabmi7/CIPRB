package com.alhikmah.ciprb;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Shihab on 3/11/2016.
 */
public class ApplicationData {

    public static String URL_HOUSE_HOLD_MEMBERS = "http://saeradesign.com/LumenApi/public/index.php/api/household";
    public static String URL_INJURY_MORBIDITY = "http://saeradesign.com/LumenApi/public/index.php/api/injuryactivity";

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

    public static int SERIAL = 1;

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
