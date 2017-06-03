package com.usamakzafar.newsreader;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.usamakzafar.newsreader.helpers.Adapters.NewsStoryAdapter;
import com.usamakzafar.newsreader.helpers.Listener.RecyclerItemClickListener;
import com.usamakzafar.newsreader.helpers.Objects.NewsStory;
import com.usamakzafar.newsreader.helpers.HelpingMethods;
import com.usamakzafar.newsreader.helpers.ParseJSON;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private String TAG = MainActivity.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayout;

    //Recycler View
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean isRecyclerViewLoading;

    //Adapter for the Recycler View
    private NewsStoryAdapter mAdapter;

    //Variables to keep track of the current display
    private static int latestStoryID = 0;
    private static int currentCount = 20;

    //Variables to store the fetched data
    private static ArrayList<NewsStory> newsStories;
    private static ArrayList<Integer> newsIDList;

    public static String errorMessage;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting the actionbar/toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));

        //Find Views
        recyclerView       = (RecyclerView) findViewById(R.id.recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        //Setting Listener on the Swipe Refresh Layout
        swipeRefreshLayout.setOnRefreshListener(this);

        //to Bypass loading again in case orientation of the device has changed
        if(newsStories == null)
            newsStories = new ArrayList<>();

        //Initialize & set Adapter on the Recycler View
        mAdapter = new NewsStoryAdapter(this, newsStories);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //Adding a divider between items for clarity and for better a look
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //On Scroll Listener to detect whether the ser has scrolled to the bottom of the list.
        recyclerView.setOnScrollListener(scrollListener);

        //Implement on item Click Listener
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                //Get Selected News Story
                NewsStory selectedNewsStory = newsStories.get(position);

                //Check if the News Story has any replies
                if (selectedNewsStory.getKids().length() > 0 && selectedNewsStory.getDescendants() >0) {

                    //Make Intent for Comments Activity
                    Intent intent = new Intent(MainActivity.this, CommentsActivity.class);
                    intent.putExtra("text", selectedNewsStory.getTitle());
                    intent.putExtra("kids", selectedNewsStory.getKids().toString());

                    //Start Intent with a little animation
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_from_right, R.anim.right_from_left);
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        //Fetch News Stories
        new FetchNewsStories().execute();

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

        private boolean isUpToDate;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isRecyclerViewLoading = true;
            isUpToDate = false;
            errorMessage = null;
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //Fetch the Updated List of stories
                String s = HelpingMethods.makeHTTPCall(getString(R.string.RestTopStoriesURL));
                newsIDList = HelpingMethods.getTopNewsStoriesID(s);

                if (newsIDList == null) {
                    errorMessage = "No Internet Connection";
                }
                else {
                    //Check if the list is already up to date or if its a request for more news stories
                    if (!isUpToDate() || moreRequired()) {

                        //Update the ID of the Latest Story fetched
                        latestStoryID = newsIDList.get(0);

                        Log.i(TAG, "Received IDs in ArrayList: " + newsIDList.size());

                        //Check to see if this is a request for more stories to be loaded or to refresh the list
                        if (!moreRequired())
                            newsStories = new ArrayList<>();

                        //Loop through the new stories IDs and fetch one by one
                        for (int i = newsStories.size(); i < currentCount; i++) {

                            // Step 1: Prepare GET URL
                            String callURL = HelpingMethods.compileURLforFetchingItems(MainActivity.this,newsIDList.get(i));

                            // Step 2: Execute the HTTP Request on URL and store response in String Result
                            String result = HelpingMethods.makeHTTPCall(callURL);

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
            catch (ExecutionException e) {
                e.printStackTrace();
                errorMessage = e.getLocalizedMessage();
            } catch (InterruptedException e) {
                e.printStackTrace();
                errorMessage = e.getLocalizedMessage();
            } catch (JSONException e) {
                e.printStackTrace();
                errorMessage = e.getLocalizedMessage();
            } catch (IOException e) {
                e.printStackTrace();
                errorMessage = e.getLocalizedMessage();
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

            if (errorMessage != null)
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            else if (isUpToDate)
                Toast.makeText(MainActivity.this, getString(R.string.up_to_date_message), Toast.LENGTH_SHORT).show();
        }
    }

    //Method to check if the user has requested more stories to be loaded
    private static boolean moreRequired() {
        if (newsStories == null) return false;

        return newsStories.size() < currentCount;
    }

    //Method to check if the current list is up to date
    private static boolean isUpToDate() {
        //Checking if the latest news story is already present
        return newsIDList.get(0) == latestStoryID;
    }

    @Override
    public void onRefresh() {
        // Refresh the list if Recycler View is not already loading data
        if (!isRecyclerViewLoading)     //Execute Fetch
            new FetchNewsStories().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_about:
                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setMessage(R.string.my_message);
                builder.setPositiveButton("Okay",null);
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Recycle View Listener
    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
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
    };
}
