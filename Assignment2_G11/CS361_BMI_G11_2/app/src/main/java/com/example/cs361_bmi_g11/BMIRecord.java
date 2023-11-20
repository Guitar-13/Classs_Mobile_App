package com.example.cs361_bmi_g11;

import android.os.Parcel;
import android.os.Parcelable;

public class BMIRecord implements Parcelable {
    private String date;
    private float weight;
    private float bmi;
    private String classification;

    public BMIRecord(String date, float weight, float bmi, String classification) {
        this.date = date;
        this.weight = weight;
        this.bmi = bmi;
        this.classification = classification;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }


    protected BMIRecord(Parcel in) {
        date = in.readString();
        weight = in.readFloat();
        bmi = in.readFloat();
        classification = in.readString();
    }

    public static final Creator<BMIRecord> CREATOR = new Creator<BMIRecord>() {
        @Override
        public BMIRecord createFromParcel(Parcel in) {
            return new BMIRecord(in);
        }

        @Override
        public BMIRecord[] newArray(int size) {
            return new BMIRecord[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeFloat(weight);
        dest.writeFloat(bmi);
        dest.writeString(classification);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
