package com.usamakzafar.newsreader.helpers;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import com.usamakzafar.newsreader.R;
import com.usamakzafar.newsreader.helpers.Objects.NewsStory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by usamazafar on 01/06/2017.
 */

public class NewsStoryMethods {

    //Tag for logs
    private static String TAG = NewsStoryMethods.class.getSimpleName();

    //Method to fetch the Top Stories IDs from Hacker News
    public static ArrayList<Integer> getTopNewsStoriesID(Context context)
            throws ExecutionException, InterruptedException, JSONException {

        String s = HTTPRestCall.makeCall(context.getResources().getString(R.string.RestTopStoriesURL));
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


    //Method to Prepare URL and make call to get new News Story
    public static NewsStory getNewsStoryContent(Context context, int id)
            throws ExecutionException, InterruptedException, JSONException {

        //Prepare GET URL
        String callURL = context.getResources().getString(R.string.RestURL) + id + ".json";

        //Execute the HTTP Request on URL and store response in String Result
        String result = HTTPRestCall.makeCall(callURL);

        //Parse & Return the resulting NewsStory from the Result String
        return ParseJSON.parseNewsStory(result);
    }

    //Date parsing to to return time elapsed
    public static String parseDate(Calendar calendar){
        long now = System.currentTimeMillis();
        long then = calendar.getTimeInMillis();
        long diff = now - then;

        return (String) DateUtils.getRelativeTimeSpanString(then,now,1);
    }
}
