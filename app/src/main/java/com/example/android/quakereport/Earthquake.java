package com.example.android.quakereport;

public class Earthquake {

    private double mMagNumber;

    private String mPlaces;

    private long mTimeInMilliseconds;

    private String mUrl;

    public Earthquake(double magNumber, String places, long TimeInMilliseconds, String url){

        mMagNumber = magNumber;
        mPlaces = places;
        mTimeInMilliseconds = TimeInMilliseconds;
        mUrl=url;

    }

    public double getMagnumber(){
        return mMagNumber;
    }
    public String getPlaces(){
        return mPlaces;
    }
    public long getTimeInMilliseconds(){
        return mTimeInMilliseconds;
    }
    public String getUrl(){
        return mUrl;
    }
}
