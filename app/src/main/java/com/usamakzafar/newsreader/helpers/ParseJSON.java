package com.usamakzafar.newsreader.helpers;

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


    public static NewsStory parseNewsStory(String s){
        //Pass in the JSON Object as a String

        //If successful, create a new NewsStory using the elements of the JSON object
        NewsStory story = new NewsStory();
        try {
            //Try to create a new JSON Object from that String
            JSONObject object = new JSONObject(s);
            story.setId(          object.getInt(KEY_ID)             );
            story.setScore(       object.getInt(KEY_SCORE)          );

            story.setAuthor(      object.getString(KEY_AUTHOR)      );
            story.setType(        object.getString(KEY_TYPE)        );
            story.setTitle(       object.getString(KEY_TITLE)       );

            //Handle Unavailable Descendants
            int descendants = 0;
            try { descendants = object.getInt(KEY_DESCENDANTS); }catch (JSONException e){ e.printStackTrace(); }
            story.setDescendants( descendants );


            //Handle Unavailable Kids
            JSONArray array = new JSONArray();
            try { array = object.getJSONArray(KEY_KIDS); }catch (JSONException e){ e.printStackTrace(); }
            story.setKids( array );


            //Handle Unavailable URL
            String url = "N/A";
            try { url = object.getString(KEY_URL); } catch (JSONException e){e.printStackTrace();}
            story.setUrl( url );

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

    public static JSONObject parseComments(String s){
        //Pass in the JSON Object as a String

        try {
            //Try to create a new JSON Object from that String
            JSONObject object = new JSONObject(s);

            //If successful, create a new Comment using the elements of the JSON object
            Comment comment = new Comment();
            comment.setId(     object.getInt(KEY_ID) );
            comment.setAuthor( object.getString(KEY_AUTHOR) );
            comment.setType(   object.getString(KEY_TYPE) );

            comment.setKids(   object.getJSONArray(KEY_KIDS) );
            comment.setParentID(object.getInt(KEY_PARENT));
            comment.setText(    object.getString(KEY_COMMENT_TEXT));

            //Parse the time
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(object.getLong(KEY_TIME) * 1000L);

            comment.setTime(   calendar );

            //Return the object
            return object;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;


    }
}
