package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {


    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<Earthquake> fetchEarthquakeData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch(IOException e){
            Log.v(LOG_TAG,"error closing input stream", e);
        }
        List<Earthquake> earthquakes = extractFeatureFromJson(jsonResponse);
        return earthquakes;
    }
    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch(MalformedURLException e){
            Log.v(LOG_TAG,"Error with creating URL", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.v(LOG_TAG,"error response code: " +urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.v(LOG_TAG,"problem retrieving the earthquake JSON result",e);
        }finally{
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }

        }
        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();

    }


    /**

     * Return a list of {@link Earthquake} objects that has been built up from

     * parsing a JSON response.

     */

    private static List<Earthquake> extractFeatureFromJson(String earthquakeJson) {

        if(TextUtils.isEmpty(earthquakeJson)){
            return null;
        }
        //Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJson);
            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

            for(int i = 0; i < earthquakeArray.length();i++){
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");
                Double magnitude = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");


                Earthquake earthquake = new Earthquake(magnitude,place,time,url);
                earthquakes.add(earthquake);

            }

            // build up a list of Earthquake objects with the corresponding data.



        } catch (JSONException e) {

            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);

        }

        return earthquakes;

    }



}
