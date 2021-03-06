package com.usamakzafar.newsreader.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.usamakzafar.newsreader.R;
import com.usamakzafar.newsreader.models.Comment;
import com.usamakzafar.newsreader.network.NetworkHandler;

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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by usamazafar on 01/06/2017.
 */

public class HelpingMethods {

    // Boolean check to see if the methods are being called by test classes
    public static boolean mocked = false;

    //Method to extract the Top Stories IDs from String received from Hacker News
    public static ArrayList<Integer> readNewsIDsFromString(String s) throws JSONException {
        if (s !=null ) {

            //Parse the News Stories IDs in JSON Array
            JSONArray list = new JSONArray(s);

//            Log.i(TAG, "Adding " + list.length() + " IDs to Array List");

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

    // Method for showing message on screen
    public static void showMessage(Context c, String message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

    // Method to convert the received comments from HTML to text
    public static String HTMLtoText(String string) {
        //For testing
        if (mocked) return string;

        return Html.fromHtml(string).toString();
    }

    // Network Check
    public static boolean haveNetworkConnection(Context context) {
        if(mocked) return true;

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    //public String makeHTTPCallForArray(String callURL)
    public String makeHTTPCallForArray(String callURL) throws IOException { return makeHTTPCall(callURL); }

    public String makeHTTPCallForObject(String callURL) throws IOException { return makeHTTPCall(callURL); }

    // using OKHTTP
    private String makeHTTPCall(String callURL) throws IOException {

        if(mocked) return "Mocked return";

        String TAG = "Using OKHTTP";

        Log.i(TAG, "Beginning Call on URL: " + callURL);

        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(callURL);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static AlertDialog.Builder buildAlertForAbout(Context context) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setMessage(R.string.my_message);
        builder.setPositiveButton("Okay",null);
        return builder;
    }

    public static AlertDialog.Builder buildAlertForComment(Context context, Comment comment) {
        //Open an Alert to show the full comment
        AlertDialog.Builder commentDialog = new AlertDialog.Builder(context);

        String howLongAgo = (String) DateUtils.getRelativeTimeSpanString(
                comment.getTime().getTimeInMillis(),
                System.currentTimeMillis(),
                1);

        commentDialog.setTitle( howLongAgo + " by " + comment.getAuthor());
        commentDialog.setMessage(comment.getText());
        commentDialog.setPositiveButton("Close",null);
        return commentDialog;
    }
}
