package com.usamakzafar.newsreader;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.usamakzafar.newsreader.helpers.Adapters.CommentsAdapter;
import com.usamakzafar.newsreader.helpers.HelpingMethods;
import com.usamakzafar.newsreader.helpers.Listener.RecyclerItemClickListener;
import com.usamakzafar.newsreader.helpers.Objects.Comment;
import com.usamakzafar.newsreader.helpers.ParseJSON;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {

    // Recycler View
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    // Adapter for the Recycler View
    private CommentsAdapter mAdapter;

    // List of all commentIDs
    private JSONArray commentIDs;

    // List of all Comments
    private static ArrayList<Comment> commentsList;

    // Config Variable to store the required level number
    private int maxLevel;
    private SharedPreferences preferences;
    private String KEY_MAXLEVEL = "maxLevel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        // Get preferences for Max level configuration
        preferences = getSharedPreferences("myPref",MODE_PRIVATE);
        maxLevel = preferences.getInt(KEY_MAXLEVEL,2);

        //Setting the actionbar/toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.comments_toolbar);
        toolbar.setTitle(getString(R.string.comment_activity_prefix) + " " + getIntent().getStringExtra("text"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Load comment IDs into a JSON array
        loadCommentIDs(getIntent().getStringExtra("kids"));

        //If any error occurred
        if(commentIDs == null) {
            //Exit this activity
            onBackPressed();

        } else {
            loadComments();
        }
    }


    // Make a fresh Comment List and Put it to an adapter in the recycler view
    private void loadComments() {
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


        //Implement on item Click Listener to view full comment
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                //Get Selected News Story
                Comment comment = commentsList.get(position);

                //Open an Alert to show the full comment
                AlertDialog.Builder commentDialog = new AlertDialog.Builder(CommentsActivity.this);
                commentDialog.setTitle(HelpingMethods.parseDate(comment.getTime()) + " by " + comment.getAuthor());
                commentDialog.setMessage(comment.getText());
                commentDialog.setPositiveButton("Close",null);
                commentDialog.show();

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));


        // Load Comments
        new FetchComments().execute();

    }


    // Getting IDs in JSON Array Format from string
    private void loadCommentIDs(String kids) {
        try {
            commentIDs = new JSONArray(kids);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to Read Comments", Toast.LENGTH_SHORT).show();
        }
    }


    // Method to fetch Comments/Replies
    public class FetchComments extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            getComments(commentIDs,0);

            return null;
        }

        // Used as a separate method for nesting levels of replies
        private void getComments(JSONArray commentIDs, int level) {

            // Looping through this layer of replies
            for (int i=0;i<commentIDs.length();i++){

                try {
                    // Step 1: Prepare GET URL
                    int commentID = commentIDs.getInt(i);
                    String callURL = HelpingMethods.compileURLforFetchingItems(CommentsActivity.this, commentID);

                    // Step 2: Execute the HTTP Request on URL and store response in String Result
                    String result = HelpingMethods.makeHTTPCall(callURL);

                    // Step 3: Parse & Return the resulting Comment from the Result String
                    Comment comment = ParseJSON.parseComments(result);

                    if (comment != null) { // Deleted replies will return null

                        // Step 4: Set Comment reply level
                        comment.setLevel(level);

                        //Step 5: Add the fetched story to the list
                        commentsList.add(comment);
                        publishProgress();

                        // If this comment has further replies, load them
                        if(comment.getKids() != null && level +1 < maxLevel){
                            getComments(comment.getKids(), level + 1);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            mAdapter.notifyDataSetChanged();
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

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
                        loadComments();
                    }
                });
                settings.show();
                break;

            case R.id.menu_about:
                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setMessage(R.string.my_message);
                builder.setPositiveButton("Okay",null);
                builder.show();
                break;

            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
