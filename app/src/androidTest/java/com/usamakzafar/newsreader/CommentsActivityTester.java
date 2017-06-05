package com.usamakzafar.newsreader;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;

import com.usamakzafar.newsreader.network.NetworkHandler;

import org.junit.Rule;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

}
