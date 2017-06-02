package com.usamakzafar.newsreader.helpers;

import android.util.Log;

import com.usamakzafar.newsreader.helpers.Objects.Comment;
import com.usamakzafar.newsreader.helpers.Objects.NewsStory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by usamazafar on 01/06/2017.
 */

public class ParseJSON {
    private static String TAG = ParseJSON.class.getSimpleName();

    //Initialising the keys
    private static String KEY_AUTHOR =      "by";
    private static String KEY_DESCENDANTS = "descendants";
    private static String KEY_ID =          "id";
    private static String KEY_KIDS =        "kids";
    private static String KEY_SCORE =       "score";
    private static String KEY_TIME =        "time";
    private static String KEY_TITLE =       "title";
    private static String KEY_TYPE =        "type";
    private static String KEY_URL =         "url";
    private static String KEY_PARENT =      "parent";
    private static String KEY_COMMENT_TEXT ="text";
    private static String KEY_DELETED      ="deleted";

    //Method to format the received json string into a NewsStory Object
    public static NewsStory parseNewsStory(String s){
        //Pass in the JSON Object as a String

        //If successful, create a new NewsStory using the elements of the JSON object
        NewsStory story = new NewsStory();
        try {
            //Try to create a new JSON Object from that String
            JSONObject object = new JSONObject(s);

            //Parse All Integers
            story.setId(          parseInt(object,KEY_ID)          );
            story.setScore(       parseInt(object,KEY_SCORE)       );
            story.setDescendants( parseInt(object,KEY_DESCENDANTS) );

            // Parse ALL Strings
            story.setAuthor(     parseString(object, KEY_AUTHOR) );
            story.setType(       parseString(object, KEY_TYPE)   );
            story.setTitle(      parseString(object, KEY_TITLE)  );
            story.setUrl(        parseString(object, KEY_URL)    );

            //Parse JSON Array of Kids
            story.setKids(      parseKids(object, KEY_KIDS) );

            //Parse the time
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(object.getLong(KEY_TIME) * 1000L);
            story.setTime(        calendar );


            //Return the NewsStory object
            return story;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;


    }

    //Method to format the received json string into a Comment Object
    public static Comment parseComments(String s){
        //Pass in the JSON Object as a String

        try {
            // Try to create a new JSON Object from that String
            JSONObject object = new JSONObject(s);

            // Check to see if the comment was deleted
            if (!commentWasDeleted(object,KEY_DELETED)) {

                //If successful, create a new Comment using the elements of the JSON object
                Comment comment = new Comment();

                //Parse Integers
                comment.setId(parseInt(object, KEY_ID));
                comment.setParentID(parseInt(object, KEY_PARENT));

                //Parse Strings
                comment.setAuthor(parseString(object, KEY_AUTHOR));
                comment.setType(parseString(object, KEY_TYPE));
                comment.setText(parseString(object, KEY_COMMENT_TEXT));

                comment.setKids(parseKids(object, KEY_KIDS));

                //Parse the time
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(object.getLong(KEY_TIME) * 1000L);
                comment.setTime(calendar);

                //Return the object
                return comment;
            }
            return null;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;


    }

    private static boolean commentWasDeleted(JSONObject object, String keyDeleted) {
        try{
            return object.getBoolean(keyDeleted);
        } catch (JSONException e) { }
        return false;
    }


    // Private Method to extract
    //          String
    // from a JSON Object and Handle Error
    private static String parseString(JSONObject object, String key){
        String res = "";
        try {
            res = object.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }


    // Private Method to extract
    //      Integer
    // from an Object and Handle Error
    private static int parseInt(JSONObject object, String key){
        int i = 0;
        try {
            i = object.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return i;
    }


    // Private Method to extract
    //      JSON Array
    // from an Object and Handle Error
    private static JSONArray parseKids(JSONObject object, String key){
        JSONArray array = new JSONArray();
        try {
            array = object.getJSONArray(KEY_KIDS);
        } catch (JSONException e) {
            Log.i(TAG, "Object has no further kids");
        }
        return array;
    }

}
