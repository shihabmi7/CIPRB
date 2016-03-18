package com.alhikmah.ciprb;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefsValues {

    private SharedPreferences mPrefs;

    private String members_no = "mem_no";
    private String members_died_no = "mem_died_no";
    private String house_id = "house_id";

    private String injury_type = "injury_type";

    public int getInjury_type() {
        return mPrefs.getInt(members_no, 0);
    }

    public void setInjury_type(int mem_no) {
        mPrefs.edit().putInt(members_no, mem_no).commit();
    }

    public PrefsValues(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SharedPreferences getPrefs() {
        return mPrefs;
    }

    public int getMembersNo() {
        return mPrefs.getInt(members_no, 0);
    }

    public void setMembers_no(int mem_no) {
        mPrefs.edit().putInt(members_no, mem_no).commit();
    }

    public int getMembers_died_no() {
        return mPrefs.getInt(members_died_no, 0);
    }

    public void setMembers_died_no(int mem_died_no) {

        mPrefs.edit().putInt(members_died_no, mem_died_no).commit();

    }

    public String getHouseUniqueId() {
        return mPrefs.getString(house_id, "");
    }

    public void setHouseUnique_id(String houseUnique_id) {

        mPrefs.edit().putString(house_id, houseUnique_id).commit();

    }

    public String getActivityName() {
        return mPrefs.getString("activityName", "");
    }

    public void setActivityName(String activityName) {
        mPrefs.edit().putString("activityName", activityName).commit();
    }


    public String getLanguage() {
        return mPrefs.getString("language", "");
    }

    public void setLanguage(String language) {
        mPrefs.edit().putString("language", language).commit();
    }
}