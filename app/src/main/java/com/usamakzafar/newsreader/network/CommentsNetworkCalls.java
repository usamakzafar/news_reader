package com.usamakzafar.newsreader.network;

import android.content.Context;
import android.os.AsyncTask;

import com.usamakzafar.newsreader.R;
import com.usamakzafar.newsreader.models.Comment;
import com.usamakzafar.newsreader.utils.HelpingMethods;
import com.usamakzafar.newsreader.utils.ParseJSON;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import static com.usamakzafar.newsreader.utils.HelpingMethods.haveNetworkConnection;

/**
 * Created by usamazafar on 04/06/2017.
 */

public class CommentsNetworkCalls {

    private Context context;
    private JSONArray commentIDs;
    private int maxLevel;

    // For testing Purposes
    public CommentsUpdatedListener getListener() {
        return listener;
    }

    private CommentsUpdatedListener listener;
    private commentsFetcher fetcher;

    public HelpingMethods getHttpCall() {
        return httpCall;
    }

    public void setHttpCall(HelpingMethods httpCall) {
        this.httpCall = httpCall;
    }

    private HelpingMethods httpCall;

    public CommentsNetworkCalls(Context c, CommentsUpdatedListener commentsUpdatedListener){
        context = c;
        listener = commentsUpdatedListener;

        httpCall = new HelpingMethods();

    }

    public boolean execute(JSONArray _commentIDs, int maxLevelOfReplies){
        if (!haveNetworkConnection(context)) {
            HelpingMethods.showMessage(context,context.getString(R.string.no_internet_connection_message));
            return false;
        }
        else {
            commentIDs = _commentIDs;
            maxLevel = maxLevelOfReplies;

            fetcher = new commentsFetcher();
            fetcher.execute();
            return true;
        }
    }

    // Method to fetch Comments/Replies
    private class commentsFetcher extends AsyncTask<Void,Comment,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            getComments(commentIDs,0);

            return null;
        }

        // Used as a separate method for nesting levels of replies
        private void getComments(JSONArray commentIDs, int level) {

            // Looping through this layer of replies
            for (int i=0;i<commentIDs.length();i++){

                if(!isCancelled()) {

                    try {
                        // Step 1: Prepare GET URL
                        int commentID = commentIDs.getInt(i);
                        String callURL = HelpingMethods.compileURLforFetchingItems(context, commentID);

                        // Step 2: Execute the HTTP Request on URL and store response in String Result
                        String result = httpCall.makeHTTPCallForObject(callURL);

                        // Step 3: Parse & Return the resulting Comment from the Result String
                        Comment comment = ParseJSON.parseComments(result);

                        if (comment != null) { // Deleted replies will return null

                            // Step 4: Set Comment reply level
                            comment.setLevel(level);

                            //Step 5: Return the fetched story to the interface
                            publishProgress(comment);

                            // If this comment has further replies, load them
                            if (comment.getKids() != null && level + 1 < maxLevel)
                                getComments(comment.getKids(), level + 1);

                        }

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

        }

        @Override
        protected void onProgressUpdate(Comment... values) {
            super.onProgressUpdate(values);
            listener.onProgressUpdated(values[0]);
        }
    }

    public interface CommentsUpdatedListener {
        void onProgressUpdated(Comment comment);
    }

    public void finishIt(){
        if(fetcher!= null)
            this.fetcher.cancel(true);
    }
}
