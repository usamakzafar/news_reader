package com.usamakzafar.newsreader.utils.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.usamakzafar.newsreader.R;
import com.usamakzafar.newsreader.models.NewsStory;
import com.usamakzafar.newsreader.utils.HelpingMethods;
import com.usamakzafar.newsreader.utils.ParseJSON;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by usamazafar on 04/06/2017.
 */

public class NewsStoryNetworkCalls {
    private static String TAG = NewsStoryNetworkCalls.class.getSimpleName();

    private static Context context;
    private static ArrayList<Integer> newsIDList;
    private static ArrayList<NewsStory> newsStories;
    private static Integer latestStoryID;
    private static Integer currentCount;
    private static NewsStoriesUpdatedListener listener;

    private HTTPCallMethod httpCall;

    public NewsStoryNetworkCalls(Context c, int _currentCount, ArrayList<NewsStory> _newsStories, NewsStoriesUpdatedListener storiesListener){
        context = c;
        newsStories = _newsStories;
        currentCount = _currentCount;
        listener = storiesListener;

        httpCall = new HTTPCallMethod();

        new newsFetcher().execute();

    }

    private class newsFetcher extends AsyncTask<Void,Void,Void> {

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
                }
                else {
                    //Check if the list is already up to date or if its a request for more news stories
                    if (!isUpToDate() || moreRequired()) {

                        //Update the ID of the Latest Story fetched
                        latestStoryID = newsIDList.get(0);

                        Log.i(TAG, "Received IDs in ArrayList: " + newsIDList.size());

                        //Loop through the new stories IDs and fetch one by one
                        for (int i = newsStories.size(); i < currentCount; i++) {

                            // Step 1: Prepare GET URL
                            String callURL = HelpingMethods.compileURLforFetchingItems(context,newsIDList.get(i));

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
            listener.afterFetchingNewsStories(newsIDList.size(), isUpToDate, errorMessage);
        }
    }

    public interface NewsStoriesUpdatedListener {
        void beforeFetchingNewsStories();
        void onProgressUpdated(ArrayList<NewsStory> arrayList);
        void afterFetchingNewsStories(int totalCount, boolean isUpToDate, String error);
    }
}
