package com.usamakzafar.newsreader.helpers;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import com.usamakzafar.newsreader.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by usamazafar on 01/06/2017.
 */

public class HelpingMethods {

    //Tag for logs
    private static String TAG = HelpingMethods.class.getSimpleName();

    //Method to extract the Top Stories IDs from String received from Hacker News
    public static ArrayList<Integer> getTopNewsStoriesID(String s)
            throws ExecutionException, InterruptedException, JSONException, IOException {
        if (s !=null ) {

            //Parse the News Stories IDs in JSON Array
            JSONArray list = new JSONArray(s);

            Log.i(TAG, "Adding " + list.length() + " IDs to Array List");

            ArrayList<Integer> newsIDList = new ArrayList<>();
            // ADD Each ID to an Array List
            for (int i = 0; i < list.length(); i++) {
                newsIDList.add(list.getInt(i));
            }
            return newsIDList;
        }
        return null;
    }

    // Prepare URL according to the ID
    public static String compileURLforFetchingItems(Context context, int id){
        //Prepare GET URL
        return context.getString(R.string.RestURL) + id + ".json";

    }

    // Method to make the HTTP Call
    public static String makeHTTPCall(String callURL) throws IOException {

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

    //Date parsing to to return time elapsed
    public static String parseDate(Calendar calendar){
        long now = System.currentTimeMillis();
        long then = calendar.getTimeInMillis();

        return (String) DateUtils.getRelativeTimeSpanString(then,now,1);
    }
}
