package com.usamakzafar.newsreader;

import android.content.Intent;
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

import com.usamakzafar.newsreader.network.NetworkHandler;
import com.usamakzafar.newsreader.utils.HelpingMethods;
import com.usamakzafar.newsreader.adapters.NewsStoryAdapter;
import com.usamakzafar.newsreader.models.NewsStory;
import com.usamakzafar.newsreader.network.NewsStoryNetworkCalls;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, NewsStoryNetworkCalls.NewsStoriesUpdatedListener {

    private String TAG = NewsActivity.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayout;

    //Recycler View
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean isRecyclerViewLoading;

    private NetworkHandler handler= new NetworkHandler();

    //Adapter for the Recycler View
    private NewsStoryAdapter mAdapter;

    public static int getCurrentCount() {
        return currentCount;
    }

    //Variables to keep track of the currently displayed stories
    private static int currentCount = 20;

    // To keep record of how many stories can be loaded in total
    private static int totalStoriesCount;

    //Variables to store the fetched News Stories
    private static List<NewsStory> newsStories;

    public NewsStoryNetworkCalls getStoryNetworkCalls() {
        return storyNetworkCalls;
    }

    // For making the network calls for news
    private NewsStoryNetworkCalls storyNetworkCalls = handler.getNewsStory(this,this);

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

        initActivity();
    }

    public NetworkHandler getHandler() {
        return handler;
    }

    public void initActivity() {

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

        //Fetch News Stories
        //storyNetworkCalls = handler.getNewsStory(this,this); TODO uncomment
        storyNetworkCalls.execute(currentCount,newsStories);
    }

    //Method to increase number of Loaded stories (initially 20)
    public void fetchMoreNewsStories() {
        //Increase the number of stories to load
        if(currentCount+20 <= totalStoriesCount)
            currentCount += 20;

        //Send Load request again
        storyNetworkCalls.execute(currentCount,newsStories);

        Log.i(TAG, "Request made for more news Stories, total count: " + currentCount);
    }

    @Override
    public void beforeFetchingNewsStories() {
        isRecyclerViewLoading = true;
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onProgressUpdated(List<NewsStory> arrayList) {
        newsStories = arrayList;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void afterFetchingNewsStories(int totalCount, boolean isUpToDate, String errorMessage) {
        totalStoriesCount = totalCount;

        isRecyclerViewLoading = false;
        swipeRefreshLayout.setRefreshing(false);

        if (errorMessage != null)
            HelpingMethods.showMessage(NewsActivity.this, errorMessage);
        else if (isUpToDate)
            HelpingMethods.showMessage(NewsActivity.this, getString(R.string.up_to_date_message));
    }

    @Override
    public void onRefresh() {
        // Refresh the list if Recycler View is not already loading data
        if (!isRecyclerViewLoading)     //Execute Fetch
            storyNetworkCalls.execute(currentCount,newsStories);
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
                HelpingMethods.buildAlertForAbout(this).show();
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
                        HelpingMethods.showMessage(NewsActivity.this, getString(R.string.scroll_bottom_message));
                        fetchMoreNewsStories();
                    }
                }
            }
        }
    };
}
