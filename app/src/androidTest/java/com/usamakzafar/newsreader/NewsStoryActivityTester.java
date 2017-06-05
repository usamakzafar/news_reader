package com.usamakzafar.newsreader;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.usamakzafar.newsreader.adapters.NewsStoryAdapter;

import junit.framework.Assert;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.usamakzafar.newsreader.RecyclerViewMatcher.withRecyclerView;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by usamazafar on 05/06/2017.
 */

class NewsStoryActivityTester {

    private NewsActivity activity;
    private CommentsActivity commentsActivity;

    public NewsStoryActivityTester(ActivityTestRule<NewsActivity> mActivityRule) {
        this.activity = mActivityRule.getActivity();
    }

    public void checkIfRecyclerViewIsPopulated(){
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview);
        assertTrue(recyclerView.getChildCount() > 0);
    }

    public void checkIfRecyclerViewExists() {
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview);
        assertNotNull(recyclerView);
    }

    public void checkIfRowZeroExists() {
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview);
        final View item1 = recyclerView.getChildAt(0);
        assertNotNull(item1);
    }

    public void clickRowZeroAndVerifyCommentsActivityStarted() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(CommentsActivity.class.getName(), null, false);

        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        instrumentation.waitForIdleSync();
        Activity nextActivity = instrumentation.waitForMonitorWithTimeout(monitor, 5000);

        // Check If new activity is invoked
        assertNotNull(nextActivity);

        // Check if the activity is an instance of CommentsActivity
        assertTrue(nextActivity instanceof CommentsActivity);

        commentsActivity = (CommentsActivity) nextActivity;
    }

    public void verifyIfCorrectValuesWerePassedToTheNewActivity() {
        // Get Title from the new activity and assert
        String title = commentsActivity.getIntent().getStringExtra("title");

        assertEquals("Title 1",title);
    }

    public void checkIfActionBarExists() {
        assertNotNull(activity.getSupportActionBar());
    }

    public void checkIfActionbarShowsCorrectTitle() {
        String title = activity.getSupportActionBar().getTitle().toString();
        assertNotNull(title);
        assertEquals("News Reader", title);
    }

    public void checkScrollingToBottom() {
        int bottomPos= 19;
        onView(withId(R.id.recyclerview)).perform(scrollToPosition(bottomPos));
        onView(withId(R.id.recyclerview)).perform(swipeUp());
    }

    public void checkIfMoreLoaded() {
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerview);
        int loadedCount = recyclerView.getAdapter().getItemCount();

        assertTrue(loadedCount > 20);
    }
}
