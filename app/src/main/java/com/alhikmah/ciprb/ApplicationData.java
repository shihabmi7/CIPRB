package com.alhikmah.ciprb;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Shihab on 3/11/2016.
 */
public class ApplicationData {

    public static String URL_HOUSE_HOLD_MEMBERS = "";


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


    public static void gotToNextActivity(Activity activity, Class actiivty) {

        activity.startActivity(new Intent(activity, actiivty));

    }

    public static void gotToNextActivity(Activity activity, Class actiivty, Person aPerson) {

        Intent intent = new Intent(activity, actiivty);
        intent.putExtra("aPerson", aPerson);
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
