package com.usamakzafar.newsreader.helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.usamakzafar.newsreader.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by usamazafar on 01/06/2017.
 */

public class RestGetter extends AsyncTask<String,Void,String> {
    private String TAG = RestGetter.class.getSimpleName();
    private Context context;
    private ProgressBar progressBar;

    public RestGetter(){
    }
    public RestGetter(Context c, ProgressBar p){
        this.context = c;
        this.progressBar = p;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... params) {

        Log.i(TAG, "Beginning Call on URL: " + params[0]);
        // String sURL = context.getResources().getString(R.string.RestURL)+ params[0] + ".json";

        try {
            URL url = new URL(params[0]);

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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }
}
