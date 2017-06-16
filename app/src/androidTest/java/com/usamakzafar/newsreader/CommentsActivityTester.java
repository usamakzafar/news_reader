package com.usamakzafar.newsreader;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.usamakzafar.newsreader.adapters.CommentsViewHolder;
import com.usamakzafar.newsreader.listener.RecyclerItemClickListener;
import com.usamakzafar.newsreader.models.Comment;
import com.usamakzafar.newsreader.network.NetworkHandler;
import com.usamakzafar.newsreader.utils.ParseJSON;

import org.junit.Rule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.usamakzafar.newsreader.RecyclerViewMatcher.withRecyclerView;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by usamazafar on 05/06/2017.
 */

class CommentsActivityTester {

    private CommentsActivity activity;

    public CommentsActivityTester(ActivityTestRule<CommentsActivity> mActivityRule) {
        this.activity = mActivityRule.getActivity();
    }

    public void checkIfRecyclerViewIsPopulated(){
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview_comments);
        assertTrue(recyclerView.getChildCount() > 0);
    }

    public void checkIfRecyclerViewExists() {
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview_comments);
        assertNotNull(recyclerView);
    }

    public void checkifTheFirstChildIsPresent() {
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview_comments);
        final View item1 = recyclerView.getChildAt(0);
        assertNotNull(item1);
    }

    public void checkTheDataInTheFirstChild() {
        int childPosition = 0;
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview_comments);
        final View item1 = recyclerView.getChildAt(childPosition);
        assertNotNull(item1);

        RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(childPosition);

        onView(withRecyclerView(R.id.recyclerview_comments).atPosition(childPosition))
                .check(matches(hasDescendant(withText("Sample Comment"))));
    }

    public void checkIfActionBarExists() {
        assertNotNull(activity.getSupportActionBar());
    }

    public void checkIfActionbarShowsCorrectTitle() {
        String title = activity.getSupportActionBar().getTitle().toString();
        assertNotNull(title);
        assertEquals("Comments from Test Scenario", title);
    }


    public void listener() {
        RecyclerItemClickListener spylistener = spy(activity.getListener());
        RecyclerItemClickListener.OnItemClickListener spyOnitem = spy(spylistener.getmListener());
        activity.getListener().setmListener(spyOnitem);

        activity.setListener(spylistener);

        verify(spylistener, times(1)).onInterceptTouchEvent(any(RecyclerView.class),any(MotionEvent.class));
        verify(spyOnitem, times(1)).onItemClick(any(View.class),anyInt());



    }

    public void viewholder() {
        View itemView = LayoutInflater.from(activity.getApplicationContext())
                .inflate(R.layout.comment_list_item, null, false);

        CommentsViewHolder holder = new CommentsViewHolder(activity.getApplicationContext(),itemView);
        String commentJson= "{\"by\":\"wallnuss\",\"id\":14467718" +
                ",\"kids\":[14467781,14468256,14470479,14469443,14470448]" +
                ",\"parent\":14458955,\"text\":\"sample text\"" +
                ",\"time\":1496381915,\"type\":\"comment\"}";

        Comment comment = ParseJSON.parseComments(commentJson);
        comment.setLevel(0);
        holder.addCommentToView(comment,0);

        assertEquals(  comment.getAuthor() , holder.getAuthor().getText().toString() );
        assertEquals(  comment.getKids().length() + " replies"  , holder.getReplies().getText().toString() );
        assertEquals(  comment.getText()   , holder.getText().getText().toString() );

        String howLongAgo = (String) DateUtils.getRelativeTimeSpanString(
                comment.getTime().getTimeInMillis(),
                System.currentTimeMillis(),
                1);

        assertEquals(  howLongAgo   , holder.getTime().getText().toString());


    }
}
