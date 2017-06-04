package com.usamakzafar.newsreader.utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import com.usamakzafar.newsreader.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
    public static ArrayList<Integer> readNewsIDsFromString(String s) throws JSONException {
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

    // Date parsing to to return time elapsed
    public static String parseDate(Calendar calendar){
        long now = System.currentTimeMillis();
        long then = calendar.getTimeInMillis();

        return (String) DateUtils.getRelativeTimeSpanString(then,now,1);
    }

    // Method for showing message on screen
    public static void showMessage(Context c, String message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }
}
