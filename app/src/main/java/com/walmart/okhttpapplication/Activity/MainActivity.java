package com.walmart.okhttpapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.walmart.okhttpapplication.Helper.HttpAsyncTask;
import com.walmart.okhttpapplication.Interface.IGetResponseFromCallBack;
import com.walmart.okhttpapplication.Model.LocLatLng;
import com.walmart.okhttpapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";


    OkHttpClient client;
    String res = "";
    String postcode = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
        postcode = "94555";
        urlBuilder.addQueryParameter("address", postcode);
        String url = urlBuilder.build().toString();
        final Request request = new Request.Builder().url(url).build();


        getLatLng(request, new IGetResponseFromCallBack() {
            @Override
            public void getResponseFromCallBack(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray results = jsonResponse.getJSONArray("results");
                    JSONObject geometry = results.getJSONObject(0).getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    String lat = location.getString("lat");
                    String lng = location.getString("lng");
                    Log.v("lat/lng=", String.format("%s/%s", lat, lng));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });*/
        new HttpAsyncTask().execute("94555");
        LocLatLng rLocLatLng = new LocLatLng().getByPostCode("94555");
        Log.v("Result=", rLocLatLng.getLat()+"");

    }

    public void getLatLng(Request request, final IGetResponseFromCallBack callBack) {

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                //Log.v("Response=", response.toString());
                if (response.isSuccessful()) {

                    try {

                        callBack.getResponseFromCallBack(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    throw new IOException("Response=" + response);
                }
            }
        });


    }
}
