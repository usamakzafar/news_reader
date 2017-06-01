package com.usamakzafar.newsreader.helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.util.Log;

import com.usamakzafar.newsreader.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class NewsStoryMethods {

    //Tag for logs
    private static String TAG = NewsStoryMethods.class.getSimpleName();

    //Method to fetch the Top Stories IDs from Hacker News
    public static ArrayList<Integer> getTopNewsStoriesID(Context context)
            throws ExecutionException, InterruptedException, JSONException {

        RestGetter getTopStories = new RestGetter();
        String s = getTopStories.execute(context.getResources().getString(R.string.RestTopStoriesURL)).get();

        //Parse the News Stories IDs in JSON Array
        JSONArray list = new JSONArray(s);

        Log.i(TAG, "Adding " + list.length() +" IDs to Array List" );

        ArrayList<Integer> newsIDList = new ArrayList<>();
        // ADD Each ID to an Array List
        for (int i=0; i< list.length(); i++){
            newsIDList.add(list.getInt(i));
        }
        return newsIDList;
    }


    //Method to Prepare URL and make call to get new News Story
    public static NewsStory getNewsStoryContent(Context context, int id)
            throws ExecutionException, InterruptedException, JSONException {

        //Prepare GET URL
        String callURL = context.getResources().getString(R.string.RestURL) + id + ".json";

        //Execute and store response in String Result
        String result = NewsStoryMethods.getNewsContent(callURL);

        //Parse & Return the resulting NewsStory from the Result String
        return ParseJSON.parseNewsStory(result);
    }

    //Getting the news Story Method
    public static String getNewsContent(String callURL) {
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

    //Date parsing to to return time elapsed
    public static String parseDate(Calendar calendar){
        long now = System.currentTimeMillis();
        long then = calendar.getTimeInMillis();
        long diff = now - then;

        return (String) DateUtils.getRelativeTimeSpanString(then,now,1);
    }
}
