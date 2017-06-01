package com.usamakzafar.newsreader;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.usamakzafar.newsreader.helpers.Adapters.NewsStoryAdapter;
import com.usamakzafar.newsreader.helpers.NewsStory;
import com.usamakzafar.newsreader.helpers.NewsStoryMethods;
import com.usamakzafar.newsreader.helpers.RestGetter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private NewsStoryAdapter mAdapter;
    private ArrayList<NewsStory> newsList;
    private ArrayList<Integer> newsIDList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        swipeRefreshLayout.setOnRefreshListener(this);

        try {
            newsIDList = new ArrayList<>();
            newsIDList = NewsStoryMethods.getNewsStories(this);
            Log.i(TAG, "Received IDs in ArrayList: " + newsIDList.size() );
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
/*
        mAdapter = new NewsStoryAdapter(newsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);*/
    }

    @Override
    public void onRefresh() {

    }
}
