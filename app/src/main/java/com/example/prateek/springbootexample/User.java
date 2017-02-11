package com.example.prateek.springbootexample;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Prateek on 25-01-2017.
 */

public class User {


    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;

    @SerializedName("occupation")
    String occupation;
/*    @SerializedName("company")
    String company;
    @SerializedName("phone")
    String phone;*/
    @SerializedName("google")
    String google;

    public User(){

    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }


    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

}
