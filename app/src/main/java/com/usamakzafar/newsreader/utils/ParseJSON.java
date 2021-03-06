package com.usamakzafar.newsreader.utils;

import android.text.Html;
import android.util.Log;

import com.usamakzafar.newsreader.models.Comment;
import com.usamakzafar.newsreader.models.NewsStory;

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
    public static NewsStory parseNewsStory(String s) throws JSONException {

        //Create a new NewsStory using the elements of the JSON object
        NewsStory story = new NewsStory();


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
            story.setKids(      parseKids(object) );

            //Parse the time
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(object.getLong(KEY_TIME) * 1000L);
            story.setTime(        calendar );


            //Return the NewsStory object
            return story;

    }


    // Method to format the received json string into a Comment Object
    public static Comment parseComments(String s) throws JSONException {
        //Pass in the JSON Object as a String

        // Try to create a new JSON Object from that String
        JSONObject object = new JSONObject(s);

        // Check to see if the comment was deleted
        if (!commentWasDeleted(object)) {

            //If successful, create a new Comment using the elements of the JSON object
            Comment comment = new Comment();

            //Parse Integers
            comment.setId(parseInt(object, KEY_ID));
            comment.setParentID(parseInt(object, KEY_PARENT));

            //Parse Strings
            comment.setAuthor(parseString(object, KEY_AUTHOR));
            comment.setType(parseString(object, KEY_TYPE));

            // Comment Text is in HTML and Needs to be converted
            String html = parseString(object, KEY_COMMENT_TEXT);
            String text = HelpingMethods.HTMLtoText(html);
            comment.setText(text);

            comment.setKids(parseKids(object));

            //Parse the time
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(object.getLong(KEY_TIME) * 1000L);
            comment.setTime(calendar);

            //Return the object
            return comment;
        }
        return null;
    }


    // Check is the comment was deleted.
    // Only some comments have property "deleted"
    private static boolean commentWasDeleted(JSONObject object) {
        try{
            return object.getBoolean(KEY_DELETED);
        } catch (JSONException e) { }
        return false;
    }


    // Private Method to extract
    //          String
    // from a JSON Object and Handle Error
    private static String parseString(JSONObject object, String key) {
        try {
            return object.getString(key);
        }catch (Exception e) {e.printStackTrace();}
        return "";
    }


    // Private Method to extract
    //      Integer
    // from an Object and Handle Error
    private static int parseInt(JSONObject object, String key) {
        try {
            return object.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }


    // Private Method to extract
    //      JSON Array
    // from an Object and Handle Error
    private static JSONArray parseKids(JSONObject object){
        JSONArray array = new JSONArray();
        try {
            array = object.getJSONArray(KEY_KIDS);
        }catch (Exception e){e.printStackTrace();}
        return array;
    }

}
