package com.usamakzafar.newsreader.robolectric.comments_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.usamakzafar.newsreader.CommentsActivity;
import com.usamakzafar.newsreader.R;
import com.usamakzafar.newsreader.adapters.CommentsViewHolder;
import com.usamakzafar.newsreader.models.Comment;
import com.usamakzafar.newsreader.utils.HelpingMethods;
import com.usamakzafar.newsreader.utils.ParseJSON;

import org.json.JSONException;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by usamazafar on 20/06/2017.
 */

public class CommentsActivityTester {

    private CommentsActivity activity;
    private Intent intent;
    private RecyclerView recyclerView;

    public CommentsActivityTester(CommentsActivity _activity) {
        this.activity = _activity;
    }

    public void setUpMockNetworkCall() {
        HelpingMethods mockCall = mock(HelpingMethods.class);

        HelpingMethods.mocked = true;
        try {
            when(mockCall.makeHTTPCallForObject(anyString())).thenReturn("{\"by\":\"wallnuss\",\"id\":14467718,\"kids\":[14467781,14468256,14470479,14470448,14469443],\"parent\":14458955,\"text\":\"The only sad part of this story is the design was meant to celebrate John Conway and his Game of Life [1] (Conway actually was a lecturer at Cambridge, when he introduced GoL).<p>[1] From 2014: <a href=\\\"http:&#x2F;&#x2F;www.railwaygazette.com&#x2F;news&#x2F;infrastructure&#x2F;single-view&#x2F;view&#x2F;cambridge-science-park-station-to-incorporate-game-of-life.html\\\" rel=\\\"nofollow\\\">http:&#x2F;&#x2F;www.railwaygazette.com&#x2F;news&#x2F;infrastructure&#x2F;single-vie...</a>\",\"time\":1496381915,\"type\":\"comment\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        activity.getCommentsNetworkCalls().setHttpCall(mockCall);
        recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview_comments);
    }

    public void checkIntentNotNull() {
        intent = activity.getIntent();
        assertNotNull(intent);
    }

    public void checkTitlePassedInIntent() {
        String title = intent.getStringExtra("title");
        assertNotNull(title);
        assertEquals("Title 1",title);
    }

    public void checkChildrenPassedInIntent() {
        String kids = intent.getStringExtra("kids");
        assertNotNull(kids);
        assertEquals("[1,2,3,4]",kids);
    }

    public void checkActionBarNotNull() {
        ActionBar actionBar = activity.getSupportActionBar();
        assertNotNull(actionBar);
    }

    public void checkActionBarTitle() {
        ActionBar actionBar = activity.getSupportActionBar();
        assertNotNull(actionBar);

        String title = actionBar.getTitle().toString();
        assertNotNull(title);
        assertEquals("Comments from Title 1",title);

    }

    public void testCommentsViewHolder() throws JSONException {

        View itemView = LayoutInflater.from(activity)
                .inflate(R.layout.comment_list_item, null, false);
        CommentsViewHolder holder = new CommentsViewHolder(activity, itemView);

        Comment comment = getDummyComment();

        holder.addCommentToView(comment);

        assertNotNull(holder);

        assertNotNull(holder.getAuthor());
        assertEquals("wallnuss",holder.getAuthor().getText().toString());

        assertNotNull(holder.getReplies());
        assertEquals("5 replies",holder.getReplies().getText().toString());

        assertNotNull(holder.getText());
        assertEquals("Hello this is a sample comment",holder.getText().getText().toString());

    }



    public Comment getDummyComment() throws JSONException {
        return ParseJSON.parseComments("{\"by\":\"wallnuss\",\"id\":14467718,\"kids\":[14467781,14468256,14470479,14470448,14469443],\"parent\":14458955,\"text\":\"Hello this is a sample comment\",\"time\":1496381915,\"type\":\"comment\"}");
    }
}
