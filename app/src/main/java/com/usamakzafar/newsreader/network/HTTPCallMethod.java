package com.usamakzafar.newsreader.network;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by usamazafar on 04/06/2017.
 */

public class HTTPCallMethod {

    // using OKHTTP
    public String makeHTTPCall(String callURL) throws IOException {

        String TAG = "Using OKHTTP";

        Log.i(TAG, "Beginning Call on URL: " + callURL);

        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(callURL);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // using HTTPUrlConnection
    /*public String makeHTTPCall2(String callURL) throws IOException {

        String TAG = "Using HTTPurlconnection";

        Log.i(TAG, "Beginning Call on URL: " + callURL);

        // Get URL
        URL url = new URL(callURL);

        // Open HTTP Connection from URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Get Input Stream
        InputStream is = connection.getInputStream();

        // Get Input Stream Reader
        InputStreamReader reader = new InputStreamReader(is);

        //Loop through all data in Input Stream and store in String
        int data = reader.read();
        String result = "";
        while (data != -1) {
            char c = (char) data;
            result += c;
            data = reader.read();
        }

        Log.i(TAG, "Returning String: " + result);
        return result;
    }*/
}
