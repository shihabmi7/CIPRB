package com.ciprb.injury;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefsValues {

    private SharedPreferences mPrefs;

    private String members_no = "mem_no";
    private String members_died_no = "mem_died_no";
    private String house_id = "house_id";
    private String interviewer_code = "interviewer_code";
    private String injury_type = "injury_type";
    private String house_hold_no = "house_hold_no";
    private String serial = "serial";
    private String death_serial = "death_serial";

    private String house_characteristics = "house_characteristics";

    Context context = null;

    public boolean getHouse_characteristics() {
        return mPrefs.getBoolean(this.house_characteristics, false);
    }

    public void setHouse_characteristics(Boolean house_characteristics) {
        mPrefs.edit().putBoolean(this.house_characteristics, house_characteristics).commit();
    }


    public int getSerial() {
        return mPrefs.getInt(serial, ApplicationData.SERIAL);
    }

    public void setSerial(int serial_no) {
        mPrefs.edit().putInt(serial, serial_no).commit();
    }
    public int getDeathSerial() {
        return mPrefs.getInt(death_serial, ApplicationData.SERIAL_DEATH);
    }

    public void setDeathSerial(int serial_no) {
        mPrefs.edit().putInt(death_serial, serial_no).commit();
    }

    public String gethouse_hold_no() {
        return mPrefs.getString(house_hold_no, "");
    }

    public void sethouse_hold_no(String house_hold) {
        mPrefs.edit().putString(house_hold_no, house_hold).commit();
    }

    public String getInterviewer_code() {
        return mPrefs.getString(interviewer_code, "");
    }

    public void setinterviewer_code(String code) {
        mPrefs.edit().putString(interviewer_code, code).commit();
    }


    public int getInjury_type() {
        return mPrefs.getInt(members_no, 0);
    }

    public void setInjury_type(int mem_no) {
        mPrefs.edit().putInt(members_no, mem_no).commit();
    }

    public PrefsValues(Context context) {

        this.context = context;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this.context);

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

    private void clearPreference(String value) {


        SharedPreferences settings = context.getSharedPreferences("PreferencesName", Context.MODE_PRIVATE);
        settings.edit().clear().commit();

//        SharedPreferences.Editor.clear();
    }
}