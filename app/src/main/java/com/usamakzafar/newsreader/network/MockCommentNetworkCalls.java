package com.usamakzafar.newsreader.network;

import android.content.Context;

import com.usamakzafar.newsreader.models.Comment;

import org.json.JSONArray;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by usamazafar on 04/06/2017.
 */

class MockCommentNetworkCalls extends CommentsNetworkCalls {

    public MockCommentNetworkCalls(Context c, CommentsUpdatedListener listener) {
        super(c,listener);
    }


    @Override
    public boolean execute(JSONArray _commentIDs, int maxLevelOfReplies) {
        Comment c = new Comment();
        c.setId(1);
        c.setText("Sample Comment");
        c.setLevel(0);
        c.setAuthor("ihsanulhaq89");
        c.setType("comment");
        c.setTime(Calendar.getInstance());

        getListener().onProgressUpdated(c);
        return true;
    }
}
