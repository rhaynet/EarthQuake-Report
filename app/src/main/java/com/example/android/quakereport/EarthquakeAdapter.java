package com.example.android.quakereport;
import android.graphics.drawable.GradientDrawable;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;


import com.example.android.quakereport.Earthquake;
import com.example.android.quakereport.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPERATOR = "of";
    String place;
    String placePrime;

    public EarthquakeAdapter(Activity context, List<Earthquake> Earthquakes){
        super(context,0, Earthquakes);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Earthquake currentEarthQuake = getItem(position);


        /** finding a textview with the corresponding id**/
        TextView magTextView = (TextView) listItemView.findViewById(R.id.mag_text_view);
        String formattedMagnitude = formatMagnitude(currentEarthQuake.getMagnumber());
        magTextView.setText(formattedMagnitude);

        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();

        int magnitudeColor = getMagnitudeColor(currentEarthQuake.getMagnumber());

        magnitudeCircle.setColor(magnitudeColor);

        TextView placeTextView = (TextView) listItemView.findViewById(R.id.place_text_view);
        placeTextView.setText(place);
        TextView placeprimeTextView = (TextView) listItemView.findViewById(R.id.placeprime_text_view);
        placeprimeTextView.setText(placePrime);

        Date dateObject = new Date(currentEarthQuake.getTimeInMilliseconds());
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        String formattedDate = formatDate(dateObject);
        dateTextView.setText(formattedDate);

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_text_view);
        String formattedTime = formatTime(dateObject);
        timeTextView.setText(formattedTime);



        String originalLocaton = currentEarthQuake.getPlaces();
        if(originalLocaton.contains(LOCATION_SEPERATOR)){
            String[] parts = originalLocaton.split(LOCATION_SEPERATOR);
            place = parts[0] + LOCATION_SEPERATOR;
            placePrime = parts[1];
        }
        else{
            place = getContext().getString(R.string.near_the);
            placePrime = originalLocaton;
        }




        return listItemView;
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch(magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;


        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }

    private String formatMagnitude(double magnitude){
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
                return magnitudeFormat.format(magnitude);

    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }


}
