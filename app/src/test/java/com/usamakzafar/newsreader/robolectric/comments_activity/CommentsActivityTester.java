package com.usamakzafar.newsreader.robolectric.comments_activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.usamakzafar.newsreader.CommentsActivity;
import com.usamakzafar.newsreader.NewsActivity;
import com.usamakzafar.newsreader.R;
import com.usamakzafar.newsreader.adapters.CommentsAdapter;
import com.usamakzafar.newsreader.adapters.CommentsViewHolder;
import com.usamakzafar.newsreader.models.Comment;
import com.usamakzafar.newsreader.utils.HelpingMethods;
import com.usamakzafar.newsreader.utils.ParseJSON;

import org.json.JSONException;
import org.junit.Assert;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.fakes.RoboMenu;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowPopupMenu;
import org.robolectric.shadows.ShadowToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

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


    public void checkIfAdapterIsWorkingCorrectly() throws JSONException {
        List<Comment> commentsList = new ArrayList<>();
        commentsList.add(getDummyComment());

        CommentsAdapter adapter = new CommentsAdapter(activity,commentsList);

        Assert.assertNotNull(adapter);
        Assert.assertEquals(1, adapter.getItemCount());

        View itemView = LayoutInflater.from(activity)
                .inflate(R.layout.comment_list_item, null, false);
        CommentsViewHolder holder = new CommentsViewHolder(activity,itemView);

        adapter.onBindViewHolder(holder,0);

        Assert.assertNotNull(holder.getAuthor());
        Assert.assertEquals("wallnuss",holder.getAuthor().getText().toString());

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

    public void testOnClickItem() throws JSONException {

        recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview_comments);
        recyclerView.measure(0, 0);
        recyclerView.layout(0, 0, 100, 10000);

        assertNotNull(recyclerView.getChildAt(0));
        recyclerView.getChildAt(0).performClick();

    }


    public Comment getDummyComment() throws JSONException {
        return ParseJSON.parseComments("{\"by\":\"wallnuss\",\"id\":14467718,\"kids\":[14467781,14468256,14470479,14470448,14469443],\"parent\":14458955,\"text\":\"Hello this is a sample comment\",\"time\":1496381915,\"type\":\"comment\"}");
    }

    public void checkRepliesDialog() {
        MenuItem menuItem = new RoboMenuItem(R.id.reply_level);
        activity.onOptionsItemSelected(menuItem);

        AlertDialog alert = activity.buildAlertForSettingReplyLevel().create();

        assertNotNull(alert);

        alert.show();

        assertTrue(alert.isShowing());
    }

    public void checkAboutDialog(){
        MenuItem menuItem = new RoboMenuItem(R.id.menu_about);
        activity.onOptionsItemSelected(menuItem);

        AlertDialog alert = HelpingMethods.buildAlertForAbout(activity).create();

        assertNotNull(alert);

        alert.show();

        assertTrue(alert.isShowing());
    }

    public void testCommentAlert() throws JSONException {
        AlertDialog alert = HelpingMethods.buildAlertForComment(activity,getDummyComment()).create();

        assertNotNull(alert);

        alert.show();

        assertTrue(alert.isShowing());
    }

    public void testBackButton() {
        activity.onBackPressed();
        ActionBar actionBar = activity.getSupportActionBar();
        ShadowActivity activityShadow = shadowOf(activity);
        assertTrue(activityShadow.isFinishing());
    }

    public void testMenuInflation() {
        Menu menu = new RoboMenu();
        activity.onCreateOptionsMenu(menu);

        assertNotNull(menu.findItem(R.id.menu_about));
        assertNotNull(menu.findItem(R.id.reply_level));
    }

    public void testCommentLoadingError() {
        activity.loadCommentIDs("invalid kids json string");
        assertEquals("Unable to Read Comment IDs", ShadowToast.getTextOfLatestToast());
    }
}
