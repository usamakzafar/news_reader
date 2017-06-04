package com.usamakzafar.newsreader.network;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by usamazafar on 04/06/2017.
 */

public class HTTPCallMethod {
    // Method to make the HTTP Call
    public String makeHTTPCall(String callURL) throws IOException {

        String TAG = "AsyncTaskGetNewsContent";

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
    }
}
