package com.usamakzafar.newsreader;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.usamakzafar.newsreader.adapters.CommentsAdapter;
import com.usamakzafar.newsreader.network.NetworkHandler;
import com.usamakzafar.newsreader.utils.HelpingMethods;
import com.usamakzafar.newsreader.models.Comment;
import com.usamakzafar.newsreader.network.CommentsNetworkCalls;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity implements CommentsNetworkCalls.CommentsUpdatedListener{

    // Recycler View
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    // Adapter for the Recycler View
    private CommentsAdapter mAdapter;

    // List of all commentIDs
    private JSONArray commentIDs;

    // List of all Comments
    private List<Comment> commentsList;

    public CommentsNetworkCalls getCommentsNetworkCalls() {
        return commentsNetworkCalls;
    }

    // For making the network calls for comments
    private CommentsNetworkCalls commentsNetworkCalls = new NetworkHandler().getComments(this,this);

    // Config Variable to store the required level number
    private int maxLevel;
    private SharedPreferences preferences;
    private String KEY_MAXLEVEL = "maxLevel";

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        //Setting the actionbar/toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.comments_toolbar);
        toolbar.setTitle(getCommentsActivityTitle(getIntent().getStringExtra("title")));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Setting swipe refresh layout only for loading
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        // Get preferences for Max level configuration
        preferences = getSharedPreferences("myPref",MODE_PRIVATE);
        maxLevel = preferences.getInt(KEY_MAXLEVEL,2);

        // Load comment IDs into a JSON array
        loadCommentIDs(getIntent().getStringExtra("kids"));

        //If any error occurred
        if(commentIDs == null) {
            //Exit this activity
            onBackPressed();

        } else {
            initActivity();
        }
    }


    // Make a fresh Comment List and Put it to an adapter in the recycler view
    private void initActivity() {
        commentsList = new ArrayList<>();

        //Initialize & set Adapter on the Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_comments);

        mAdapter = new CommentsAdapter(this, commentsList);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // Adding a divider between items for clarity and for better a look
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        // Load Comments
        /*commentsNetworkCalls = new NetworkHandler().getComments(this,this);*/
        if(commentsNetworkCalls.execute(commentIDs,maxLevel))
            swipeRefreshLayout.setRefreshing(true);

    }


    // Getting IDs in JSON Array Format from string
    public void loadCommentIDs(String kids) {
        try {
            commentIDs = new JSONArray(kids);
        } catch (JSONException e) {
            HelpingMethods.showMessage(this, getString(R.string.comment_ids_error));
        }
    }

    @Override
    public void onProgressUpdated(Comment comment) {
        //if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);

        commentsList.add(comment);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        commentsNetworkCalls.finishIt();
        //Reverse activity transition animation
        overridePendingTransition(R.anim.right_from_left_back,R.anim.left_from_right_back);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_comments,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.reply_level:
                AlertDialog.Builder settings = buildAlertForSettingReplyLevel();
                settings.show();
                break;
            case R.id.menu_about:
                HelpingMethods.buildAlertForAbout(this).show();
                break;

            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public AlertDialog.Builder buildAlertForSettingReplyLevel() {
        // Alert Dialog to configure reply level setting
        AlertDialog.Builder settings= new AlertDialog.Builder(this);

        settings.setTitle("Choose Reply Level");

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.settings_reply_level, null);
        settings.setView(dialogView);

        final EditText num = (EditText) dialogView.findViewById(R.id.level_number);
        num.setText(String.valueOf(maxLevel));

        settings.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int entered = Integer.valueOf(num.getText().toString());
                if (entered == 0 || entered >= 20) entered = 20;
                maxLevel = entered;
                preferences.edit().putInt(KEY_MAXLEVEL,maxLevel).apply();
                initActivity();
            }
        });
        return settings;
    }

    public String getCommentsActivityTitle(String title) {
        return getString(R.string.comment_activity_prefix) + " " + title;
    }
}
