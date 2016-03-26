package com.ciprb.injury;

import java.io.Serializable;

/**
 * Created by Shihab on 3/14/2016.
 */
@SuppressWarnings("serial")
public class Person implements Serializable{

    private String date_of_birth;
    private String current_age;
    private String occupasion;
    private String marital_status;
    private String realation_with_hh;
    private String person_id;
    private String members_name;
    private String sex;
    private int injury_number;
    private String injury_type;


    private String interviewer_unique;
    private String how_many_injury_last_six;
    private String injury_last_six, smoking, buttle_nut, swimming;


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getMembers_name() {
        return members_name;
    }

    public void setMembers_name(String members_name) {
        this.members_name = members_name;
    }

    public int getInjury_number() {
        return injury_number;
    }

    public void setInjury_number(int injury_number) {
        this.injury_number = injury_number;
    }

    public String getInjury_type() {
        return injury_type;
    }

    public void setInjury_type(String injury_type) {
        this.injury_type = injury_type;
    }


    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getCurrent_age() {
        return current_age;
    }

    public void setCurrent_age(String current_age) {
        this.current_age = current_age;
    }


    public String getOccupasion() {
        return occupasion;
    }

    public void setOccupasion(String occupasion) {
        this.occupasion = occupasion;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }


    public String getRealation_with_hh() {
        return realation_with_hh;
    }

    public void setRealation_with_hh(String realation_with_hh) {
        this.realation_with_hh = realation_with_hh;
    }
}
