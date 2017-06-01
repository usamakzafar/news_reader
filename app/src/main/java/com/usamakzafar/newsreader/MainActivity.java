package com.usamakzafar.newsreader;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.usamakzafar.newsreader.helpers.Adapters.NewsStoryAdapter;
import com.usamakzafar.newsreader.helpers.NewsStory;
import com.usamakzafar.newsreader.helpers.NewsStoryMethods;
import com.usamakzafar.newsreader.helpers.ParseJSON;
import com.usamakzafar.newsreader.helpers.RestGetter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean isRecyclerViewLoading;
    private SwipeRefreshLayout swipeRefreshLayout;

    private NewsStoryAdapter mAdapter;

    private static int latestStoryID = 0;
    private static int currentCount = 20;

    private static ArrayList<NewsStory> newsStories;
    private static ArrayList<Integer> newsIDList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        //Setting Listener on the Swipe Refresh Layout
        swipeRefreshLayout.setOnRefreshListener(this);

        //to Bypass loading again in case orientation of the device has changed
        if(newsStories ==null)
            newsStories = new ArrayList<>();

        //Initialize & set Adapter on the Recycler View
        mAdapter = new NewsStoryAdapter(this, newsStories);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //On Scroll Listener to detect whether the ser has scrolled to the bottom of the list.
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!isRecyclerViewLoading) {
                    if (recyclerView.getAdapter().getItemCount() != 0) {
                        int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                        if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1) {
                            //If User has Scrolled to the bottom of the list, fetch more news stories
                            fetchMoreNewsStories();
                            Toast.makeText(MainActivity.this, "Loading more stories", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });



        try {
            //Fetch the new News Story IDs
            newsIDList = NewsStoryMethods.getTopNewsStoriesID(this);

            //Fetch the News Stories
            new FetchNewsStories().execute();

        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, R.string.interrupted_exception_message, Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, R.string.interrupted_exception_message, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, R.string.json_exception_message, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRefresh() {
        try {
            //Fetch the Updated List of stories
            newsIDList = NewsStoryMethods.getTopNewsStoriesID(MainActivity.this);

            //Check if the list is already up to date
            if (isUpToDate())
                Toast.makeText(this, R.string.up_to_date_message, Toast.LENGTH_SHORT).show();
            else
                new FetchNewsStories().execute();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //Method to increase number of Loaded stories (initially 20)
    private void fetchMoreNewsStories() {
        //Increase the number of stories to load
        if(currentCount+20 <= newsIDList.size())
            currentCount += 20;

        //Send Load request again
        new FetchNewsStories().execute();


        Log.i(TAG, "Request made for more news Stories, total count: " + currentCount);
    }

    //News Story Fetch in Background Method
    public class FetchNewsStories extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isRecyclerViewLoading = true;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                Log.i(TAG, "Received IDs in ArrayList: " + newsIDList.size());

                //Check to see if this is a request for more stories to be loaded or to refresh the list
                if (!moreRequired())
                    newsStories = new ArrayList<>();

                for (int i = newsStories.size(); i < currentCount; i++) {
                    NewsStory story = NewsStoryMethods.getNewsStoryContent(MainActivity.this, newsIDList.get(i));
                    if(story != null) {
                        newsStories.add(story);
                        publishProgress();
                    }
                }

            }catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            isRecyclerViewLoading = false;
            swipeRefreshLayout.setRefreshing(false);
            mAdapter.notifyDataSetChanged();
        }
    }

    //Method to check if the user has requested more stories to be loaded
    private static boolean moreRequired() {
        if (newsStories == null) return false;

        return newsStories.size() < currentCount;
    }

    //Method to check if the current list is up to date
    private static boolean isUpToDate() {
        //Checking if the latest news story is present
        return newsIDList.get(0) == latestStoryID;
    }

}
