package com.walmart.okhttpapplication.Helper;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.walmart.okhttpapplication.Model.LocLatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by sgovind on 12/4/15.
 */
public class HttpAsyncTask extends AsyncTask<String, Void, Boolean> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(String... postcodes) {
        String postcode = postcodes[0];
        final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();

        urlBuilder.addQueryParameter("address", postcode);
        String url = urlBuilder.build().toString();
        final Request request = new Request.Builder().url(url).build();
        String responseString = "";
        try {
            Response response = client.newCall(request).execute();
            if ( response.code() == 200) {
                responseString = response.body().string();
                //Log.v("Response:", responseString);
                JSONObject jsonResponse = null;
                try {
                    jsonResponse = new JSONObject(responseString);
                    JSONArray results = jsonResponse.getJSONArray("results");
                    JSONObject geometry = results.getJSONObject(0).getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    String lat = location.getString("lat");
                    String lng = location.getString("lng");
                    Log.v("lat/lng=", String.format("%s/%s", lat, lng));
                    LocLatLng locLatLng = new LocLatLng();
                    locLatLng.setPostcode(postcode);
                    locLatLng.setLat(Double.parseDouble(lat));
                    locLatLng.setLng(Double.parseDouble(lng));
                    locLatLng.save();
                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;


    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        //LocLatLng rLocLatLng = new LocLatLng().getByPostCode("94555");
        //Log.v("Result=", rLocLatLng.getLat()+"");


    }


}
