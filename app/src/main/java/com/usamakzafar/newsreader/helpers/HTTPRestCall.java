package com.usamakzafar.newsreader.helpers;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by usamazafar on 01/06/2017.
 */

public class HTTPRestCall {

    public static String makeCall(String callURL){

        String TAG = "AsyncTaskGetNewsContent";

        Log.i(TAG, "Beginning Call on URL: " + callURL);
        // String sURL = context.getResources().getString(R.string.RestURL)+ params[0] + ".json";

        try {
            URL url = new URL(callURL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream is = connection.getInputStream();

            InputStreamReader reader = new InputStreamReader(is);

            int data = reader.read();

            String result = "";
            while (data != -1){
                char c = (char) data;
                result += c;
                data = reader.read();
            }

            Log.i(TAG, "Returning String: " + result);
            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
