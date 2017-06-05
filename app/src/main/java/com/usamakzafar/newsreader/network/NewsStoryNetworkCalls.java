package com.usamakzafar.newsreader.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.usamakzafar.newsreader.R;
import com.usamakzafar.newsreader.models.NewsStory;
import com.usamakzafar.newsreader.utils.HelpingMethods;
import com.usamakzafar.newsreader.utils.ParseJSON;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.usamakzafar.newsreader.utils.HelpingMethods.haveNetworkConnection;

/**
 * Created by usamazafar on 04/06/2017.
 */

public class NewsStoryNetworkCalls {
    private String TAG = NewsStoryNetworkCalls.class.getSimpleName();

    private Context context;
    private ArrayList<Integer> newsIDList;
    private Integer latestStoryID;

    public NewsStoriesUpdatedListener getListener() {
        return listener;
    }

    private NewsStoriesUpdatedListener listener;

    private HTTPCallMethod httpCall;

    public NewsStoryNetworkCalls(Context c, NewsStoriesUpdatedListener storiesListener){
        context = c;
        listener = storiesListener;
        httpCall = new HTTPCallMethod();
    }

    public void execute( int currentCount, List<NewsStory> newsStories){
        if (!haveNetworkConnection(context)) {
            HelpingMethods.showMessage(context,context.getString(R.string.no_internet_connection_message));
        }
        else {
            new newsFetcher(newsStories, currentCount).execute();
        }
    }

    private class newsFetcher extends AsyncTask<Void,Void,Void> {

        private final List<NewsStory> newsStories;
        private final Integer currentCount;

        public newsFetcher(List<NewsStory> newsStories, Integer currentCount) {
            this.newsStories = newsStories;
            this.currentCount = currentCount;
        }

        private boolean isUpToDate;

        private String errorMessage;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            errorMessage = null;
            isUpToDate = false;
            listener.beforeFetchingNewsStories();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //Fetch the Updated List of stories
                String s = httpCall.makeHTTPCall(context.getString(R.string.RestTopStoriesURL));
                newsIDList = HelpingMethods.readNewsIDsFromString(s);

                if (newsIDList == null) {
                    //No Internet Connection
                    errorMessage = context.getString(R.string.no_internet_connection_message);
                } else {
                    //Check if the list is already up to date or if its a request for more news stories
                    if (!isUpToDate() || moreRequired()) {

                        //Update the ID of the Latest Story fetched
                        latestStoryID = newsIDList.get(0);

                        Log.i(TAG, "Received IDs in ArrayList: " + newsIDList.size());

                        //Loop through the new stories IDs and fetch one by one
                        for (int i = newsStories.size(); i < currentCount; i++) {

                            // Step 1: Prepare GET URL
                            String callURL = HelpingMethods.compileURLforFetchingItems(context, newsIDList.get(i));

                            // Step 2: Execute the HTTP Request on URL and store response in String Result
                            String result = httpCall.makeHTTPCall(callURL);

                            // Step 3: Parse & Return the resulting NewsStory from the Result String
                            NewsStory story = ParseJSON.parseNewsStory(result);

                            //Step 4: Add the fetched story to the list
                            if (story != null) {
                                newsStories.add(story);
                                publishProgress();
                            }

                        }
                    } else {
                        isUpToDate = true;
                    }
                }
            }
            // Catch the Exceptions
            catch (JSONException e) {
                e.printStackTrace();
                errorMessage = context.getString(R.string.json_exception_message);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

        //Method to check if the user has requested more stories to be loaded
        private boolean moreRequired() {
            if (newsStories == null) return false;

            return newsStories.size() < currentCount;
        }

        //Method to check if the current list is up to date
        private boolean isUpToDate() {
            //Checking if the latest news story is already present
            return newsIDList.get(0) == latestStoryID;
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            listener.onProgressUpdated(newsStories);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            int size = 0;
            if (newsIDList!= null)
                size = newsIDList.size();

            listener.afterFetchingNewsStories(size, isUpToDate, errorMessage);
        }
    }

    public interface NewsStoriesUpdatedListener {
        void beforeFetchingNewsStories();
        void onProgressUpdated(List<NewsStory> arrayList);
        void afterFetchingNewsStories(int totalCount, boolean isUpToDate, String error);
    }

}
