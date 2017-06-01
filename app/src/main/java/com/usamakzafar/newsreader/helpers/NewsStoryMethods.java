package com.usamakzafar.newsreader.helpers;

import android.content.Context;
import android.util.Log;

import com.usamakzafar.newsreader.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by usamazafar on 01/06/2017.
 */

public class NewsStoryMethods {
    private static String TAG = NewsStoryMethods.class.getSimpleName();

    public static ArrayList<Integer> getNewsStories(Context context) throws ExecutionException, InterruptedException, JSONException {
        RestGetter getTopStories = new RestGetter();
        String s = getTopStories.execute(context.getResources().getString(R.string.RestTopStoriesURL)).get();
        JSONArray list = new JSONArray(s);

        Log.i(TAG, "Adding " + list.length() +" IDs to Array List" );

        ArrayList<Integer> newsIDsList = new ArrayList<>();
        for (int i=0; i< list.length(); i++){

            newsIDsList.add(list.getInt(i));
        }
        return newsIDsList;
    }

}
